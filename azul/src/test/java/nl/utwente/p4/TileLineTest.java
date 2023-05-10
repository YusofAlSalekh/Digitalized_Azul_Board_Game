package nl.utwente.p4;

import nl.utwente.p4.components.PlayerBoard;
import nl.utwente.p4.components.Tile;
import nl.utwente.p4.components.TileLine;
import nl.utwente.p4.constants.TileType;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TileLineTest {
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
}
