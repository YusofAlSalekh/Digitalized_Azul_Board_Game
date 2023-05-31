package nl.utwente.p4.components;

import nl.utwente.p4.gamestate.TileLineAdapter;

public class TileLineFactory {
    public static GeneralTileLine createTileLine(int size) {
        if (System.getProperties().containsKey("externalTileLine")) {
            return new TileLineAdapter(size);
        } else {
            return new TileLine(size);
        }
    }
}
