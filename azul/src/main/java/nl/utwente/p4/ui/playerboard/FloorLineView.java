package nl.utwente.p4.ui.playerboard;

import nl.utwente.p4.components.Player;
import nl.utwente.p4.constants.TileType;
import nl.utwente.p4.ui.helper.ColorConverter;

import javax.swing.*;
import java.awt.*;

public class FloorLineView extends JPanel {
    public FloorLineView(Player player) {
        JPanel layout = new JPanel();
        layout.setLayout(new GridLayout(2, 7, 5, 0));

        for (int i : player.getFloorLine().getFloorScores()) {
            JLabel label = new JLabel();
            label.setText(Integer.toString(i));
            label.setHorizontalAlignment(JLabel.CENTER);
            label.setSize(new Dimension(20, 20));
            layout.add(new JLabel(Integer.toString(i), JLabel.CENTER));
        }

        for (int i = 0; i < 7; i++) {
            JButton floorLineButton = new JButton(" ");
            floorLineButton.setBackground(ColorConverter.convert(TileType.NULL));
            floorLineButton.setSize(new Dimension(20, 20));
            layout.add(floorLineButton);
        }

        add(layout);
    }
}
