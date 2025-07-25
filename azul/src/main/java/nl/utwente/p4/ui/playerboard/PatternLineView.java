package nl.utwente.p4.ui.playerboard;

import lombok.Getter;
import nl.utwente.p4.components.*;
import nl.utwente.p4.constants.TileType;
import nl.utwente.p4.ui.GameView;
import nl.utwente.p4.ui.gametable.FactoryView;
import nl.utwente.p4.ui.helper.ColorConverter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Getter
public class PatternLineView extends JPanel {
    private final ArrayList<ArrayList<JButton>> patternLineButtons;
    private final Box patternLineLayout;

    public PatternLineView(Player player) {
        patternLineButtons = new ArrayList<>();
        patternLineLayout = Box.createVerticalBox();

        createPatternLineView(player);

        add(patternLineLayout);
    }

    private void createPatternLineView(Player player) {
        for (int i = 0; i < 5; i++) {
            GeneralTileLine tileLine = player.getPatternLine().getTileLines().get(i);
            Box tileLineLayout = Box.createHorizontalBox();
            tileLineLayout.setAlignmentX(RIGHT_ALIGNMENT);

            ArrayList<JButton> tileLineButtons = new ArrayList<>();
            for (int j = 0; j < tileLine.getLineSize(); j++) {
                JButton patternLineButton = createPatternLineTileView(player, i);
                tileLineButtons.add(patternLineButton);
                tileLineLayout.add(patternLineButton);
                tileLineLayout.add(Box.createHorizontalStrut(5));
            }

            patternLineButtons.add(tileLineButtons);
            patternLineLayout.add(tileLineLayout);
            patternLineLayout.add(Box.createVerticalStrut(5));
        }
    }

    private JButton createPatternLineTileView(Player player, int row) {
        JButton patternLineButton = new JButton(" ");
        patternLineButton.setBackground(ColorConverter.convertDisabled(TileType.NULL));
        patternLineButton.setSize(new Dimension(20, 20));
        patternLineButton.setEnabled(false);
        patternLineButton.addActionListener(e -> fillTileView(player, row));
        return patternLineButton;
    }

    private void fillTileView(Player currPlayer, int row) {
        Tile selectedTile = Game.getInstance().getCurrSelectedTile();
        if (selectedTile == null) return;
        try {
            performTileOffer(currPlayer, selectedTile, row);
        } catch (Exception e) {
            handleTileOfferError(e);
           return;
        }
        refreshViews(currPlayer, row);
        Game.getInstance().setCurrSelectedFactory(null);
        Game.getInstance().setCurrSelectedTile(null);
        Game.getInstance().nextPlayer();
    }

    public void toggleEnable(boolean isEnabled) {
        Player currPlayer = Game.getInstance().getCurrentPlayer();
        List<GeneralTileLine> currPlayerTileLines = currPlayer.getPatternLine().getTileLines();

        for (int i = 0; i < patternLineButtons.size(); i++) {
            GeneralTileLine tileLine = currPlayerTileLines.get(i);
            for (int j = 0; j < patternLineButtons.get(i).size(); j++) {
                JButton patternLineButton = refreshColor(tileLine, i, j, isEnabled);
                patternLineButton.setEnabled(isEnabled);
            }
        }
    }

    public void refresh(Player player) {
        for (int i = 0; i < 5; i++) {
            refreshRow(player, i, false);
        }
    }

    public void refreshRow(Player player, int row, boolean isEnabled) {
        GeneralTileLine tileLineToRefresh = player.getPatternLine().getTileLines().get(row);
        for (int col = 0; col < patternLineButtons.get(row).size(); col++) {
            refreshColor(tileLineToRefresh, row, col, isEnabled);
        }
    }

    private JButton refreshColor(GeneralTileLine tileLineToRefresh, int row, int col, boolean isEnabledColor) {
        HashMap<TileType, Color> colorMap = ColorConverter.getColorMapEnabled();
        if (!isEnabledColor) {
            colorMap = ColorConverter.getColorMapDisabled();
        }

        JButton patternLineButton = patternLineButtons.get(row).get(col);
        ArrayList<Tile> tiles = tileLineToRefresh.getTiles();
        TileType lineType =  tileLineToRefresh.getLineType();
        if (lineType == TileType.NULL || col >= tiles.size()) {
            patternLineButton.setBackground(colorMap.get(TileType.NULL));
        } else {
            patternLineButton.setBackground(ColorConverter.convert(lineType));
        }

        return patternLineButton;
    }

    public void performTileOffer(Player player, Tile selectedTile, int row) {
        if (Game.getInstance().getCurrSelectedFactory() != null) {
            player.getFactoryOfferFromFactory(
                    Game.getInstance().getCurrSelectedFactory(),
                    selectedTile.getType(),
                    row
            );
        } else {
            player.getFactoryOfferFromTileTable(
                    selectedTile,
                    row
            );
        }
    }

    public void handleTileOfferError(Exception error) {
        JOptionPane errorMessagePane = new JOptionPane(error.getMessage(), JOptionPane.ERROR_MESSAGE);
        JDialog dialog = errorMessagePane.createDialog(null, "Error adding to Pattern Line");
        dialog.setModal(false);
        dialog.setVisible(true);

        new Timer(5000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.setVisible(false);
            }
        }).start();
    }

    public void refreshViews(Player player, int row) {
        refreshRow(player, row, true);
        toggleEnable(false);
        for (FactoryView factoryView : GameView.getInstance().getFactoryViews()) {
            factoryView.refresh();
        }
        GameView.getInstance().getTileTableView().refresh();
        FloorLineView floorLineView = GameView.getInstance().getBoardViews().get(Game.getInstance().getCurrPlayerIdx()).getFloorLineView();
        floorLineView.refresh(player);
        floorLineView.toggleEnable(false);
    }
}
