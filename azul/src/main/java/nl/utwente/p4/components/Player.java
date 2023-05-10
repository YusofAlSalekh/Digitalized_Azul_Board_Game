package nl.utwente.p4.components;

import lombok.Data;
import nl.utwente.p4.constants.TileType;

import java.util.ArrayList;

@Data
public class Player {
    private PlayerBoard board;

    public Player() {
        this.board = new PlayerBoard();
    }

    /***
     * Method to get given tiles from the tiletable
     * @param tileTable table to take tiles from
     * @param pickedTile tile to be picked
     * @param rowNum row number to add picked tiles to
     */
    public void getFactoryOfferFromTileTable(TileTable tileTable, Tile pickedTile, Integer rowNum) {
        ArrayList<Tile> tilesFromTable = tileTable.takeTiles(pickedTile.getType());
        this.board.addTiles(tilesFromTable, rowNum);
    }
}
