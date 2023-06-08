package nl.utwente.p4.unit;

import nl.utwente.p4.components.*;
import nl.utwente.p4.constants.TileType;
import nl.utwente.p4.exceptions.PatternLineFilledException;
import nl.utwente.p4.exceptions.TileColourNotMatchedException;
import nl.utwente.p4.exceptions.TileColourNotMatchedWallTileColourException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class PatternLineTest {

    @Test
    void addTiles_tilesAdded_true() {
        // arrange
        Game.getInstance().setNumOfPlayers(2);
        Game.getInstance().startGame();
        PatternLine patternLine = new PatternLine();
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
        ArrayList<Tile> excess1 = patternLine.addTiles(tiles, 0);
        ArrayList<Tile> excess2 = patternLine.addTiles(tiles2, 4);

        // Test that correct amount of tiles is returned as excess tiles
        assertEquals(2, excess1.size());
        assertEquals(0, excess2.size());
        // Test that correct amount of tiles is in correct rows
        assertEquals(1, patternLine.getTileLines().get(0).getTiles().size());
        assertEquals(4, patternLine.getTileLines().get(4).getTiles().size());
    }

    @Test
    void addTilesToTheTileLineWhenTtIsAlreadyFilled() {
        // arrange
        Game.getInstance().setNumOfPlayers(2);
        Game.getInstance().startGame();
        PatternLine patternLine = new PatternLine();
        ArrayList<Tile> tiles = new ArrayList<>();
        tiles.add(new Tile(TileType.BLACK));
        tiles.add(new Tile(TileType.BLACK));
        tiles.add(new Tile(TileType.BLACK));

        ArrayList<Tile> tiles2 = new ArrayList<>();
        tiles2.add(new Tile(TileType.WHITE));
        tiles2.add(new Tile(TileType.WHITE));
        tiles2.add(new Tile(TileType.WHITE));
        tiles2.add(new Tile(TileType.WHITE));

        // make the tile lines filled

        patternLine.addTiles(tiles, 2);
        patternLine.addTiles(tiles2, 3);

        // Test that tile lines are filled
        assertEquals(3, patternLine.getTileLines().get(2).getTiles().size());
        assertEquals(4, patternLine.getTileLines().get(3).getTiles().size());


        // Test that there will be an exception when we try to add more tiles to the filled tile lines

        assertThrows(PatternLineFilledException.class, () -> patternLine.addTiles(tiles, 2));
        assertThrows(PatternLineFilledException.class, () -> patternLine.addTiles(tiles, 3));
    }

    @Test
    void AddTilesOfOneColourToTheTileLineWhenItAlreadyContainsTilesOfDifferentColour() {
        // arrange
        Game.getInstance().setNumOfPlayers(2);
        Game.getInstance().startGame();
        PatternLine patternLine = new PatternLine();
        ArrayList<Tile> tiles = new ArrayList<>();
        tiles.add(new Tile(TileType.BLACK));
        tiles.add(new Tile(TileType.BLACK));

        // fill fourth row with two black tiles
        patternLine.addTiles(tiles, 3);

        // Test that fourth row contains two tiles
        assertEquals(2, patternLine.getTileLines().get(3).getTiles().size());

        // Test that there will be an exception when we try to Add Tiles Of other colours
        ArrayList<Tile> tiles2 = new ArrayList<>();
        tiles2.add(new Tile(TileType.BLUE));

        assertThrows(TileColourNotMatchedException.class,
                () -> patternLine.addTiles(tiles2, 3));
    }

    @Test
    void addTilesToTheTileLineWhenTileOfThisColorIsAlreadyOnTheWall() {
        // arrange
        Game.getInstance().setNumOfPlayers(2);
        Game.getInstance().startGame();
        Game.getInstance().getCurrentPlayer().getWall().addTile(new Tile(TileType.BLACK), 4);

        PatternLine patternLine = new PatternLine();
        ArrayList<Tile> tiles = new ArrayList<>();
        tiles.add(new Tile(TileType.BLACK));
        tiles.add(new Tile(TileType.BLACK));
        tiles.add(new Tile(TileType.BLACK));

        //Test that there will be an exception when we try to Add Tiles Of colours that the wall already contains
        assertThrows(TileColourNotMatchedWallTileColourException.class,
                () -> patternLine.addTiles(tiles, 4));
    }
    @Test
    void checkIfPatterLineRowIsFilled() {
        // arrange
        Game.getInstance().setNumOfPlayers(2);
        Game.getInstance().startGame();
        PatternLine patternLine = new PatternLine();
        ArrayList<Tile> tiles = new ArrayList<>();
        tiles.add(new Tile(TileType.BLACK));

        // act
        patternLine.addTiles(tiles, 0);
        assertTrue(patternLine.isRowFilled(0));
       }
    @Test
    void resetPatterLineRow() {
        // arrange
        Game.getInstance().setNumOfPlayers(2);
        Game.getInstance().startGame();
        PatternLine patternLine = new PatternLine();
        ArrayList<Tile> tiles = new ArrayList<>();
        tiles.add(new Tile(TileType.BLACK));

        // act
        patternLine.addTiles(tiles, 0);
        assertEquals(1,patternLine.getTileLines().get(0).getTiles().size());
        patternLine.clearPatterLineRow(0);
        assertEquals(0,patternLine.getTileLines().get(0).getTiles().size());
    }

}
