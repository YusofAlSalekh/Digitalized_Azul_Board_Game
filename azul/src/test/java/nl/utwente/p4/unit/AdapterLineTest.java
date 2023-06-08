package nl.utwente.p4.unit;

import nl.utwente.p4.components.GeneralTileLine;
import nl.utwente.p4.components.Tile;
import nl.utwente.p4.constants.TileType;
import core.gamestate.TileLineAdapter;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AdapterLineTest {
    @Test
    void createTileLineWithSize_0() {

        GeneralTileLine tileLine = new TileLineAdapter();

        assertEquals(0, tileLine.getLineSize());
    }

    @Test
    void addTilesToLine_tilesAddedToLine_true() {
        // arrange
        GeneralTileLine tileLine = new TileLineAdapter(2);
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
        GeneralTileLine tileLine = new TileLineAdapter(3);
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
        GeneralTileLine tileLine = new TileLineAdapter(2);
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
        GeneralTileLine tileLine = new TileLineAdapter(2);
        ArrayList<Tile> tiles = new ArrayList<>();
        tiles.add(new Tile(TileType.BLACK));

        // act
        tileLine.addTilesToLine(tiles);
        tileLine.checkAndSetLineType(TileType.YELLOW);

        // Test that tileline is filled the right amount
        assertEquals(TileType.BLACK, tileLine.getLineType());
    }
}
