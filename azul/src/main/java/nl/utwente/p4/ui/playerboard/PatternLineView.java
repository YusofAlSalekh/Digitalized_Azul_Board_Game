package nl.utwente.p4.ui.playerboard;

import nl.utwente.p4.components.Player;
import nl.utwente.p4.components.TileLine;
import nl.utwente.p4.constants.TileType;
import nl.utwente.p4.ui.helper.ColorConverter;

import javax.swing.*;
import java.awt.*;

public class PatternLineView extends JPanel {
    public PatternLineView(Player player) {
        Box layout = Box.createVerticalBox();

        for (TileLine tileLine : player.getPatternLine().getTileLines()) {
            Box tileLineLayout = Box.createHorizontalBox();
            tileLineLayout.setAlignmentX(RIGHT_ALIGNMENT);

            for (int i = 0; i < tileLine.getLineSize(); i++) {
                JButton patternLineButton = new JButton(" ");

                patternLineButton.setBackground(ColorConverter.convertDisabled(TileType.NULL));
                patternLineButton.setSize(new Dimension(20, 20));
                patternLineButton.setEnabled(false);

                tileLineLayout.add(patternLineButton);
                tileLineLayout.add(Box.createHorizontalStrut(5));
            }

            layout.add(tileLineLayout);
            layout.add(Box.createVerticalStrut(5));
        }

        add(layout);
    }
}
