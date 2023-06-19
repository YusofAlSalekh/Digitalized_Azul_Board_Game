package unit;

import nl.utwente.p4.components.*;
import nl.utwente.p4.constants.TileType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class FloorLineTest {
    @BeforeEach
    void setup() {
        Game game = Game.getInstance();
        game.setNumOfPlayers(2);
        game.setTileBag(new TileBag());
        game.setGameBoxLid(new GameBoxLid());
        game.setTileTable(new TileTable());
        game.setFactories(new ArrayList<>());
        game.setPlayers(new ArrayList<>());
        game.setCurrPlayerIdx(0);
        game.startGame();
    }

    @Test
    void addTile_tileAdded_true() {
        // arrange
        FloorLine floorLine = new FloorLine();
        Tile tile = new Tile(TileType.BLACK);

        // act
        floorLine.addTile(tile);

        // assert
        assertEquals(1, floorLine.getTiles().size());
    }

    @Test
    void addTile_tileAdded_false() {
        // arrange
        Game game = Game.getInstance();

        game.getTileBag().setTiles(new ArrayList<>());
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
        assertEquals(1, game.getGameBoxLid().getTiles().size());
    }

    @Test
    void clearFloorLine_FloorCleared_true() {
        // arrange
        FloorLine floorLine = new FloorLine();
        ArrayList<Tile> tiles = new ArrayList<>();
        tiles.add(new Tile(TileType.BLACK));
        tiles.add(new Tile(TileType.BLACK));
        tiles.add(new Tile(TileType.BLACK));
        tiles.add(new Tile(TileType.BLACK));
        tiles.add(new Tile(TileType.BLACK));
        tiles.add(new Tile(TileType.BLACK));
        tiles.add(new Tile(TileType.BLACK));

        // act
        floorLine.setTiles(tiles);
        assertEquals(7, floorLine.getTiles().size());
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

    @Test
    void getTotalFloorScore_noFloorLineTiles() {
        // arrange
        FloorLine floorLine = new FloorLine();
        // assert
        assertEquals(0, floorLine.getTotalFloorScore());
    }

    @Test
    void getTotalFloorScore_hasFloorLineTiles() {
        // arrange
        FloorLine floorLine = new FloorLine();
        floorLine.addTile(new Tile(TileType.BLACK));
        floorLine.addTile(new Tile(TileType.BLUE));
        // assert
        assertEquals(-2, floorLine.getTotalFloorScore());
    }
}
