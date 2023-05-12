package nl.utwente.p4;

import nl.utwente.p4.components.Factory;
import nl.utwente.p4.components.Game;
import nl.utwente.p4.components.Tile;
import nl.utwente.p4.constants.TileType;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class GameTest {
    @Test
    void startGame_createdTwoPlayers_true() { // methodName_behaviorToBeTested_expectedResult
        // arrange and reset player count
        Game game = Game.getInstance();
        game.setPlayers(new ArrayList<>());

        // act
        game.startGame();

        // assert
        // reference for assertion methods: https://junit.org/junit5/docs/5.0.1/api/org/junit/jupiter/api/Assertions.html
        assertNotNull(game.getPlayers());
        assertEquals(2, game.getPlayers().size());
    }

    @Test
    void prepareNextRound_nextRoundPreparedWithFullFactoriesFromGameBoxLid_true() { // methodName_behaviorToBeTested_expectedResult
        // Clear tilebag and factories
        Game game = Game.getInstance();
        game.setPlayers(new ArrayList<>());
        game.startGame();
        game.getTileBag().getAndRemoveTiles();
        for (Factory f : game.getFactories()) {
            f.takeAllTiles();
        }

        // Fill game box lid with tiles
        ArrayList<Tile> tiles = new ArrayList<>();
        for (int i=0; i<20; i++) {
            tiles.add(new Tile(TileType.WHITE));
        }
        game.addTilesToGameBoxLid(tiles);

        // act
        game.prepareNextRound();

        // Check that all factories are full and game box lid is empty
        assertEquals(4, game.getFactories().get(0).getTiles().size());
        assertEquals(4, game.getFactories().get(1).getTiles().size());
        assertEquals(4, game.getFactories().get(2).getTiles().size());
        assertEquals(4, game.getFactories().get(3).getTiles().size());
        assertEquals(4, game.getFactories().get(4).getTiles().size());
        assertEquals(0, game.getGameBoxLid().getTiles().size());

    }
}
