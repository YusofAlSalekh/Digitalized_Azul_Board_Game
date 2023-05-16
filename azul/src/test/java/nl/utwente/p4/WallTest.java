package nl.utwente.p4;

import nl.utwente.p4.components.Tile;
import nl.utwente.p4.components.Wall;
import nl.utwente.p4.constants.TileType;
import org.junit.jupiter.api.Test;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

class WallTest {
    @Test
    void  checkIfWallIsBuildCorrectly(){
        Wall wall = new Wall();
        assertEquals(5, wall.getTiles().length);
    }
       @Test
       void  addTileToWall(){
           Wall wall = new Wall();
           Tile tile = new Tile(TileType.RED);
           wall.addTile(tile, 0);
           assertEquals(tile.getType(), wall.getTiles()[0].get(tile.getType()));
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
        for (Map w : wall.getTiles()) {
            System.out.println(w);
        }
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
        for (Map w : wall.getTiles()) {
            System.out.println(w);
        }
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
        for (Map w : wall.getTiles()) {
            System.out.println(w);
        }
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