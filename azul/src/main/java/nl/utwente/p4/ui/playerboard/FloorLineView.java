package nl.utwente.p4.ui.playerboard;

import lombok.Getter;
import nl.utwente.p4.components.Game;
import nl.utwente.p4.components.Player;
import nl.utwente.p4.components.Tile;
import nl.utwente.p4.constants.FloorScore;
import nl.utwente.p4.constants.TileType;
import nl.utwente.p4.ui.GameView;
import nl.utwente.p4.ui.gametable.FactoryView;
import nl.utwente.p4.ui.helper.ColorConverter;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

@Getter
public class FloorLineView extends JPanel {
    private final ArrayList<JButton> floorLineButtons;
    private final JPanel floorLineLayout;
    public FloorLineView(Player player) {
        floorLineButtons = new ArrayList<>();
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
                if (tileType == TileType.FIRST_PLAYER) floorLineButton.setText("FP");
            } else {
                floorLineButton.setBackground(ColorConverter.convertDisabled(TileType.NULL));
            }
            floorLineButton.setSize(new Dimension(20, 20));
            floorLineButton.setEnabled(false);
            floorLineButton.addActionListener(e -> fillTileView(player));
            floorLineButtons.add(floorLineButton);
            floorLineLayout.add(floorLineButton);
        }
    }

    private void fillTileView(Player currPlayer) {
        Tile selectedTile = Game.getInstance().getCurrSelectedTile();
        if (selectedTile == null) return;

        performTileOffer(currPlayer, selectedTile);
        refreshViews(currPlayer);

        Game.getInstance().setCurrSelectedFactory(null);
        Game.getInstance().setCurrSelectedTile(null);
        Game.getInstance().nextPlayer();
    }

    public void refresh(Player player) {
        floorLineLayout.removeAll();
        floorLineButtons.clear();
        createFloorScoreView(player);
        createFloorTilesView(player);
    }

    public void toggleEnable(boolean isEnabled) {
        Player currPlayer = Game.getInstance().getCurrentPlayer();
        ArrayList<Tile> currPlayerFloorLine = currPlayer.getFloorLine().getTiles();

        for (int i = currPlayerFloorLine.size(); i < floorLineButtons.size(); i++) {
            JButton floorLineButton = refreshColor(i, isEnabled);
            floorLineButton.setEnabled(isEnabled);
        }
    }

    public void performTileOffer(Player player, Tile selectedTile) {
        if (Game.getInstance().getCurrSelectedFactory() != null) {
            player.addFloorLineFromFactory(
                    Game.getInstance().getCurrSelectedFactory(),
                    selectedTile.getType()
            );
        } else {
            player.addFloorLineFromTileTable(
                    selectedTile
            );
        }
    }

    public void refreshViews(Player player) {
        refresh(player);
        toggleEnable(false);
        for (FactoryView factoryView : GameView.getInstance().getFactoryViews()) {
            factoryView.refresh();
        }
        GameView.getInstance().getTileTableView().refresh();
        PatternLineView patternLineView = GameView.getInstance().getBoardViews().get(
                Game.getInstance().getCurrPlayerIdx()).getPatternLineView();
        patternLineView.refresh(player);
        patternLineView.toggleEnable(false);
    }

    private JButton refreshColor(int idx, boolean isEnabledColor) {
        HashMap<TileType, Color> colorMap = ColorConverter.getColorMapEnabled();
        if (!isEnabledColor) {
            colorMap = ColorConverter.getColorMapDisabled();
        }

        JButton floorLineButton = floorLineButtons.get(idx);
        floorLineButton.setBackground(colorMap.get(TileType.NULL));

        return floorLineButton;
    }

}
