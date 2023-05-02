package nl.utwente.p4.components;

import java.util.ArrayList;

public class TileTable {
    private ArrayList<Tile> tiles;
    private boolean hasBeenTaken;

    public ArrayList<Tile> getTiles() {
        return tiles;
    }

    public void setTiles(ArrayList<Tile> tiles) {
        this.tiles = tiles;
    }

    public boolean isHasBeenTaken() {
        return hasBeenTaken;
    }

    public void setHasBeenTaken(boolean hasBeenTaken) {
        this.hasBeenTaken = hasBeenTaken;
    }

    // TODO: implement method
    public ArrayList<Tile> takeTiles(Tile tile) { return null; }

    // TODO: implement method
    public void addTile(Tile tile) { }

    // TODO: implement method
    public boolean isFirstPlayerToTake() { return false; }

    // TODO: implement method
    public boolean isEmpty() { return false; }
}
