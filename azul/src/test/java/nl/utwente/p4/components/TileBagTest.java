package nl.utwente.p4.components;

import nl.utwente.p4.constants.TileType;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class TileBagTest {
    @Test
    void getRandomTile_returnsRandomTile_true() {
        // arrange
        TileBag tileBag = new TileBag();
        ArrayList<Tile> tiles = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            tiles.add(new Tile(TileType.BLACK));
        }
        tileBag.setTiles(tiles);

        // act
        Tile randomTile = tileBag.getRandomTile();

        // assert
        assertNotNull(randomTile);
    }

    @Test
    void getRandomTile_returnsRandomTile_emptyTileBag() {
        // arrange
        TileBag tileBag = new TileBag();

        // act
        Tile randomTile = tileBag.getRandomTile();

        // assert
        assertNull(randomTile);
    }

    @Test
    void addTiles_tilesAdded_true() {
        // arrange
        TileBag tileBag = new TileBag();
        ArrayList<Tile> tiles = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            tiles.add(new Tile(TileType.BLACK));
        }
        tileBag.setTiles(tiles);

        ArrayList<Tile> newTiles = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            newTiles.add(new Tile(TileType.BLUE));
        }

        // act
        tileBag.addTiles(newTiles);

        // assert
        int tileBagSize = tileBag.getTiles().size();
        assertEquals(8, tileBagSize);
        for (int i = tileBagSize - newTiles.size(); i < tileBagSize; i++) {
            assertEquals(newTiles.get(i - 5), tileBag.getTiles().get(i));
        }
    }
}