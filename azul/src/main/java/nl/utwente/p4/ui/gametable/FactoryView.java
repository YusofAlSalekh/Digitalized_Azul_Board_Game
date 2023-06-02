package nl.utwente.p4.ui.gametable;

import nl.utwente.p4.components.Factory;
import nl.utwente.p4.components.Game;
import nl.utwente.p4.components.Tile;
import nl.utwente.p4.ui.helper.ColorConverter;

import javax.swing.*;
import java.awt.*;

public class FactoryView extends JPanel {
    private final Box layout;

    public FactoryView() {
        layout = Box.createVerticalBox();
        createFactories();
        add(layout);
    }

    private void createFactories() {
        for (int i = 0; i < Game.getInstance().numOfFactories(); i++) {
            JPanel factory = new JPanel();
            factory.setLayout(new GridLayout(2, 2, 2, 2));

            Factory f = Game.getInstance().getFactories().get(i);
            for (int j = 0; j < 4; j++) {
                factory.add(createSingleFactory(f.getTiles().get(j)));
            }

            layout.add(factory);
            layout.add(Box.createVerticalStrut(20));
        }
    }

    private JButton createSingleFactory(Tile tile) {
        JButton tileButton = new JButton(" ");
        tileButton.setBackground(ColorConverter.convert(tile.getType()));
        tileButton.setSize(new Dimension(20, 20));
        return tileButton;
    }
}
