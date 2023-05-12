package nl.utwente.p4.components;

import lombok.Data;
import nl.utwente.p4.constants.TileType;

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

    /**
     * Clear floor line of tiles.
     * If the floor contains the first player tile, mark the player as starting player for next round
     * @return true if first player tile was found, false if not
     */
    public Boolean clearFloorLine() {
        Boolean firstPlayerFound = this.tiles.contains(new Tile(TileType.FIRST_PLAYER));
        Game.getInstance().addTilesToGameBoxLid(tiles);
        this.tiles = new ArrayList<>();
        return firstPlayerFound;
    }

    // TODO: implement method
    public int getTotalFloorScore() {
        return 0;
    }
}
