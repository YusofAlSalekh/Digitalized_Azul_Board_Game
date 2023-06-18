package nl.utwente.p4.ui.gametable;

import lombok.Getter;
import nl.utwente.p4.components.Factory;
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
public class FactoryView extends JPanel {
    private final int idx;
    private JPanel factoryLayout;
    private final ArrayList<JButton> factoryTileButtons;

    public FactoryView(int idx) {
        this.idx = idx;
        factoryTileButtons = new ArrayList<>();

        createFactoryLayout();
        createFactoryView();
    }

    private void createFactoryLayout() {
        factoryLayout = new JPanel();
        factoryLayout.setLayout(new GridLayout(2, 2, 2, 2));
    }

    private void createFactoryView() {
        Factory factory = Game.getInstance().getFactories().get(idx);
        for (int j = 0; j < factory.getTiles().size(); j++) {
            JButton factoryTileButton = createFactoryTileView(factory, factory.getTiles().get(j));
            factoryTileButtons.add(factoryTileButton);
            factoryLayout.add(factoryTileButton);
        }
    }

    private JButton createFactoryTileView(Factory factory, Tile tile) {
        JButton factoryTileButton = new JButton(" ");
        factoryTileButton.setBackground(ColorConverter.convert(tile.getType()));
        factoryTileButton.setSize(new Dimension(20, 20));
        factoryTileButton.addActionListener(e -> selectFactoryTileView(factory, tile));
        return factoryTileButton;
    }

    private void selectFactoryTileView(Factory factory, Tile tile) {
        Game.getInstance().setCurrSelectedFactory(factory);
        Game.getInstance().setCurrSelectedTile(tile);

        BoardView currPlayerBoardView = GameView.getInstance().getBoardViews().get(Game.getInstance().getCurrPlayerIdx());
        currPlayerBoardView.getPatternLineView().toggleEnable(true);
        currPlayerBoardView.getFloorLineView().toggleEnable(true);
    }

    public void refresh() {
        Factory f = Game.getInstance().getFactories().get(idx);

        for (int i = 0; i < factoryTileButtons.size(); i++) {
            JButton factoryTileButton = factoryTileButtons.get(i);

            if (f.getTiles().isEmpty()) {
                factoryTileButton.setEnabled(false);
                factoryTileButton.setBackground(ColorConverter.convert(TileType.NULL));
            } else {
                factoryTileButton.setEnabled(true);
                factoryTileButton.setBackground(ColorConverter.convert(f.getTiles().get(i).getType()));
            }
        }
    }

    public void reset() {
        factoryLayout.removeAll();
        factoryTileButtons.clear();
        createFactoryView();
    }
}
