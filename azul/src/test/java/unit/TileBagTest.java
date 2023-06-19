package unit;

import nl.utwente.p4.components.*;
import nl.utwente.p4.constants.TileType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class TileBagTest {
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
}