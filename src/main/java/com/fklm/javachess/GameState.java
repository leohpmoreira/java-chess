package com.fklm.javachess;

import com.fklm.javachess.model.chessmen.Piece;

public class GameState {
    private GameState prev,future;
    private Player playerTurn = Player.WHITE;
    private Piece[][] pieces;

    public GameState(){
    }

    public void setPieces(int x, int y){

    }
}
