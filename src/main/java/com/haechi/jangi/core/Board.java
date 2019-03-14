package com.haechi.jangi.core;

import com.haechi.jangi.display.JangiFrame;

public class Board {
    private Cell[][] cells;
    private JangiFrame jangiFrame;
    private Cell selectedCell;
    private boolean redTurn = false;
    private final static int UP = 0;
    private final static int RIGHT = 1;
    private final static int DOWN = 2;
    private final static int LEFT = 3;


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

        cells[5][4].setPiece(new Piece(false, Piece.TRAIN));
        cells[4][5].setPiece(new Piece(true, Piece.ELEPHANT));
        cells[2][5].setPiece(new Piece(false, Piece.CANNON));
        cells[7][5].setPiece(new Piece(true, Piece.CANNON));
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

            case Piece.TRAIN: markMovableTrain(posX, posY);
            break;

            case Piece.CANNON: markMovableCannon(posX, posY);
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

    private void markMovableTrain(int posX, int posY) {
        //  markMovableCannon() 함수처럼
        //  하위 함수를 만든뒤 UP, RIGHT, DOWN, LEFT로 호출하면
        //  더 읽기 쉬운 코다가 될듯
        for(int i = 1; ; i++) {
            if(!markMovableCell(posX + i, posY)) break;
            if(isHoldableCell(posX + i, posY)) break;
        }
        for(int i = 1; ; i++) {
            if(!markMovableCell(posX - i, posY)) break;
            if(isHoldableCell(posX - i, posY)) break;
        }
        for(int i = 1; ; i++) {
            if(!markMovableCell(posX, posY + i)) break;
            if(isHoldableCell(posX, posY + i)) break;
        }
        for(int i = 1; ; i++) {
            if(!markMovableCell(posX, posY - i)) break;
            if(isHoldableCell(posX, posY - i)) break;
        }
    }

    private void markMovableCannon(int posX, int posY) {
        markMovableCannon(UP, posX, posY);
        markMovableCannon(RIGHT, posX, posY);
        markMovableCannon(DOWN, posX, posY);
        markMovableCannon(LEFT, posX, posY);
    }

    private void markMovableCannon(int direction, int posX, int posY) {
        int number = 0;

        for(int i = 1; ; i++) {
            switch(direction) {
                case UP:
                    number = posY + i;
                    posY = number;
                    break;

                case RIGHT:
                    number = posX + i;
                    posX = number;
                    break;

                case DOWN:
                    number = posY - i;
                    posY = number;
                    break;

                case LEFT:
                    number = posX - i;
                    posX = number;
                    break;
            }

            if(!validIndex(posX, posY)) break;
            if(cells[posX][posY].getPiece() == null) continue;
            if(cells[posX][posY].getPiece() != null && cells[posX][posY].getPiece().getType() == Piece.CANNON) break;
            if(cells[posX][posY].getPiece() != null) {
                if (direction == UP || direction == DOWN) {
                    for (int y = number + 1; ; y++) {
                        if (!validIndex(posX, y)) break;
                        if (!markMovableCell(posX, y)) break;
                        if (isHoldableCell(posX, y)) break;
                    }

                    break;
                }
                else if (direction == RIGHT || direction == LEFT) {
                    for (int x = number + 1; ; x++) {
                        if (!validIndex(x, posY)) break;
                        if (!markMovableCell(x, posY)) break;
                        if (isHoldableCell(x, posY)) break;
                    }

                    break;
                }
            }
        }
    }

    private boolean markMovableCell(int posX, int posY) {
        if(!isMovableCell(posX, posY)) return false;

        cells[posX][posY].setMovable(true);
        return true;
    }

    private boolean isMovableCell(int posX, int posY) {
        if(validIndex(posX, posY) && cells[posX][posY].getPiece() == null) return true;

        return isHoldableCell(posX, posY);
    }

    private boolean isHoldableCell(int posX, int posY) {
        return validIndex(posX, posY) && cells[posX][posY].getPiece() != null && cells[posX][posY].getPiece().isRed() != redTurn;
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