package nl.utwente.p4.components;

import java.util.ArrayList;

public class Wall {
    private ArrayList<ArrayList<Tile>> tiles;

    public ArrayList<ArrayList<Tile>> getTiles() {
        return tiles;
    }

    public void setTiles(ArrayList<ArrayList<Tile>> tiles) {
        this.tiles = tiles;
    }

    public Wall() {
        this.tiles = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            this.tiles.add(new ArrayList<>());
        }
    }

    // TODO: implement method
    public int addTile(Tile tiles, int row) {
        // check if tile is not filled in row, then add to row
        return 0;
    }

    // TODO: implement method
    public boolean isTileFilled(Tile tile, int row) {
        return false;
    }

    // TODO: implement method
    public boolean isRowFilled(int row) {
        return false;
    }

    // TODO: implement method
    public int countFilledRows() {
        return 0;
    }

    // TODO: implement method
    public int countFilledColumns() {
        return 0;
    }

    // TODO: implement method
    public int countCompleteTiles() {
        // iterate through each tile type
        return 0;
    }
}
