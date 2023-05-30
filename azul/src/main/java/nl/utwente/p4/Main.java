package nl.utwente.p4;

import nl.utwente.p4.components.Game;
import nl.utwente.p4.ui.GameView;

public class Main {
    public static void main(String[] args) {
        Game.getInstance().play(2);
    }
}