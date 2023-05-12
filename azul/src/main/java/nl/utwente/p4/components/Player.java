package nl.utwente.p4.components;

import lombok.Data;
import nl.utwente.p4.constants.TileType;

import java.util.ArrayList;

@Data
public class Player {
    private PlayerBoard board;

    private Boolean firstPlayer;

    public Player() {
        this.board = new PlayerBoard();
        this.firstPlayer = false;
    }

    /**
     * Calculates the players boards floor lines score and adjusts the players boards score accordingly.
     * Also sets the player as first player if a first player was found on the floor line.
     */
    public void calculateFloorLineScore() {
        Boolean foundFirstPlayerTile = this.board.calculateFloorLineScore();
        this.firstPlayer = foundFirstPlayerTile;
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
