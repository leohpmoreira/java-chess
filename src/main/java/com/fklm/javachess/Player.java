package com.fklm.javachess;

public enum Player {
    WHITE,BLACK;
    public Player opponent(){
        return this == WHITE? BLACK : WHITE;
    }

}
