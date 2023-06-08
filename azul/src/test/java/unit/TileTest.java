package unit;

import nl.utwente.p4.components.Tile;
import nl.utwente.p4.constants.TileType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TileTest {

    @Test
    void getType() {
        var tile = new Tile(TileType.BLUE);
        assertEquals(TileType.BLUE, tile.getType());
    }
    @Test
    void setType() {
        var tile = new Tile(TileType.BLUE);
        tile.setType(TileType.YELLOW);
        assertEquals(TileType.YELLOW, tile.getType());
    }
}