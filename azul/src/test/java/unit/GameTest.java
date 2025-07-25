package unit;

import nl.utwente.p4.components.*;
import nl.utwente.p4.constants.TileType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {
    @BeforeEach
    void setup() {
        Game game = Game.getInstance();
        game.setNumOfPlayers(2);
        game.setTileBag(new TileBag());
        game.setGameBoxLid(new GameBoxLid());
        game.setTileTable(new TileTable());
        game.setFactories(new ArrayList<>());
        game.setPlayers(new ArrayList<>());
        game.setCurrPlayerIdx(0);
        game.startGame();
    }

    @Test
    void startGame_gameSetup_true() {
        // arrange
        Game game = Game.getInstance();
        int numOfFactories = 2 * game.getNumOfPlayers() + 1;

        // assert
        // reference for assertion methods: https://junit.org/junit5/docs/5.0.1/api/org/junit/jupiter/api/Assertions.html
        // assert player
        assertNotNull(game.getPlayers());
        assertEquals(game.getNumOfPlayers(), game.getPlayers().size());

        // assert tile bag
        assertNotNull(game.getTileBag());
        assertNotNull(game.getTileBag().getTiles());
        assertEquals(100 - (4 * numOfFactories), game.getTileBag().getTiles().size());  // not 100 bcos 20 have been added to factories

        // assert factories
        assertNotNull(game.getFactories());
        assertEquals(numOfFactories, game.getFactories().size());
    }

    @Test
    void resetComponentsForNextRound_resetFirstHasBeenTaken_firstHasBeenTakenFalse() {
        // arrange
        Game game = Game.getInstance();
        game.getTileTable().setFirstHasBeenTaken(true);

        // act
        game.resetComponentsForNextRound();

        // assert
        assertFalse(game.getTileTable().isFirstHasBeenTaken());
    }

    @Test
    void resetComponentsForNextRound_refillFactories_completelyRefilled() {
        // arrange
        Game game = Game.getInstance();
        int numOfFactories = 5;

        TileBag tileBag = new TileBag();
        for (int i = 0; i < numOfFactories * 4; i++) {
            tileBag.getTiles().add(new Tile(TileType.BLACK));
        }
        game.setTileBag(tileBag);

        ArrayList<Factory> factories = new ArrayList<>();
        for (int i = 0; i < numOfFactories; i++) {
            factories.add(new Factory(new ArrayList<>()));
        }
        game.setFactories(factories);

        // act
        game.resetComponentsForNextRound();

        // assert tile bag
        assertEquals(0, game.getTileBag().getTiles().size());

        // assert factories
        for (int i = 0; i < game.getFactories().size(); i++) {
            assertEquals(4, game.getFactories().get(i).getTiles().size());
        }
    }

    @Test
    void resetComponentsForNextRound_refillFactories_notEnoughTiles() {
        // arrange
        Game game = Game.getInstance();
        int numOfFactories = 2;
        TileBag tileBag = new TileBag();
        for (int i = 0; i < numOfFactories * 4; i++) {
            tileBag.getTiles().add(new Tile(TileType.BLACK));
        }
        game.setTileBag(tileBag);

        numOfFactories = 5;
        ArrayList<Factory> factories = new ArrayList<>();
        for (int i = 0; i < numOfFactories; i++) {
            factories.add(new Factory(new ArrayList<>()));
        }
        game.setFactories(factories);

        // act
        game.resetComponentsForNextRound();

        // assert tile bag
        assertEquals(0, game.getTileBag().getTiles().size());

        // assert factories
        for (int i = 0; i < 2; i++) {
            assertEquals(4, game.getFactories().get(i).getTiles().size());
        }
        for (int i = 2; i < game.getFactories().size(); i++) {
            assertEquals(0, game.getFactories().get(i).getTiles().size());
        }
    }

    @Test
    void resetComponentsForNextRound_nextRoundPreparedWithFullFactoriesFromGameBoxLid_true() {
        Game game = Game.getInstance();

        // Remove tiles from factories
        for (Factory f: game.getFactories()) {
            f.takeAllTiles();
        }

        // act
        game.resetComponentsForNextRound();

        // Check that all factories are full and game box lid is empty
        assertEquals(4, game.getFactories().get(0).getTiles().size());
        assertEquals(4, game.getFactories().get(1).getTiles().size());
        assertEquals(4, game.getFactories().get(2).getTiles().size());
        assertEquals(4, game.getFactories().get(3).getTiles().size());
        assertEquals(4, game.getFactories().get(4).getTiles().size());
        assertEquals(0, game.getGameBoxLid().getTiles().size());
    }

    @Test
    void createStartingTiles_StartingTilesCreated_true() {
        // act
        Game game = Game.getInstance();
        ArrayList<Tile> tiles = game.createStartingTiles();

        // Check that there are 20 tiles per type and 100 tiles in total
        assertEquals(100, tiles.size());
        assertEquals(20, tiles.stream().filter(t -> t.getType() == TileType.BLACK).count());
        assertEquals(20, tiles.stream().filter(t -> t.getType() == TileType.YELLOW).count());
        assertEquals(20, tiles.stream().filter(t -> t.getType() == TileType.BLUE).count());
        assertEquals(20, tiles.stream().filter(t -> t.getType() == TileType.RED).count());
        assertEquals(20, tiles.stream().filter(t -> t.getType() == TileType.WHITE).count());
    }

    @Test
    void createStartingFactories_StartingFactoriesCreated_true() {
        // act
        Game game = Game.getInstance();
        int numOfPlayers = game.getNumOfPlayers();
        ArrayList<Factory> factories = game.createStartingFactories();

        // Check that the number of factories is the same as number of players times two plus 1
        assertEquals(2 * numOfPlayers + 1, factories.size());
        // Check that all factories contain correct number of tiles
        for (Factory f: factories) {
            assertEquals(4, f.getTiles().size());
        }
    }

    @Test
    void hasAnyPlayerFilledRow_True() {
      Game game = Game.getInstance();
      var player = game.getPlayers().get(0);
      var array = new Tile[]{new Tile(TileType.BLUE),new Tile(TileType.RED),
              new Tile(TileType.WHITE), new Tile(TileType.BLACK),
              new Tile(TileType.YELLOW),};
        for (var item : array) {
            ArrayList<Tile> t = new ArrayList<>();
            t.add(item);
            player.addTiles(t,0);
            int score = player.getWall().addFromPatterLineToWall(player.getPatternLine(),player.getFloorLine().getTotalFloorScore());
            player.addScore(score);
            player.getPatternLine().clearPatterLineRow(0);
        }
      assertTrue(game.hasAnyPlayerFilledRow());
      assertEquals(13, game.getPlayers().get(game.getWinningPlayer()).getScoreTrack());
    }
    @Test
    void hasAnyPlayerFilledRow_False() {
        Game game = Game.getInstance();
        assertFalse(game.hasAnyPlayerFilledRow());
        assertEquals(game.getPlayers().get(0).getScoreTrack(), game.getPlayers().get(game.getWinningPlayer()).getScoreTrack());
    }
    @Test
    void getTilesFromLid_Empty() {
        Game game = Game.getInstance();
        assertEquals(0,game.getGameBoxLid().getTiles().size());
    }

    @Test
    void getCurrentPlayer_Player1Returned_true() {
        // act
        Player currentPlayer = Game.getInstance().getCurrentPlayer();

        // Check that the current player is the first player since it's still the first turn
        assertEquals(Game.getInstance().getPlayers().get(0), currentPlayer);
    }

    @Test
    void getCurrentPlayer_NoPlayerToReturn_true() {
        // act
        Game.getInstance().setPlayers(new ArrayList<>());
        Player currentPlayer = Game.getInstance().getCurrentPlayer();

        // Check that we received null, since there were no players to return
        assertNull(currentPlayer);
    }

    @Test
    void currSelectedFactory_success() {
        Game.getInstance().setCurrSelectedFactory(new Factory(new ArrayList<>()));
        Factory currSelectedFactory = Game.getInstance().getCurrSelectedFactory();

        assertNotNull(currSelectedFactory);
        assertEquals(0, currSelectedFactory.getTiles().size());
    }

    @Test
    void currSelectedTile_success() {
        Game.getInstance().setCurrSelectedTile(new Tile(TileType.BLACK));

        Tile currSelectedTile = Game.getInstance().getCurrSelectedTile();

        assertNotNull(currSelectedTile);
        assertEquals(TileType.BLACK, currSelectedTile.getType());
    }

    @Test
    void currPlayerIdx_success() {
        assertEquals(0, Game.getInstance().getCurrPlayerIdx());
    }

    @Test
    void tileLineIsExternal_true() {
        Game.getInstance().setTileLineIsExternal(true);

        assertTrue(Game.getInstance().isTileLineIsExternal());
    }

    @Test
    void initializePlay() {
        Game.getInstance().initializePlay(2, true);

        assertEquals(2, Game.getInstance().getNumOfPlayers());
        assertEquals(0, Game.getInstance().getCurrPlayerIdx());
        assertTrue(Game.getInstance().tileLineIsExternal());
    }

    @Test
    void nextPlayer_continueToNextPlayer_factoriesNotEmpty() {
        ArrayList<Tile> tiles = new ArrayList<>();
        tiles.add(new Tile(TileType.BLACK));
        Factory factory = new Factory(tiles);
        ArrayList<Factory> factories = new ArrayList<>();
        factories.add(factory);
        Game.getInstance().setFactories(factories);

        Game.getInstance().nextPlayer();

        assertEquals(1, Game.getInstance().getCurrPlayerIdx());
    }

    @Test
    void nextPlayer_continueToFirstPlayer_factoriesNotEmpty() {
        ArrayList<Tile> tiles = new ArrayList<>();
        tiles.add(new Tile(TileType.BLACK));
        Factory factory = new Factory(tiles);
        ArrayList<Factory> factories = new ArrayList<>();
        factories.add(factory);
        Game.getInstance().setFactories(factories);

        Game.getInstance().nextPlayer();
        Game.getInstance().nextPlayer();

        assertEquals(0, Game.getInstance().getCurrPlayerIdx());
    }

    @Test
    void nextPlayer_continueToNextPlayer_tileTableNotEmpty() {
        ArrayList<Tile> tiles = new ArrayList<>();
        Factory factory = new Factory(tiles);
        ArrayList<Factory> factories = new ArrayList<>();
        factories.add(factory);
        Game.getInstance().setFactories(factories);

        Game.getInstance().nextPlayer();

        assertEquals(1, Game.getInstance().getCurrPlayerIdx());
    }

    @Test
    void nextPlayer_continueToFirstPlayer_tileTableNotEmpty() {
        ArrayList<Tile> tiles = new ArrayList<>();
        Factory factory = new Factory(tiles);
        ArrayList<Factory> factories = new ArrayList<>();
        factories.add(factory);
        Game.getInstance().setFactories(factories);

        Game.getInstance().setCurrPlayerIdx(1);
        Game.getInstance().nextPlayer();

        assertEquals(0, Game.getInstance().getCurrPlayerIdx());
    }
}
