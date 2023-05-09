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

    public Factory(ArrayList<Tile> tiles) {
        this.tiles = tiles;
    }

    // TODO: implement method
    public ArrayList<Tile> takeTiles(Tile tile) {
        var taken = tiles.stream().filter(t -> t.getType() == tile.getType()).toList();

        tiles.removeIf(t -> t.getType() == tile.getType());

        return new ArrayList<>(taken);
    }

    public void addTile(Tile tile) {
        this.tiles.add(tile);
    }

    // TODO: implement method
    public ArrayList<Tile> getRemainingTiles() { return tiles; }
    // returns the unselected tiles on a factory
}
