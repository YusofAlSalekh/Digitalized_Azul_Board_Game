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

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ChangeTileSelectedSystemTest {
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
    void systemTest_changeTileSelected() {
        Game game = Game.getInstance();

        // dummy add some tiles to tile table
        TileTable tileTable = game.getTileTable();
        tileTable.addTile(new Tile(TileType.YELLOW));
        tileTable.addTile(new Tile(TileType.BLACK));
        tileTable.addTile(new Tile(TileType.BLACK));
        tileTable.addTile(new Tile(TileType.YELLOW));
        tileTable.addTile(new Tile(TileType.WHITE));

        game.play(2, false);
        GameView gameView = GameView.getInstance();

        // Select tile from first factory
        FactoryView chosenFactory = gameView.getFactoryViews().get(0);
        JButton chosenFactoryTile = (JButton) chosenFactory.getFactoryLayout().getComponent(0);
        TileType chosenFactoryTileType = ColorConverter.reverse(chosenFactoryTile.getBackground());
        chosenFactoryTile.doClick();

        // Assert that the chosen tile is same type in game class selected tile and red type
        assertEquals(game.getCurrSelectedTile().getType(), chosenFactoryTileType);
        // assertEquals(TileType.RED, chosenFactoryTileType);
        // assertEquals(TileType.RED, game.getCurrSelectedTile().getType());

        // select first yellow tile from tile table
        Box tileTableLayout = (Box) gameView.getTileTableView().getComponent(0);
        JButton chosenTableTile = (JButton) tileTableLayout.getComponent(2);  // select the dummy yellow tile, order of tiles is FP -> strut -> Yellow -> strut
        TileType chosenTableTileType = ColorConverter.reverse(chosenTableTile.getBackground());
        chosenTableTile.doClick();

        // Check that game current selected tile is the same as the chosenTableTileType, which means that game logic
        // follows the UI. Also check that the new type is yellow, which should be the tile table tile type
        assertEquals(game.getCurrSelectedTile().getType(), chosenTableTileType);
        assertEquals(TileType.YELLOW, chosenTableTileType);
        assertEquals(TileType.YELLOW, game.getCurrSelectedTile().getType());
    }
}
