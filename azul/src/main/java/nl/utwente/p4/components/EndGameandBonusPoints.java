package nl.utwente.p4.components;

import java.util.ArrayList;

public class EndGameandBonusPoints {
    // TODO: implement method
    public void endGame() {
        boolean hasGameEnded = false;
        // TODO: iterate through players arraylist to check if anyone has a row filled out
        for (Player p : players) {
            if (p.hasFilledRow()) {
                hasGameEnded = true;
                break;
            }
        }

        if (!hasGameEnded) {
            return;
        }

        int highestScore = -99999;
        // TODO: call the calcuate score function for each player & check player with the highest score
        for (Player p : players) {
            int score = p.calculateScore();

            // check if this score was highest
            if (score > highestScore) {
                highestScore = score;
            }
        }
    }

}
