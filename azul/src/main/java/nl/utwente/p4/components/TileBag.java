package nl.utwente.p4.components;

import java.util.ArrayList;

public class TileBag {
    private ArrayList<Tile> tiles;

    public ArrayList<Tile> getTiles() {
        return tiles;
    }

    public void setTiles(ArrayList<Tile> tiles) {
        this.tiles = tiles;
    }

    public TileBag() {
        this.tiles = new ArrayList<>();
    }

    // TODO: implement method
    public Tile getRandomTile() {
        return null;
    }

    // TODO: implement method
    public void addTiles(ArrayList<Tile> tiles) { }

    // TODO: implement method
    public boolean isEmpty() { return false; }
}
