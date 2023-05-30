package nl.utwente.p4.components;

import lombok.Data;

import java.util.ArrayList;

@Data
public class TileStash {
    protected ArrayList<Tile> tiles;

    public TileStash() {
        this.tiles = new ArrayList<>();
    }

    public void addTiles(ArrayList<Tile> tiles) {
        this.tiles.addAll(tiles);
    }

    public boolean isEmpty() { return this.tiles.size() == 0; }
}
