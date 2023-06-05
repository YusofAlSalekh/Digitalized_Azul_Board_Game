package nl.utwente.p4.components;

import lombok.Getter;
import nl.utwente.p4.constants.TileType;
import nl.utwente.p4.exceptions.TileColourNotMatchedException;
import nl.utwente.p4.exceptions.PatternLineFilledException;
import nl.utwente.p4.exceptions.TileColourNotMatchedWallTileColourException;

import java.util.ArrayList;

@Getter
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
     * @return any excess tiles
     */
    public ArrayList<Tile> addTiles(ArrayList<Tile> tilesToAdd, int row) {
        TileLine tileLine = this.tileLines.get(row);
        return tileLine.addTilesToLine(tilesToAdd);
    }

    /**
     * Check if the tiles can be added to the chosen pattern line row
     *
     * @param tilesToAdd tiles to add
     * @param row        row index to add to
     */
    public void checkAddTiles(ArrayList<Tile> tilesToAdd, int row) {
        ArrayList<Tile> tilesToCheck = new ArrayList<>(tilesToAdd);
        if (tilesToCheck.size() > 0 && tilesToCheck.get(0).getType() == TileType.FIRST_PLAYER) {
            tilesToCheck.remove(0);
        }

        TileLine tileLine = this.tileLines.get(row);
        Game game = Game.getInstance();
        Wall currentPlayerWall = game.getCurrentPlayer().getWall();

        // check if row is not filled yet
        if (tileLine.isFilled()) {
            throw new PatternLineFilledException("The pattern line is filled");

            // ensure that colour matches
        } else if (!tileLine.getTiles().isEmpty() &&
                tilesToCheck.stream().anyMatch(t -> t.getType() != tileLine.getTiles().get(0).getType())) {
            throw new TileColourNotMatchedException("The pattern line already consists of tiles of a different color");

            // ensure that colour is not filled in wall already
        } else if (currentPlayerWall.isTileFilled(tilesToCheck.get(0), row)) {
            throw new TileColourNotMatchedWallTileColourException("Tile of this color is already on the wall");
        }
    }

    public boolean isRowFilled(int row) {
        return tileLines.get(row).isFilled();
    }

    // this logic was forgotten so i added it to make the game playable
    public void clearPatterLineRow(int row){
        tileLines.set( row,new TileLine(this.tileLines.get(row).getLineSize()));
    }


}
