package nl.utwente.p4.ui.playerboard;

import nl.utwente.p4.components.Player;
import nl.utwente.p4.constants.TileType;
import nl.utwente.p4.ui.helper.ColorConverter;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class WallView extends JPanel {
    public WallView(Player player) {
        Box layout = Box.createVerticalBox();

        for (Map<TileType, TileType> map : player.getWall().getTiles()) {
            Box wallLayout = Box.createHorizontalBox();

            for (TileType tileType : map.keySet()) {
                JButton wallButton = new JButton(" ");

                wallButton.setBackground(ColorConverter.convertDisabled(tileType));
                wallButton.setSize(new Dimension(20, 20));
                wallButton.setEnabled(false);

                wallLayout.add(wallButton);
                wallLayout.add(Box.createHorizontalStrut(5));
            }

            layout.add(wallLayout);
            layout.add(Box.createVerticalStrut(5));
        }

        add(layout);
    }
}
