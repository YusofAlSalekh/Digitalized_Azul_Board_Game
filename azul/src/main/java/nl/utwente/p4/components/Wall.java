package nl.utwente.p4.components;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import nl.utwente.p4.constants.TileType;

import java.util.*;

@Data
public class Wall {
    @Setter(AccessLevel.PRIVATE)
    private Map<TileType, TileType>[] tiles;
    @Setter(AccessLevel.PRIVATE)
    private int totalScore =  0;

    /**
     * @return the first set of tiles to the wall.
     * This is index 0 of the wall hashmap.
     */
    private TileType[] WallSet() {
        return new TileType[]{TileType.BLUE, TileType.YELLOW, TileType.RED, TileType.BLACK, TileType.WHITE};
    }

    /**
     * @param set gets the tile set and then rotates it to the right .
     * @return the new rotated set of tiles.
     */
    private static TileType[] RotateSet(TileType[] set){
        //move set one index to the right
        TileType lastSetElement = set[set.length - 1];
        System.arraycopy(set, 0, set, 1, set.length - 1);
        set[0] = lastSetElement;
        return set;
    }

    /**
     * create the wall for player
     */
    public Wall() {
        this.tiles = new HashMap[5];
        TileType[] set = WallSet();

        for (int i = 0; i < 5; i++) {
            this.tiles[i] = new LinkedHashMap<>();
            for (TileType key : set){
                this.tiles[i].put(key, null);
            }
            set = RotateSet(set);
        }
        setTiles(this.tiles);
    }

    /**
     * @param tile get the tile that needs to be added into the wall.
     * @param row get the row where the tile needs to be inserted in.
     * @return the newly inserted tile.
     */
    public  Map<TileType, TileType>  addTile(Tile tile, int row) {
        Map<TileType, TileType> targetRow = this.tiles[row];
        for (TileType column : targetRow.keySet()) {
            if (column == tile.getType() && targetRow.get(column) == null) {   // check if tile is not filled in row, then add to row
                targetRow.put(column, tile.getType());
                int  points = getTotalScore() + getPointsFromTile(tile,row) + 1; // gain +1 point for inserting a tile into the wall
                setTotalScore(points);
            }
        }
        return this.tiles[row];
    }

    /**
     * @param tile get the tile that needs to be checked.
     * @param row get which row the tile need to be checked if it exists in.
     * @return boolean if tile exists in the row.
     */
    public boolean isTileFilled(Tile tile, int row) {
        Map<TileType, TileType> targetRow = this.tiles[row];
        return targetRow.containsValue(tile.getType());
    }

    /**
     * @param row get the row that needs to be checked.
     * @return boolean if the row is full.
     */
    public boolean isRowFilled(int row) {

        Map<TileType, TileType> targetRow = this.tiles[row];
        for (TileType item : targetRow.keySet()) {
            if(targetRow.get(item) == null) return false;
        }
        return true;
    }

    /**
     * @param column get which column needs to be checked.
     * @return boolean if the column is full.
     */
    public boolean isColumnFilled(int column){
        Map targetRow ;
        int rowIndex = 0;
        List<String> listOFTargetRow ;
        while (rowIndex < 5){
            targetRow = this.tiles[rowIndex];
            listOFTargetRow = new ArrayList<>(targetRow.keySet());
            if(targetRow.get(listOFTargetRow.get(column)) == null) return false;
            rowIndex++;
        }
        return true;
    }

    /**
     * @param tile get the tile that needs to be found.
     * @param row get row that the tile needs to be checked in.
     * @return the index position of the tile.
     */
    private int getTileIndex(Tile tile,int row) {
            int i = 0;
            Map<TileType, TileType> targetRow = this.tiles[row];
            for (Map.Entry<TileType, TileType> entry : targetRow.entrySet()) {
                if (entry.getKey() == tile.getType() && entry.getValue() == tile.getType()) {
                    return i;
                }
                i++;
            }
        return -1; // Tile was not found
    }


    /**
     * @param tile get the tile that was added into the wall.
     * @param row get the row where the tile was added in.
     * @return the points taken by calling the countHorizontalTiles and countVerticalTiles methods.
     */
    private  int getPointsFromTile(Tile tile, int row){
        return countHorizontalTiles(tile,row) + countVerticalTiles(tile,row);
    }

    /**
     * @param tile get the tile that was added.
     * @param row get the row that the tile was added in.
     * @return the points gained from adding the tile into the wall horizontally.
     */
    private int countHorizontalTiles(Tile tile,int row) {
        int count = 0;
        int left = getTileIndex(tile,row) - 1;
        int right = getTileIndex(tile,row) + 1;
        Map targetrow = this.tiles[row];
        List<String> listOFTargetRow = new ArrayList<>(targetrow.keySet());
        // count tiles to the left
        count += countTilesLeft(left,targetrow,listOFTargetRow);
        // count tiles to the right
        count += countTilesRight(right,targetrow,listOFTargetRow);
        return count;
    }

    /**
     * @param left get the index of the hashmap to the left of the newly added tile.
     * @param targetrow get the row where the tile was added in.
     * @param listOFTargetRow get the Arraylist of the hashmap(in order to find the position of the tile that was added using the index)
     * @return return the points gained from the left of the tile.
     */
    private int countTilesLeft(int left,Map targetrow, List<String> listOFTargetRow){
        int result = 0;
        while (left >= 0 && targetrow.get(listOFTargetRow.get(left)) != null) {
            result++;
            left--;
        }
        return result;
    }

    /**
     * @param right get the index of the hashmap to the right of the newly added tile.
     * @param targetrow get the row where the tile was added in.
     * @param listOFTargetRow get the Arraylist of the hashmap(in order to find the position of the tile that was added using the index)
     * @return return the points gained from the left of the tile.
     */
    private int countTilesRight(int right,Map targetrow, List<String> listOFTargetRow){
        int result = 0;
        while (right < 5 && targetrow.get(listOFTargetRow.get(right)) != null) {
            result++;
            right++;
        }
        return result;
    }

    /**
     * @param tile get the tile that was added.
     * @param row get the row that the tile was added in.
     * @return the points gained from adding the tile into the wall vertically.
     */
    public int countVerticalTiles(Tile tile,int row) {
        int count = 0;
        int up =  row - 1;
        int down = row + 1;
        int index = getTileIndex(tile,row);
        // count tiles above
        count += countTilesUp(up,index);
        // count tiles below
        count += countTilesDown(down,index);
        return count;
    }

    /**
     * @param up get the row above the tile that was added.
     * @param index get the index position of the added tile.
     * @return points gained from all tiles above the nealy added tile.
     */
    private int countTilesUp(int up,int index){
        int result = 0;
        Map targetrow ;
        List<String> listOFTargetRow ;
        while (up >= 0 ) {
            targetrow = this.tiles[up];
            listOFTargetRow = new ArrayList<>(targetrow.keySet());
            if (targetrow.get(listOFTargetRow.get(index)) == null) break;
            result++;
            up--;
        }
        return result;
    }

    /**
     * @param down get the row below the tile that was added.
     * @param index get the index position of the added tile.
     * @return points gained from all tiles below the nealy added tile.
     */
    private int countTilesDown(int down,int index){
        int result = 0;
        Map targetrow ;
        List<String> listOFTargetRow ;
        while (down < 5 ) {
            targetrow = this.tiles[down];
            listOFTargetRow = new ArrayList<>(targetrow.keySet());
            if (targetrow.get(listOFTargetRow.get(index)) == null)  break;
            result++;
            down++;
        }
        return result;
    }


    /**
     * @param patternLine  get the players patterLine.
     * @param getTotalFloorScore get the players floorLine score.
     * @return the final points gained after adding all new tiles to the wall and deducting them by the floorLine score.
     * extra tiles are added in an array and then added to the games gameBoxLid
     */
    public int addFromPatterLineToWall(PatternLine patternLine, int getTotalFloorScore){
        setTotalScore(0);
        ArrayList<Tile> extraTiles = checkBeforeAddingToWall(patternLine);
        Game.getInstance().addTilesToGameBoxLid(extraTiles);
        return deductScoreFromFloorLine(getTotalFloorScore);
    }

    /**
     * @param patternLine get the players patterLine.
     * @return an Array of all extra tiles in the patterLine.
     */
    public ArrayList<Tile> checkBeforeAddingToWall(PatternLine patternLine){
        int index = 0;
        ArrayList<Tile> extraTiles = new ArrayList<>();
        for (TileLine row: patternLine.getTileLines()) {
            if(row.isFilled()){
                addTile(row.getTiles().get(row.getLineSize()-1),index);
                for (int j = 0; j < row.getLineSize() -1 ; j++) {
                    extraTiles.add(new Tile(row.getLineType()));
                }
            }
            index ++;
        }
        return  extraTiles;
    }


    /**
     * @param getTotalFloorScore get the players floorLine score.
     * @return the totalScore after deducting the floorLine score form it.
     */
    public int deductScoreFromFloorLine(int getTotalFloorScore){
        int total =  Math.max(0 , getTotalScore() - getTotalFloorScore ) ;
        setTotalScore(total);
        return getTotalScore();
    }

}
