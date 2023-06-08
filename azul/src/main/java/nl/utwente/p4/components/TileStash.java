package nl.utwente.p4.components;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter @Setter
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
