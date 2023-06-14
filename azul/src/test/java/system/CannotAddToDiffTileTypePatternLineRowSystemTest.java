package system;

import nl.utwente.p4.components.*;
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
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class CannotAddToDiffTileTypePatternLineRowSystemTest {
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
    void systemTest_cannotAddToDiffTileTypePatternLineRow() {
        Game game = Game.getInstance();
        game.play(2, false);
        GameView gameView = GameView.getInstance();

        // select factory tile
        FactoryView chosenFactory = gameView.getFactoryViews().get(0);
        JButton chosenFactoryTile = (JButton) chosenFactory.getFactoryLayout().getComponent(0);
        Color chosenFactoryTileColor = chosenFactoryTile.getBackground();
        chosenFactoryTile.doClick();

        // get curr player board view
        int currPlayerIdx = game.getCurrPlayerIdx();
        BoardView currPlayerBoardView = gameView.getBoardViews().get(currPlayerIdx);

        // dummy add other color tiles to pattern line row
        PatternLine patternLine = game.getPlayers().get(currPlayerIdx).getPatternLine();
        ArrayList<Tile> dummyTiles = new ArrayList<>();
        for (TileType tileType : new ArrayList<>(
                Arrays.asList(TileType.RED, TileType.BLUE, TileType.BLACK, TileType.WHITE, TileType.YELLOW))) {
            if (tileType != ColorConverter.reverse(chosenFactoryTileColor)) {
                dummyTiles.add(new Tile(tileType));
                break;
            }
        }
        patternLine.addTiles(dummyTiles, 2);

        // select pattern line row to add tile
        ArrayList<JButton> chosenPatternLineRow = currPlayerBoardView.getPatternLineView().getPatternLineButtons().get(2);
        JButton chosenPatternLineTile = chosenPatternLineRow.get(0);
        Color chosenPatternLineTileColor = chosenPatternLineTile.getBackground();
        chosenPatternLineTile.doClick();

        // assert tile on pattern line is unchanged
        for (JButton patternLineButton : chosenPatternLineRow) {
            assertEquals(chosenPatternLineTileColor, patternLineButton.getBackground());
        }

        // assert floor line still empty
        for (JButton floorLineButton : currPlayerBoardView.getFloorLineView().getFloorLineButtons()) {
            assertEquals(" ", floorLineButton.getText());
            assertEquals(ColorConverter.convert(TileType.NULL), floorLineButton.getBackground());
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
