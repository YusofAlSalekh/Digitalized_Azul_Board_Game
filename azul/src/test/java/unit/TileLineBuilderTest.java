package unit;

import core.gamestate.TileLineAdapter;
import nl.utwente.p4.components.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class TileLineBuilderTest {
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
    void createTileLineBuilder() {
        TileLineBuilder tileLineBuilder = new TileLineBuilder();
        assertNotNull(tileLineBuilder);
    }

    @Test
    void createTileLine_useTileLine() {
        Game.getInstance().setTileLineIsExternal(false);

        GeneralTileLine tileLine = TileLineBuilder.createTileLine(1);

        assertTrue(tileLine instanceof TileLine);
        assertEquals(1, tileLine.getLineSize());
    }

    @Test
    void createTileLine_useTileLineAdapter() {
        Game.getInstance().setTileLineIsExternal(true);

        GeneralTileLine tileLine = TileLineBuilder.createTileLine(1);

        assertTrue(tileLine instanceof TileLineAdapter);
        assertEquals(1, tileLine.getLineSize());
    }
}