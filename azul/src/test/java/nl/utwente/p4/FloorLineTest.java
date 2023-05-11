package nl.utwente.p4;

import nl.utwente.p4.components.FloorLine;
import nl.utwente.p4.components.Game;
import nl.utwente.p4.components.Tile;
import nl.utwente.p4.constants.TileType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class FloorLineTest {
    @Test
    void addTile_tileAdded_true() { // methodName_behaviorToBeTested_expectedResult
        // arrange
        FloorLine floorLine = new FloorLine();
        Tile tile = new Tile(TileType.BLACK);

        // act
        Boolean wasAdded = floorLine.addTile(tile);

        // assert
        assertEquals(true, wasAdded);
        assertEquals(1, floorLine.getTiles().size());
    }

    @Test
    void addTile_tileAdded_false() { // methodName_behaviorToBeTested_expectedResult
        // arrange
        FloorLine floorLine = new FloorLine();
        Tile tile1 = new Tile(TileType.BLACK);
        Tile tile2 = new Tile(TileType.BLACK);
        Tile tile3 = new Tile(TileType.BLACK);
        Tile tile4 = new Tile(TileType.BLACK);
        Tile tile5 = new Tile(TileType.BLACK);
        Tile tile6 = new Tile(TileType.BLACK);
        Tile tile7 = new Tile(TileType.BLACK);
        Tile tile8 = new Tile(TileType.BLACK);

        // act
        Boolean wasAdded1 = floorLine.addTile(tile1);
        Boolean wasAdded2 = floorLine.addTile(tile2);
        Boolean wasAdded3 = floorLine.addTile(tile3);
        Boolean wasAdded4 = floorLine.addTile(tile4);
        Boolean wasAdded5 = floorLine.addTile(tile5);
        Boolean wasAdded6 = floorLine.addTile(tile6);
        Boolean wasAdded7 = floorLine.addTile(tile7);
        Boolean wasAdded8 = floorLine.addTile(tile8);

        // assert
        assertEquals(true, wasAdded7);
        assertEquals(false, wasAdded8);
        assertEquals(7, floorLine.getTiles().size());
    }
}
