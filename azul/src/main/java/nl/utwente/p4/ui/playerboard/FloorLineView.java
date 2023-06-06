package nl.utwente.p4.ui.playerboard;

import nl.utwente.p4.components.Player;
import nl.utwente.p4.components.Tile;
import nl.utwente.p4.constants.FloorScore;
import nl.utwente.p4.constants.TileType;
import nl.utwente.p4.ui.helper.ColorConverter;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class FloorLineView extends JPanel {
    private final JPanel floorLineLayout;
    public FloorLineView(Player player) {
        // TODO: allow to add to floor line as well in factory offer
        floorLineLayout = new JPanel();
        floorLineLayout.setLayout(new GridLayout(2, 7, 5, 0));

        createFloorScoreView(player);
        createFloorTilesView(player);

        add(floorLineLayout);
    }

    private void createFloorScoreView(Player player) {
        for (int i : FloorScore.getValues()) {
            JLabel label = new JLabel();
            label.setText(Integer.toString(i));
            label.setHorizontalAlignment(JLabel.CENTER);
            label.setSize(new Dimension(20, 20));
            floorLineLayout.add(label);
        }
    }

    private void createFloorTilesView(Player player) {
        ArrayList<Tile> playerFloorLineTiles = player.getFloorLine().getTiles();
        for (int i = 0; i < 7; i++) {
            JButton floorLineButton = new JButton(" ");
            if (i < playerFloorLineTiles.size()) {
                TileType tileType = playerFloorLineTiles.get(i).getType();
                floorLineButton.setBackground(ColorConverter.convert(tileType));
                if (tileType == TileType.FIRST_PLAYER) {
                    floorLineButton.setText("FP");
                }
            } else {
                floorLineButton.setBackground(ColorConverter.convert(TileType.NULL));
            }
            floorLineButton.setSize(new Dimension(20, 20));
            floorLineButton.setEnabled(false);
            floorLineLayout.add(floorLineButton);
        }
    }

    public void refresh(Player player) {
        floorLineLayout.removeAll();
        createFloorScoreView(player);
        createFloorTilesView(player);
    }
}
