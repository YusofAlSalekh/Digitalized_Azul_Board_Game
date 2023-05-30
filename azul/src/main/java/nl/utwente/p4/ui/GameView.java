package nl.utwente.p4.ui;

import nl.utwente.p4.ui.gametable.FactoryCollectionView;
import nl.utwente.p4.ui.gametable.TileTableView;
import nl.utwente.p4.ui.playerboard.BoardCollectionView;

import javax.swing.*;
import java.awt.*;

public class GameView extends JFrame {
    private final JPanel layout;

    public GameView() {
        this.setTitle("Azul");
        layout = new JPanel();
        setupLayout();
        showFrame();
    }

    private void setupLayout() {
        layout.setPreferredSize(new Dimension(1200,720));
        layout.setLayout(new BorderLayout());

        var boardView = new BoardCollectionView();
        layout.add(boardView, BorderLayout.WEST);

        var factoryView = new FactoryCollectionView();
        layout.add(factoryView, BorderLayout.CENTER);

        var tileTableView = new TileTableView();
        layout.add(tileTableView, BorderLayout.EAST);

        add(layout);
    }

    private void showFrame() {
        setVisible(true);
        pack();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}
