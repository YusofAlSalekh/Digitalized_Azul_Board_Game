package nl.utwente.p4.components;

import java.util.ArrayList;

public class PatternLine {
    private ArrayList<ArrayList<Tile>> tiles;

    public ArrayList<ArrayList<Tile>> getTiles() {
        return tiles;
    }

    public void setTiles(ArrayList<ArrayList<Tile>> tiles) {
        this.tiles = tiles;
    }

    public PatternLine() {
        this.tiles = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            this.tiles.add(new ArrayList<>());
        }
    }

    // TODO: implement method
    public boolean addTiles(ArrayList<Tile> tiles, int row) {
        // check if row is not filled yet
        // ensure that colour matches
        // ensure that colour is not filled in wall already
        return true;
    }

    // TODO: implement method
    public boolean isRowFilled(int row) {
        return false;
    }

    // TODO: implement method
    public Tile getFilledRow(int row) {
        // remove all tiles from row
        // return 1 tile
        return null;
    }
}
