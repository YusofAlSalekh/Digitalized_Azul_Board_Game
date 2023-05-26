package nl.utwente.p4.ui.playerboard;

import javax.swing.*;
import java.awt.*;

public class BoardView extends JPanel {
    private final JPanel layout;

    public BoardView() {
        layout = new JPanel();
        layout.setPreferredSize(new Dimension(450,250));
        layout.setBackground(new Color(78, 79, 80));
        add(layout);
    }
}
