package nl.utwente.p4.gamestate;

import nl.utwente.p4.components.GeneralTileLine;
import nl.utwente.p4.components.Tile;
import nl.utwente.p4.constants.TileType;
import nl.utwente.p4.exceptions.PatternLineFilledException;
import nl.utwente.p4.exceptions.WrongTileType;

import java.util.ArrayList;

public class TileLineAdapter implements GeneralTileLine {
    private PatternLine line;

    public TileLineAdapter() {
    }

    public TileLineAdapter(int size) {
        line = new PatternLine(size);
    }

    @Override
    public TileType getLineType() {
        nl.utwente.p4.gamestate.TileType v = line.getTileType();
        if (v == nl.utwente.p4.gamestate.TileType.BLACK)
            return TileType.BLACK;
        if (v == nl.utwente.p4.gamestate.TileType.RED)
            return TileType.RED;
        if (v == nl.utwente.p4.gamestate.TileType.GREEN)
            return TileType.WHITE;
        if (v == nl.utwente.p4.gamestate.TileType.BLUE)
            return TileType.BLUE;
        if (v == nl.utwente.p4.gamestate.TileType.YELLOW)
            return TileType.YELLOW;

        throw new WrongTileType("Wrong Tile Type");
    }

    @Override
    public int getLineSize() {
        if (line == null) {
            return 0;
        }
        return line.getCapacity();
    }

    @Override
    public ArrayList<Tile> getTiles() {

        ArrayList<Tile> temp = new ArrayList<>(line.getTileCount());
        for (int i = 0; i < line.getTileCount(); i++) {
            temp.add(new Tile(getLineType()));
        }
        return temp;
    }

    @Override
    public ArrayList<Tile> addTilesToLine(ArrayList<Tile> tilesToAdd) {
        if (tilesToAdd.size() < 1 || isFilled()) return tilesToAdd;
        checkAndSetLineType(tilesToAdd.get(0).getType());

        int temp = line.addToLine(gePatternLineType(tilesToAdd.get(0).getType()), tilesToAdd.size());
        while (tilesToAdd.size() != temp) {

            tilesToAdd.remove(0);
        }
        return tilesToAdd;
    }

    @Override
    public void checkAndSetLineType(TileType type) {
        if (line.getTileCount() == 0 && type != null) {
            setLineType(type);
        }
    }

    @Override
    public boolean isFilled() {
        return line.getTileCount() >= line.getCapacity();
    }

    //TODO
    @Override
    public void setTiles(ArrayList<Tile> tiles) {
    }

    @Override
    public void setLineSize(int lineSize) {
        line.setCapacity(lineSize);
    }

    @Override
    public void setLineType(TileType lineType) {
        if (lineType == TileType.BLACK)
            line.setTileType(nl.utwente.p4.gamestate.TileType.BLACK);
        if (lineType == TileType.RED)
            line.setTileType(nl.utwente.p4.gamestate.TileType.RED);
        if (lineType == TileType.BLUE)
            line.setTileType(nl.utwente.p4.gamestate.TileType.BLUE);
        if (lineType == TileType.WHITE)
            line.setTileType(nl.utwente.p4.gamestate.TileType.GREEN);
        if (lineType == TileType.YELLOW)
            line.setTileType(nl.utwente.p4.gamestate.TileType.YELLOW);
    }

    private nl.utwente.p4.gamestate.TileType gePatternLineType(TileType tileType) {

        switch (tileType) {

            case BLACK -> {
                return nl.utwente.p4.gamestate.TileType.BLACK;
            }

            case WHITE -> {
                return nl.utwente.p4.gamestate.TileType.GREEN;
            }

            case BLUE -> {
                return nl.utwente.p4.gamestate.TileType.BLUE;
            }

            case YELLOW -> {
                return nl.utwente.p4.gamestate.TileType.YELLOW;
            }

            case RED -> {
                return nl.utwente.p4.gamestate.TileType.RED;
            }
        }
        throw new WrongTileType("Wrong Tile Type");
    }
}
