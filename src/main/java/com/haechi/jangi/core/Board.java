package com.haechi.jangi.core;

import com.haechi.jangi.display.JangiFrame;

public class Board {
    private Cell[][] cells;
    private JangiFrame jangiFrame;

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

    public void cellClicked(int posX, int posY) {
        jangiFrame.redraw();
    }
}
