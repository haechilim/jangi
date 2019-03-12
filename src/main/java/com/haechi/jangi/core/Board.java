package com.haechi.jangi.core;

import com.haechi.jangi.display.JangiFrame;

public class Board {
    private Cell[][] cells;
    private JangiFrame jangiFrame;
    private Cell selectedCell;
    private boolean redTurn = true;

    public void init() {
        makeCells();
        resetPiecesPosition();

        jangiFrame = new JangiFrame("해치 장기", this);
        jangiFrame.init();
        jangiFrame.redraw();
    }

    private void makeCells() {
        cells = new Cell[9][];

        for(int x = 0; x < 9; x++) {
            cells[x] = new Cell[10];

            for(int y = 0; y < 10; y++) {
                cells[x][y] = new Cell();
            }
        }
    }

    private void resetPiecesPosition() {
        cells[0][0].setPiece(new Piece(true, Piece.TRAIN));
        cells[1][0].setPiece(new Piece(true, Piece.ELEPHANT));
        cells[2][0].setPiece(new Piece(true, Piece.HORSE));
        cells[3][0].setPiece(new Piece(true, Piece.SECRETARY));
        cells[5][0].setPiece(new Piece(true, Piece.SECRETARY));
        cells[6][0].setPiece(new Piece(true, Piece.ELEPHANT));
        cells[7][0].setPiece(new Piece(true, Piece.HORSE));
        cells[8][0].setPiece(new Piece(true, Piece.TRAIN));
        cells[4][1].setPiece(new Piece(true, Piece.KING));
        cells[1][2].setPiece(new Piece(true, Piece.CANNON));
        cells[7][2].setPiece(new Piece(true, Piece.CANNON));
        cells[0][3].setPiece(new Piece(true, Piece.PRIVATE));
        cells[2][3].setPiece(new Piece(true, Piece.PRIVATE));
        cells[4][3].setPiece(new Piece(true, Piece.PRIVATE));
        cells[6][3].setPiece(new Piece(true, Piece.PRIVATE));
        cells[8][3].setPiece(new Piece(true, Piece.PRIVATE));

        cells[0][6].setPiece(new Piece(false, Piece.PRIVATE));
        cells[2][6].setPiece(new Piece(false, Piece.PRIVATE));
        cells[4][6].setPiece(new Piece(false, Piece.PRIVATE));
        cells[6][6].setPiece(new Piece(false, Piece.PRIVATE));
        cells[8][6].setPiece(new Piece(false, Piece.PRIVATE));
        cells[1][7].setPiece(new Piece(false, Piece.CANNON));
        cells[7][7].setPiece(new Piece(false, Piece.CANNON));
        cells[4][8].setPiece(new Piece(false, Piece.KING));
        cells[0][9].setPiece(new Piece(false, Piece.TRAIN));
        cells[1][9].setPiece(new Piece(false, Piece.HORSE));
        cells[2][9].setPiece(new Piece(false, Piece.ELEPHANT));
        cells[3][9].setPiece(new Piece(false, Piece.SECRETARY));
        cells[5][9].setPiece(new Piece(false, Piece.SECRETARY));
        cells[6][9].setPiece(new Piece(false, Piece.HORSE));
        cells[7][9].setPiece(new Piece(false, Piece.ELEPHANT));
        cells[8][9].setPiece(new Piece(false, Piece.TRAIN));

        cells[4][5].setPiece(new Piece(true, Piece.HORSE));
    }

    public Cell[][] getCells() {
        return cells;
    }

    private void selectedCell(Cell cell) {
        if(cell.getPiece() != null && cell.getPiece().isRed() == redTurn) {
            cell.setSelected(true);
            selectedCell = cell;
        }
    }

    private void markMovable(Cell cell, int posX, int posY) {
        if(cell.getPiece() == null || cell.getPiece().isRed() != redTurn) return;

        switch (cell.getPiece().getType()) {
            case Piece.PRIVATE: markMovablePrivate(posX, posY);
            break;

            case Piece.KING: markMovableKing(posX, posY);
            break;

            case Piece.SECRETARY: markMovableSecretary(posX, posY);
            break;

            case Piece.HORSE: markMovableHorse(posX, posY);
            break;

            case Piece.ELEPHANT: markMovableElephant(posX, posY);
            break;
        }
    }

    private void markMovablePrivate(int posX, int posY) {
        int offset = redTurn ? 1 : -1;

        markMovableCell(posX, posY + offset);
        markMovableCell(posX + 1, posY);
        markMovableCell(posX - 1, posY);
    }

    private void markMovableKing(int posX, int posY) {
        if(validIndexRoyalPalace(redTurn, posX, posY - 1)) markMovableCell(posX, posY - 1);
        if(validIndexRoyalPalace(redTurn, posX + 1, posY - 1)) markMovableCell(posX + 1, posY - 1);
        if(validIndexRoyalPalace(redTurn, posX + 1, posY)) markMovableCell(posX + 1, posY);
        if(validIndexRoyalPalace(redTurn, posX + 1, posY + 1)) markMovableCell(posX + 1, posY + 1);
        if(validIndexRoyalPalace(redTurn, posX, posY + 1)) markMovableCell(posX, posY + 1);
        if(validIndexRoyalPalace(redTurn, posX - 1, posY + 1)) markMovableCell(posX - 1, posY + 1);
        if(validIndexRoyalPalace(redTurn, posX - 1, posY)) markMovableCell(posX - 1, posY);
        if(validIndexRoyalPalace(redTurn, posX - 1, posY - 1)) markMovableCell(posX - 1, posY - 1);
    }

    private void markMovableSecretary(int posX, int posY) {
        markMovableKing(posX, posY);
    }

    private void markMovableHorse(int posX, int posY) {
        if(isProgressCell(posX, posY - 1)) {
            markMovableCell(posX + 1, posY - 2);
            markMovableCell(posX - 1, posY - 2);
        }
        if(isProgressCell(posX + 1, posY)) {
            markMovableCell(posX + 2, posY - 1);
            markMovableCell(posX + 2, posY + 1);
        }
        if(isProgressCell(posX, posY + 1)) {
            markMovableCell(posX + 1,posY + 2);
            markMovableCell(posX - 1,posY + 2);
        }
        if(isProgressCell(posX - 1, posY)) {
            markMovableCell(posX - 2, posY - 1);
            markMovableCell(posX - 2, posY + 1);
        }
    }

    private void markMovableElephant(int posX, int posY) {
        if(isProgressCell(posX, posY - 1)) {
            if(isProgressCell(posX + 1, posY - 2)) markMovableCell(posX + 2, posY - 3);
            if(isProgressCell(posX - 1, posY - 2)) markMovableCell(posX - 2, posY - 3);
        }
        if(isProgressCell(posX + 1, posY)) {
            if(isProgressCell(posX + 2, posY - 1)) markMovableCell(posX + 3, posY - 2);
            if(isProgressCell(posX + 2, posY + 1)) markMovableCell(posX + 3, posY + 2);
        }
        if(isProgressCell(posX, posY + 1)) {
            if(isProgressCell(posX + 1,posY + 2)) markMovableCell(posX + 2, posY + 3);
            if(isProgressCell(posX - 1,posY + 2)) markMovableCell(posX - 2, posY + 3);
        }
        if(isProgressCell(posX - 1, posY)) {
            if(isProgressCell(posX - 2, posY - 1)) markMovableCell(posX - 3, posY - 2);
            if(isProgressCell(posX - 2, posY + 1)) markMovableCell(posX - 3, posY + 2);
        }
    }

    private void markMovableCell(int posX, int posY) {
        if(isMovableCell(posX, posY)) cells[posX][posY].setMovable(true);
    }

    private boolean isMovableCell(int posX, int posY) {
        if(validIndex(posX, posY) && cells[posX][posY].getPiece() == null) return true;

        else if(validIndex(posX, posY) && cells[posX][posY].getPiece() != null && cells[posX][posY].getPiece().isRed() != redTurn) {
            return true;
        }

        return false;
    }

    private boolean isProgressCell(int posX, int posY) {
        return validIndex(posX, posY) && cells[posX][posY].getPiece() == null;
    }

    private boolean validIndex(int posX, int posY) {
        return (posX >= 0 && posX < 9) && (posY >= 0 && posY < 10);
    }

    private boolean validIndexRoyalPalace(boolean red, int posX, int posY) {
        if(!validIndex(posX, posY)) return false;

        if(red) return (posX >= 3 && posX <= 5) && (posY >= 0 && posY <= 3);
        else return (posX >= 3 && posX <= 5) && (posY >= 7 && posY <= 9);
    }

    private void clearSelectedCell() {
        for(int x = 0; x < 9; x++) {
            for(int y = 0; y < 10; y++) {
                cells[x][y].setSelected(false);
            }
        }
    }

    private void clearMovableCells() {
        for(int x = 0; x < 9; x++) {
            for(int y = 0; y < 10; y++) {
                cells[x][y].setMovable(false);
            }
        }
    }

    public void cellClicked(int posX, int posY) {
        Cell cell = cells[posX][posY];

        clearSelectedCell();
        clearMovableCells();
        selectedCell(cell);
        markMovable(cell, posX, posY);
        jangiFrame.redraw();
    }
}