package unit;

import nl.utwente.p4.components.Tile;
import nl.utwente.p4.components.TileTable;
import nl.utwente.p4.constants.TileType;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class TileTableTest {
    @Test
    void getMatchingTiles_tilesMatched_true() {
        // arrange
        TileTable tileTable = new TileTable();
        tileTable.addTile(new Tile(TileType.BLACK));
        tileTable.addTile(new Tile(TileType.BLACK));
        tileTable.addTile(new Tile(TileType.BLACK));
        tileTable.addTile(new Tile(TileType.YELLOW));
        tileTable.addTile(new Tile(TileType.WHITE));

        // act
        ArrayList<Tile> takenTiles = tileTable.getMatchingTiles(TileType.BLACK);

        // Test that correct number of tiles was taken, 3 black 1 first player tile
        assertEquals(4, takenTiles.size());
        // Test that correct tiles were taken and first player tile was taken
        ArrayList<Tile> expected = new ArrayList<>();
        expected.add(new Tile(TileType.FIRST_PLAYER));
        expected.add(new Tile(TileType.BLACK));
        expected.add(new Tile(TileType.BLACK));
        expected.add(new Tile(TileType.BLACK));
        assertEquals(expected, takenTiles);
        assertEquals(TileType.FIRST_PLAYER, takenTiles.get(0).getType());
        assertEquals(TileType.BLACK, takenTiles.get(1).getType());
    }


    @Test
    void takeTiles_tilesTaken_true() {
        // arrange
        TileTable tileTable = new TileTable();
        tileTable.addTile(new Tile(TileType.BLACK));
        tileTable.addTile(new Tile(TileType.BLACK));
        tileTable.addTile(new Tile(TileType.BLACK));
        tileTable.addTile(new Tile(TileType.YELLOW));
        tileTable.addTile(new Tile(TileType.WHITE));

        // act
        tileTable.takeTiles(TileType.BLACK);

        // Test that correct number of tiles was taken, leaving only 2 tiles in the tile table
        assertEquals(2, tileTable.getTiles().size());
        // Test that correct tiles were taken and first player tile was taken

        for (Tile tile : tileTable.getTiles()) {
            assertNotEquals(TileType.BLACK, tile.getType());
        }
        assertTrue(tileTable.isFirstHasBeenTaken());
    }
    @Test
    void isFirstHasBeenTaken_getValue_false() {
        // arrange
        TileTable tileTable = new TileTable();

        // act
        boolean hasBeenTaken = tileTable.isFirstHasBeenTaken();

        // assert
        assertFalse(hasBeenTaken);
    }

    @Test
    void setFirstHasBeenTaken_setNewValue_true() {
        // arrange
        TileTable tileTable = new TileTable();

        // act
        tileTable.setFirstHasBeenTaken(true);

        // assert
        assertTrue(tileTable.isFirstHasBeenTaken());
    }
}
