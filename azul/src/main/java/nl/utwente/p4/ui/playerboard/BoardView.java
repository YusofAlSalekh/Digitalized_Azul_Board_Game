package nl.utwente.p4.ui.playerboard;

import lombok.Data;
import nl.utwente.p4.components.Player;

import javax.swing.*;
import java.awt.*;

@Data
public class BoardView extends JPanel {
    private JPanel boardLayout;
    private ScoreTrackView scoreTrackView;
    private PatternLineView patternLineView;
    private WallView wallView;
    private FloorLineView floorLineView;

    public BoardView(Player player) {
        createBoardLayout();
        createScoreTrackView(player);
        createPatternLineView(player);
        createWallView(player);
        createFloorLineView(player);
    }

    private void createBoardLayout() {
        boardLayout = new JPanel();
        boardLayout.setLayout(new BorderLayout());
        boardLayout.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        boardLayout.setPreferredSize(new Dimension(450,250));
    }

    private void createScoreTrackView(Player player) {
        scoreTrackView = new ScoreTrackView(player);
        boardLayout.add(scoreTrackView, BorderLayout.NORTH);
    }

    private void createPatternLineView(Player player) {
        patternLineView = new PatternLineView(player);
        boardLayout.add(patternLineView, BorderLayout.WEST);
    }

    private void createWallView(Player player) {
        wallView = new WallView(player);
        boardLayout.add(wallView, BorderLayout.EAST);
    }

    private void createFloorLineView(Player player) {
        floorLineView = new FloorLineView(player);
        boardLayout.add(floorLineView, BorderLayout.SOUTH);
    }

    public void updateWallTiling(Player player) {
        wallView.refresh(player);
        patternLineView.refresh(player);
        floorLineView.refresh(player);
        scoreTrackView.refresh(player);
    }
}
