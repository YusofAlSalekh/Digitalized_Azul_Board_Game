package nl.utwente.p4.ui.playerboard;

import nl.utwente.p4.components.Player;
import nl.utwente.p4.constants.TileType;
import nl.utwente.p4.ui.helper.ColorConverter;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class WallView extends JPanel {
    private static Box wallLayout;

    public WallView(Player player) {
        wallLayout = Box.createVerticalBox();
        createWallView(player);
        add(wallLayout);
    }

    private void createWallView(Player player) {
        for (Map<TileType, TileType> map : player.getWall().getTiles()) {
            Box wallLayout = Box.createHorizontalBox();

            for (TileType tileType : map.keySet()) {
                wallLayout.add(createWallButtonView(tileType));
                wallLayout.add(Box.createHorizontalStrut(5));
            }

            WallView.wallLayout.add(wallLayout);
            WallView.wallLayout.add(Box.createVerticalStrut(5));
        }
    }

    private JButton createWallButtonView(TileType tileType) {
        JButton wallButton = new JButton(" ");
        wallButton.setBackground(ColorConverter.convertDisabled(tileType));
        wallButton.setSize(new Dimension(20, 20));
        wallButton.setEnabled(false);
        return wallButton;
    }
}
