package core.gamestate;

import nl.utwente.p4.components.GeneralTileLine;
import nl.utwente.p4.components.Tile;
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
    public nl.utwente.p4.constants.TileType getLineType() {
        core.gamestate.TileType v = line.getTileType();
        return convertFromForeignType(v);
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

    /***
     * Add tiles to the tile line. If the line is empty, set the line type according to the tiles to be added
     * @param tilesToAdd tiles to add
     * @return excess tiles
     */
    @Override
    public ArrayList<Tile> addTilesToLine(ArrayList<Tile> tilesToAdd) {
        if (tilesToAdd.size() < 1 || isFilled()) return tilesToAdd;
        checkAndSetLineType(tilesToAdd.get(0).getType());

        int temp = line.addToLine(convertToForeignType(tilesToAdd.get(0).getType()), tilesToAdd.size());
        while (tilesToAdd.size() != temp) {

            tilesToAdd.remove(0);
        }
        return tilesToAdd;
    }

    @Override
    public void checkAndSetLineType(nl.utwente.p4.constants.TileType type) {
        if (line.getTileCount() == 0 && type != null) {
            setLineType(type);
        }
    }

    @Override
    public boolean isFilled() {
        return line.getTileCount() >= line.getCapacity();
    }

    @Override
    public void setLineType(nl.utwente.p4.constants.TileType lineType) {
        line.setTileType(convertToForeignType(lineType));
    }

    @Override
    public void empty() {
        line.clearLine();
    }


    /**
     * Converts our TileType to the TileType of Team 5 and
     *
     * @return tile of a certain color
     */
    private core.gamestate.TileType convertToForeignType(nl.utwente.p4.constants.TileType tileType) {
        switch (tileType) {
            case BLACK -> {
                return core.gamestate.TileType.BLACK;
            }
            case WHITE -> {
                return core.gamestate.TileType.GREEN;
            }
            case BLUE -> {
                return core.gamestate.TileType.BLUE;
            }
            case YELLOW -> {
                return core.gamestate.TileType.YELLOW;
            }
            case RED -> {
                return core.gamestate.TileType.RED;
            }
        }
        throw new WrongTileType("Wrong Tile Type");
    }

    /**
     * Converts the TileType of Team 5 to our TileType and
     *
     * @return tile of a certain color
     */
    private static nl.utwente.p4.constants.TileType convertFromForeignType(core.gamestate.TileType tileType) {
        switch (tileType) {
            case BLACK -> {
                return nl.utwente.p4.constants.TileType.BLACK;
            }
            case GREEN -> {
                return nl.utwente.p4.constants.TileType.WHITE;
            }
            case BLUE -> {
                return nl.utwente.p4.constants.TileType.BLUE;
            }
            case YELLOW -> {
                return nl.utwente.p4.constants.TileType.YELLOW;
            }
            case RED -> {
                return nl.utwente.p4.constants.TileType.RED;
            }
        }
        throw new WrongTileType("Wrong Tile Type");
    }
}
