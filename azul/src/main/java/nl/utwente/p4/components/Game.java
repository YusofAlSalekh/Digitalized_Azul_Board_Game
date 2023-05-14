package nl.utwente.p4.components;

import lombok.Data;
import nl.utwente.p4.constants.TileType;

import java.util.ArrayList;
import java.util.Arrays;

@Data
public class Game {
    private TileBag tileBag;
    private TileBag gameBoxLid;
    private TileTable tileTable;
    private ArrayList<Factory> factories;
    private ArrayList<Player> players;

    private static Game instance;

    private Game() {
        this.tileBag = new TileBag();
        this.gameBoxLid = new TileBag();
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

    private static final int numOfPlayers = 2;

    // TODO: add overall game logic here
    public void play() {
        startGame();
        prepareNextRound();
    }

    public void startGame() {
        // Create players
        for (int i = 0; i < numOfPlayers; i++) {
            this.players.add(new Player());
        }

        // Create tile bag
        ArrayList<TileType> tileTypes = new ArrayList<>(
                Arrays.asList(TileType.RED, TileType.BLUE, TileType.BLACK, TileType.WHITE, TileType.YELLOW));
        for (TileType type : tileTypes) {
            ArrayList<Tile> initialBagTiles = new ArrayList<>();
            for (int i = 0; i < 20; i++) {
                initialBagTiles.add(new Tile(type));
            }
            this.tileBag.addTiles(initialBagTiles);
        }

        // Create factories
        int numOfFactories = 2 * numOfPlayers + 1;
        for (int i = 0; i < numOfFactories; i++) {
            ArrayList<Tile> initialFactoryTiles = new ArrayList<>();
            for (int j = 0; j < 4; j++) {
                initialFactoryTiles.add(this.tileBag.getRandomTile());
            }
            this.factories.add(new Factory(initialFactoryTiles));
        }

        // TODO: this is used for testing, remove before submission
        tempPrintTileBag();
        tempPrintFactories();

        // TODO: this is used for testing, remove before submission
        tempTestTakeTileFromTable();
    }

    /***
     * Method to Pick all tiles of the same color from any one Factory,
     * then move the remaining tiles from this Factory to the center of the table
     * and afterwards add the tiles player picked to one of the 5 pattern lines on player board
     *
     * @param factoryIdx id factory from which player take tiles
     * @param color the colour(type) of tiles that player take
     * @param player the player who takes tiles
     * @param row the row in which player put tiles
     */
    public void pickTilesFromFactory(int factoryIdx, TileType color, Player player, int row) {

        ArrayList<Tile> pickedTiles = factories.get(factoryIdx).takeTiles(color);

        factories.get(factoryIdx).getRemainingTiles().forEach(t -> tileTable.addTile(t));

        ArrayList<Tile> excess = player.getBoard().getPatternLine().addTiles(pickedTiles, row, player.getBoard().getWall());

        excess.forEach(t -> player.getBoard().getFloorLine().addTile(t));
    }

    // TODO: implement method
    public void wallTiling() {
    }

    public void prepareNextRound() {
        for (Factory f : this.factories) {
            for (int i = 0; i < 4; i++) {
                Tile newTile = this.tileBag.getRandomTile();
                if (newTile != null) {
                    f.addTile(newTile);
                }
            }
        }
        this.tileTable.setFirstHasBeenTaken(false);

        // TODO: this is used for testing, remove before submission
        tempPrintFactories();
    }

    // TODO: implement method
    public void endGame() {
        boolean hasGameEnded = false;
        
        for (Player p : players) {
            if (p.hasFilledRow()) {
                hasGameEnded = true;
                break;
            }
        }

        if (!hasGameEnded) {
            return;
        }

        int highestScore = -99999;
        for (Player p : players) {
            int score = p.calculateFinalScore();

            // check if this score was highest
            if (score > highestScore) {
                highestScore = score;
            }
        }

    }

    // TODO: this is used for testing, remove before submission
    public void tempPrintTileBag() {
        System.out.println("printing tile bag");
        for (Tile t : this.tileBag.getTiles()) {
            System.out.print(t.getType() + ", ");
        }
    }

    // TODO: this is used for testing, remove before submission
    public void tempPrintFactories() {
        System.out.println("\nprinting factories");
        for (Factory f : this.factories) {
            System.out.println("factory:");
            for (Tile t : f.getTiles()) {
                System.out.print(t.getType() + ", ");
            }
            System.out.println();
        }
    }

    // TODO: this is used for testing, remove before submission
    public void tempTestTakeTileFromTable() {
        // Test player 1 taking tiles from the table
        // Add tiles for testing so the table should contain the tiles below
        tileTable.addTile(new Tile(TileType.WHITE));
        tileTable.addTile(new Tile(TileType.WHITE));
        tileTable.addTile(new Tile(TileType.YELLOW));
        tileTable.addTile(new Tile(TileType.BLUE));
        tileTable.addTile(new Tile(TileType.RED));
        System.out.println("Table contains tiles: ");
        for (int i = 0; i < tileTable.getTiles().size(); i++) {
            System.out.println("Tile with index " + i + " and type " + tileTable.getTiles().get(i).getType() + " found!");
        }

        // Next player 1 tries to get green type tiles and insert into first line with length 1.
        // The other green tiles should go to the players floorline with the first player tile
        Player player1 = players.get(0);
        player1.getFactoryOfferFromTileTable(tileTable, tileTable.getTiles().get(1), 0);

        System.out.println("Checking first player pattern lines");
        for (int i = 0; i < player1.getBoard().getPatternLine().getTileLines().size(); i++) {
            TileLine tileLine = player1.getBoard().getPatternLine().getTileLines().get(i);
            System.out.println("Found tile line with size " + tileLine.getLineSize() + " and filled with " + tileLine.getTiles().size() + " tiles!");
            ArrayList<Tile> tiles = tileLine.getTiles();
            System.out.println("Tile line " + i + " includes following tiles:");
            for (int j = 0; j < tiles.size(); j++) {
                System.out.println("Tile at index " + j + " with type " + tiles.get(j).getType() + " found!");

            }
            System.out.println("");
        }

        ArrayList<Tile> floorTiles = player1.getBoard().getFloorLine().getTiles();
        System.out.println("Checking first player floor line, which contains " + floorTiles.size() + " tiles");
        for (int i = 0; i < floorTiles.size(); i++) {
            System.out.println("Floor tile index " + i + " with type " + floorTiles.get(i).getType());
        }
    }
}
    
