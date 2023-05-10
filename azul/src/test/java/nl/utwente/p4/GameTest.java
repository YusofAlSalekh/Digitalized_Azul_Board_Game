package nl.utwente.p4;

import nl.utwente.p4.components.Game;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class GameTest {
    @Test
    void startGame_createdTwoPlayers_true() { // methodName_behaviorToBeTested_expectedResult
        // arrange
        Game game = new Game();

        // act
        game.startGame();

        // assert
        // reference for assertion methods: https://junit.org/junit5/docs/5.0.1/api/org/junit/jupiter/api/Assertions.html
        assertNotNull(game.getPlayers());
        assertEquals(2, game.getPlayers().size());
    }
}
