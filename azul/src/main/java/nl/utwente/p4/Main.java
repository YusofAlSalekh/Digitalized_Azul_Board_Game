package nl.utwente.p4;

import nl.utwente.p4.components.Game;
import nl.utwente.p4.ui.GameView;

/**
 * In order to start this game with the external PatternLine add -DexternTileLine JVM command line option, e.g.
 * java -DexternTileLine -jar __our.jar__
 */
public class Main {
    public static void main(String[] args) {
        boolean tileLineIsExternal = System.getProperties().containsKey("externalTileLine");

        Game.getInstance().play(2, tileLineIsExternal);
    }
}