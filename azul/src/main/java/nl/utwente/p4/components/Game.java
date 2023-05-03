package nl.utwente.p4.components;

import java.util.ArrayList;

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

    // TODO: implement method
    public void startGame() {}

    // TODO: implement method
    public void getFactoryOfferFromFactory() {}

    // TODO: implement method
    public void getFactoryOfferFromTileTable() {}

    // TODO: implement method
    public void wallTiling() {}

    // TODO: implement method
    public void prepareNextRound() {}

    // TODO: implement method
    public void endGame() {}
}
