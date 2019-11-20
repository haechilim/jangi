package com.haechi.jangi.display;

public class JangiPiece {
    private static final String[] types = { "k", "s", "h", "e", "t", "c", "p" };

    public static String getPiecePath(boolean red, int type) {
        return String.format("%s.png", getKey(red, type));
    }

    public static String getKey(boolean red, int type) {
        return String.format("%s%s", red ? "r" : "b", toType(type));
    }

    private static String toType(int type) {
        return type>=0 && type<types.length ? types[type] : "";
    }
}