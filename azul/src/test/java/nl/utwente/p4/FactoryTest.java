package nl.utwente.p4;

import nl.utwente.p4.components.Factory;
import nl.utwente.p4.components.Tile;
import nl.utwente.p4.constants.TileType;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class FactoryTest {
    @Test
    void getTileFromFactory() {
        ArrayList<Tile> tiles = new ArrayList<>();
        Factory factory = new Factory(tiles);
        tiles.add(new Tile(TileType.BLACK));
        tiles.add(new Tile(TileType.YELLOW));
        tiles.add(new Tile(TileType.BLACK));
        factory.setTiles(tiles);
        assertEquals(TileType.BLACK, factory.getTiles().get(0).getType());
        assertEquals(TileType.YELLOW, factory.getTiles().get(1).getType());
        assertEquals(TileType.BLACK, factory.getTiles().get(2).getType());
    }
    @Test
    void addTile_TakeTile_getRemaining() {
        ArrayList<Tile> tiles = new ArrayList<>();
        Factory factory = new Factory(tiles);
        factory.addTile(new Tile(TileType.BLACK));
        factory.addTile(new Tile(TileType.YELLOW));
        factory.addTile(new Tile(TileType.BLACK));
        factory.takeTiles(TileType.YELLOW);
        assertEquals(2,factory.getRemainingTiles().size());
        assertEquals(TileType.BLACK, factory.takeAllTiles().get(0).getType());
        assertEquals(0, factory.getRemainingTiles().size());



    }
}