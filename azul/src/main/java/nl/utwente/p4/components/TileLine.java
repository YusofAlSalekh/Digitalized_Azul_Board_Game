package nl.utwente.p4.components;

import lombok.Getter;
import lombok.Setter;
import nl.utwente.p4.constants.TileType;

import java.util.ArrayList;

public class TileLine {
    @Getter @Setter
    private ArrayList<Tile> tiles;
    @Getter
    private int lineSize;
    @Getter
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

    /***
     * Add tiles to the tile line. If the line is empty, set the line type according to the tiles to be added
     * @param tilesToAdd tiles to add
     * @return excess tiles
     */
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
     * @param type type to change tile line to
     */
    public void checkAndSetLineType(TileType type) {
        if (this.tiles.size() == 0 && type != TileType.NULL) this.lineType = type;
    }

    public boolean isFilled() {
        return lineSize == tiles.stream().filter(t -> t.getType() != TileType.NULL).count();
    }
}
