package nl.utwente.p4.components;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import nl.utwente.p4.constants.TileType;

import java.util.ArrayList;
import java.util.Arrays;

@Data
public class Player {
    private PatternLine patternLine;
    private Wall wall;
    private FloorLine floorLine;
    private int scoreTrack;
    private Boolean firstPlayer;

    public Player() {
        this.patternLine = new PatternLine();
        this.wall = new Wall();
        this.floorLine = new FloorLine();
        this.scoreTrack = 0;
        this.firstPlayer = false;
    }

    /**
     * Calculates the boards floorlines score and reducts it from the scoretrack
     * Mark player as first player if first player tile was found on the floorline
     */
    public void calculateFloorLineScore() {
        this.scoreTrack -= floorLine.getTotalFloorScore();
        this.firstPlayer = floorLine.clearFloorLine();
    }

    /**
     * Method to get given tiles from the games tiletable
     * @param pickedTile tile to be picked
     * @param rowNum row number to add picked tiles to
     */
    public void getFactoryOfferFromTileTable(Tile pickedTile, Integer rowNum) {
        TileTable tileTable = Game.getInstance().getTileTable();
        ArrayList<Tile> tilesFromTable = tileTable.takeTiles(pickedTile.getType());
        this.addTiles(tilesFromTable, rowNum);
    }

    /***
     * Method to Pick all tiles of the same color from any one Factory,
     * then move the remaining tiles from this Factory to the center of the table
     * and afterwards add given tiles to the player board pattern line.
     * Excess tiles are added to the players Floorline or the BoxLid
     *
     * @param factory the factory which tile is taken from
     * @param color the colour(type) of tiles that player take
     * @param row the row in which player put tiles
     */
    public void getFactoryOfferFromFactory(Factory factory, TileType color, int row) {
        TileTable tileTable = Game.getInstance().getTileTable();
        ArrayList<Tile> pickedTiles = factory.takeTiles(color);
        factory.getRemainingTiles().forEach(tileTable::addTile);
        this.addTiles(pickedTiles, row);
    }

    public boolean hasFilledRow() {
        for (int i = 0; i < 5; i++) {
            if (this.wall.isRowFilled(i)) {
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
            if (this.getWall().isRowFilled(i)) {
                totalBonusPoints += 2; // Add 2 points for each full row
            }
        }
    
        // Check for full columns
        // TODO: Refactor to own method
        ArrayList<TileType> tileTypes = new ArrayList<>(
                Arrays.asList(TileType.RED, TileType.BLUE, TileType.BLACK, TileType.WHITE, TileType.YELLOW));

        for (int i = 0; i < 5; i++) {
            if (this.getWall().isColumnFilled(i)) totalBonusPoints += 7; // Add 7 points for each full column
        }
    
        // Check for tile type sets
        // TODO: Refactor to own method
        for (TileType type : tileTypes){
            tempTile = new Tile(type);
            counter = 0;
            for (int i = 0; i < 5; i++) {
                if (this.getWall().isTileFilled(tempTile, i)) {
                    counter++;
                }
            }
            if (counter == 5) {
                totalBonusPoints += 10; // Add 10 points for each full tile type set
            }
        }
    
        // Add up all the bonuses to the score
        this.addScore(totalBonusPoints);

        return this.getScoreTrack();
    }

    public void addScore(int value) {
        this.scoreTrack += value;
    }


    /**
     * Method to add given tiles to the player boards pattern line
     * Excess tiles are added to the players floorline or the gameboxlid
     * @param tiles tiles to be added
     * @param rowNum row to add tiles to
     */
    public void addTiles(ArrayList<Tile> tiles, Integer rowNum) {
        // If first player to take from table, add first token to floor
        if (tiles.get(0).getType() == TileType.FIRST_PLAYER) {
            this.floorLine.addTile(tiles.remove(0));
        }

        ArrayList<Tile> excessTiles = this.patternLine.addTiles(tiles, rowNum);

        excessTiles.forEach(tile -> this.floorLine.addTile(tile));
    }
}
