package nl.utwente.p4.components;

import nl.utwente.p4.components.*;
import nl.utwente.p4.constants.TileType;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {
    @Test
    void getFactoryOfferFromTileTable_factoryOfferTakenFromTable_true() {
        // arrange
        Player player = new Player();
        ArrayList<Player> players = new ArrayList<>();
        players.add(player);
        Game.getInstance().setPlayers(players);
        Game.getInstance().setCurrPlayerIdx(0);
        TileTable tileTable = Game.getInstance().getTileTable();

        tileTable.addTile(new Tile(TileType.BLACK));
        tileTable.addTile(new Tile(TileType.BLACK));
        tileTable.addTile(new Tile(TileType.BLACK));
        tileTable.addTile(new Tile(TileType.YELLOW));
        tileTable.addTile(new Tile(TileType.WHITE));

        // Get black tiles from tile table and add to row index 1, which has size 2
        player.getFactoryOfferFromTileTable(new Tile(TileType.BLACK), 1);

        // Test that player floorline contains correct tile amount and first player tile
        assertEquals(2, player.getFloorLine().getTiles().size());
        assertEquals(TileType.FIRST_PLAYER, player.getFloorLine().getTiles().get(0).getType());
        // Test that player board correct row contains right amount of tiles
        assertEquals(2, player.getPatternLine().getTileLines().get(1).getTiles().size());
    }


    // kept the name playerBoard so to know that this part was separate component before
    @Test
    void addTiles_tilesAdded_true() {
        // arrange
        Player playerBoard = new Player();
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

    @Test
    void addTilesToFillGameBox_tilesAddedToGameBox_true() {
        // Clear game box lid first
        Game game = Game.getInstance();
        game.setNumOfPlayers(2);
        game.startGame();
        Game.getInstance().getGameBoxLid().getAndRemoveTiles();

        // arrange
        Player playerBoard = new Player();
        ArrayList<Tile> tiles = new ArrayList<>();
        tiles.add(new Tile(TileType.BLACK));
        tiles.add(new Tile(TileType.BLACK));
        tiles.add(new Tile(TileType.BLACK));
        tiles.add(new Tile(TileType.BLACK));
        tiles.add(new Tile(TileType.BLACK));
        tiles.add(new Tile(TileType.BLACK));
        tiles.add(new Tile(TileType.BLACK));
        tiles.add(new Tile(TileType.BLACK));
        tiles.add(new Tile(TileType.BLACK));

        // act
        playerBoard.addTiles(tiles, 0);

        // Test that correct lines are filled the right amount, row 1 size 1
        assertEquals(1, playerBoard.getPatternLine().getTileLines().get(0).getTiles().size());
        // Test that floor contains all excess tiles, so a full 7 length row
        assertEquals(7, playerBoard.getFloorLine().getTiles().size());
        // Test that game box lid contains all excess tiles from floor, so a 1 tile
        assertEquals(1, Game.getInstance().getGameBoxLid().getTiles().size());
    }

    @Test
    void getFactoryOfferFromFactory() {

        // arrange
        Game game = Game.getInstance();
        game.setNumOfPlayers(2);
        game.startGame();
        game.setGameBoxLid(new GameBoxLid());
        game.setTileTable(new TileTable());
        Player player = game.getCurrentPlayer();

        ArrayList<Tile> tiles = new ArrayList<>();
        tiles.add(new Tile(TileType.BLACK));
        tiles.add(new Tile(TileType.BLACK));
        tiles.add(new Tile(TileType.BLACK));
        tiles.add(new Tile(TileType.BLUE));

        //Put 3 Black and 1 Blue tiles in the factory with an index of 0
        Factory factory = new Factory(tiles);
        ArrayList<Factory> factories = new ArrayList<>();
        factories.add(factory);

        ArrayList<Tile> tiles1 = new ArrayList<>();
        tiles1.add(new Tile(TileType.BLUE));
        tiles1.add(new Tile(TileType.BLACK));
        tiles1.add(new Tile(TileType.BLACK));
        tiles1.add(new Tile(TileType.YELLOW));
        tiles1.add(new Tile(TileType.RED));
        tiles1.add(new Tile(TileType.BLUE));

        FloorLine floorLine = new FloorLine();
        //Put 6 tiles to the floor line
        floorLine.setTiles(tiles1);
        player.setFloorLine(floorLine);

        //Testing that floor line contains 6 tiles
        assertEquals(6, player.getFloorLine().getTiles().size());

        TileTable tileTable = Game.getInstance().getTileTable();

        //Testing a method getFactoryOfferFromFactory
        //in which we take Black tiles from the factory with an index of 0 and place them in the first row
        player.getFactoryOfferFromFactory(factories.get(0), TileType.BLACK, 0);

        //Testing that after applying getFactoryOfferFromFactory
        //there is only one tile in the first row
        assertEquals(1, player.getPatternLine().getTileLines().get(0).getTiles().size());

        //Testing that after applying getFactoryOfferFromFactory
        //2 tiles will be in the tile table(FIRST_PLAYER and Blue)
        assertEquals(2, tileTable.getTiles().size());

        //Testing that 1 of two excess Black tiles goes to the floor line and now there are 7 tiles in the floor line
        assertEquals(7, player.getFloorLine().getTiles().size());

        //Testing that the third Black tile goes to Box lid
        assertEquals(1, game.getGameBoxLid().getTiles().size());
        //Testing if player is first to go
        assertFalse( game.getPlayers().get(0).getFirstPlayer());
    }
    @Test
    void setScore(){
        Player player = new Player();
        player.setScoreTrack(200);
        assertEquals(200,player.getScoreTrack());
    }
    @Test
    void calculateFinalScore(){
        Player player = new Player();
        assertEquals(0,player.calculateFinalScore());
        // wall tiles horizantally
        player.getWall().addTile(new Tile(TileType.BLUE),0);
        player.getWall().addTile(new Tile(TileType.RED),0);
        player.getWall().addTile(new Tile(TileType.WHITE),0);
        player.getWall().addTile(new Tile(TileType.YELLOW),0);
        player.getWall().addTile(new Tile(TileType.BLACK),0);
        // wall tiles vertically
        player.getWall().addTile(new Tile(TileType.WHITE),1);
        player.getWall().addTile(new Tile(TileType.BLACK),2);
        player.getWall().addTile(new Tile(TileType.RED),3);
        player.getWall().addTile(new Tile(TileType.YELLOW),4);
        // wall tiles set
        player.getWall().addTile(new Tile(TileType.BLACK), 1);
        player.getWall().addTile(new Tile(TileType.BLACK), 3);
        player.getWall().addTile(new Tile(TileType.BLACK), 4);

        assertEquals(19,player.calculateFinalScore());
    }
    @Test
    void calculateFloorLineScore(){
        Player player = new Player();
        player.calculateFloorLineScore();
    }
    @Test
    void checkIfRowIsFilled_True(){
        Player player = new Player();
        player.getWall().addTile(new Tile(TileType.BLUE),0);
        player.getWall().addTile(new Tile(TileType.RED),0);
        player.getWall().addTile(new Tile(TileType.BLACK),0);
        player.getWall().addTile(new Tile(TileType.WHITE),0);
        player.getWall().addTile(new Tile(TileType.YELLOW),0);
        assertTrue(player.hasFilledRow());
    }
    @Test
    void checkIfRowIsFilled_false(){
        Player player = new Player();
        assertFalse(player.hasFilledRow());
    }

    @Test
    void completeHorizontalLines_none() {
        Player player = new Player();
        assertEquals(0, player.completeHorizontalLines());
    }

    @Test
    void completeHorizontalLines_exists() {
        Player player = new Player();
        player.getWall().addTile(new Tile(TileType.BLUE),0);
        player.getWall().addTile(new Tile(TileType.RED),0);
        player.getWall().addTile(new Tile(TileType.WHITE),0);
        player.getWall().addTile(new Tile(TileType.YELLOW),0);
        player.getWall().addTile(new Tile(TileType.BLACK),0);
        player.getWall().addTile(new Tile(TileType.BLUE),1);
        player.getWall().addTile(new Tile(TileType.RED),1);
        player.getWall().addTile(new Tile(TileType.WHITE),1);
        player.getWall().addTile(new Tile(TileType.YELLOW),1);
        player.getWall().addTile(new Tile(TileType.BLACK),1);
        assertEquals(2, player.completeHorizontalLines());
    }

    @Test
    void addFloorLineFromFactory() {
        // arrange
        Game game = Game.getInstance();
        game.setGameBoxLid(new GameBoxLid());
        game.setTileTable(new TileTable());
        Player player = new Player();

        ArrayList<Tile> tiles = new ArrayList<>();
        tiles.add(new Tile(TileType.BLACK));
        tiles.add(new Tile(TileType.BLACK));
        tiles.add(new Tile(TileType.BLACK));
        tiles.add(new Tile(TileType.BLUE));
        Factory factory = new Factory(tiles);

        TileTable tileTable = Game.getInstance().getTileTable();

        // act
        player.addFloorLineFromFactory(factory, TileType.BLACK);

        // assert
        assertEquals(3, player.getFloorLine().getTiles().size());
        assertEquals(2, tileTable.getTiles().size());
        assertEquals(0, factory.getTiles().size());
    }

    @Test
    void addFloorLineFromTileTable() {
        // arrange
        Game game = Game.getInstance();
        game.setGameBoxLid(new GameBoxLid());
        Player player = new Player();

        TileTable tileTable = new TileTable();
        tileTable.addTile(new Tile(TileType.BLACK));
        tileTable.addTile(new Tile(TileType.BLACK));
        tileTable.addTile(new Tile(TileType.BLACK));
        tileTable.addTile(new Tile(TileType.YELLOW));
        tileTable.addTile(new Tile(TileType.WHITE));
        Game.getInstance().setTileTable(tileTable);

        // act
        player.addFloorLineFromTileTable(new Tile(TileType.BLACK));

        // assert
        assertEquals(4, player.getFloorLine().getTiles().size());
        assertEquals(TileType.FIRST_PLAYER, player.getFloorLine().getTiles().get(0).getType());
        assertEquals(2, tileTable.getTiles().size());
    }
}
