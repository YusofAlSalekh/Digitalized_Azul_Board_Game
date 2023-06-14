package system;

import nl.utwente.p4.components.*;
import nl.utwente.p4.constants.TileType;
import nl.utwente.p4.ui.GameView;
import nl.utwente.p4.ui.helper.ColorConverter;
import nl.utwente.p4.ui.playerboard.BoardView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class AddToPatternLineFromTileTableSystemTest {
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
    void systemTest_addToPatternLineFromTileTable() {
        Game game = Game.getInstance();

        // dummy add some tiles to tile table
        TileTable tileTable = game.getTileTable();
        tileTable.addTile(new Tile(TileType.BLACK));
        tileTable.addTile(new Tile(TileType.BLACK));
        tileTable.addTile(new Tile(TileType.BLACK));
        tileTable.addTile(new Tile(TileType.YELLOW));
        tileTable.addTile(new Tile(TileType.WHITE));

        game.play(2, false);
        GameView gameView = GameView.getInstance();

        // select tile table tile
        Box tileTableLayout = (Box) gameView.getTileTableView().getComponent(0);
        JButton chosenTableTile = (JButton) tileTableLayout.getComponent(2);  // select the dummy black tile, order of tiles is FP -> strut -> BLACK -> strut
        Color chosenTableTileColor = chosenTableTile.getBackground();
        int countMatchingTableTiles = 0;
        for (int i = 0; i < tileTableLayout.getComponentCount(); i++) {
            if (tileTableLayout.getComponent(i).getBackground() == chosenTableTileColor) {
                countMatchingTableTiles++;
            }
        }
        chosenTableTile.doClick();

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
        int maxRow = Math.min(countMatchingTableTiles, chosenPatternLineRowSize);
        for (int i = 0; i < maxRow; i++) {
            JButton patternLineButton = chosenPatternLineRow.get(i);
            assertEquals(chosenTableTileColor, patternLineButton.getBackground());
            assertFalse(patternLineButton.isEnabled());
        }

        // assert first player tile added to floor line
        JButton firstPlayerButtonInFloorLine = currPlayerBoardView.getFloorLineView().getFloorLineButtons().get(0);
        assertEquals(ColorConverter.convert(TileType.FIRST_PLAYER), firstPlayerButtonInFloorLine.getBackground());
        assertFalse(firstPlayerButtonInFloorLine.isEnabled());

        // assert excess tiles added to floor line
        if (countMatchingTableTiles > chosenPatternLineRowSize) {
            for (int i = 1; i < countMatchingTableTiles - chosenPatternLineRowSize; i++) {
                JButton floorLineButton = currPlayerBoardView.getFloorLineView().getFloorLineButtons().get(i);
                assertEquals(chosenTableTileColor, floorLineButton.getBackground());
                assertFalse(floorLineButton.isEnabled());
            }
        }

        // assert tile table contains remaining tiles
        assertEquals(2, tileTableLayout.getComponentCount() / 2);  // remaining tiles are YELLOW and WHITE, divided by 2 to remove struts
    }
}
