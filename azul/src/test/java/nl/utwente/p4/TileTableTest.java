package nl.utwente.p4;

import nl.utwente.p4.components.Tile;
import nl.utwente.p4.components.TileLine;
import nl.utwente.p4.components.TileTable;
import nl.utwente.p4.constants.TileType;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TileTableTest {
    @Test
    void takeTiles_tilesTaken_true() {
        // arrange
        TileTable tileTable = new TileTable();
        ArrayList<Tile> tiles = new ArrayList<>();
        tileTable.addTile(new Tile(TileType.BLACK));
        tileTable.addTile(new Tile(TileType.BLACK));
        tileTable.addTile(new Tile(TileType.BLACK));
        tileTable.addTile(new Tile(TileType.YELLOW));
        tileTable.addTile(new Tile(TileType.GREEN));

        // act
        ArrayList<Tile> takenTiles = tileTable.takeTiles(TileType.BLACK);

        // Test that correct number of tiles was taken, 3 black 1 first player tile
        assertEquals(4, takenTiles.size());
        // Test that correct tiles were taken and first player tile was taken
        ArrayList<Tile> expected = new ArrayList<Tile>();
        expected.add(new Tile(TileType.FIRST_PLAYER));
        expected.add(new Tile(TileType.BLACK));
        expected.add(new Tile(TileType.BLACK));
        expected.add(new Tile(TileType.BLACK));
        assertEquals(expected, takenTiles);
        assertEquals(TileType.FIRST_PLAYER, takenTiles.get(0).getType());
        assertEquals(TileType.BLACK, takenTiles.get(1).getType());
        assertEquals(true, tileTable.isFirstHasBeenTaken());
    }
}
