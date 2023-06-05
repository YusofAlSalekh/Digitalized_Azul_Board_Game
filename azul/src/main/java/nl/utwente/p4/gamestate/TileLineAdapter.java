package nl.utwente.p4.gamestate;

import nl.utwente.p4.components.GeneralTileLine;
import nl.utwente.p4.components.Tile;
import nl.utwente.p4.constants.TileType;
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
    public void setLineType(TileType lineType) {
        line.setTileType(convertToForeignType(lineType));
    }

    /**
     * Converts our TileType to the TileType of Team 5 and
     *
     * @return tile of a certain color
     */
    private nl.utwente.p4.gamestate.TileType convertToForeignType(TileType tileType) {

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

    /**
     * Converts the TileType of Team 5 to our TileType and
     *
     * @return tile of a certain color
     */
    private static TileType convertFromForeignType(nl.utwente.p4.gamestate.TileType v) {
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
}
