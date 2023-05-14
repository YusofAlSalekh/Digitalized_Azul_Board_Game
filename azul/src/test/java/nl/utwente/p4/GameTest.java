package nl.utwente.p4;

import nl.utwente.p4.components.*;
import nl.utwente.p4.constants.TileType;
import org.junit.jupiter.api.Test;


import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class GameTest {
    @Test
    void startGame_createdTwoPlayers_true() { // methodName_behaviorToBeTested_expectedResult
        // arrange
        Game game = Game.getInstance();

        // act
        game.startGame();

        // assert
        // reference for assertion methods: https://junit.org/junit5/docs/5.0.1/api/org/junit/jupiter/api/Assertions.html
        assertNotNull(game.getPlayers());
        assertEquals(2, game.getPlayers().size());
    }

    @Test
    void pickTilesFromFactory() {

        // arrange
        Game game = Game.getInstance();

        Player player = new Player();
        ArrayList<Player> players = new ArrayList<>();
        players.add(player);
        game.setPlayers(players);

        ArrayList<Tile> tiles = new ArrayList<>();
        tiles.add(new Tile(TileType.BLACK));
        tiles.add(new Tile(TileType.BLACK));
        tiles.add(new Tile(TileType.RED));
        tiles.add(new Tile(TileType.BLUE));

        //Put 2 Black, 1 Red and 1 Blue tiles in the factory with an index of 0
        Factory factory = new Factory(tiles);
        ArrayList<Factory> factories = new ArrayList<>();
        factories.add(factory);
        game.setFactories(factories);

        TileTable tileTable = new TileTable();
        game.setTileTable(tileTable);

        //Testing a method pickTilesFromFactory
        //in which we take Black tiles from the factory with an index of 0 and place them in the first row
        game.pickTilesFromFactory(0, TileType.BLACK, player, 0);

        //Testing that after applying pickTilesFromFactory
        //there is only one tile in the first row
        assertEquals(1, player.getBoard().getPatternLine().getTileLines().get(0).getTiles().size());

        //Testing that after applying pickTilesFromFactory
        //3 tiles will be in the tile table(FIRST_PLAYER,Red and Blue)
        assertEquals(3, tileTable.getTiles().size());

        //Testing that 1 excess Black tile goes to the floor line
        assertEquals(1, player.getBoard().getFloorLine().getTiles().size());
    }
}
