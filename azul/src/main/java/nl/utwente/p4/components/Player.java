package nl.utwente.p4.components;

import lombok.Getter;
import lombok.Setter;
import nl.utwente.p4.constants.TileType;

import java.util.ArrayList;
import java.util.Arrays;

@Getter
public class Player {
    private final PatternLine patternLine;
    private final Wall wall;
    @Setter private FloorLine floorLine;
    @Setter private int scoreTrack;

    public Player() {
        this.patternLine = new PatternLine();
        this.wall = new Wall();
        this.floorLine = new FloorLine();
        this.scoreTrack = 0;
    }

    /**
     * Method to get given tiles from the games tile table
     * and add to pattern line
     *
     * @param pickedTile tile to be picked
     * @param rowNum row number to add picked tiles to
     */
    public void getFactoryOfferFromTileTable(Tile pickedTile, Integer rowNum) {
        TileTable tileTable = Game.getInstance().getTileTable();

        ArrayList<Tile> matchedTiles = tileTable.getMatchingTiles(pickedTile.getType());
        this.addTiles(matchedTiles, rowNum);

        tileTable.takeTiles(pickedTile.getType());
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
        ArrayList<Tile> matchedTiles = factory.getMatchingTiles(color);
        this.addTiles(matchedTiles, row);

        factory.takeTiles(color);

        TileTable tileTable = Game.getInstance().getTileTable();
        factory.getRemainingTiles().forEach(tileTable::addTile);
    }

    public boolean hasFilledRow() {
        for (int i = 0; i < 5; i++) {
            if (this.wall.isRowFilled(i)) {
                return true;
            }
        }
        return false;
    }


    public int calculateFinalScore() {
        int totalBonusPoints = 0;

        totalBonusPoints += calculateFullRowsBonusPoints();
        totalBonusPoints += calculateFullColumnsBonusPoints();
        totalBonusPoints += calculateTileTypeSetsBonusPoints();

        // Add up all the bonuses to the score
        addScore(totalBonusPoints);

        return getScoreTrack();
    }

    private int calculateTileTypeSetsBonusPoints() {
        int bonusPoints = 0;
        ArrayList<TileType> tileTypes = new ArrayList<>(
                Arrays.asList(TileType.RED, TileType.BLUE, TileType.BLACK, TileType.WHITE, TileType.YELLOW));

        for (TileType type : tileTypes){
           Tile tempTile = new Tile(type);
            int counter = 0;
            for (int i = 0; i < 5; i++) {
                if (this.getWall().isTileFilled(tempTile, i)) {
                    counter++;
                }
            }
            if (counter == 5) {
                bonusPoints += 10; // Add 10 points for each full tile type set
            }
        }
        return bonusPoints;
    }

    private int calculateFullColumnsBonusPoints() {
        int bonusPoints = 0;
        // Check for full columns
        for (int i = 0; i < 5; i++) {
            if (this.getWall().isColumnFilled(i))
                bonusPoints += 7; // Add 7 points for each full column
        }
        return bonusPoints;
    }

    private int calculateFullRowsBonusPoints() {
        int bonusPoints = 0;
        for (int i = 0; i < 5; i++) {
            if (this.getWall().isRowFilled(i)) {
                bonusPoints += 2; // Add 2 points for each full row
            }
        }
        return bonusPoints;
    }

    public int completeHorizontalLines(){
        int counter = 0;
        for (int i = 0; i < 5; i++) {
            if (this.getWall().isRowFilled(i)) {
                counter++;
            }
        }
        return counter;
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
        this.patternLine.checkAddTiles(tiles, rowNum);
        // If first player to take from table, add first token to floor
        if (tiles.get(0).getType() == TileType.FIRST_PLAYER) {
            this.floorLine.addTile(tiles.remove(0));
        }

        ArrayList<Tile> excessTiles = this.patternLine.addTiles(tiles, rowNum);

        excessTiles.forEach(tile -> this.floorLine.addTile(tile));
    }

    /***
     * Method to pick tiles from a factory
     * and add them to floor line
     * and remove the remaining unpicked tiles from the factory
     * and add them to the tile table
     *
     * @param factory the factory which tile is taken from
     * @param color the colour(type) of tiles that player take
     */
    public void addFloorLineFromFactory(Factory factory, TileType color) {
        ArrayList<Tile> matchedTiles = factory.getMatchingTiles(color);
        matchedTiles.forEach(tile -> this.floorLine.addTile(tile));

        factory.takeTiles(color);

        TileTable tileTable = Game.getInstance().getTileTable();
        factory.getRemainingTiles().forEach(tileTable::addTile);
    }

    /**
     * Method to pick tiles from the tile table
     * and add them to floor line
     *
     * @param pickedTile tile to be picked
     */
    public void addFloorLineFromTileTable(Tile pickedTile) {
        TileTable tileTable = Game.getInstance().getTileTable();

        ArrayList<Tile> matchedTiles = tileTable.getMatchingTiles(pickedTile.getType());
        matchedTiles.forEach(tile -> this.floorLine.addTile(tile));

        tileTable.takeTiles(pickedTile.getType());
    }
}
