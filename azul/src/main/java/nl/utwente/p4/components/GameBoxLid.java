package nl.utwente.p4.components;

import lombok.Data;
import lombok.Getter;

import java.util.ArrayList;

@Getter
public class GameBoxLid extends TileStash{

    public GameBoxLid() {
        super();
    }

    /**
     * Removes all the tiles from the GameBoxLid and returns them. GameBoxLid will be empty after this
     * @return array of all the tiles in the GameBoxLid
     */
    public ArrayList<Tile> getAndRemoveTiles() {
        ArrayList<Tile> tilesToReturn = this.tiles;
        this.tiles = new ArrayList<>();
        return tilesToReturn;
    }
}
