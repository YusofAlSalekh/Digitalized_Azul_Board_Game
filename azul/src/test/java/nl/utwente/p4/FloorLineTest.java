package nl.utwente.p4;

import nl.utwente.p4.components.FloorLine;
import nl.utwente.p4.components.Game;
import nl.utwente.p4.components.Tile;
import nl.utwente.p4.constants.TileType;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class FloorLineTest {
    @Test
    void addTile_tileAdded_true() { // methodName_behaviorToBeTested_expectedResult
        // arrange
        FloorLine floorLine = new FloorLine();
        Tile tile = new Tile(TileType.BLACK);

        // act
        floorLine.addTile(tile);

        // assert
        assertEquals(1, floorLine.getTiles().size());
    }

    @Test
    void addTile_tileAdded_false() { // methodName_behaviorToBeTested_expectedResult
        // arrange
        Game game = Game.getInstance();
        game.getTileBag().setTiles(new ArrayList<Tile>());
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
        floorLine.addTile(tile1);
        floorLine.addTile(tile2);
        floorLine.addTile(tile3);
        floorLine.addTile(tile4);
        floorLine.addTile(tile5);
        floorLine.addTile(tile6);
        floorLine.addTile(tile7);
        floorLine.addTile(tile8);

        // assert
        assertEquals(7, floorLine.getTiles().size());
        assertEquals(1, Game.getInstance().getGameBoxLid().getTiles().size());
    }

    @Test
    void clearFloorLine_FloorCleared_true() { // methodName_behaviorToBeTested_expectedResult
        // arrange
        FloorLine floorLine = new FloorLine();
        Tile tile1 = new Tile(TileType.BLACK);
        Tile tile2 = new Tile(TileType.BLACK);
        Tile tile3 = new Tile(TileType.BLACK);
        Tile tile4 = new Tile(TileType.BLACK);
        Tile tile5 = new Tile(TileType.BLACK);
        Tile tile6 = new Tile(TileType.BLACK);
        Tile tile7 = new Tile(TileType.BLACK);

        // act
        floorLine.addTile(tile1);
        floorLine.addTile(tile2);
        floorLine.addTile(tile3);
        floorLine.addTile(tile4);
        floorLine.addTile(tile5);
        floorLine.addTile(tile6);
        floorLine.addTile(tile7);
        floorLine.clearFloorLine();

        // assert
        assertEquals(0, floorLine.getTiles().size());
    }

    @Test
    void clearFloorLine_FloorCleared_FirstPlayerFound() { // methodName_behaviorToBeTested_expectedResult
        // arrange
        FloorLine floorLine = new FloorLine();
        Tile tile1 = new Tile(TileType.BLACK);
        Tile tile2 = new Tile(TileType.FIRST_PLAYER);
        Tile tile3 = new Tile(TileType.BLACK);
        Tile tile4 = new Tile(TileType.BLACK);
        Tile tile5 = new Tile(TileType.BLACK);
        Tile tile6 = new Tile(TileType.BLACK);
        Tile tile7 = new Tile(TileType.BLACK);

        // act
        floorLine.addTile(tile1);
        floorLine.addTile(tile2);
        floorLine.addTile(tile3);
        floorLine.addTile(tile4);
        floorLine.addTile(tile5);
        floorLine.addTile(tile6);
        floorLine.addTile(tile7);
        Boolean firstPlayerFound = floorLine.clearFloorLine();

        // assert
        assertEquals(true, firstPlayerFound);
    }
    //since the logic has yet to be added we just check that the result is 0
    @Test
    void getFloorLineScore() {
        // arrange
        FloorLine floorLine = new FloorLine();
        // assert
        assertEquals(0, floorLine.getTotalFloorScore());
    }
}
