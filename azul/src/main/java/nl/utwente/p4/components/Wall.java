package nl.utwente.p4.components;

import nl.utwente.p4.constants.TileType;

import java.util.*;

public class Wall {
private Map<TileType, TileType>[] tiles;
    public Map<TileType, TileType>[] getTiles() {
        return tiles;
    }
    public void setTiles(Map<TileType, TileType>[] tiles) {
        this.tiles = tiles;
    }
    public TileType[] WallSet() {
        TileType[] set = {TileType.BLUE,TileType.YELLOW,TileType.RED,TileType.BLACK,TileType.WHITE};
        return set;
    }
    public static TileType[] RotateSet(TileType[] set){
        //move set one index to the right
        TileType lastSetElement = set[set.length - 1];
        System.arraycopy(set, 0, set, 1, set.length - 1);
        set[0] = lastSetElement;
        return set;
    }
    public Wall() {
        this.tiles = new HashMap[5];
        TileType[] set = WallSet();

        for (int i = 0; i < 5; i++) {
            this.tiles[i] = new LinkedHashMap<>();
            for (TileType key : set){
                this.tiles[i].put(key, null);
            }
            set = RotateSet(set);
        }
        setTiles(this.tiles);
    }

    // TODO: implement method
    public int addTile(Tile tiles, int row) {
        // check if tile is not filled in row, then add to row
        return 0;
    }

    // TODO: implement method
    public boolean isTileFilled(Tile tile, int row) {
        return false;
    }

    // TODO: implement method
    public boolean isRowFilled(int row) {
        return false;
    }

    // TODO: implement method
    public int countFilledRows() {
        return 0;
    }

    // TODO: implement method
    public int countFilledColumns() {
        return 0;
    }

    // TODO: implement method
    public int countCompleteTiles() {
        // iterate through each tile type
        return 0;
    }
}
