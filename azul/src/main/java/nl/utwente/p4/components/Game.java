package nl.utwente.p4.components;

import lombok.Getter;
import lombok.Setter;
import nl.utwente.p4.constants.TileType;
import nl.utwente.p4.ui.GameView;

import java.util.ArrayList;

@Getter @Setter
public class Game {
    private boolean tileLineIsExternal;
    private Factory currSelectedFactory;
    private Tile currSelectedTile;

    private TileBag tileBag;
    private GameBoxLid gameBoxLid;
    private TileTable tileTable;
    private ArrayList<Factory> factories;
    private ArrayList<Player> players;
    private static Game instance;
    private int numOfPlayers;
    private int currPlayerIdx;
    static final int tilesPerFactory = 4;

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
     *
     * @return Player whose turn it is, null if there are no players
     */
    public Player getCurrentPlayer() {
        if (this.players.size() < 1) return null;
        return this.players.get(this.currPlayerIdx);
    }

    public void play(int numOfPlayers, boolean tileLineIsExternal) {
        initializePlay(numOfPlayers, tileLineIsExternal);
        GameView.getInstance();
    }

    public void initializePlay(int numOfPlayers, boolean tileLineIsExternal) {
        this.numOfPlayers = numOfPlayers;
        this.currPlayerIdx = 0;
        this.tileLineIsExternal = tileLineIsExternal;
        startGame();
    }

    public void nextPlayer() {
        boolean isAllFactoriesEmpty = true;
        for (Factory factory : factories) {
            if (!factory.getTiles().isEmpty()) {
                isAllFactoriesEmpty = false;
                break;
            }
        }

        if (isAllFactoriesEmpty && tileTable.getTiles().isEmpty()) {
            wallTiling();
            return;
        }

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
     *
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
     *
     * @return number of factories
     */
    public int numOfFactories() {
        return 2 * this.numOfPlayers + 1;
    }

    /**
     * Initialize array of factories for the game, containing random tiles from tilebag
     *
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

    /**
     * Perform wall-tiling phase in both backend and frontend
     */
    public void wallTiling() {
        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i);
            int score = player.getWall().addFromPatterLineToWall(player.getPatternLine(), player.getFloorLine().getTotalFloorScore());
            player.setScoreTrack(score);
            boolean firstPlayerFound = player.getFloorLine().clearFloorLine();
            if (firstPlayerFound) {
                currPlayerIdx = i;
            }

            GameView.getInstance().getBoardViews().get(i).updateWallTiling(player);
        }

        if (hasAnyPlayerFilledRow()) {
            endGame();
        } else {
            prepareNextRound();
        }
    }

    public void prepareNextRound() {
        resetComponentsForNextRound();
        GameView.getInstance().prepareNextRound();
    }

    public void resetComponentsForNextRound() {
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
        tileTable.reset();
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

    public void endGame() {
        GameView.getInstance().endGame(getWinningPlayer());
    }

    public boolean hasAnyPlayerFilledRow() {
        for (Player player : players) {
            if (player.hasFilledRow()) {
                return true;
            }
        }
        return false;
    }

    public int getWinningPlayer() {
        int winningPlayerIdx = 0;

        for (int i = 1; i < players.size(); i++) {
            Player winningPlayer = players.get(winningPlayerIdx);
            Player player = players.get(i);

            int score = player.calculateFinalScore();
            int completeLines = player.completeHorizontalLines();

            if (score > winningPlayer.getScoreTrack()) {
                winningPlayerIdx = i;
            } else if (score == winningPlayer.getScoreTrack() && completeLines > winningPlayer.completeHorizontalLines()) {
                winningPlayerIdx = i;
            }
        }

        return winningPlayerIdx;
    }

    public boolean tileLineIsExternal() {
        return tileLineIsExternal;
    }
}
