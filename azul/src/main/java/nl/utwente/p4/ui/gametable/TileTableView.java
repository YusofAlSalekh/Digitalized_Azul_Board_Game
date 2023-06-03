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
        createTileTableView();
        add(layout);
    }

    public void createTileTableView() {
        layout.removeAll();

        for (Tile tile : Game.getInstance().getTileTable().getTiles()) {
            JButton tileButton = createTableTileButtonView(tile);
            layout.add(tileButton);
            layout.add(Box.createVerticalStrut(5));
        }
    }

    private JButton createTableTileButtonView(Tile tile) {
        JButton tileButton = new JButton();
        tileButton.setSize(new Dimension(20, 20));

        if (tile.getType() == TileType.FIRST_PLAYER) {
            tileButton.setText("-1");
            tileButton.setBackground(ColorConverter.convert(TileType.WHITE));
            tileButton.setEnabled(false);
        } else {
            tileButton.setText(" ");
            tileButton.setBackground(ColorConverter.convert(tile.getType()));
            tileButton.setEnabled(true);
        }
        tileButton.addActionListener(e -> selectTableTileView(tile));
        return tileButton;
    }

    private void selectTableTileView(Tile tile) {
        Game.getInstance().setCurrSelectedTableTile(tile);
        Game.getInstance().setCurrSelectedFactory(null);
        Game.getInstance().setCurrSelectedFactoryTile(null);
        GameView.getInstance().getBoardViews().get(Game.getInstance().getCurrPlayerIdx()).getPatternLineView().toggleEnable(true);
    }

    public void refresh() {
        createTileTableView();
        revalidate();
    }
}
