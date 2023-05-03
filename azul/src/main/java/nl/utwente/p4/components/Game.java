package nl.utwente.p4.components;

import nl.utwente.p4.constants.TileType;

import java.util.ArrayList;
import java.util.Arrays;

public class Game {
    private TileBag tileBag;
    private TileTable tileTable;
    private ArrayList<Factory> factories;
    private ArrayList<Player> players;

    public Game() {
        this.tileBag = new TileBag();
        this.tileTable = new TileTable();
        this.factories = new ArrayList<>();
        this.players = new ArrayList<>();
    }

    private static final int numOfPlayers = 2;

    // TODO: add overall game logic here
    public void play(){
        startGame();
    }

    public void startGame() {
        // Create players
        for (int i = 0; i < numOfPlayers; i++) {
            this.players.add(new Player());
        }

        // Create tile bag
        ArrayList<TileType> tileTypes = new ArrayList<>(
                Arrays.asList(TileType.RED, TileType.BLUE, TileType.BLACK, TileType.GREEN, TileType.YELLOW));
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

        // TODO: used for testing, remove before submission
        /* System.out.println("printing tile bag");
        for (Tile t : this.tileBag.getTiles()) {
            System.out.print(t.getType() + ", ");
        }
        System.out.println("\nprinting factories");
        for (Factory f : this.factories) {
            System.out.println("factory:");
            for (Tile t : f.getTiles()) {
                System.out.print(t.getType() + ", ");
            }
            System.out.println();
        } */
    }

    // TODO: implement method
    public void getFactoryOfferFromFactory() {}


    // TODO: implement method
    public void wallTiling() {}

    // TODO: implement method
    public void prepareNextRound() {}

    // TODO: implement method
    public void endGame() {}
}
