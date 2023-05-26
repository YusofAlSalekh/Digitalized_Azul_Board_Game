package nl.utwente.p4.ui.playerboard;

import nl.utwente.p4.components.Player;

import javax.swing.*;

public class ScoreTrackView extends JPanel {
    public ScoreTrackView(Player player) {
        JPanel layout = new JPanel();
        JLabel label = new JLabel();
        label.setText("Score: " + player.getScoreTrack());
        label.setHorizontalAlignment(JLabel.CENTER);
        layout.add(label);
        add(layout);
    }
}
