package com.haechi.jangi.display;

import java.awt.*;

public class JangiPiece {
    private static final String classpath = JangiPiece.class.getResource("/").getPath();
    private static final String[] types = { "k", "s", "h", "e", "t", "c", "p" };

    public static Image getPiece(boolean red, int type) {
        String path = String.format("%s%s.png", classpath, getKey(red, type));
        return Util.loadImage(path);
    }

    public static String getKey(boolean red, int type) {
        return String.format("%s%s", red ? "r" : "b", toType(type));
    }

    private static String toType(int type) {
        return type>=0 && type<types.length ? types[type] : "";
    }
}
