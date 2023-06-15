package system;

import nl.utwente.p4.components.*;
import nl.utwente.p4.constants.TileType;
import nl.utwente.p4.ui.GameView;
import nl.utwente.p4.ui.gametable.FactoryView;
import nl.utwente.p4.ui.gametable.TileTableView;
import nl.utwente.p4.ui.helper.ColorConverter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class CannotAddIfTileTypeAlreadyOnWallSystemTest {
    @BeforeEach
    void setup() {
        Game game = Game.getInstance();
        game.setTileBag(new TileBag());
        game.setGameBoxLid(new GameBoxLid());
        game.setTileTable(new TileTable());
        game.setFactories(new ArrayList<>());
        game.setPlayers(new ArrayList<>());
        game.setCurrPlayerIdx(0);

        GameView gameView = GameView.getInstance();
        gameView.setBoardViews(new ArrayList<>());
        gameView.setFactoryViews(new ArrayList<>());
        gameView.setGameLayout(new JPanel());
    }

    @Test
    void systemTest_cannotAddIfTileTypeAlreadyOnWall() {
        Game game = Game.getInstance();
        game.play(2, false);
        GameView gameView = GameView.getInstance();

        // select one factory tile
        FactoryView chosenFactory = gameView.getFactoryViews().get(0);
        JButton chosenFactoryTile = (JButton) chosenFactory.getFactoryLayout().getComponent(0);
        Color chosenFactoryTileColor = chosenFactoryTile.getBackground();
        TileType chosenType = ColorConverter.reverse(chosenFactoryTileColor);
        chosenFactoryTile.doClick();

        // Fill the chosen tile color in to the players wall for the second row
        Wall firstWall = game.getCurrentPlayer().getWall();
        firstWall.addTile(new Tile(chosenType), 2);

        // Try adding the chosen tile to second patternline row, should fail since the wall second row already has that type filled
        JButton button = gameView.getBoardViews().get(game.getCurrPlayerIdx()).getPatternLineView().getPatternLineButtons().get(2).get(0);
        button.doClick();

        // assert patternline still empty, since tile wasnt added because the color is already on the wall
        ArrayList<JButton> buttons = gameView.getBoardViews().get(game.getCurrPlayerIdx()).getPatternLineView().getPatternLineButtons().get(2);
        for (JButton b: buttons) {
            assertEquals(TileType.NULL, ColorConverter.reverse(b.getBackground()));
        }

        // assert factory still has 4 tiles
        for (int i = 0; i < chosenFactory.getFactoryLayout().getComponentCount(); i++) {
            assertNotEquals(ColorConverter.convert(TileType.NULL), chosenFactory.getFactoryLayout().getComponent(i).getBackground());
        }

        // assert tile table still only contains first player tile
        TileTableView tileTableView = gameView.getTileTableView();
        assertEquals(1 + 1, ((Box) tileTableView.getComponent(0)).getComponentCount());  // 1 first player button + 1 vertical strut
        JButton firstPlayerButton = (JButton) ((Box) tileTableView.getComponent(0)).getComponent(0);
        assertEquals("FP", firstPlayerButton.getText());
    }
}
