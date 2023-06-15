package system;

import nl.utwente.p4.components.Game;
import nl.utwente.p4.components.GameBoxLid;
import nl.utwente.p4.components.TileBag;
import nl.utwente.p4.components.TileTable;
import nl.utwente.p4.constants.TileType;
import nl.utwente.p4.ui.GameView;
import nl.utwente.p4.ui.gametable.FactoryView;
import nl.utwente.p4.ui.gametable.TileTableView;
import nl.utwente.p4.ui.helper.ColorConverter;
import nl.utwente.p4.ui.playerboard.BoardView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class AddFromFactoryToFloorLineSystemTest {
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
    void systemTest_addFromFactoryToFloorLine() {
        Game game = Game.getInstance();
        game.play(2, false);
        GameView gameView = GameView.getInstance();

        // select a tile from first factory
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

        // get current player board view
        int currPlayerIdx = game.getCurrPlayerIdx();
        BoardView currPlayerBoardView = gameView.getBoardViews().get(currPlayerIdx);

        // Check that floor line buttons are enabled
        for (JButton floorButton : currPlayerBoardView.getFloorLineView().getFloorLineButtons()) {
            assertEquals(ColorConverter.convert(TileType.NULL), floorButton.getBackground());
            assertTrue(floorButton.isEnabled());
        }

        // select floorline to add tile, we select the second button to make sure selecting any button fills
        // the tile in the first available slot, and not the slot that was selected
        JButton floorButton = currPlayerBoardView.getFloorLineView().getFloorLineButtons().get(1);
        floorButton.doClick();

        // assert tile or tiles were added to floor line in order starting from 0.
        for (int i = 0; i < countMatchingFactoryTiles; i++) {
            JButton floorLineButton = currPlayerBoardView.getFloorLineView().getFloorLineButtons().get(i);
            assertEquals(chosenFactoryTileColor, floorLineButton.getBackground());
            assertFalse(floorLineButton.isEnabled());
        }

        // assert factory is empty
        for (int i = 0; i < chosenFactory.getFactoryLayout().getComponentCount(); i++) {
            assertEquals(ColorConverter.convert(TileType.NULL), chosenFactory.getFactoryLayout().getComponent(i).getBackground());
        }

        // assert other factory tiles added to tile table
        TileTableView tileTableView = gameView.getTileTableView();
        int tileTableSize = ((Box) tileTableView.getComponent(0)).getComponentCount();
        assertEquals(2 * (1 + (4 - countMatchingFactoryTiles)), tileTableSize);
        for (int i = 0; i < tileTableSize; i += 2) {
            JButton tileTableButton = (JButton) ((Box) tileTableView.getComponent(0)).getComponent(i);
            if (i == 0) {
                assertEquals("FP", tileTableButton.getText());
                assertFalse(tileTableButton.isEnabled());
                assertEquals(ColorConverter.convert(TileType.WHITE), tileTableButton.getBackground());
            } else {
                assertEquals(" ", tileTableButton.getText());
                assertTrue(tileTableButton.isEnabled());
            }
        }
    }
}
