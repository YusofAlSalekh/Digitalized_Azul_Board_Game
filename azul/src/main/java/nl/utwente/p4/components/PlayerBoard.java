package nl.utwente.p4.components;

import nl.utwente.p4.constants.TileType;

import java.util.ArrayList;

public class PlayerBoard {
    private PatternLine patternLine;
    private Wall wall;
    private FloorLine floorLine;
    private int scoreTrack;

    public PatternLine getPatternLine() {
        return patternLine;
    }

    public void setPatternLine(PatternLine patternLine) {
        this.patternLine = patternLine;
    }

    public Wall getWall() {
        return wall;
    }

    public void setWall(Wall wall) {
        this.wall = wall;
    }

    public FloorLine getFloorLine() {
        return floorLine;
    }

    public void setFloorLine(FloorLine floorLine) {
        this.floorLine = floorLine;
    }

    public int getScoreTrack() {
        return scoreTrack;
    }

    public void setScoreTrack(int scoreTrack) {
        this.scoreTrack = scoreTrack;
    }

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
     * @param tiles tiles to be added
     * @param rowNum row to add tiles to
     */
    public void addTiles(ArrayList<Tile> tiles, Integer rowNum) {
        // If first player to take from table, add first token to floor
        if (tiles.get(0).getType() == TileType.FIRST_PLAYER) {
            this.floorLine.addTile(tiles.get(0));
            tiles.remove(0);
        }

        ArrayList<Tile> excessTiles = this.patternLine.addTiles(tiles, rowNum);

        // Add excess tiles to the floor
        for (int i = 0; i < excessTiles.size(); i++) {
            floorLine.addTile(excessTiles.get(i));
        }
    }
}
