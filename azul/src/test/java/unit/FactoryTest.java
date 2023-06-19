package unit;

import nl.utwente.p4.components.*;
import nl.utwente.p4.constants.TileType;
import nl.utwente.p4.ui.GameView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class FactoryTest {
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
    void getMatchingTiles_tileTypeMatched_true() {
        ArrayList<Tile> tiles = new ArrayList<>();
        Factory factory = new Factory(tiles);
        factory.addTile(new Tile(TileType.BLACK));
        factory.addTile(new Tile(TileType.YELLOW));
        factory.addTile(new Tile(TileType.BLACK));

        ArrayList<Tile> matchedTiles = factory.getMatchingTiles(TileType.YELLOW);

        assertEquals(1, matchedTiles.size());
        assertEquals(TileType.YELLOW, matchedTiles.get(0).getType());
    }

    @Test
    void takeTiles_tileTypeTaken_true() {
        ArrayList<Tile> tiles = new ArrayList<>();
        Factory factory = new Factory(tiles);
        factory.addTile(new Tile(TileType.BLACK));
        factory.addTile(new Tile(TileType.YELLOW));
        factory.addTile(new Tile(TileType.BLACK));

        factory.takeTiles(TileType.YELLOW);

        assertEquals(2, factory.getTiles().size());
        assertEquals(TileType.BLACK, factory.getTiles().get(0).getType());
    }
}