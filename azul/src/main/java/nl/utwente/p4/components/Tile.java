package nl.utwente.p4.components;

import lombok.Data;
import nl.utwente.p4.constants.TileType;

@Data
public class Tile {
    private TileType type;

    public Tile(TileType type) {
        this.type = type;
    }
}
