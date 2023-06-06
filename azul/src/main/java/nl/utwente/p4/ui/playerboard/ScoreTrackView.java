package nl.utwente.p4.ui.playerboard;

import nl.utwente.p4.components.Player;

import javax.swing.*;

public class ScoreTrackView extends JPanel {
    private JPanel scoreTrackLayout;

    public ScoreTrackView(Player player) {
        scoreTrackLayout = new JPanel();
        createScoreTrackView(player);
        add(scoreTrackLayout);
    }

    private void createScoreTrackView(Player player) {
        JLabel label = new JLabel();
        label.setText("Score: " + player.getScoreTrack());
        label.setHorizontalAlignment(JLabel.CENTER);
        scoreTrackLayout.add(label);
    }

    public void refresh(Player player) {
        scoreTrackLayout.removeAll();
        createScoreTrackView(player);
    }
}
