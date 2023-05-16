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
    private TileType[] WallSet() {
        return new TileType[]{TileType.BLUE, TileType.YELLOW, TileType.RED, TileType.BLACK, TileType.WHITE};
    }

    private static TileType[] RotateSet(TileType[] set){
        //move set one index to the right
        TileType lastSetElement = set[set.length - 1];
        System.arraycopy(set, 0, set, 1, set.length - 1);
        set[0] = lastSetElement;
        return set;
    }
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
    public  Map<TileType, TileType>  addTile(Tile tile, int row) {
        // check if tile is not filled in row, then add to row

        Map<TileType, TileType> targetRow = this.tiles[row];
        for (TileType column : targetRow.keySet()) {
            if (column == tile.getType() && targetRow.get(column) == null) {
                targetRow.put(column, tile.getType());
                int  points = getTotalScore() + getScoreFromTile(tile,row) + 1; // gain +1 point for inserting a tile into the wall
                setTotalScore(points);
            }
        }
        return this.tiles[row];
    }

    public boolean isTileFilled(Tile tile, int row) {
        Map<TileType, TileType> targetRow = this.tiles[row];
        return targetRow.containsValue(tile.getType());
    }
    public boolean isRowFilled(int row) {

        Map<TileType, TileType> targetRow = this.tiles[row];
        for (TileType item : targetRow.keySet()) {
            if(targetRow.get(item) == null) return false;
        }
        return true;
    }
    // Check if column is filled
    //  requires params of the column you want to check if filled
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
    private int countHorizontalTiles(Tile tile,int row) {
        int count = 0;
        int left = getTileIndex(tile,row) - 1;
        int right = getTileIndex(tile,row) + 1;
        Map targetrow = this.tiles[row];
        List<String> listOFTargetRow = new ArrayList<>(targetrow.keySet());
        // count tiles to the left
        while (left >= 0 && targetrow.get(listOFTargetRow.get(left)) != null) {
            count++;
            left--;
        }
        // count tiles to the right
        while (right < 5 && targetrow.get(listOFTargetRow.get(right)) != null) {
            count++;
            right++;
        }
        return count;
    }
    public int countVerticalTiles(Tile tile,int row) {
        int count = 0;
        int up =  row - 1;
        int down = row + 1;
        int index = getTileIndex(tile,row);
        Map targetrow ;
        List<String> listOFTargetRow ;
        // count tiles above
        // TODO: Refactor to own method
        while (up >= 0 ) {
            targetrow = this.tiles[up];
            listOFTargetRow = new ArrayList<>(targetrow.keySet());
            if (targetrow.get(listOFTargetRow.get(index)) == null) break;
            count++;
            up--;
        }
        // count tiles below
        // TODO: Refactor to own method
        while (down < 5 ) {
            targetrow = this.tiles[down];
            listOFTargetRow = new ArrayList<>(targetrow.keySet());
            if (targetrow.get(listOFTargetRow.get(index)) == null)  break;
            count++;
            down++;
        }
        return count;
    }
    private  int getScoreFromTile(Tile tile,int row){
        return countHorizontalTiles(tile,row) + countVerticalTiles(tile,row);
    }

    //add tiles from the patterline to the wall
    //extra tiles are added in an array and then added to the games gameBoxLid
    public int addFromPatterLineToWall(PatternLine patternLine, int getTotalFloorScore){
        int index = 0;
        setTotalScore(0);
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
        Game.getInstance().addTilesToGameBoxLid(extraTiles);
        return deductScoreFromFloorLine(getTotalFloorScore);
    }

    public int deductScoreFromFloorLine(int getTotalFloorScore){
        int total =  Math.max(0 , getTotalScore() - getTotalFloorScore ) ;
        setTotalScore(total);
        return getTotalScore();
    }


    // TODO: implement method
    public int countFilledRows() {
        return 0;
    }

    // TODO: implement method
    public int countFilledColumns() {
        return 0;
    }

    // TODO: implement method
    public int countCompleteTiles() {
        // iterate through each tile type
        return 0;
    }
}
