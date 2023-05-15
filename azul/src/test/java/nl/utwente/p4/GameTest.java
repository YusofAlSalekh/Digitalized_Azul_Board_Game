package nl.utwente.p4;

import nl.utwente.p4.components.*;
import nl.utwente.p4.constants.TileType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {
    @Test
    void startGame_gameSetup_true() { // methodName_behaviorToBeTested_expectedResult
        // arrange
        Game game = Game.getInstance();
        game.setPlayers(new ArrayList<>());
        game.setTileBag(new TileBag());
        game.setFactories(new ArrayList<>());
        game.setNumOfPlayers(2);
        int numOfFactories = 2 * game.getNumOfPlayers() + 1;

        // act
        game.startGame();

        // assert
        // reference for assertion methods: https://junit.org/junit5/docs/5.0.1/api/org/junit/jupiter/api/Assertions.html
        // assert player
        assertNotNull(game.getPlayers());
        assertEquals(game.getNumOfPlayers(), game.getPlayers().size());

        // assert tile bag
        assertNotNull(game.getTileBag());
        assertNotNull(game.getTileBag().getTiles());
        assertEquals(100 - (4 * numOfFactories), game.getTileBag().getTiles().size());  // not 100 bcos 20 have been added to factories

        // assert factories
        assertNotNull(game.getFactories());
        assertEquals(numOfFactories, game.getFactories().size());
    }

    @Test
    void prepareNextRound_resetFirstHasBeenTaken_firstHasBeenTakenFalse() {
        // arrange
        Game game = Game.getInstance();
        game.getTileTable().setFirstHasBeenTaken(true);

        // act
        game.prepareNextRound();

        // assert
        assertFalse(game.getTileTable().isFirstHasBeenTaken());
    }

    @Test
    void prepareNextRound_refillFactories_completelyRefilled() {
        // arrange
        Game game = Game.getInstance();
        int numOfFactories = 5;

        TileBag tileBag = new TileBag();
        for (int i = 0; i < numOfFactories * 4; i++) {
            tileBag.getTiles().add(new Tile(TileType.BLACK));
        }
        game.setTileBag(tileBag);

        ArrayList<Factory> factories = new ArrayList<>();
        for (int i = 0; i < numOfFactories; i++) {
            factories.add(new Factory(new ArrayList<>()));
        }
        game.setFactories(factories);

        // act
        game.prepareNextRound();

        // assert tile bag
        assertEquals(0, game.getTileBag().getTiles().size());

        // assert factories
        for (int i = 0; i < game.getFactories().size(); i++) {
            assertEquals(4, game.getFactories().get(i).getTiles().size());
        }
    }

    @Test
    void prepareNextRound_refillFactories_notEnoughTiles() {
        // arrange
        Game game = Game.getInstance();
        int numOfFactories;

        numOfFactories = 2;
        TileBag tileBag = new TileBag();
        for (int i = 0; i < numOfFactories * 4; i++) {
            tileBag.getTiles().add(new Tile(TileType.BLACK));
        }
        game.setTileBag(tileBag);

        numOfFactories = 5;
        ArrayList<Factory> factories = new ArrayList<>();
        for (int i = 0; i < numOfFactories; i++) {
            factories.add(new Factory(new ArrayList<>()));
        }
        game.setFactories(factories);

        // act
        game.prepareNextRound();

        // assert tile bag
        assertEquals(0, game.getTileBag().getTiles().size());

        // assert factories
        for (int i = 0; i < 2; i++) {
            assertEquals(4, game.getFactories().get(i).getTiles().size());
        }
        for (int i = 2; i < game.getFactories().size(); i++) {
            assertEquals(0, game.getFactories().get(i).getTiles().size());
        }
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
