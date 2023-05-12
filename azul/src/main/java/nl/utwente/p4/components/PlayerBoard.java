package nl.utwente.p4.components;

import lombok.Data;
import nl.utwente.p4.constants.TileType;

import java.util.ArrayList;

@Data
public class PlayerBoard {
    private PatternLine patternLine;
    private Wall wall;
    private FloorLine floorLine;
    private int scoreTrack;

    public PlayerBoard() {
        this.patternLine = new PatternLine();
        this.wall = new Wall();
        this.floorLine = new FloorLine();
        this.scoreTrack = 0;
    }

    // TODO: implement method
    public void addScore(int value) { }
    
    // TODO: implement method
    public void subtractScore(int value) { }

    /***
     * Method to add given tiles to the player boards pattern line
     * Excess tiles are added to the players floorline or the tilebag
     * @param tiles tiles to be added
     * @param rowNum row to add tiles to
     */
    public void addTiles(ArrayList<Tile> tiles, Integer rowNum) {
        // If first player to take from table, add first token to floor
        if (tiles.get(0).getType() == TileType.FIRST_PLAYER) {
            this.floorLine.addTile(tiles.remove(0));
        }

        ArrayList<Tile> excessTiles = this.patternLine.addTiles(tiles, rowNum);

        // Add excess tiles to the floor or game box lid if floor is full
        Boolean prevWasAddedToFloor = true;
        ArrayList<Tile> tilesToLid = new ArrayList<>();
        for (int i = 0; i < excessTiles.size(); i++) {
            prevWasAddedToFloor = floorLine.addTile(excessTiles.get(i));
            if (!prevWasAddedToFloor) {
                tilesToLid.add(excessTiles.get(i));
            }
        }

        Game.getInstance().addTilesToGameBoxLid(tilesToLid);
    }
}
