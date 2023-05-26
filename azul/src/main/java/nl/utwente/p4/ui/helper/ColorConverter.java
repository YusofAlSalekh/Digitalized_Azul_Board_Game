package nl.utwente.p4.ui.helper;

import nl.utwente.p4.components.Tile;
import nl.utwente.p4.constants.TileType;

import java.awt.*;
import java.util.HashMap;

public class ColorConverter {
    private static final HashMap<TileType, Color> enabled = new HashMap<>() {{
        put(TileType.BLACK, Color.BLACK);
        put(TileType.WHITE, Color.WHITE);
        put(TileType.BLUE, Color.BLUE);
        put(TileType.YELLOW, Color.YELLOW);
        put(TileType.RED, Color.RED);
        put(TileType.NULL, Color.GRAY);
    }};

    private static final HashMap<TileType, Color> disabled = new HashMap<>() {{
        put(TileType.BLACK, new Color(0, 0, 0, 77));
        put(TileType.WHITE, new Color(192, 192, 192, 77));
        put(TileType.BLUE, new Color(0, 0, 255, 77));
        put(TileType.YELLOW, new Color(255, 255, 0, 77));
        put(TileType.RED, new Color(255, 0, 0, 77));
        put(TileType.NULL, Color.GRAY);
    }};

    public static Color convert(TileType tileType) {
        return enabled.get(tileType);
    }

    public static Color convertDisabled(TileType tileType) { return disabled.get(tileType); }
}
