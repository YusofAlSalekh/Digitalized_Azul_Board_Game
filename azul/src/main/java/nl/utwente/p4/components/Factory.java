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

    /**
     * get the matching tiles of a tile type color
     * @param color the colour of tiles to match
     * @return list of matching tiles
     */
    public ArrayList<Tile> getMatchingTiles(TileType color) {
        var taken = tiles.stream().filter(t -> t.getType() == color).toList();
        return new ArrayList<>(taken);
    }

    /***
     *method that takes tiles of one colour from the factory
     * @param color the colour of tiles that player decided to take
     */
    public void takeTiles(TileType color) {
        tiles.removeIf(t -> t.getType() == color);
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

    //Tiles should also be removed from the factory, instead of just returning them.
    public ArrayList<Tile> getRemainingTiles() {
        ArrayList<Tile> newList = new ArrayList<>(tiles);
        tiles.clear();
        return newList;
    }
}
