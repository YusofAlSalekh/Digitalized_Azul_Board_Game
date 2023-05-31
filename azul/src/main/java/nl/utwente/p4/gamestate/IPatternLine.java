package nl.utwente.p4.gamestate;

public interface IPatternLine {

    int addToLine(TileType type, int count);
    boolean isFull();
    TileType getTileType();

    int clearLine();

}
