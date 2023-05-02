package nl.utwente.p4.components;

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
}
