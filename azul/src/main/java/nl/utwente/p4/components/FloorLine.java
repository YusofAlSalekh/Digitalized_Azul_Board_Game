package nl.utwente.p4.components;

import java.util.ArrayList;

public class FloorLine {
    private static final int[] floorScores = {-1, -1, -2, -2, -2, -3, -3};
    private ArrayList<Tile> tiles;

    public ArrayList<Tile> getTiles() {
        return tiles;
    }

    public void setTiles(ArrayList<Tile> tiles) {
        this.tiles = tiles;
    }

    public FloorLine() {
        this.tiles = new ArrayList<>();
    }

    // TODO: implement method
    public boolean addTile(Tile tile) {
        // return true if still <7, else return false
        return true;
    }

    // TODO: implement method
    public int getTotalFloorScore() {
        return 0;
    }
}
