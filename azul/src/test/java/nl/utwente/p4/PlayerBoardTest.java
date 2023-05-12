package nl.utwente.p4;

import nl.utwente.p4.components.PatternLine;
import nl.utwente.p4.components.PlayerBoard;
import nl.utwente.p4.components.Tile;
import nl.utwente.p4.constants.TileType;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlayerBoardTest {
    @Test
    void addTiles_tilesAdded_true() {
        // arrange
        PlayerBoard playerBoard = new PlayerBoard();
        ArrayList<Tile> tiles = new ArrayList<>();
        tiles.add(new Tile(TileType.BLACK));
        tiles.add(new Tile(TileType.BLACK));
        tiles.add(new Tile(TileType.BLACK));

        ArrayList<Tile> tiles2 = new ArrayList<>();
        tiles2.add(new Tile(TileType.WHITE));
        tiles2.add(new Tile(TileType.WHITE));
        tiles2.add(new Tile(TileType.WHITE));
        tiles2.add(new Tile(TileType.WHITE));

        // act
        playerBoard.addTiles(tiles, 0);
        playerBoard.addTiles(tiles2, 4);

        // Test that correct lines are filled the right amount
        assertEquals(1, playerBoard.getPatternLine().getTileLines().get(0).getTiles().size());
        assertEquals(4, playerBoard.getPatternLine().getTileLines().get(4).getTiles().size());
        // Test that floor contains all excess tiles
        assertEquals(2, playerBoard.getFloorLine().getTiles().size());
    }
}
