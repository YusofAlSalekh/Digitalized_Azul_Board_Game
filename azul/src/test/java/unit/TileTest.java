package unit;

import nl.utwente.p4.components.*;
import nl.utwente.p4.constants.TileType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class TileTest {
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
    void getType() {
        var tile = new Tile(TileType.BLUE);
        assertEquals(TileType.BLUE, tile.getType());
    }
    @Test
    void setType() {
        var tile = new Tile(TileType.BLUE);
        tile.setType(TileType.YELLOW);
        assertEquals(TileType.YELLOW, tile.getType());
    }
}