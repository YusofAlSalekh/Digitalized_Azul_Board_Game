package nl.utwente.p4.components;

import lombok.Getter;
import nl.utwente.p4.exceptions.TileColourNotMatchedException;
import nl.utwente.p4.exceptions.PatternLineFilledException;
import nl.utwente.p4.exceptions.TileColourNotMatchedWallTileColourException;

import java.util.ArrayList;
import java.util.List;

@Getter
public class PatternLine {
    private List<GeneralTileLine> tileLines;

    public PatternLine() {
        // Initialize lines with tileLines of sizes from 1 to 5
        this.tileLines = new ArrayList<>();
        for (int i = 1; i < 6; i++) {
            this.tileLines.add(TileLineBuilder.createTileLine(i));
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

        GeneralTileLine tileLine = this.tileLines.get(row);
        Game game = Game.getInstance();
        Wall currentPlayerWall = game.getCurrentPlayer().getWall();

        // check if row is not filled yet
        if (tileLine.isFilled()) {
            throw new PatternLineFilledException("The pattern line is filled");

            // ensure that colour matches
        } else if (!tileLine.getTiles().isEmpty() &&
                tilesToAdd.stream().anyMatch(t -> t.getType() != tileLine.getTiles().get(0).getType())) {
            throw new TileColourNotMatchedException("The pattern line already consists of tiles of a different color");

            // ensure that colour is not filled in wall already
        } else if (currentPlayerWall.isTileFilled(tilesToAdd.get(0), row)) {
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
    public void clearPatterLineRow(int row) {
        tileLines.set(row, TileLineBuilder.createTileLine(this.tileLines.get(row).getLineSize()));
    }


}
