package nl.utwente.p4.components;

import java.util.ArrayList;

public class Factory {
    private ArrayList<Tile> tiles;

    public ArrayList<Tile> getTiles() {
        return tiles;
    }

    public void setTiles(ArrayList<Tile> tiles) {
        this.tiles = tiles;
    }

    public Factory() {
        this.tiles = new ArrayList<>();
    }

    // TODO: implement method
    public ArrayList<Tile> takeTiles(Tile tile) { return null; }

    // TODO: implement method
    public void addTile(Tile tile) { }

    // TODO: implement method
    public ArrayList<Tile> getRemainingTiles() { return null; }
}
