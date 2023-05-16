package nl.utwente.p4;

import nl.utwente.p4.components.PatternLine;
import nl.utwente.p4.components.Tile;
import nl.utwente.p4.components.Wall;
import nl.utwente.p4.constants.TileType;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

class WallTest {
    @Test
    void  checkIfWallIsBuildCorrectly(){
        Wall wall = new Wall();
        assertEquals(5, wall.getTiles().length);
    }
    //This is the brute force way of adding tiles to the wall
       @Test
       void  addTileToWallBrute(){
           Wall wall = new Wall();
           Tile tile = new Tile(TileType.RED);
           wall.addTile(tile, 0);
           assertEquals(tile.getType(), wall.getTiles()[0].get(tile.getType()));
       }
    //This is the correct way of adding tiles to the wall
    @Test
    void  addTileToWallCorrect(){
        PatternLine p = new PatternLine();
        Wall wall = new Wall();
        ArrayList<Tile> tiles1 = new ArrayList<>();
        Tile tile1 = new Tile(TileType.RED);
        tiles1.add(tile1);

        ArrayList<Tile> tiles2 = new ArrayList<>();
        Tile tile2 = new Tile(TileType.BLACK);
        tiles2.add(tile2);
        tiles2.add(tile2);

        ArrayList<Tile> tiles3 = new ArrayList<>();
        Tile tile3 = new Tile(TileType.YELLOW);
        tiles3.add(tile3);
        tiles3.add(tile3);
        tiles3.add(tile3);

        p.addTiles(tiles1,0,wall);
        p.addTiles(tiles2,1,wall);
        p.addTiles(tiles3,2,wall);

        wall.addFromPatterLineToWall(p);

        assertEquals(tile1.getType(), wall.getTiles()[0].get(tile1.getType()));
        assertEquals(tile2.getType(), wall.getTiles()[1].get(tile2.getType()));
        assertEquals(tile3.getType(), wall.getTiles()[2].get(tile3.getType()));
    }

    @Test
    void  checkIfTileIsFilled(){
        Wall wall = new Wall();
        Tile tile = new Tile(TileType.RED);
        wall.addTile(tile, 2);
        assertTrue(wall.isTileFilled(tile,2));
    }
    @Test
    void  checkIfRowIsFilled(){
        Wall wall = new Wall();
        wall.addTile(new Tile(TileType.BLUE), 0);
        wall.addTile(new Tile(TileType.WHITE), 0);
        wall.addTile(new Tile(TileType.RED), 0);
        wall.addTile(new Tile(TileType.BLACK), 0);
        wall.addTile(new Tile(TileType.YELLOW), 0);

        assertTrue(wall.isRowFilled(0));
    }
    @Test
    void  checkIfColumnIsFilled_Pass(){
        Wall wall = new Wall();
        wall.addTile(new Tile(TileType.WHITE), 0);
        wall.addTile(new Tile(TileType.BLACK), 1);
        wall.addTile(new Tile(TileType.RED), 2);
        wall.addTile(new Tile(TileType.YELLOW), 3);
        wall.addTile(new Tile(TileType.BLUE), 4);

        assertTrue(wall.isColumnFilled(4));
    }
    @Test
    void  checkIfColumnIsFilled_Fail(){
        Wall wall = new Wall();
        wall.addTile(new Tile(TileType.BLUE), 0);
        wall.addTile(new Tile(TileType.WHITE), 1);
        wall.addTile(new Tile(TileType.BLACK), 2);
        wall.addTile(new Tile(TileType.RED), 3);
        wall.addTile(new Tile(TileType.YELLOW), 2);

        assertFalse(wall.isColumnFilled(0));
    }
    @Test
    void checkForDuplicateTile(){
        Wall wall = new Wall();
        wall.addTile(new Tile(TileType.YELLOW), 0);
        wall.addTile(new Tile(TileType.YELLOW), 0);
        assertEquals(1,wall.getTotalScore());
    }
    @Test
    void  countHorizontally(){
        Wall wall = new Wall();
        wall.addTile(new Tile(TileType.WHITE), 0);
        wall.addTile(new Tile(TileType.RED), 0);
        wall.addTile(new Tile(TileType.BLACK), 0);
        wall.addTile(new Tile(TileType.YELLOW), 0);
        assertEquals(9,wall.getTotalScore());
    }
    @Test
    void  countVertically(){
        Wall wall = new Wall();
        wall.addTile(new Tile(TileType.BLUE), 1);
        wall.addTile(new Tile(TileType.YELLOW), 0);
        wall.addTile(new Tile(TileType.WHITE), 2);
        wall.addTile(new Tile(TileType.BLACK), 3);
        assertEquals(10,wall.getTotalScore());
    }
    @Test
    void  checkWallScoreP12(){
        Wall wall = new Wall();
        wall.addTile(new Tile(TileType.WHITE), 0);
        wall.addTile(new Tile(TileType.RED), 0);
        wall.addTile(new Tile(TileType.BLACK), 0);
        wall.addTile(new Tile(TileType.YELLOW), 0);
        wall.addTile(new Tile(TileType.BLUE), 1);
        wall.addTile(new Tile(TileType.WHITE), 3);
        assertEquals(12,wall.getTotalScore());
    }
    @Test
    void  checkWallScoreP20(){
        Wall wall = new Wall();
        wall.addTile(new Tile(TileType.WHITE), 0);
        wall.addTile(new Tile(TileType.RED), 0);
        wall.addTile(new Tile(TileType.BLACK), 0);
        wall.addTile(new Tile(TileType.YELLOW), 0);
        wall.addTile(new Tile(TileType.BLUE), 1);
        wall.addTile(new Tile(TileType.RED), 4);
        wall.addTile(new Tile(TileType.WHITE), 2);
        wall.addTile(new Tile(TileType.BLACK), 3);
        assertEquals(20,wall.getTotalScore());
    }

    @Test
    void checkIFScoreP0() {
        Wall wall = new Wall();
        wall.addTile(new Tile(TileType.WHITE), 0);
        wall.deductScoreFromFloorLine(15);
        assertEquals(0,wall.getTotalScore());
    }
}