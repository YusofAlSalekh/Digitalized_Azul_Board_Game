package nl.utwente.p4.components;

import nl.utwente.p4.constants.TileType;

import java.util.ArrayList;

public class TileLine {
    private ArrayList<Tile> tiles;
    private int lineSize;

    private TileType lineType;

    public TileLine() {
        this.tiles = new ArrayList<>();
        this.lineSize = 0;
        this.lineType = TileType.NULL;
    }

    public TileLine(int size) {
        this.tiles = new ArrayList<>(size);
        this.lineSize = size;
        this.lineType = TileType.NULL;
    }

    public TileType getLineType() {
        return this.lineType;
    }

    public int getLineSize() {
        return this.lineSize;
    }


    /***
     * Add tiles to the tile line. If the line is empty, set the line type according to the tiles to be added
     * @param tilesToAdd tiles to add
     * @return excess tiles
     */
    public ArrayList<Tile> addTilesToLine(ArrayList<Tile> tilesToAdd) {
        ArrayList<Tile> excess = new ArrayList<>();

        if (tiles.size() == 0 && lineType == TileType.NULL) this.lineType = tilesToAdd.get(0).getType();
        if (lineType == tilesToAdd.get(0).getType()) {
            int freeSlots = lineSize - tiles.size();
            for (int i = 0; i < tilesToAdd.size(); i++) {
                if (freeSlots > i) {
                    tiles.add(tilesToAdd.get(i));
                } else {
                    excess.add(tilesToAdd.get(i));
                }
            }
        }
        return excess;
    }
}
