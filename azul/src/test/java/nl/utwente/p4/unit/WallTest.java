package nl.utwente.p4.unit;

import nl.utwente.p4.components.*;
import nl.utwente.p4.constants.TileType;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

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
        Game.getInstance().setNumOfPlayers(2);
        Game.getInstance().startGame();
        PatternLine p = new PatternLine();
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

        p.addTiles(tiles1,0);
        p.addTiles(tiles2,1);
        p.addTiles(tiles3,2);

        Wall currentWall = Game.getInstance().getCurrentPlayer().getWall();
        currentWall.addFromPatterLineToWall(p,0);

        assertEquals(tile1.getType(), currentWall.getTiles()[0].get(tile1.getType()));
        assertEquals(tile2.getType(), currentWall.getTiles()[1].get(tile2.getType()));
        assertEquals(tile3.getType(), currentWall.getTiles()[2].get(tile3.getType()));
    }

    //test with a player instance
    // note the  player1.getFloorLine().getTotalFloorScore() is assumed to be 0 in this test
    @Test
    void testWallWithPlayerInstance(){
        ArrayList<Player> players = new ArrayList<>();
        players.add(new Player());
        players.add(new Player());
        Game.getInstance().setPlayers(players);
        Game.getInstance().setNumOfPlayers(2);
        Game.getInstance().startGame();
        
        Player currentPlayer = Game.getInstance().getCurrentPlayer();
        Wall wall = currentPlayer.getWall();
        PatternLine patternLine = currentPlayer.getPatternLine();

        ArrayList<Tile> tiles1 = new ArrayList<>();
        Tile tile1 = new Tile(TileType.RED);
        tiles1.add(tile1);

        ArrayList<Tile> tiles2 = new ArrayList<>();
        Tile tile2 = new Tile(TileType.BLACK);
        tiles2.add(tile2);
        tiles2.add(tile2);

        ArrayList<Tile> tiles3 = new ArrayList<>();
        Tile tile3 = new Tile(TileType.RED);
        tiles3.add(tile3);
        tiles3.add(tile3);
        tiles3.add(tile3);

        patternLine.addTiles(tiles1,0);
        patternLine.addTiles(tiles2,1);
        patternLine.addTiles(tiles3,2);

        int score = wall.addFromPatterLineToWall(patternLine , currentPlayer.getFloorLine().getTotalFloorScore());
        currentPlayer.addScore(score);

        assertEquals(4 , currentPlayer.getScoreTrack());
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