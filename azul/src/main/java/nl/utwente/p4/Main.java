package nl.utwente.p4;

import nl.utwente.p4.components.Game;

public class Main {
    public static void main(String[] args) {
        System.out.println("Azul");
        Game.getInstance().play();
    }
}