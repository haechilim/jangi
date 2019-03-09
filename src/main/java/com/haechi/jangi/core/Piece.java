package com.haechi.jangi.core;

public class Piece {
    public static final int KING = 0;       //  왕(楚 or 漢)
    public static final int SECRETARY = 1;  //  사(士)
    public static final int HORSE = 2;      //  마(馬)
    public static final int ELEPHANT = 3;   //  상(象)
    public static final int TRAIN = 4;      //  차(車)
    public static final int CANNON = 5;     //  포(砲)
    public static final int PRIVATE = 6;    //  졸(卒 or 兵)

    private boolean red;
    private int type;

    public Piece(boolean red, int type) {
        this.red = red;
        this.type = type;
    }

    public boolean isRed() {
        return red;
    }

    public void setRed(boolean red) {
        this.red = red;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}