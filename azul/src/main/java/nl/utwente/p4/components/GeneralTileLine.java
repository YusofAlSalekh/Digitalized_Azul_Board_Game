package nl.utwente.p4.components;

import nl.utwente.p4.constants.TileType;

import java.util.ArrayList;

public interface GeneralTileLine {
    TileType getLineType();

    int getLineSize();

    ArrayList<Tile> getTiles();

    ArrayList<Tile> addTilesToLine(ArrayList<Tile> tilesToAdd);

    void checkAndSetLineType(TileType type);

    boolean isFilled();

    void setTiles(ArrayList<Tile> tiles);


    void setLineType(TileType lineType);
}
