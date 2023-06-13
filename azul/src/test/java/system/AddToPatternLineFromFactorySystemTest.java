package system;

import nl.utwente.p4.components.Game;
import nl.utwente.p4.constants.TileType;
import nl.utwente.p4.ui.GameView;
import nl.utwente.p4.ui.gametable.FactoryView;
import nl.utwente.p4.ui.gametable.TileTableView;
import nl.utwente.p4.ui.helper.ColorConverter;
import nl.utwente.p4.ui.playerboard.BoardView;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class AddToPatternLineFromFactorySystemTest {
    @Test
    void systemTest_addToPatternLineFromFactory() {
        Game game = Game.getInstance();
        game.play(2, false);
        GameView gameView = GameView.getInstance();

        // select factory tile
        FactoryView chosenFactory = gameView.getFactoryViews().get(0);
        JButton chosenFactoryTile = (JButton) chosenFactory.getFactoryLayout().getComponent(0);
        Color chosenFactoryTileColor = chosenFactoryTile.getBackground();
        int countMatchingFactoryTiles = 0;
        for (int i = 0; i < 4; i++) {
            if (chosenFactory.getFactoryLayout().getComponent(i).getBackground() == chosenFactoryTileColor) {
                countMatchingFactoryTiles++;
            }
        }
        chosenFactoryTile.doClick();

        // get curr player board view
        int currPlayerIdx = game.getCurrPlayerIdx();
        BoardView currPlayerBoardView = gameView.getBoardViews().get(currPlayerIdx);

        // assert pattern line enabled
        for (ArrayList<JButton> patternLineRow : currPlayerBoardView.getPatternLineView().getPatternLineButtons()) {
            for (JButton patternLineButton : patternLineRow) {
                assertEquals(ColorConverter.convert(TileType.NULL), patternLineButton.getBackground());
                assertTrue(patternLineButton.isEnabled());
            }
        }

        // assert floor line enabled
        for (JButton floorLineButton : currPlayerBoardView.getFloorLineView().getFloorLineButtons()) {
            assertEquals(ColorConverter.convert(TileType.NULL), floorLineButton.getBackground());
            assertTrue(floorLineButton.isEnabled());
        }

        // select pattern line row to add tile
        ArrayList<JButton> chosenPatternLineRow = currPlayerBoardView.getPatternLineView().getPatternLineButtons().get(0);
        JButton chosenPatternLineTile = chosenPatternLineRow.get(0);
        int chosenPatternLineRowSize = chosenPatternLineRow.size();
        chosenPatternLineTile.doClick();

        // assert tile added to pattern line
        int maxRow = Math.min(countMatchingFactoryTiles, chosenPatternLineRowSize);
        for (int i = 0; i < maxRow; i++) {
            JButton patternLineButton = chosenPatternLineRow.get(i);
            assertEquals(chosenFactoryTileColor, patternLineButton.getBackground());
            assertFalse(patternLineButton.isEnabled());
        }

        // assert excess tiles added to floor line
        if (countMatchingFactoryTiles > chosenPatternLineRowSize) {
            for (int i = 0; i < countMatchingFactoryTiles - chosenPatternLineRowSize; i++) {
                JButton floorLineButton = currPlayerBoardView.getFloorLineView().getFloorLineButtons().get(i);
                assertEquals(chosenFactoryTileColor, floorLineButton.getBackground());
                assertFalse(floorLineButton.isEnabled());
            }
        }

        // assert factory is empty
        for (int i = 0; i < chosenFactory.getFactoryLayout().getComponents().length; i++) {
            assertEquals(new Color(238, 238, 238), chosenFactory.getFactoryLayout().getComponent(i).getBackground());
        }

        // assert other factory tiles added to tile table
        TileTableView tileTableView = (TileTableView) gameView.getGameLayout().getComponent(2);
        int tileTableSize = ((Box) tileTableView.getComponent(0)).getComponents().length;
        assertEquals(2 * (1 + (4 - countMatchingFactoryTiles)), tileTableSize);
        for (int i = 0; i < tileTableSize; i += 2) {
            JButton tileTableButton = (JButton) ((Box) tileTableView.getComponent(0)).getComponent(i);
            if (i == 0) {
                assertEquals("FP", tileTableButton.getText());
                assertFalse(tileTableButton.isEnabled());
                assertEquals(new Color(255, 255, 255), tileTableButton.getBackground());
            } else {
                assertEquals(" ", tileTableButton.getText());
                assertTrue(tileTableButton.isEnabled());
            }
        }
    }
}
