package nl.utwente.p4.components;

import nl.utwente.p4.constants.TileType;

import java.util.*;

public class Wall {
    private Map<TileType, TileType>[] tiles;
    public Map<TileType, TileType>[] getTiles() {
        return tiles;
    }
    public void setTiles(Map<TileType, TileType>[] tiles) {
        this.tiles = tiles;
    }
    public TileType[] WallSet() {
        TileType[] set = {TileType.BLUE,TileType.YELLOW,TileType.RED,TileType.BLACK,TileType.WHITE};
        return set;
    }

    int totalScore =0;
    public static TileType[] RotateSet(TileType[] set){
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

    // TODO: implement method
    public Map addTile(Tile tile, int row) {
        // check if tile is not filled in row, then add to row
        Map<TileType, TileType> targetRow = this.tiles[row];
        for (TileType column : targetRow.keySet()) {
            if (column == tile.getType() && targetRow.get(column) == null) {
                targetRow.put(column, tile.getType());
            }
        }
        totalScore += 1; // gain 1 point for adding tile to wall
        totalScore += getScoreFromTile( tile,row);
        return this.tiles[row];
    }

    // TODO: implement method
    public boolean isTileFilled(Tile tile, int row) {
        Map<TileType, TileType> targetRow = this.tiles[row];
        if(targetRow.containsValue(tile.getType())) return true;
        return false;
    }

    // TODO: implement method
    public boolean isRowFilled(int row) {

        Map<TileType, TileType> targetRow = this.tiles[row];
        for (TileType item : targetRow.keySet()) {
            if(targetRow.get(item) == null) return false;
        }
        return true;
    }
    public int getTileIndex(Tile tile,int row) {
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
    public int countHorizontalTiles(Tile tile,int row) {
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
        while (up >= 0 ) {
            targetrow = this.tiles[up];
            listOFTargetRow = new ArrayList<>(targetrow.keySet());
            if (targetrow.get(listOFTargetRow.get(index)) == null) break;
            count++;
            up--;
        }
        // count tiles below
        while (down < 5 ) {
            targetrow = this.tiles[down];
            listOFTargetRow = new ArrayList<>(targetrow.keySet());
            if (targetrow.get(listOFTargetRow.get(index)) == null)  break;
            count++;
            down++;
        }
        return count;
    }
    public  int getScoreFromTile(Tile tile,int row){
        int total = 0 ;
        total += countHorizontalTiles(tile,row);
        total += countVerticalTiles(tile,row);
        return total;
    }
    public int deductScoreFromFloorLine(int getTotalFloorScore){
        return  totalScore -= getTotalFloorScore ;
    }
    public int getTotalScore(){
        return totalScore;
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
