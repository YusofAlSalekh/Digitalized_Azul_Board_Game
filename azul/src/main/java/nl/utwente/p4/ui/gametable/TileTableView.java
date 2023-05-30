package nl.utwente.p4.ui.gametable;

import nl.utwente.p4.components.Game;
import nl.utwente.p4.components.Tile;
import nl.utwente.p4.constants.TileType;
import nl.utwente.p4.ui.GameView;
import nl.utwente.p4.ui.helper.ColorConverter;

import javax.swing.*;
import java.awt.*;

public class TileTableView extends JPanel {
    private static Box layout;
    public TileTableView() {
        layout = Box.createVerticalBox();
        createTileTable();
        add(layout);
    }

    public void createTileTable() {
        layout.removeAll();

        for (Tile tile : Game.getInstance().getTileTable().getTiles()) {
            JButton tileButton = new JButton();
            tileButton.setSize(new Dimension(20, 20));
            tileButton.setEnabled(true);

            if (tile.getType() == TileType.FIRST_PLAYER) {
                tileButton.setText("-1");
                tileButton.setBackground(ColorConverter.convert(TileType.WHITE));
            } else {
                tileButton.setText(" ");
                tileButton.setBackground(ColorConverter.convert(tile.getType()));
            }

            layout.add(tileButton);
            layout.add(Box.createVerticalStrut(5));
        }
    }

    public void refresh() {
        createTileTable();
        revalidate();
    }
}
