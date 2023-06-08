package nl.utwente.p4.components;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import nl.utwente.p4.constants.TileType;

public class TileTable {
    @Getter
    private ArrayList<Tile> tiles;
    @Getter @Setter
    private boolean firstHasBeenTaken;

    public TileTable() {
        this.tiles = new ArrayList<>(List.of(new Tile(TileType.FIRST_PLAYER)));
        this.firstHasBeenTaken = false;
    }

    /***
     * Method to get matched tiles of given tile type from the tile table
     * @param takenTileType tile type to be matched
     * @return taken tiles
     */
    public ArrayList<Tile> getMatchingTiles(TileType takenTileType) {
        ArrayList<Tile> takenTiles = new ArrayList<>();

        // Add firstPlayerToTake tile if first player to take tiles
        if (!isFirstHasBeenTaken()) {
            Tile firstTile = this.tiles.get(0);
            takenTiles.add(firstTile);
        }

        // Find correct tiles to take by their type
        for (Tile tile : this.tiles) {
            if (tile.getType() == takenTileType) {
                takenTiles.add(tile);
            }
        }

        return takenTiles;
    }

    /***
     * Method to take tiles of given tile type from the tile table
     * @param takenTileType tilet type to be taken
     */
    public void takeTiles(TileType takenTileType) {
        ArrayList<Tile> takenTiles = getMatchingTiles(takenTileType);
        addFirstPlayerTileIfFirstPlayerTakeTiles(takenTiles);

        // Remove taken tiles from table tiles
        for (Tile tile : takenTiles) {
            this.tiles.remove(tile);
        }
    }

    private void addFirstPlayerTileIfFirstPlayerTakeTiles(ArrayList<Tile> takenTiles) {
        // Add firstPlayerToTake tile if first player to take tiles
        if (!isFirstHasBeenTaken()) {
            Tile firstTile = this.tiles.remove(0);
            takenTiles.add(firstTile);
            setFirstHasBeenTaken(true);
        }
    }

    /***
     * Add given tile to the tiletable
     * @param tile tile to be added to the table
     */
    public void addTile(Tile tile) {
        this.tiles.add(tile);
    }

    /**
     * reset the tile table to initial state.
     */
    public void reset() {
        this.tiles = new ArrayList<>(List.of(new Tile(TileType.FIRST_PLAYER)));
    }
}
