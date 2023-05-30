package nl.utwente.p4.ui.playerboard;

import nl.utwente.p4.components.*;
import nl.utwente.p4.constants.TileType;
import nl.utwente.p4.ui.gametable.FactoryView;
import nl.utwente.p4.ui.helper.ColorConverter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class PatternLineView extends JPanel {
    private final ArrayList<ArrayList<JButton>> patternLineButtons;
    private final Box layout;

    public PatternLineView(Player player) {
        patternLineButtons = new ArrayList<>();
        layout = Box.createVerticalBox();
        createPatternLine(player);
        add(layout);
    }

    private void createPatternLine(Player player) {
        for (int i = 0; i < player.getPatternLine().getTileLines().size(); i++) {
            TileLine tileLine = player.getPatternLine().getTileLines().get(i);
            Box tileLineLayout = Box.createHorizontalBox();
            tileLineLayout.setAlignmentX(RIGHT_ALIGNMENT);

            ArrayList<JButton> tileLineButtons = new ArrayList<>();
            for (int j = 0; j < tileLine.getLineSize(); j++) {
                JButton patternLineButton = createTile();
                int finalI = i;
                patternLineButton.addActionListener(e -> fillTile(player, finalI));

                tileLineButtons.add(patternLineButton);

                tileLineLayout.add(patternLineButton);
                tileLineLayout.add(Box.createHorizontalStrut(5));
            }
            patternLineButtons.add(tileLineButtons);

            layout.add(tileLineLayout);
            layout.add(Box.createVerticalStrut(5));
        }
    }

    private JButton createTile() {
        JButton patternLineButton = new JButton(" ");
        patternLineButton.setBackground(ColorConverter.convertDisabled(TileType.NULL));
        patternLineButton.setSize(new Dimension(20, 20));
        patternLineButton.setEnabled(false);
        return patternLineButton;
    }

    private void fillTile(Player currPlayer, int row) {
        Factory currSelectedFactory = Game.getInstance().getCurrSelectedFactory();
        Tile currSelectedFactoryTile = Game.getInstance().getCurrSelectedFactoryTile();

        if (currSelectedFactory != null && currSelectedFactoryTile != null) {
            currPlayer.getFactoryOfferFromFactory(currSelectedFactory,
                    Game.getInstance().getTileTable(),
                    currSelectedFactoryTile.getType(),
                    row);

            this.refresh(row);

            for (FactoryView factoryView : Game.getInstance().getFactoryViews()) {
                factoryView.refresh();
            }

            Game.getInstance().setCurrSelectedFactory(null);
            Game.getInstance().setCurrSelectedFactoryTile(null);
        }
        // else if currSelectedTileTable get factory offer from tile table
        // else notif no tiles selected

        toggleEnable(false);
        Game.getInstance().nextPlayer();
    }

    public void toggleEnable(boolean isEnabled) {
        Player currPlayer = Game.getInstance().getPlayers().get(Game.getInstance().getCurrPlayerIdx());
        ArrayList<TileLine> currPlayerTileLines = currPlayer.getPatternLine().getTileLines();

        for (int i = 0; i < patternLineButtons.size(); i++) {
            TileLine tileLine = currPlayerTileLines.get(i);
            for (int j = 0; j < patternLineButtons.get(i).size(); j++) {
                JButton patternLineButton = refreshColor(tileLine, i, j, isEnabled);
                patternLineButton.setEnabled(isEnabled);
            }
        }
    }

    public void refresh(int row) {
        Player currPlayer = Game.getInstance().getPlayers().get(Game.getInstance().getCurrPlayerIdx());
        TileLine tileLineToRefresh = currPlayer.getPatternLine().getTileLines().get(row);

        for (int col = 0; col < patternLineButtons.get(row).size(); col++) {
            refreshColor(tileLineToRefresh, row, col, true);
        }
    }

    private JButton refreshColor(TileLine tileLineToRefresh, int row, int col, boolean isEnabled) {
        HashMap<TileType, Color> colorMap = ColorConverter.getColorMapEnabled();
        if (!isEnabled) {
            colorMap = ColorConverter.getColorMapDisabled();
        }

        JButton patternLineButton = patternLineButtons.get(row).get(col);
        ArrayList<Tile> tiles = tileLineToRefresh.getTiles();
        TileType lineType =  tileLineToRefresh.getLineType();

        if (lineType == TileType.NULL) {
            patternLineButton.setBackground(colorMap.get(TileType.NULL));
        } else {
            if (col < tiles.size()) {
                patternLineButton.setBackground(colorMap.get(lineType));
            } else {
                patternLineButton.setBackground(colorMap.get(TileType.NULL));
            }
        }

        return patternLineButton;
    }
}
