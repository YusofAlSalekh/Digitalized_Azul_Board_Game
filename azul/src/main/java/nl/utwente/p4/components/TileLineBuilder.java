package nl.utwente.p4.components;

import nl.utwente.p4.gamestate.TileLineAdapter;

/**
 * We use this class so that
 * it automatically runs the program with our component(TileLine) or with the component of team 5(TileLineAdapter)
 */
public class TileLineBuilder {
    public static GeneralTileLine createTileLine(int size) {
        if (System.getProperties().containsKey("externalTileLine")) {
            return new TileLineAdapter(size);
        } else {
            return new TileLine(size);
        }
    }
}
