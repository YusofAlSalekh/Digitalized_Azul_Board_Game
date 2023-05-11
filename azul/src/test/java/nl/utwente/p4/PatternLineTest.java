package nl.utwente.p4;

import nl.utwente.p4.components.FloorLine;
import nl.utwente.p4.components.PatternLine;
import nl.utwente.p4.components.Tile;
import nl.utwente.p4.constants.TileType;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PatternLineTest {

    @Test
    void addTiles_tilesAdded_true() {
        // arrange
        PatternLine patternLine = new PatternLine();
        ArrayList<Tile> tiles = new ArrayList<>();
        tiles.add(new Tile(TileType.BLACK));
        tiles.add(new Tile(TileType.BLACK));
        tiles.add(new Tile(TileType.BLACK));

        ArrayList<Tile> tiles2 = new ArrayList<>();
        tiles2.add(new Tile(TileType.GREEN));
        tiles2.add(new Tile(TileType.GREEN));
        tiles2.add(new Tile(TileType.GREEN));
        tiles2.add(new Tile(TileType.GREEN));

        // act
        ArrayList<Tile> excess1 = patternLine.addTiles(tiles, 0);
        ArrayList<Tile> excess2 = patternLine.addTiles(tiles2, 4);

        // Test that correct amount of tiles is returned as excess tiles
        assertEquals(2, excess1.size());
        assertEquals(0, excess2.size());
        // Test that correct amount of tiles is in correct rows
        assertEquals(1, patternLine.getTileLines().get(0).getTiles().size());
        assertEquals(4, patternLine.getTileLines().get(4).getTiles().size());
    }
}
