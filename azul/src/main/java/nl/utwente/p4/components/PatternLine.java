package nl.utwente.p4.components;

import java.util.ArrayList;

public class PatternLine {
    private ArrayList<TileLine> tileLines;

    public ArrayList<TileLine> getTileLines() {
        return tileLines;
    }

    public void setTileLines(ArrayList<TileLine> tileLines) {
        this.tileLines = tileLines;
    }

    public PatternLine() {
        // Initialize lines with tileLines of sizes from 1 to 5
        this.tileLines = new ArrayList<>();
        for (int i = 1; i < 6; i++) {
            this.tileLines.add(new TileLine(i));
        }
    }

    /***
     * Method to add given tiles to the given row
     * @param tilesToAdd tiles to add
     * @param row row index to add to
     * @return any excess tiles
     */
    public ArrayList<Tile> addTiles(ArrayList<Tile> tilesToAdd, int row) {
        // check if row is not filled yet
        // ensure that colour matches
        // ensure that colour is not filled in wall already
        TileLine tileLine = this.tileLines.get(row);
        ArrayList<Tile> excessTiles = tileLine.addTilesToLine(tilesToAdd);
        return excessTiles;
    }

    // TODO: implement method
    public boolean isRowFilled(int row) {
        return tileLines.get(row).isFilled();
    }



    // TODO: implement method
    public Tile getFilledRow(int row) {
        // remove all tiles from row
        // return 1 tile
        return null;
    }
}
