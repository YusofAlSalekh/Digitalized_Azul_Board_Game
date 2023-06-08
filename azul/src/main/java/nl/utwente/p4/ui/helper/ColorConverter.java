package nl.utwente.p4.ui.helper;

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
        put(TileType.NULL, new Color(238, 238, 238));
        put(TileType.FIRST_PLAYER, Color.WHITE);
    }};

    private static final HashMap<TileType, Color> disabled = new HashMap<>() {{
        int alpha = 30;
        put(TileType.BLACK, new Color(0, 0, 0, alpha));
        put(TileType.WHITE, new Color(192, 192, 192, alpha));
        put(TileType.BLUE, new Color(0, 0, 255, alpha));
        put(TileType.YELLOW, new Color(255, 255, 0, alpha));
        put(TileType.RED, new Color(255, 0, 0, alpha));
        put(TileType.NULL, new Color(199, 199, 199));
        put(TileType.FIRST_PLAYER, Color.WHITE);
    }};

    public static HashMap<TileType, Color> getColorMapEnabled() {
        return enabled;
    }

    public static HashMap<TileType, Color> getColorMapDisabled() {
        return disabled;
    }

    public static Color convert(TileType tileType) {
        return enabled.get(tileType);
    }

    public static Color convertDisabled(TileType tileType) { return disabled.get(tileType); }
}
