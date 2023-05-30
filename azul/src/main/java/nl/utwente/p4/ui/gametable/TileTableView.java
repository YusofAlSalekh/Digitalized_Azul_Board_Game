package nl.utwente.p4.ui.gametable;

import nl.utwente.p4.constants.TileType;
import nl.utwente.p4.ui.helper.ColorConverter;

import javax.swing.*;
import java.awt.*;

public class TileTableView extends JPanel {
    public TileTableView() {
        Box layout = Box.createVerticalBox();

        JButton firstTileButton = new JButton("-1");
        firstTileButton.setBackground(ColorConverter.convert(TileType.WHITE));
        firstTileButton.setSize(new Dimension(20, 20));
        firstTileButton.setEnabled(false);
        layout.add(firstTileButton);

        add(layout);
    }
}
