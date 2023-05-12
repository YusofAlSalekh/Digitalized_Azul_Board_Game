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
    public void play(){
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

    // TODO: implement method
    public void getFactoryOfferFromFactory() {}

    // TODO: implement method
    public void wallTiling() {}

    public void prepareNextRound() {
        for (Factory f  : this.factories) {
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
    public void endGame() {}

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

<<<<<<< HEAD
    // TODO: implement method
    public void getFactoryOfferFromFactory() {}
=======
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
        for (int i = 0; i<tileTable.getTiles().size(); i++) {
            System.out.println("Tile with index " + i + " and type " + tileTable.getTiles().get(i).getType() + " found!");
        }
>>>>>>> main


    // TODO: implement method
    public void wallTiling() {}

    // TODO: implement method
    public void prepareNextRound() {}

    // TODO: implement method
<<<<<<< HEAD
    public void endGame() {
        boolean hasGameEnded = false;
        
        // TODO: iterate through players arraylist to check if anyone has a row filled out
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
        // TODO: call the calcuate score function for each player & check player with the highest score
        for (Player p : players) {
            int score = p.calculateScore();

            // check if this score was highest
            if (score > highestScore) {
                highestScore = score;
            }
           // ArrayList<Tile> floorTiles = player1.getBoard().getFloorLine().getTiles();
            // System.out.println("Checking first player floor line, which contains " + floorTiles.size() + " tiles");
            //for (int i=0; i<floorTiles.size(); i++) {
                //System.out.println("Floor tile index " + i + " with type " + floorTiles.get(i).getType());
        }

    }
=======
    public void endGame() {}
>>>>>>> main
}

    