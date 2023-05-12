package nl.utwente.p4;

import nl.utwente.p4.components.Player;
import nl.utwente.p4.components.Tile;
import nl.utwente.p4.components.TileLine;
import nl.utwente.p4.components.TileTable;
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
        assertEquals(2, player.getBoard().getFloorLine().getTiles().size());
        assertEquals(TileType.FIRST_PLAYER, player.getBoard().getFloorLine().getTiles().get(0).getType());
        // Test that player board correct row contains right amount of tiles
        assertEquals(2, player.getBoard().getPatternLine().getTileLines().get(1).getTiles().size());
    }
}
