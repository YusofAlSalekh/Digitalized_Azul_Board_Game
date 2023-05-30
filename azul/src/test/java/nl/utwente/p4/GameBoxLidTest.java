package nl.utwente.p4.components;

import nl.utwente.p4.constants.TileType;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameBoxLidTest {
    @Test
    void getAndRemoveTiles_EmptyGameBoxLid_NoTilesReturned() {
        // arrange
        GameBoxLid gameBoxLid = new GameBoxLid();

        // act
        ArrayList<Tile> tiles = gameBoxLid.getAndRemoveTiles();

        // Check that removed tiles are 0 and gameBoxLid contains 0 tiles
        assertEquals(0, tiles.size());
        assertEquals(0, gameBoxLid.getTiles().size());
    }

    @Test
    void getAndRemoveTiles_TilesReturnedAndBoxEmpty_BoxEmpty() {
        // arrange
        GameBoxLid gameBoxLid = new GameBoxLid();
        ArrayList<Tile> tiles = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            tiles.add(new Tile(TileType.BLACK));
        }
        gameBoxLid.addTiles(tiles);

        // act
        ArrayList<Tile> returnedTiles = gameBoxLid.getAndRemoveTiles();

        // Check that box is empty and excess contains all the tiles from the box
        assertEquals(0, gameBoxLid.getTiles().size());
        assertEquals(10, returnedTiles.size());
    }
}
