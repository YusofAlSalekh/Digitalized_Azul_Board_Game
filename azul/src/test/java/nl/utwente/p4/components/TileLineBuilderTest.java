package nl.utwente.p4.components;

import core.gamestate.TileLineAdapter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TileLineBuilderTest {
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