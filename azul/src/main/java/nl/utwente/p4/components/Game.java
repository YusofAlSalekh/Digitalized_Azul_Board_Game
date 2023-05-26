package nl.utwente.p4.components;

import lombok.Data;
import nl.utwente.p4.constants.TileType;

import java.util.ArrayList;
import java.util.Arrays;

@Data
public class Game {
    private TileBag tileBag;
    private GameBoxLid gameBoxLid;
    private TileTable tileTable;
    private ArrayList<Factory> factories;
    private ArrayList<Player> players;
    private static Game instance;
    private int numOfPlayers;
    static final int tilesPerFactory = 4;

    private int highestScore = -1;

    private Game() {
        this.tileBag = new TileBag();
        this.gameBoxLid = new GameBoxLid();
        this.tileTable = new TileTable();
        this.factories = new ArrayList<>();
        this.players = new ArrayList<>();
    }

    public static Game getInstance() {
        if (instance == null) {
            instance = new Game();
        }
        return instance;
    }

    public ArrayList<Tile> getTilesFromGameBoxLid() {
        return this.gameBoxLid.getAndRemoveTiles();
    }

    public void addTilesToGameBoxLid(ArrayList<Tile> tiles) {
        this.gameBoxLid.addTiles(tiles);
    }

    // TODO: add overall game logic here
    public void play(int numOfPlayers) {
        this.numOfPlayers = numOfPlayers;
        startGame();
        prepareNextRound();
    }

    public void startGame() {
        // Create players
        for (int i = 0; i < numOfPlayers; i++) {
            this.players.add(new Player());
        }

        this.tileBag.addTiles(createStartingTiles());

        ArrayList<Factory> initialFactories = createStartingFactories();
        for (Factory f:initialFactories) this.factories.add(f);

    }

    /***
     * Method to Pick all tiles of the same color from any one Factory,
     * then move the remaining tiles from this Factory to the center of the table
     * and afterwards add given tiles to the player board pattern line.
     * Excess tiles are added to the players Floorline or the BoxLid
     *
     * @param factoryIdx id factory from which player take tiles
     * @param color the colour(type) of tiles that player take
     * @param player the player who takes tiles
     * @param row the row in which player put tiles
     */
    public void pickTilesFromFactory(int factoryIdx, TileType color, Player player, int row) {

        ArrayList<Tile> pickedTiles = factories.get(factoryIdx).takeTiles(color);

        factories.get(factoryIdx).getRemainingTiles().forEach(t -> tileTable.addTile(t));

        player.addTiles(pickedTiles, row);
    }

    /**
     * Initialize a tile array containing 20 tiles of each type
     * @return tile array
     */
    public ArrayList<Tile> createStartingTiles() {
        ArrayList<Tile> tiles = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            tiles.add(new Tile(TileType.RED));
            tiles.add(new Tile(TileType.BLUE));
            tiles.add(new Tile(TileType.BLACK));
            tiles.add(new Tile(TileType.WHITE));
            tiles.add(new Tile(TileType.YELLOW));
        }
        return tiles;
    }

    /**
     * Initialize array of factories for the game, containing random tiles from tilebag
     * @return factory array
     */
    public ArrayList<Factory> createStartingFactories() {
        ArrayList<Factory> initialFactories = new ArrayList<>();
        int numOfFactories = 2 * this.numOfPlayers + 1;
        for (int i = 0; i < numOfFactories; i++) {
            ArrayList<Tile> initialFactoryTiles = new ArrayList<>();
            for (int j = 0; j < tilesPerFactory; j++) {
                initialFactoryTiles.add(this.tileBag.getRandomTile());
            }
            initialFactories.add(new Factory(initialFactoryTiles));
        }
        return initialFactories;
    }

    // TODO: implement method
    public void wallTiling() {
    }


    public void prepareNextRound() {
        for (Factory f : this.factories) {
            boolean noMoreTiles = false;
            for (int i = 0; i < 4; i++) {
                if (!addRandomTileToFactory(f)) {
                    noMoreTiles = true;
                    break;
                }
            }
            // just in case it does not break in the first loop
            if (noMoreTiles) {
                break;
            }
        }
        resetFirstState();
    }

    private boolean addRandomTileToFactory(Factory factory) {
        Tile newTile = this.tileBag.getRandomTile();
        if (newTile != null) {
            factory.addTile(newTile);
            return true;
        } else {
            fillTileBagFromGameBoxLid();
            newTile = this.tileBag.getRandomTile();
            if (newTile != null) {
                factory.addTile(newTile);
                return true;
            }
        }
        return false;
    }

    private void fillTileBagFromGameBoxLid() {
        ArrayList<Tile> gameBoxLidTiles = this.gameBoxLid.getAndRemoveTiles();
        this.tileBag.addTiles(gameBoxLidTiles);
    }

    private void resetFirstState() {
        this.tileTable.setFirstHasBeenTaken(false);
    }

    // TODO: implement method
    public boolean endGame() {
        if (!hasAnyPlayerFilledRow()) {
            return false;
        }

        calculateHighestScore();
        return true;
    }

    private boolean hasAnyPlayerFilledRow() {
        for (Player player : players) {
            if (player.hasFilledRow()) {
                return true;
            }
        }
        return false;
    }

    private int calculateHighestScore() {
        for (Player player : players) {
            int score = player.calculateFinalScore();
            if (score > getHighestScore()) {
                setHighestScore(score);
            }
        }
        return getHighestScore();
    }
}
    
