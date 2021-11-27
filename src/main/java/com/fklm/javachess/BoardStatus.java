package com.fklm.javachess;

import com.fklm.javachess.model.chessmen.Piece;

import java.io.IOException;
import java.util.ArrayList;

public class BoardStatus {
    private Player playerTurn;
    private ArrayList<Position>whitePositon;
    private ArrayList<Position>blackPosition;
    private ArrayList<Position> possibleWhite;
    private ArrayList<Position> possibleBlack;

    public BoardStatus(){
        this.playerTurn = Player.WHITE;
    }

    public boolean isObstructe(Move move){
        int dirX,dirY;
        int x,y;
        dirX = (Math.abs(move.getXDif()))/move.getXDif();
        dirY = (Math.abs(move.getYDif()))/move.getYDif();
    }
}
