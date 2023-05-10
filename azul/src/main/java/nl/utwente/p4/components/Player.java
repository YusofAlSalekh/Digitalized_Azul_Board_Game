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
    public boolean hasFilledRow() {
        // Check for full rows and end the game if a row has exactly 5 tiles
        for(int i=0; i<board.wall.tile.length; i++) {
            int count = 0;
            for(int j=0; j<board.wall.tile[i].length; j++) {
                if(board.wall.tile[i][j] != null) {
                    count++;
                }
            }
            if(count == 5) {
                return true;
            }
        }
    }

public int calculateScore() {
        int TotalBonusPoints = 0;
        int fullRowBonus = 0;
        int fullColumnBonus = 0;
        int colorBonus = 0;
    
        // Check for full rows and end the game if a row has exactly 5 tiles
        for(int i=0; i<board.wall.tile.length; i++) {
            int count = 0;
            for(int j=0; j<board.wall.tile[i].length; j++) {
                if(board.wall.tile[i][j] != null) {
                    count++;
                }
            }
            if(count == 5) {
                fullRowBonus += 2; // Add 2 points for each full row
            }
        }
    
        // Check for full columns
        for(int j=0; j<board.wall.tile[0].length; j++) {
            int count = 0;
            for(int i=0; i<board.wall.tile.length; i++) {
                if(board.wall.tile[i][j] != null) {
                    count++;
                }
            }
            if(count == 5) {
                fullColumnBonus += 7; // Add 7 points for each full column
            }
        }
    
        // Check for color sets
        for(Color color : Color.values()) {
            int count = 0;
            for(int i=0; i<board.wall.tile.length; i++) {
                for(int j=0; j<board.wall.tile[i].length; j++) {
                    if(board.wall.tile[i][j] != null && board.wall.tile[i][j].getColor() == color) {
                        count++;
                    }
                }
            }
            if(count == 5) {
                colorBonus += 10; // Add 10 points for each full color set
            }
        }
    
        // Add up all the bonuses and score
        TotalBonusPoints = fullRowBonus + fullColumnBonus + colorBonus;
        player.addToscoreTrack(TotalBonusPoints);
    
        return TotalBonusPoints;
    }
}
