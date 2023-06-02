package nl.utwente.p4.ui.playerboard;

import nl.utwente.p4.components.Player;
import nl.utwente.p4.constants.TileType;
import nl.utwente.p4.ui.helper.ColorConverter;

import javax.swing.*;
import java.awt.*;

public class FloorLineView extends JPanel {
    private JPanel floorLineLayout;
    public FloorLineView(Player player) {
        floorLineLayout = new JPanel();
        floorLineLayout.setLayout(new GridLayout(2, 7, 5, 0));

        createFloorScoreView(player);
        createFloorTilesView(player);

        add(floorLineLayout);
    }

    private void createFloorScoreView(Player player) {
        for (int i : player.getFloorLine().getFloorScores()) {
            JLabel label = new JLabel();
            label.setText(Integer.toString(i));
            label.setHorizontalAlignment(JLabel.CENTER);
            label.setSize(new Dimension(20, 20));
            floorLineLayout.add(label);
        }
    }

    private void createFloorTilesView(Player player) {
        for (int i = 0; i < 7; i++) {
            JButton floorLineButton = new JButton(" ");
            floorLineButton.setBackground(ColorConverter.convert(TileType.NULL));
            floorLineButton.setSize(new Dimension(20, 20));
            floorLineButton.setEnabled(false);
            floorLineLayout.add(floorLineButton);
        }
    }
}
