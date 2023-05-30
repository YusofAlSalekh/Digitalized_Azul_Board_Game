package nl.utwente.p4.ui.playerboard;

import nl.utwente.p4.components.Game;
import nl.utwente.p4.components.Player;

import javax.swing.*;

public class BoardCollectionView extends JPanel {
    public BoardCollectionView() {
        Box layout = Box.createVerticalBox();

        for (Player player : Game.getInstance().getPlayers()) {
            BoardView boardView = new BoardView(player);
            Game.getInstance().getBoardViews().add(boardView);

            layout.add(boardView.getBoardLayout());
            layout.add(Box.createVerticalStrut(20));
        }

        add(layout);
    }
}
