package nl.utwente.p4.components;

import lombok.Data;
import nl.utwente.p4.exceptions.TileColourNotMatchedException;
import nl.utwente.p4.exceptions.PatternLineFilledException;
import nl.utwente.p4.exceptions.TileColourNotMatchedWallTileColourException;

import java.util.ArrayList;

@Data
public class PatternLine {
    private ArrayList<TileLine> tileLines;

    public PatternLine() {
        // Initialize lines with tileLines of sizes from 1 to 5
        this.tileLines = new ArrayList<>();
        for (int i = 1; i < 6; i++) {
            this.tileLines.add(new TileLine(i));
        }
    }

    /**
     * Method to add given tiles to the given row
     *
     * @param tilesToAdd tiles to add
     * @param row        row index to add to
     * @param wall       the wall of the board, to check that there is no tile of particular color on the wall
     * @return any excess tiles
     */
    public ArrayList<Tile> addTiles(ArrayList<Tile> tilesToAdd, int row, Wall wall) {

        TileLine tileLine = this.tileLines.get(row);

        // check if row is not filled yet
        if (tileLine.isFilled()) {
            throw new PatternLineFilledException("The pattern line is filled");

            // ensure that colour matches
        } else if (!tileLine.getTiles().isEmpty() &&
                tilesToAdd.stream().anyMatch(t -> t.getType() != tileLine.getTiles().get(0).getType())) {
            throw new TileColourNotMatchedException("The pattern line already consists of tiles of a different color");

            // ensure that colour is not filled in wall already
        } else if (wall.isTileFilled(tilesToAdd.get(0), row)) {
            throw new TileColourNotMatchedWallTileColourException("Tile of this color is already on the wall");
        } else {
            ArrayList<Tile> excessTiles = tileLine.addTilesToLine(tilesToAdd);
            return excessTiles;
        }
    }

    public boolean isRowFilled(int row) {
        return tileLines.get(row).isFilled();
    }

    // this logic was forgotten so i added it to make the game playable
    public void clearPatterLineRow(int row){
        tileLines.set( row,new TileLine(this.tileLines.get(row).getLineSize()));
    }

    // TODO: implement method
    public Tile getFilledRow(int row) {
        // remove all tiles from row
        // return 1 tile
        return null;
    }
}
