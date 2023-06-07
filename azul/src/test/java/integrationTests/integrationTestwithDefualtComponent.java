package integrationTests;

import nl.utwente.p4.components.Game;
import nl.utwente.p4.components.PatternLine;
import nl.utwente.p4.components.Tile;
import nl.utwente.p4.constants.TileType;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class integrationTestwithDefualtComponent {
    @Test
    void createPatterLineWithDefault(){
        Game game = Game.getInstance();
        game.getPlayers().clear();
        game.setNumOfPlayers(2);
        game.setTileLineIsExternal(false);
        game.startGame();
        var p1 = game.getPlayers().get(0);
        assertEquals(2,game.getPlayers().size());
        assertEquals(1,p1.getPatternLine().getTileLines().get(0).getLineSize());
        assertEquals(2,p1.getPatternLine().getTileLines().get(1).getLineSize());
        assertEquals(3,p1.getPatternLine().getTileLines().get(2).getLineSize());
        assertEquals(4,p1.getPatternLine().getTileLines().get(3).getLineSize());
        assertEquals(5,p1.getPatternLine().getTileLines().get(4).getLineSize());
    }
    @Test
    void addTilesToPatterLine_Default(){
        Game game = Game.getInstance();
        game.getPlayers().clear();
        game.setNumOfPlayers(2);
        game.setTileLineIsExternal(true);
        game.startGame();
        var p1 = game.getPlayers().get(0);

        ArrayList<Tile> tiles1 = new ArrayList<>();
        tiles1.add(new Tile(TileType.RED));
        tiles1.add(new Tile(TileType.RED));

        ArrayList<Tile> tiles2 = new ArrayList<>();
        tiles2.add(new Tile(TileType.BLACK));
        tiles2.add(new Tile(TileType.BLACK));
        tiles2.add(new Tile(TileType.BLACK));

        p1.getPatternLine().addTiles(tiles1,1);
        p1.getPatternLine().addTiles(tiles2,2);

        assertEquals("RED",p1.getPatternLine().getTileLines().get(1).getTiles().get(0).getType().toString());
        assertEquals("RED",p1.getPatternLine().getTileLines().get(1).getLineType().toString());
        assertEquals("BLACK",p1.getPatternLine().getTileLines().get(2).getTiles().get(0).getType().toString());
        assertEquals("BLACK",p1.getPatternLine().getTileLines().get(2).getLineType().toString());

    }

    @Test
    void addTilesToPatterLine_TileOverFlow_Default(){
        Game game = Game.getInstance();
        game.getPlayers().clear();
        game.setNumOfPlayers(2);
        game.setTileLineIsExternal(true);
        game.startGame();
        var p1 = game.getPlayers().get(0);
        // 2 red tiles to be expected in the floorline
        ArrayList<Tile> tiles1 = new ArrayList<>();
        tiles1.add(new Tile(TileType.RED));
        tiles1.add(new Tile(TileType.RED));
        tiles1.add(new Tile(TileType.RED));
        tiles1.add(new Tile(TileType.RED));
        var eccess = p1.getPatternLine().addTiles(tiles1,1);
        for (var x :eccess) {
            p1.getFloorLine().addTile(x);
        }
        assertEquals("RED",p1.getPatternLine().getTileLines().get(1).getTiles().get(0).getType().toString());
        assertEquals("RED",p1.getFloorLine().getTiles().get(0).getType().toString());
    }

    @Test
    void addTilesToPatterLine_ThenToWall_ThenCleanPatterWall_Default(){
        Game game = Game.getInstance();
        game.getPlayers().clear();
        game.setNumOfPlayers(2);
        game.setTileLineIsExternal(true);
        game.startGame();
        var p1 = game.getPlayers().get(0);
        // 2 red tiles to be expected in the floorline
        ArrayList<Tile> tiles1 = new ArrayList<>();
        tiles1.add(new Tile(TileType.RED));
        tiles1.add(new Tile(TileType.RED));
        p1.getPatternLine().addTiles(tiles1,1);
        if(p1.getPatternLine().isRowFilled(1)){
            p1.getWall().addFromPatterLineToWall(p1.getPatternLine(),1);
        }
        assertEquals("RED",p1.getPatternLine().getTileLines().get(1).getTiles().get(0).getType().toString());
        assertEquals("RED",p1.getPatternLine().getTileLines().get(1).getLineType().toString());
        assertTrue(p1.getWall().isTileFilled(new Tile(TileType.RED),1));

        p1.getPatternLine().clearPatterLineRow(1);

        assertTrue(p1.getPatternLine().getTileLines().get(0).getTiles().isEmpty());

        assertEquals(null,p1.getPatternLine().getTileLines().get(1).getLineType());


    }
}
