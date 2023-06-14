package system;

import nl.utwente.p4.components.Game;
import nl.utwente.p4.components.GameBoxLid;
import nl.utwente.p4.components.TileBag;
import nl.utwente.p4.components.TileTable;
import nl.utwente.p4.constants.FloorScore;
import nl.utwente.p4.constants.TileType;
import nl.utwente.p4.ui.GameView;
import nl.utwente.p4.ui.gametable.FactoryView;
import nl.utwente.p4.ui.gametable.TileTableView;
import nl.utwente.p4.ui.helper.ColorConverter;
import nl.utwente.p4.ui.playerboard.BoardView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class InitialGameStartSystemTest {
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
    void systemTest_initialGameStart() {
        int numOfPlayers = 2;
        Game game = Game.getInstance();
        game.play(numOfPlayers, false);
        GameView gameView = GameView.getInstance();

        // assert that the layouts exist
        assertNotNull(gameView.getBoardViews());
        assertNotNull(gameView.getFactoryViews());
        assertNotNull(gameView.getGameLayout());

        // assert that there are a proper number of board views and factory views
        assertEquals(numOfPlayers, gameView.getBoardViews().size());
        assertEquals(game.numOfFactories(), gameView.getFactoryViews().size());

        // assert that each board view has the proper components
        for (BoardView boardView : gameView.getBoardViews()) {
            assertNotNull(boardView.getScoreTrackView());
            assertNotNull(boardView.getPatternLineView());
            assertNotNull(boardView.getWallView());
            assertNotNull(boardView.getFloorLineView());

            // assert that score track is empty
            JLabel scoreTrackLabel = ((JLabel) ((JPanel) boardView.getScoreTrackView().getComponent(0)).getComponent(0));
            assertEquals("Score: 0", scoreTrackLabel.getText());

            // assert that pattern line is empty
            int struts = 5;
            int numOfPatternLineRows = 5;
            assertEquals(numOfPatternLineRows + struts, boardView.getPatternLineView().getPatternLineLayout().getComponentCount());
            assertEquals(numOfPatternLineRows, boardView.getPatternLineView().getPatternLineButtons().size());
            for (int i = 0 ; i < boardView.getPatternLineView().getPatternLineButtons().size(); i++) {
                ArrayList<JButton> patternLineRow = boardView.getPatternLineView().getPatternLineButtons().get(i);
                assertEquals(i + 1, patternLineRow.size());
                for (JButton patternLineButton : patternLineRow) {
                    assertEquals(" ", patternLineButton.getText());
                    assertEquals(ColorConverter.convertDisabled(TileType.NULL), patternLineButton.getBackground());
                    assertFalse(patternLineButton.isEnabled());
                }
            }

            // assert that wall is empty
            int numOfWallRows = 5;
            int numOfWallTiles = 5;
            assertEquals(numOfWallRows + struts, boardView.getWallView().getWallLayout().getComponentCount());
            for (int i = 0; i < numOfWallRows + struts; i += 2) {
                assertEquals(5 + struts, ((Box) boardView.getWallView().getWallLayout().getComponent(i)).getComponentCount());
                for (int j = 0; j < numOfWallTiles + struts; j += 2) {
                    JButton wallButton = (JButton) ((Box) boardView.getWallView().getWallLayout().getComponent(i)).getComponent(j);
                    assertEquals(" ", wallButton.getText());
                    assertNotEquals(ColorConverter.convertDisabled(TileType.NULL), wallButton.getBackground());
                    assertFalse(wallButton.isEnabled());
                }
            }

            // assert that floor line is empty
            int numOfFloorLineTiles = 7;
            assertEquals(numOfFloorLineTiles * 2, boardView.getFloorLineView().getFloorLineLayout().getComponentCount());
            assertEquals(numOfFloorLineTiles, boardView.getFloorLineView().getFloorLineButtons().size());
            for (int i = 0; i < numOfFloorLineTiles; i++) {
                JLabel floorScoreLabel = (JLabel) (boardView.getFloorLineView().getFloorLineLayout().getComponent(i));
                assertEquals(Integer.toString(FloorScore.getValues()[i]), floorScoreLabel.getText());
            }
            for (JButton floorLineButton : boardView.getFloorLineView().getFloorLineButtons()) {
                assertEquals(" ", floorLineButton.getText());
                assertEquals(ColorConverter.convert(TileType.NULL), floorLineButton.getBackground());
                assertFalse(floorLineButton.isEnabled());
            }
        }

        // assert that each factory has 4 tiles
        for (FactoryView factoryView : gameView.getFactoryViews()) {
            assertEquals(4, factoryView.getFactoryTileButtons().size());
            for (JButton factoryTileButton : factoryView.getFactoryTileButtons()) {
                assertEquals(" ", factoryTileButton.getText());
                assertTrue(factoryTileButton.isEnabled());
            }
        }

        // assert that tile table has 1 first player tile
        TileTableView tileTableView = gameView.getTileTableView();
        assertEquals(1 + 1, ((Box) tileTableView.getComponent(0)).getComponentCount());  // 1 first player button + 1 vertical strut
        JButton firstPlayerButton = (JButton) ((Box) tileTableView.getComponent(0)).getComponent(0);
        assertEquals("FP", firstPlayerButton.getText());
        assertFalse(firstPlayerButton.isEnabled());
        assertEquals(ColorConverter.convert(TileType.WHITE), firstPlayerButton.getBackground());
    }
}
