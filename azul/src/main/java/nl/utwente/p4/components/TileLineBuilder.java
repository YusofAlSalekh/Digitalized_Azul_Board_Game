package nl.utwente.p4.components;

import core.gamestate.TileLineAdapter;

/**
 * We use this class so that
 * it automatically runs the program with our component(TileLine) or with the component of team 5(TileLineAdapter)
 */
public class TileLineBuilder {
    public static GeneralTileLine createTileLine(int size) {
        if (Game.getInstance().tileLineIsExternal()) {
            return new TileLineAdapter(size);
        } else {
            return new TileLine(size);
        }
    }
}
