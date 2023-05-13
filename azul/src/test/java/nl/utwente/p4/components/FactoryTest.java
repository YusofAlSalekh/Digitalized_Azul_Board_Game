package nl.utwente.p4.components;

import nl.utwente.p4.constants.TileType;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class FactoryTest {

    @Test
    void addTile_tileAdded_true() {
        // arrange
        ArrayList<Tile> tiles = new ArrayList<>();
        tiles.add(new Tile(TileType.BLACK));
        Factory factory = new Factory(tiles);

        // act
        factory.addTile(new Tile(TileType.BLUE));

        // assert
        assertEquals(2, factory.getTiles().size());
        assertEquals(TileType.BLUE, factory.getTiles().get(factory.getTiles().size() - 1).getType());
    }
}