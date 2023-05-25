package nl.utwente.p4.ui;

import javax.swing.*;
import java.awt.*;

public class GameView extends JFrame {
    private final JPanel mainLayout;

    public GameView() {
        this.setTitle("Azul");
        mainLayout = new JPanel();
        setupLayout();
        showFrame();
    }

    private void setupLayout() {
        mainLayout.setPreferredSize(new Dimension(1200,720));
        mainLayout.setBackground(new Color(30,36,40));
        mainLayout.setLayout(new BorderLayout());
        add(mainLayout);
    }

    private void showFrame() {
        setVisible(true);
        setResizable(false);
        pack();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}
