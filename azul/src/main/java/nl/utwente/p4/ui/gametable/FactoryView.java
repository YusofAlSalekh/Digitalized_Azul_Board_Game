package nl.utwente.p4.ui.gametable;

import lombok.Data;
import nl.utwente.p4.components.Factory;
import nl.utwente.p4.components.Game;
import nl.utwente.p4.components.Tile;
import nl.utwente.p4.constants.TileType;
import nl.utwente.p4.ui.helper.ColorConverter;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

@Data
public class FactoryView extends JPanel {
    private int idx;
    private JPanel factoryLayout;
    private ArrayList<JButton> factoryTileButtons;

    public FactoryView(int idx) {
        this.idx = idx;
        factoryTileButtons = new ArrayList<>();

        factoryLayout = new JPanel();
        factoryLayout.setLayout(new GridLayout(2, 2, 2, 2));


        createFactory();
    }

    private void createFactory() {
        Factory factory = Game.getInstance().getFactories().get(idx);
        for (int j = 0; j < 4; j++) {
            Tile tile = factory.getTiles().get(j);
            JButton factoryTileButton = createFactoryTile(tile.getType());
            factoryTileButton.addActionListener(e -> selectFactoryTile(factory, tile));

            factoryTileButtons.add(factoryTileButton);
            factoryLayout.add(factoryTileButton);
        }
    }

    private JButton createFactoryTile(TileType tileType) {
        JButton factoryTileButton = new JButton(" ");
        factoryTileButton.setBackground(ColorConverter.convert(tileType));
        factoryTileButton.setSize(new Dimension(20, 20));
        return factoryTileButton;
    }

    private void selectFactoryTile(Factory factory, Tile tile) {
        Game.getInstance().setCurrSelectedFactory(factory);
        Game.getInstance().setCurrSelectedFactoryTile(tile);
        Game.getInstance().getBoardViews().get(Game.getInstance().getCurrPlayerIdx()).getPatternLineView().toggleEnable(true);
    }

    public void refresh() {
        Factory f = Game.getInstance().getFactories().get(idx);

        for (int i = 0; i < factoryTileButtons.size(); i++) {
            JButton factoryTileButton = factoryTileButtons.get(i);

            if (f.getTiles().size() < 4) { // TODO: this is quick fix to avoid other bug
//            if (f.getTiles().isEmpty()) {
                factoryTileButton.setEnabled(false);
                factoryTileButton.setBackground(ColorConverter.convert(TileType.NULL));
            } else {
                factoryTileButton.setEnabled(true);
                factoryTileButton.setBackground(ColorConverter.convert(f.getTiles().get(i).getType()));
            }
        }
    }
}
