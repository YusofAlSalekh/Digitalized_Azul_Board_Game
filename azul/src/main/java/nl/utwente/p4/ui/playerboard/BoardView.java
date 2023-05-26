package nl.utwente.p4.ui.playerboard;

import nl.utwente.p4.components.Game;
import nl.utwente.p4.components.Player;

import javax.swing.*;
import java.awt.*;

public class BoardView extends JPanel {
    public BoardView() {
        Box layout = Box.createVerticalBox();

        for (Player player : Game.getInstance().getPlayers()) {
            JPanel boardLayout = new JPanel();
            boardLayout.setLayout(new BorderLayout());
            boardLayout.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            boardLayout.setPreferredSize(new Dimension(450,250));

            var floorLineView = new FloorLineView(player);
            boardLayout.add(floorLineView, BorderLayout.SOUTH);

            layout.add(boardLayout);
            layout.add(Box.createVerticalStrut(20));
        }

        add(layout);
    }
}
