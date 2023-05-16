package nl.utwente.p4.components;

import lombok.Data;
import nl.utwente.p4.constants.TileType;

import java.util.ArrayList;
import java.util.Arrays;

@Data
public class Player {
    // TODO: Refactor to remove playerboard and include it's attributes and methods for player
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

    public boolean hasFilledRow() {
        for (int i = 0; i < 5; i++) {
            if (this.board.getWall().isRowFilled(i)) {
                return true;
            }
        }
        return false;
    }

    // TODO: Refactor to use multiple methods
    public int calculateFinalScore() {
        int totalBonusPoints = 0;
        int counter = 0;
        Tile tempTile = null;
    
        // Check for full rows
        // TODO: Refactor to own method
        for (int i = 0; i < 5; i++) {
            if (this.board.getWall().isRowFilled(i)) {
                totalBonusPoints += 2; // Add 2 points for each full row
            }
        }
    
        // Check for full columns
        // TODO: Refactor to own method
        ArrayList<TileType> tileTypes = new ArrayList<>(
                Arrays.asList(TileType.RED, TileType.BLUE, TileType.BLACK, TileType.WHITE, TileType.YELLOW));
        for (TileType type : tileTypes) {
            tempTile = new Tile(type);
            counter = 1 + this.board.getWall().countVerticalTiles(tempTile, 0);
            if (counter == 5) {
                totalBonusPoints += 7; // Add 7 points for each full column
            }
        }
    
        // Check for tile type sets
        // TODO: Refactor to own method
        for (TileType type : tileTypes){
            tempTile = new Tile(type);
            counter = 0;
            for (int i = 0; i < 5; i++) {
                if (this.board.getWall().isTileFilled(tempTile, i)) {
                    counter++;
                }
            }
            if (counter == 5) {
                totalBonusPoints += 10; // Add 10 points for each full tile type set
            }
        }
    
        // Add up all the bonuses to the score
        this.board.addScore(totalBonusPoints);

        return this.board.getScoreTrack();
    }
}
