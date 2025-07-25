package nl.utwente.p4.components;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import nl.utwente.p4.constants.FloorScore;
import nl.utwente.p4.constants.TileType;

import java.util.ArrayList;

@Getter
public class FloorLine {
    @Setter
    private ArrayList<Tile> tiles;

    public FloorLine() {
        this.tiles = new ArrayList<>();
    }

    /**
     * Add tile to floorline, if the floor isn't yet full, otherwise add tile to game box lid
     * @param tile tile to add
     */
    public void addTile(Tile tile) {
        if (tiles.size() < 7) {
            this.tiles.add(tile);
        } else {
            ArrayList<Tile> tileToAdd = new ArrayList<>();
            tileToAdd.add(tile);
            Game.getInstance().addTilesToGameBoxLid(tileToAdd);
        }
    }

    /**
     * Clear floor line of tiles.
     * If the floor contains the first player tile, mark the player as starting player for next round
     * @return true if first player tile was found, false if not
     */
    public Boolean clearFloorLine() {
        Boolean firstPlayerFound = this.tiles.remove(new Tile(TileType.FIRST_PLAYER));
        Game.getInstance().addTilesToGameBoxLid(tiles);
        this.tiles = new ArrayList<>();
        return firstPlayerFound;
    }

    /**
     * Calculate total floor score based on fixed mapping.
     * @return value of total floor score
     */
    public int getTotalFloorScore() {
        int floorScore = 0;
        for (int i = 0; i < tiles.size(); i++) {
            floorScore += FloorScore.getValues()[i];
        }
        return floorScore;
    }
}
