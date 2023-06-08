package unit;

import nl.utwente.p4.components.Tile;
import nl.utwente.p4.components.TileLine;
import nl.utwente.p4.constants.TileType;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TileLineTest {
    @Test
    void createTileLineWithSize_0() {

        TileLine tileLine = new TileLine();

        assertEquals(0, tileLine.getLineSize());
    }
    @Test
    void addTilesToLine_tilesAddedToLine_true() {
        // arrange
        TileLine tileLine = new TileLine(2);
        ArrayList<Tile> tiles = new ArrayList<>();
        tiles.add(new Tile(TileType.BLACK));
        tiles.add(new Tile(TileType.BLACK));
        tiles.add(new Tile(TileType.BLACK));

        // act
        ArrayList<Tile> excess = tileLine.addTilesToLine(tiles);

        // Test that tileline is filled the right amount
        assertEquals(2, tileLine.getLineSize());
        assertEquals(2, tileLine.getTiles().size());
        // Test that floor contains all excess tiles
        assertEquals(1, excess.size());
    }

    @Test
    void addTilesToLine_tilesNotAdded_true() {
        // arrange
        TileLine tileLine = new TileLine(3);
        ArrayList<Tile> tiles = new ArrayList<>();
        tiles.add(new Tile(TileType.BLACK));
        tiles.add(new Tile(TileType.BLACK));
        tiles.add(new Tile(TileType.BLACK));

        ArrayList<Tile> newTiles = new ArrayList<>();
        newTiles.add(new Tile(TileType.BLACK));
        newTiles.add(new Tile(TileType.BLACK));
        newTiles.add(new Tile(TileType.BLACK));

        // act
        ArrayList<Tile> excess = tileLine.addTilesToLine(tiles);
        ArrayList<Tile> excess2 = tileLine.addTilesToLine(newTiles);

        // Test that tileline is filled the right amount
        assertEquals(3, tileLine.getLineSize());
        assertEquals(3, tileLine.getTiles().size());
        // Test that floor contains all excess tiles
        assertEquals(0, excess.size());
        assertEquals(3, excess2.size());
    }

    @Test
    void addTilesToLine_emptyArrayNotAdded_true() {
        // arrange
        TileLine tileLine = new TileLine(2);
        ArrayList<Tile> tiles = new ArrayList<>();

        // act
        ArrayList<Tile> excess = tileLine.addTilesToLine(tiles);

        // Test that tileline is filled the right amount
        assertEquals(2, tileLine.getLineSize());
        assertEquals(0, tileLine.getTiles().size());
        // Test that floor contains all excess tiles
        assertEquals(0, excess.size());
    }

    @Test
    void checkAndSetLineType_lineTypeNotChanged_true() {
        // arrange
        TileLine tileLine = new TileLine(2);
        ArrayList<Tile> tiles = new ArrayList<>();
        tiles.add(new Tile(TileType.BLACK));

        // act
        tileLine.addTilesToLine(tiles);
        tileLine.checkAndSetLineType(TileType.YELLOW);

        // Test that tileline is filled the right amount
        assertEquals(TileType.BLACK, tileLine.getLineType());
    }
}
