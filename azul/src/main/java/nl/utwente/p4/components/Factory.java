package nl.utwente.p4.components;

import lombok.Data;
import nl.utwente.p4.constants.TileType;

import java.util.ArrayList;

@Data
public class Factory {
    private ArrayList<Tile> tiles;

    public Factory(ArrayList<Tile> tiles) {
        this.tiles = tiles;
    }

    /***
     *method that takes tiles of one colour from the factory
     * @param color the colour of tails that player decided to take
     * @return list of taken tiles
     */
    public ArrayList<Tile> takeTiles(TileType color) {

        var taken = tiles.stream().filter(t -> t.getType() == color).toList();

        tiles.removeIf(t -> t.getType() == color);

        return new ArrayList<>(taken);
    }

    /**
     * Take all the tiles from the factory
     * @return tiles from the factory
     */
    public ArrayList<Tile> takeAllTiles() {
        ArrayList<Tile> tilesToTake = this.tiles;
        this.tiles = new ArrayList<>();
        return tilesToTake;
    }

    public void addTile(Tile tile) {
        this.tiles.add(tile);
    }

    public ArrayList<Tile> getRemainingTiles() {
        return tiles;
    }
}
