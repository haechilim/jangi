package com.haechi.jangi.core;

public class Cell {
    private boolean selected;
    private boolean movable;
    private Piece piece;

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isMovable() {
        return movable;
    }

    public void setMovable(boolean movable) {
        this.movable = movable;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public boolean existsPiece() {
        return piece != null;
    }

    public boolean isEmpty() {
        return piece == null;
    }
}
