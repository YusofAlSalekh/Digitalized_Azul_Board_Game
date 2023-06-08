package nl.utwente.p4.ui.playerboard;

import nl.utwente.p4.components.Player;
import nl.utwente.p4.components.Tile;
import nl.utwente.p4.constants.TileType;
import nl.utwente.p4.ui.helper.ColorConverter;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class WallView extends JPanel {
    private final Box wallLayout;

    public WallView(Player player) {
        wallLayout = Box.createVerticalBox();
        createWallView(player);
        add(wallLayout);
    }

    private void createWallView(Player player) {
        for (int i = 0; i < player.getWall().getTiles().length; i++) {
            Map<TileType, TileType> map = player.getWall().getTiles()[i];
            Box wallRow = Box.createHorizontalBox();

            for (TileType tileType : map.keySet()) {
                wallRow.add(createWallButtonView(player, tileType, i));
                wallRow.add(Box.createHorizontalStrut(5));
            }

            wallLayout.add(wallRow);
            wallLayout.add(Box.createVerticalStrut(5));
        }
    }

    private JButton createWallButtonView(Player player, TileType tileType, int row) {
        JButton wallButton = new JButton(" ");
        if (player.getWall().isTileFilled(new Tile(tileType), row)) {
            wallButton.setBackground(ColorConverter.convert(tileType));
        } else {
            wallButton.setBackground(ColorConverter.convertDisabled(tileType));
        }
        wallButton.setSize(new Dimension(20, 20));
        wallButton.setEnabled(false);
        return wallButton;
    }

    public void refresh(Player player) {
        wallLayout.removeAll();
        createWallView(player);
    }
}
