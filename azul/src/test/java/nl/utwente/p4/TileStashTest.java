package nl.utwente.p4;

import nl.utwente.p4.components.Tile;
import nl.utwente.p4.components.TileStash;
import nl.utwente.p4.constants.TileType;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class TileStashTest {
    @Test
    void addTiles_tilesAdded_true() {
        // arrange
        TileStash tileStash = new TileStash();
        ArrayList<Tile> tiles = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            tiles.add(new Tile(TileType.BLACK));
        }
        tileStash.setTiles(tiles);

        ArrayList<Tile> newTiles = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            newTiles.add(new Tile(TileType.BLUE));
        }

        // act
        tileStash.addTiles(newTiles);

        // assert
        int tileBagSize = tileStash.getTiles().size();
        assertEquals(8, tileBagSize);
        for (int i = tileBagSize - newTiles.size(); i < tileBagSize; i++) {
            assertEquals(newTiles.get(i - 5), tileStash.getTiles().get(i));
        }
    }
    @Test
    void tileStash_checkIfEmpty_true() {
        TileStash tileStash = new TileStash();
        assertTrue(tileStash.isEmpty());
    }
    @Test
    void tileStash_checkIfEmpty_false() {
        TileStash tileStash = new TileStash();
        ArrayList<Tile> tiles = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            tiles.add(new Tile(TileType.BLACK));
        }
        tileStash.setTiles(tiles);
        assertFalse(tileStash.isEmpty());
    }
}
