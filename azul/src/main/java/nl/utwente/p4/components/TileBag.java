package nl.utwente.p4.components;

import lombok.Data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Random;

@Data
public class TileBag extends TileStash {

    public TileBag() {
        super();
    }

    public Tile getRandomTile() {
        if (this.tiles.isEmpty()) return null;
        Collections.shuffle(this.tiles);
        return this.tiles.remove(0);
    }

    public ArrayList<Tile> getAndRemoveTiles() {
        ArrayList<Tile> tilesToReturn = this.tiles;
        this.tiles = new ArrayList<>();
        return tilesToReturn;
    }

}
