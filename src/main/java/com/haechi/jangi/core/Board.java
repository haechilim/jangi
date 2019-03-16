package com.haechi.jangi.core;

import com.haechi.jangi.display.JangiFrame;

public class Board {
    private static final int LEFT = 1;
    private static final int RIGHT = 2;
    private static final int TOP = 3;
    private static final int BOTTOM = 4;

    private Cell[][] cells;
    private JangiFrame jangiFrame;
    private Cell selectedCell;
    private boolean redTurn = false;

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
    }

    public Cell[][] getCells() {
        return cells;
    }

    private void selectCell(Cell cell) {
        if(cell.existsPiece() && cell.getPiece().isRed() == redTurn) {
            cell.setSelected(true);
            selectedCell = cell;
        }
    }

    private void markMovableCell(Cell cell, int posX, int posY) {
        if(cell.isEmpty() || cell.getPiece().isRed() != redTurn) return;

        switch (cell.getPiece().getType()) {
            case Piece.PRIVATE:
                markMovablePrivate(posX, posY);
                break;

            case Piece.KING:
                markMovableKing(posX, posY);
                break;

            case Piece.SECRETARY:
                markMovableSecretary(posX, posY);
                break;

            case Piece.HORSE:
                markMovableHorse(posX, posY);
                break;

            case Piece.ELEPHANT:
                markMovableElephant(posX, posY);
                break;

            case Piece.TRAIN:
                markMovableTrain(posX, posY);
                break;

            case Piece.CANNON:
                markMovableCannon(posX, posY);
                break;
        }
    }

    private void markMovablePrivate(int posX, int posY) {
        markMovableCell(posX, posY + (redTurn ? 1 : -1));
        markMovableCell(posX + 1, posY);
        markMovableCell(posX - 1, posY);
    }

    private void markMovableKing(int posX, int posY) {
        if(isIndexInsideRoyalPalace(posX, posY - 1)) markMovableCell(posX, posY - 1);
        if(isIndexInsideRoyalPalace(posX + 1, posY - 1)) markMovableCell(posX + 1, posY - 1);
        if(isIndexInsideRoyalPalace(posX + 1, posY)) markMovableCell(posX + 1, posY);
        if(isIndexInsideRoyalPalace(posX + 1, posY + 1)) markMovableCell(posX + 1, posY + 1);
        if(isIndexInsideRoyalPalace(posX, posY + 1)) markMovableCell(posX, posY + 1);
        if(isIndexInsideRoyalPalace(posX - 1, posY + 1)) markMovableCell(posX - 1, posY + 1);
        if(isIndexInsideRoyalPalace(posX - 1, posY)) markMovableCell(posX - 1, posY);
        if(isIndexInsideRoyalPalace(posX - 1, posY - 1)) markMovableCell(posX - 1, posY - 1);
    }

    private void markMovableSecretary(int posX, int posY) {
        markMovableKing(posX, posY);
    }

    private void markMovableHorse(int posX, int posY) {
        if(isEmptyCell(posX, posY - 1)) {
            markMovableCell(posX + 1, posY - 2);
            markMovableCell(posX - 1, posY - 2);
        }
        if(isEmptyCell(posX + 1, posY)) {
            markMovableCell(posX + 2, posY - 1);
            markMovableCell(posX + 2, posY + 1);
        }
        if(isEmptyCell(posX, posY + 1)) {
            markMovableCell(posX + 1,posY + 2);
            markMovableCell(posX - 1,posY + 2);
        }
        if(isEmptyCell(posX - 1, posY)) {
            markMovableCell(posX - 2, posY - 1);
            markMovableCell(posX - 2, posY + 1);
        }
    }

    private void markMovableElephant(int posX, int posY) {
        if(isEmptyCell(posX, posY - 1)) {
            if(isEmptyCell(posX + 1, posY - 2)) markMovableCell(posX + 2, posY - 3);
            if(isEmptyCell(posX - 1, posY - 2)) markMovableCell(posX - 2, posY - 3);
        }
        if(isEmptyCell(posX + 1, posY)) {
            if(isEmptyCell(posX + 2, posY - 1)) markMovableCell(posX + 3, posY - 2);
            if(isEmptyCell(posX + 2, posY + 1)) markMovableCell(posX + 3, posY + 2);
        }
        if(isEmptyCell(posX, posY + 1)) {
            if(isEmptyCell(posX + 1,posY + 2)) markMovableCell(posX + 2, posY + 3);
            if(isEmptyCell(posX - 1,posY + 2)) markMovableCell(posX - 2, posY + 3);
        }
        if(isEmptyCell(posX - 1, posY)) {
            if(isEmptyCell(posX - 2, posY - 1)) markMovableCell(posX - 3, posY - 2);
            if(isEmptyCell(posX - 2, posY + 1)) markMovableCell(posX - 3, posY + 2);
        }
    }

    private void markMovableTrain(int posX, int posY) {
        for(int i = 1; ; i++) {
            if(!markMovableCell(posX + i, posY)) break;
            if(!isEmptyCell(posX + i, posY)) break;
        }

        for(int i = 1; ; i++) {
            if(!markMovableCell(posX - i, posY)) break;
            if(!isEmptyCell(posX - i, posY)) break;
        }

        for(int i = 1; ; i++) {
            if(!markMovableCell(posX, posY + i)) break;
            if(!isEmptyCell(posX, posY + i)) break;
        }

        for(int i = 1; ; i++) {
            if(!markMovableCell(posX, posY - i)) break;
            if(!isEmptyCell(posX, posY - i)) break;
        }
    }

    private void markMovableCannon(int posX, int posY) {
        markMovableCannon(posX, posY, LEFT);
        markMovableCannon(posX, posY, RIGHT);
        markMovableCannon(posX, posY, TOP);
        markMovableCannon(posX, posY, BOTTOM);
    }

    private void markMovableCannon(int posX, int posY, int direction) {
        boolean found = false;

        while(true) {
            if(direction == LEFT) posX--;
            else if(direction == RIGHT) posX++;
            else if(direction == TOP) posY--;
            else if(direction == BOTTOM) posY++;
            else break;

            if(!isValidIndex(posX, posY)) break;

            Cell cell = cells[posX][posY];

            if(cell.existsPiece() && cell.getPiece().getType() == Piece.CANNON) break;

            if(found) {
                markMovableCell(posX, posY);
                if(cell.existsPiece()) break;
            }
            else if(cell.existsPiece()) found = true;
        }
    }

    private boolean markMovableCell(int posX, int posY) {
        if(!isMovableCell(posX, posY)) return false;
        cells[posX][posY].setMovable(true);
        return true;
    }

    private boolean isMovableCell(int posX, int posY) {
        if(!isValidIndex(posX, posY)) return false;

        return cells[posX][posY].isEmpty() || cells[posX][posY].getPiece().isRed() != redTurn;
    }

    private boolean isEmptyCell(int posX, int posY) {
        return isValidIndex(posX, posY) && cells[posX][posY].isEmpty();
    }

    private boolean isValidIndex(int posX, int posY) {
        return (posX >= 0 && posX < 9) && (posY >= 0 && posY < 10);
    }

    private boolean isIndexInsideRoyalPalace(int posX, int posY) {
        if(!isValidIndex(posX, posY)) return false;

        if(redTurn) return (posX >= 3 && posX <= 5) && (posY >= 0 && posY <= 3);
        else return (posX >= 3 && posX <= 5) && (posY >= 7 && posY <= 9);
    }

    private void clearSelectedCells() {
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

    private boolean move(int posX, int posY) {
        if(cells[posX][posY].isMovable()) {
            cells[posX][posY].setPiece(selectedCell.getPiece());
            selectedCell.setPiece(null);
            redTurn = !redTurn;
            jangiFrame.redraw();
            return true;
        }

        return false;
    }

    public void cellClicked(int posX, int posY) {
        Cell cell = cells[posX][posY];

        move(posX, posY);
        clearSelectedCells();
        clearMovableCells();
        selectCell(cell);
        markMovableCell(cell, posX, posY);
        jangiFrame.redraw();
    }
}