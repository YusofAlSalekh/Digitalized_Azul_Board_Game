package nl.utwente.p4.components;

import lombok.Data;
import nl.utwente.p4.constants.TileType;
import nl.utwente.p4.ui.GameView;

import java.util.ArrayList;

@Data
public class Game {
    private Factory currSelectedFactory;
    private Tile currSelectedFactoryTile;
    private Tile currSelectedTableTile;

    private TileBag tileBag;
    private GameBoxLid gameBoxLid;
    private TileTable tileTable;
    private ArrayList<Factory> factories;
    private ArrayList<Player> players;
    private static Game instance;
    private int numOfPlayers;
    private int currPlayerIdx;
    static final int tilesPerFactory = 4;

    private int highestScore = -1;

    private Game() {
        this.tileBag = new TileBag();
        this.gameBoxLid = new GameBoxLid();
        this.tileTable = new TileTable();
        this.factories = new ArrayList<>();
        this.players = new ArrayList<>();
        this.currPlayerIdx = 0;
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

    /**
     * Return the player whose turn it is. (Current player)
     * @return Player whose turn it is, null if there are no players
     */
    public Player getCurrentPlayer() {
        if (this.players.size() < 1) return null;
        return this.players.get(this.currPlayerIdx);
    }

    // TODO: combine game logic with GUI
    public void play(int numOfPlayers) {
        this.numOfPlayers = numOfPlayers;
        startGame();

        this.currPlayerIdx = 0;

        GameView.getInstance();

//        while (true) {
//            factoryOffer(currPlayerIdx);
//            wallTiling();
//
//            if (hasAnyPlayerFilledRow()) {
//                break;
//            }
//            prepareNextRound();
//        }
//        endGame();
    }

    public void nextPlayer() {
        if (this.currPlayerIdx + 1 == this.players.size()) {
            this.currPlayerIdx = 0;
        } else {
            this.currPlayerIdx += 1;
        }
    }

    public void startGame() {
        // Create players
        for (int i = 0; i < numOfPlayers; i++) {
            this.players.add(new Player());
        }

        this.tileBag.addTiles(createStartingTiles());

        ArrayList<Factory> initialFactories = createStartingFactories();
        this.factories.addAll(initialFactories);
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
     * Calculate number of factories based on number of players
     * @return number of factories
     */
    public int numOfFactories() {
        return 2 * this.numOfPlayers + 1;
    }

    /**
     * Initialize array of factories for the game, containing random tiles from tilebag
     * @return factory array
     */
    public ArrayList<Factory> createStartingFactories() {
        ArrayList<Factory> initialFactories = new ArrayList<>();
        for (int i = 0; i < numOfFactories(); i++) {
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

        this.currPlayerIdx = 0;
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

    // TODO: combine method with GUI
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
    
