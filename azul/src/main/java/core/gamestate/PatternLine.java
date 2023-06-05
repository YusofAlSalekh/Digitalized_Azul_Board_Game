package core.gamestate;

import lombok.Getter;
import lombok.Setter;

public class PatternLine implements IPatternLine {

    /**
     * The amount of tiles that fit in this specific pattern line
     */
    @Getter
    @Setter
    private int capacity;

    /**
     * The current tile type of the pattern line. (Can be null)
     */
    @Getter
    @Setter
    private TileType tileType;

    /**
     * The amount of tiles currently in the pattern line
     */
    @Getter
    @Setter
    private int tileCount;

    public PatternLine(int capacity) {
        this.capacity = capacity;
        tileType = null;
        tileCount = 0;
    }

    /**
     * Try to add a set of tiles to the pattern line. Return overflow count.
     *
     * @param type  The type of tiles to add to the pattern line. <br>
     *              NOTE: This function assumes type is already checked. If type is not equal, it will be
     *              overwritten by this parameter.
     * @param count The amount of tiles of the specified to add to the line.
     * @return The amount of tiles that did not fit on the line anymore.
     */
    public int addToLine(TileType type, int count) {
        tileType = type;
        int remainder = Math.max(0, count - (capacity - tileCount));
        tileCount = Math.min(tileCount + count, capacity);
        return remainder;
    }

    /**
     * Check if a pattern line is currently (over) full
     *
     * @return True if at least full, false if not
     */
    public boolean isFull() {
        return tileCount >= capacity;
    }

    /**
     * Remove all tiles currently in the pattern line and return the amount of tiles removed.
     *
     * @return The amount of tiles that was removed from the pattern line.
     */
    public int clearLine() {
        var count = tileCount;
        tileCount = 0;
        tileType = null;
        return count;
    }

    /**
     * Remove a single tile from the PatternLine. Set tileType to null if this empties the line.
     *
     * @return the tile that is removed, null if the line is empty
     */
    public TileType removeTile() {
        TileType type;
        if (tileCount <= 0) {
            tileCount = 0;
            type = null;
        } else {
            tileCount--;
            type = tileType;
            if (tileCount == 0)
                tileType = null;
        }
        return type;
    }
}
