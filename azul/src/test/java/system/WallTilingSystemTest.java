package system;

import nl.utwente.p4.components.*;
import nl.utwente.p4.constants.TileType;
import nl.utwente.p4.ui.GameView;
import nl.utwente.p4.ui.gametable.TileTableView;
import nl.utwente.p4.ui.playerboard.BoardView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class WallTilingSystemTest {
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
    void systemTest_wallTiling() {
        Game game = Game.getInstance();

        // Initialize tiletable to have 1 red tile and 1 black tile
        TileTable tileTable = game.getTileTable();
        tileTable.addTile(new Tile(TileType.RED));
        tileTable.addTile(new Tile(TileType.BLACK));

        // Initialize empty factories
        ArrayList<Factory> factories = new ArrayList<>();
        factories.add(new Factory(new ArrayList<>()));
        factories.add(new Factory(new ArrayList<>()));
        factories.add(new Factory(new ArrayList<>()));
        factories.add(new Factory(new ArrayList<>()));
        factories.add(new Factory(new ArrayList<>()));

        game.play(2, false);
        game.setFactories(factories);
        GameView gameView = GameView.getInstance();
        gameView.getFactoryViews().forEach(factoryView -> factoryView.refresh());
        ArrayList<BoardView> boardView = gameView.getBoardViews();

        // assert that tiletable has 3 tiles, 1 first player and one red and black tile
        TileTableView tileTableView = gameView.getTileTableView();
        assertEquals(6, ((Box) tileTableView.getComponent(0)).getComponents().length);

        // assert that scoretrack is empty for both players
        JLabel scoreTrackLabel = ((JLabel) ((JPanel) boardView.get(0).getScoreTrackView().getComponent(0)).getComponent(0));
        assertEquals("Score: 0", scoreTrackLabel.getText());

        JLabel scoreTrackLabel2 = ((JLabel) ((JPanel) boardView.get(1).getScoreTrackView().getComponent(0)).getComponent(0));
        assertEquals("Score: 0", scoreTrackLabel2.getText());

        // First player takes red tile and first player tile, and add red to first patternline.
        JButton firstPlayerButton = (JButton) ((Box) tileTableView.getComponent(0)).getComponent(2);
        firstPlayerButton.doClick();
        JButton patternButton = gameView.getBoardViews().get(game.getCurrPlayerIdx())
                .getPatternLineView().getPatternLineButtons().get(0).get(0);
        patternButton.doClick();

        // Second player takes black tile and adds the tile to first patternline.
        JButton secondPlayerButton = (JButton) ((Box) tileTableView.getComponent(0)).getComponent(0);
        secondPlayerButton.doClick();
        JButton secondPatternButton = gameView.getBoardViews().get(game.getCurrPlayerIdx())
                .getPatternLineView().getPatternLineButtons().get(0).get(0);
        secondPatternButton.doClick();

        // Now both players have 1 tile in the 1 length patternline and first player has first player tile in floorline
        // Now wall tiling should happen, which would cause pattern lines and floorlines to be empty, first player to have a score of 0,
        // second player to have a score of 1, and factories being full and tiletable would have first player tile

        // Check that first player score is 0, since they got 1 point for wall tile and -1 point for floorline
        JLabel firstScoreTrackLabel = ((JLabel) ((JPanel) boardView.get(0).getScoreTrackView().getComponent(0)).getComponent(0));
        assertEquals("Score: 0", firstScoreTrackLabel.getText());

        // Check that second player score is 1, because they had an empty floorline and 1 filled in wall
        JLabel secondScoreTrackLabel2 = ((JLabel) ((JPanel) boardView.get(1).getScoreTrackView().getComponent(0)).getComponent(0));
        assertEquals("Score: 1", secondScoreTrackLabel2.getText());

        // Check that both players patternlines are empty
        for (Player p: Game.getInstance().getPlayers()) {
            PatternLine pattern = p.getPatternLine();
            for (GeneralTileLine line: pattern.getTileLines()) {
                assertEquals(TileType.NULL, line.getLineType());
                assertEquals(0, line.getTiles().size());
            }
        }

        // Check that both players floorlines are empty
        for (Player p: Game.getInstance().getPlayers()) {
            FloorLine floor = p.getFloorLine();
            assertEquals(0, floor.getTiles().size());
        }

        // Check that all factories are full
        for (Factory f: game.getFactories()) {
            assertEquals(4, f.getTiles().size());
            for (Tile t: f.getTiles()) {
                assertNotSame(t.getType(), TileType.NULL);
            }
        }

        // Check that tiletable has 1 tile which is the first player tile
        tileTableView = gameView.getTileTableView();
        assertEquals(1 + 1, ((Box) tileTableView.getComponent(0)).getComponentCount());  // 1 first player button + 1 vertical strut
        JButton firstPlayerButton2 = (JButton) ((Box) tileTableView.getComponent(0)).getComponent(0);
        assertEquals("FP", firstPlayerButton2.getText());

    }
}
