package nl.utwente.p4.components;

import lombok.Data;

import java.util.ArrayList;

@Data
public class FloorLine {
    private static final int[] floorScores = {-1, -1, -2, -2, -2, -3, -3};
    private ArrayList<Tile> tiles;

    public FloorLine() {
        this.tiles = new ArrayList<>();
    }

    /**
     * Add tile to floorline, if the floor isn't yet full
     * @param tile tile to add to the floorline
     * @return true if tile was added, false if floor is full
     */
    public boolean addTile(Tile tile) {
        if (tiles.size() >= 7) return false;
        this.tiles.add(tile);
        return true;
    }

    // TODO: implement method
    public int getTotalFloorScore() {
        return 0;
    }
}
