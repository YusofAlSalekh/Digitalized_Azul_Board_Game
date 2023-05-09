package nl.utwente.p4.components;

import java.util.ArrayList;
import java.util.Random;

public class TileBag {
    private ArrayList<Tile> tiles;

    public ArrayList<Tile> getTiles() {
        return tiles;
    }

    public void setTiles(ArrayList<Tile> tiles) {
        this.tiles = tiles;
    }

    public TileBag() {
        this.tiles = new ArrayList<>();
    }

    public Tile getRandomTile() {
        if (this.tiles.size() == 0) {
            return null;
        }

        Random rand = new Random();
        int idx = rand.nextInt(this.tiles.size());
        return this.tiles.remove(idx);
    }

    public void addTiles(ArrayList<Tile> tiles) {
        this.tiles.addAll(tiles);
    }

    // TODO: implement method
    public boolean isEmpty() { return false; }
}
