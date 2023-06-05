package nl.utwente.p4.components;

import lombok.Getter;
import nl.utwente.p4.constants.TileType;

import java.util.ArrayList;

@Getter
public class TileLine implements GeneralTileLine {
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

    @Override
    public TileType getLineType() {
        return this.lineType;
    }

    @Override
    public int getLineSize() {
        return this.lineSize;
    }

    @Override
    public ArrayList<Tile> getTiles() {
        return this.tiles;
    }


    /***
     * Add tiles to the tile line. If the line is empty, set the line type according to the tiles to be added
     * @param tilesToAdd tiles to add
     * @return excess tiles
     */
    @Override
    public ArrayList<Tile> addTilesToLine(ArrayList<Tile> tilesToAdd) {
        if (tilesToAdd.size() < 1 || isFilled()) return tilesToAdd;
        checkAndSetLineType(tilesToAdd.get(0).getType());

        while (this.lineSize - tiles.size() > 0 && tilesToAdd.size() > 0) {
            if (this.lineType == tilesToAdd.get(0).getType()) {
                this.tiles.add(tilesToAdd.remove(0));
            }
        }

        return tilesToAdd;
    }

    /**
     * Change tileline type to input type if tileline type is null, input is not null and tileline is empty
     *
     * @param type type to change tile line to
     */
    @Override
    public void checkAndSetLineType(TileType type) {
        if (this.tiles.size() == 0 && type != TileType.NULL) this.lineType = type;
    }

    @Override
    public boolean isFilled() {
        return lineSize == tiles.stream().filter(t -> t.getType() != TileType.NULL).count();
    }

    @Override
    public void setTiles(ArrayList<Tile> tiles) {

    }

    @Override
    public void setLineType(TileType lineType) {

    }
}
