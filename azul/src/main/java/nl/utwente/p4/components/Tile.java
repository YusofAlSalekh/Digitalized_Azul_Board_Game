package nl.utwente.p4.components;

import lombok.Getter;
import lombok.Setter;
import nl.utwente.p4.constants.TileType;

public class Tile {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tile tile = (Tile) o;
        return type == tile.type;
    }

    @Getter @Setter
    private TileType type;

    public Tile(TileType type) {
        this.type = type;
    }
}
