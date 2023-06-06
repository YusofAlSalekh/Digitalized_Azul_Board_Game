package nl.utwente.p4.ui;

import lombok.Getter;
import nl.utwente.p4.components.Game;
import nl.utwente.p4.components.Player;
import nl.utwente.p4.ui.gametable.FactoryView;
import nl.utwente.p4.ui.gametable.TileTableView;
import nl.utwente.p4.ui.playerboard.BoardView;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

@Getter
public class GameView extends JFrame {
    private static GameView instance;
    private final ArrayList<BoardView> boardViews;
    private final ArrayList<FactoryView> factoryViews;
    private TileTableView tileTableView;
    private final JPanel gameLayout;

    private GameView() {
        boardViews = new ArrayList<>();
        factoryViews = new ArrayList<>();
        gameLayout = new JPanel();

        setTitle("Azul");
        setupLayout();
        showFrame();
    }

    public static GameView getInstance() {
        if (instance == null) {
            instance = new GameView();
        }
        return instance;
    }

    private void setupLayout() {
        gameLayout.setPreferredSize(new Dimension(1200,720));
        gameLayout.setLayout(new BorderLayout());

        gameLayout.add(createBoardViews(), BorderLayout.WEST);

        gameLayout.add(createFactoryViews(), BorderLayout.CENTER);

        tileTableView = new TileTableView();
        gameLayout.add(tileTableView, BorderLayout.EAST);

        add(gameLayout);
    }

    private JPanel createBoardViews() {
        JPanel panel = new JPanel();
        Box layout = Box.createVerticalBox();

        for (Player player : Game.getInstance().getPlayers()) {
            BoardView boardView = new BoardView(player);
            boardViews.add(boardView);

            layout.add(boardView.getBoardLayout());
            layout.add(Box.createVerticalStrut(20));
        }

        panel.add(layout);
        return panel;
    }

    private JPanel createFactoryViews() {
        JPanel panel = new JPanel();
        Box layout = Box.createVerticalBox();

        for (int i = 0; i < Game.getInstance().numOfFactories(); i++) {
            FactoryView factoryView = new FactoryView(i);
            factoryViews.add(factoryView);

            layout.add(factoryView.getFactoryLayout());
            layout.add(Box.createVerticalStrut(20));
        }

        panel.add(layout);
        return panel;
    }

    private void showFrame() {
        setVisible(true);
        pack();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public void endGame(int winningPlayerIdx) {
        JOptionPane.showMessageDialog(null, "Player " + (winningPlayerIdx + 1) + " has won this game!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
    }
}
