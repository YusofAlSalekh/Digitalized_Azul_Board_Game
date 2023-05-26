package nl.utwente.p4.ui;

import nl.utwente.p4.ui.gametable.FactoryView;

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
        layout.setBackground(new Color(30,36,40));
        layout.setLayout(new BorderLayout());

        var factoryView = new FactoryView();
        layout.add(factoryView, BorderLayout.CENTER);

        add(layout);
    }

    private void showFrame() {
        setVisible(true);
        setResizable(false);
        pack();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}
