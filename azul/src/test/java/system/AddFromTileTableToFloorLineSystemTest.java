package system;

import nl.utwente.p4.components.*;
import nl.utwente.p4.constants.TileType;
import nl.utwente.p4.ui.GameView;
import nl.utwente.p4.ui.gametable.TileTableView;
import nl.utwente.p4.ui.helper.ColorConverter;
import nl.utwente.p4.ui.playerboard.BoardView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class AddFromTileTableToFloorLineSystemTest {
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
    void systemTest_addFromTileTableToFloorLine() {
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

        // select first black tile from tiletable
        Box tileTableLayout = (Box) gameView.getTileTableView().getComponent(0);
        JButton chosenTile = (JButton) tileTableLayout.getComponent(2);  // select the dummy black tile, order of tiles is FP -> strut -> BLACK -> strut
        Color chosenTileColor = chosenTile.getBackground();
        chosenTile.doClick();

        // get current player board view
        int currPlayerIdx = game.getCurrPlayerIdx();
        BoardView currPlayerBoardView = gameView.getBoardViews().get(currPlayerIdx);

        // Check that floor line buttons are enabled
        for (JButton floorButton : currPlayerBoardView.getFloorLineView().getFloorLineButtons()) {
            assertEquals(ColorConverter.convert(TileType.NULL), floorButton.getBackground());
            assertTrue(floorButton.isEnabled());
        }

        // select floorline to add tile, we select the second button to make sure selecting any button fills
        // the tiles in the first available slots, and not the slot that was selected
        JButton floorButton = currPlayerBoardView.getFloorLineView().getFloorLineButtons().get(1);
        floorButton.doClick();


        // Check that first player tile is in the first floorline slot and its button is disabled
        JButton firstFloorLineButton = currPlayerBoardView.getFloorLineView().getFloorLineButtons().get(0);
        assertEquals("FP", firstFloorLineButton.getText());
        assertFalse(firstFloorLineButton.isEnabled());

        // Check that 3 black tiles are also in the floorline, starting from index 1, since index 0 is first player tile
        // These buttons should also be disabled now
        for (int i = 1; i < 4; i++) {
            JButton floorLineButton = currPlayerBoardView.getFloorLineView().getFloorLineButtons().get(i);
            assertEquals(chosenTileColor, floorLineButton.getBackground());
            assertFalse(floorLineButton.isEnabled());
        }

        // assert tile table is now 2 in length, since 3 black tiles and first player tile were taken
        TileTableView tileTableView = gameView.getTileTableView();
        assertEquals(4, ((Box) tileTableView.getComponent(0)).getComponents().length); // 2 tiles + 2 struts
    }
}
