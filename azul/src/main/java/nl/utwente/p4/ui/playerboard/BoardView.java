package nl.utwente.p4.ui.playerboard;

import javax.swing.*;
import java.awt.*;

public class BoardView extends JPanel {
    private final JPanel layout;

    public BoardView() {
        layout = new JPanel();
        layout.setLayout(new BorderLayout());
        layout.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        layout.setPreferredSize(new Dimension(450,250));

        var floorLineView = new FloorLineView();
        layout.add(floorLineView, BorderLayout.SOUTH);

        add(layout);
    }
}
