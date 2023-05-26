package nl.utwente.p4.ui.playerboard;

import nl.utwente.p4.components.Player;
import nl.utwente.p4.constants.TileType;
import nl.utwente.p4.ui.helper.ColorConverter;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class WallView extends JPanel {
    public WallView(Player player) {
        JPanel layout = new JPanel();
        layout.setLayout(new GridLayout(5, 5, 5, 5));

        for (Map<TileType, TileType> map : player.getWall().getTiles()) {
            for (TileType tileType : map.keySet()) {
                JButton wallButton = new JButton(" ");
                wallButton.setBackground(ColorConverter.convertDisabled(tileType));
                wallButton.setSize(new Dimension(20, 20));
                wallButton.setEnabled(false);
                layout.add(wallButton);
            }
        }

        add(layout);
    }
}
