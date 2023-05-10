package nl.utwente.p4.components;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import lombok.Data;
import nl.utwente.p4.constants.TileType;

@Data
public class TileTable {
    private ArrayList<Tile> tiles;
    private boolean firstHasBeenTaken;

    public TileTable() {
        this.tiles = new ArrayList<>(Arrays.asList(new Tile(TileType.FIRST_PLAYER)));
        this.firstHasBeenTaken = false;
    }

    public boolean isFirstHasBeenTaken() {
        return firstHasBeenTaken;
    }

    public void setFirstHasBeenTaken(boolean firstHasBeenTaken) {
        this.firstHasBeenTaken = firstHasBeenTaken;
    }

    /***
     * Method to get tiles of given tile type from the tile table
     * @param takenTileType tile type to be taken
     * @return taken tiles
     */
    public ArrayList<Tile> takeTiles(TileType takenTileType) {
        ArrayList<Tile> takenTiles = new ArrayList<>();

        // Add firstPlayerToTake tile if first player to take tiles
        if (!isFirstHasBeenTaken()) {
            Tile firstTile = this.tiles.remove(0);
            takenTiles.add(firstTile);
            setFirstHasBeenTaken(true);
        }

        // Find correct tiles to take by their type
        for (Tile tile : this.tiles) {
            if (tile.getType() == takenTileType) {
                takenTiles.add(tile);
            }
        }

        // Remove taken tiles from table tiles
        for (Tile tile : takenTiles) {
            this.tiles.remove(tile);
        }

        return takenTiles;
    }

    /***
     * Add given tile to the tiletable
     * @param tile tile to be added to the table
     */
    public void addTile(Tile tile) {
        this.tiles.add(tile);
    }

    // TODO: implement method
    public boolean firstPlayerTileTake() { return false; }

    // TODO: implement method
    public boolean isEmpty() { return false; }
}
