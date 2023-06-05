package core.gamestate;

public interface IPatternLine {

    int addToLine(TileType type, int count);
    boolean isFull();
    TileType getTileType();

    int clearLine();

}
