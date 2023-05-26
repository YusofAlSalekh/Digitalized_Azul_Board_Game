package nl.utwente.p4.ui.helper;

import nl.utwente.p4.components.Tile;
import nl.utwente.p4.constants.TileType;

import java.awt.*;
import java.util.HashMap;

public class ColorConverter {
    private static final HashMap<TileType, Color> map = new HashMap<>() {{
        put(TileType.BLACK, Color.BLACK);
        put(TileType.WHITE, Color.WHITE);
        put(TileType.BLUE, Color.BLUE);
        put(TileType.YELLOW, Color.YELLOW);
        put(TileType.RED, Color.RED);
        put(TileType.NULL, Color.GRAY);
    }};

    public static Color convert(TileType tileType) {
        return map.get(tileType);
    }
}
