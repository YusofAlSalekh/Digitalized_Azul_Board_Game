package nl.utwente.p4.ui.gametable;

import nl.utwente.p4.components.Game;

import javax.swing.*;

public class FactoryCollectionView extends JPanel {
    private final Box layout;

    public FactoryCollectionView() {
        layout = Box.createVerticalBox();
        createFactories();
        add(layout);
    }

    private void createFactories() {
        for (int i = 0; i < Game.getInstance().numOfFactories(); i++) {
            FactoryView factoryView = new FactoryView(i);
            Game.getInstance().getFactoryViews().add(factoryView);

            layout.add(factoryView.getFactoryLayout());
            layout.add(Box.createVerticalStrut(20));
        }
    }
}
