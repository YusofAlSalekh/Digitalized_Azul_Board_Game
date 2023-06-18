package nl.utwente.p4.ui.gametable;

import lombok.Getter;
import nl.utwente.p4.components.Game;
import nl.utwente.p4.components.Tile;
import nl.utwente.p4.constants.TileType;
import nl.utwente.p4.ui.GameView;
import nl.utwente.p4.ui.helper.ColorConverter;
import nl.utwente.p4.ui.playerboard.BoardView;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

@Getter
public class TileTableView extends JPanel {
    private static Box tileTableLayout;
    private final ArrayList<JButton> tableTileButtons;

    public TileTableView() {
        tileTableLayout = Box.createVerticalBox();
        tableTileButtons = new ArrayList<>();

        createTileTableView();

        add(tileTableLayout);
    }

    public void createTileTableView() {
        for (Tile tile : Game.getInstance().getTileTable().getTiles()) {
            JButton tileButton = createTableTileButtonView(tile);
            tileTableLayout.add(tileButton);
            tileTableLayout.add(Box.createVerticalStrut(5));
        }
    }

    private JButton createTableTileButtonView(Tile tile) {
        JButton tileButton = new JButton(" ");
        tileButton.setSize(new Dimension(20, 20));
        tileButton.setEnabled(true);
        tileButton.setBackground(ColorConverter.convert(tile.getType()));
        if (tile.getType() == TileType.FIRST_PLAYER) {
            tileButton.setText("FP");
            tileButton.setEnabled(false);
        }
        tileButton.addActionListener(e -> selectTableTileView(tile));
        return tileButton;
    }

    private void selectTableTileView(Tile tile) {
        Game.getInstance().setCurrSelectedTile(tile);
        Game.getInstance().setCurrSelectedFactory(null);

        BoardView currPlayerBoardView = GameView.getInstance().getBoardViews().get(Game.getInstance().getCurrPlayerIdx());
        currPlayerBoardView.getPatternLineView().toggleEnable(true);
        currPlayerBoardView.getFloorLineView().toggleEnable(true);
    }

    public void refresh() {
        tileTableLayout.removeAll();
        createTileTableView();
        revalidate();
    }
}
