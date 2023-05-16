package nl.utwente.p4;

import nl.utwente.p4.components.*;
import nl.utwente.p4.constants.TileType;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlayerTest {
    @Test
    void getFactoryOfferFromTileTable_factoryOfferTakenFromTable_true() {
        // arrange
        Player player = new Player();
        TileTable tileTable = new TileTable();

        tileTable.addTile(new Tile(TileType.BLACK));
        tileTable.addTile(new Tile(TileType.BLACK));
        tileTable.addTile(new Tile(TileType.BLACK));
        tileTable.addTile(new Tile(TileType.YELLOW));
        tileTable.addTile(new Tile(TileType.WHITE));

        // Get black tiles from tile table and add to row index 1, which has size 2
        player.getFactoryOfferFromTileTable(tileTable, new Tile(TileType.BLACK), 1);

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
}
