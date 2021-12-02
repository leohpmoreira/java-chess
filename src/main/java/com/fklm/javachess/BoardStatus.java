package com.fklm.javachess;

import com.fklm.javachess.controller.BoardController;
import com.fklm.javachess.model.chessmen.Piece;

import java.io.IOException;
import java.util.ArrayList;

public class BoardStatus {
    private Player playerTurn = BoardController.current;
    private ArrayList<Space>whitePositon;
    private ArrayList<Space>blackPosition;
    private Space wKing;
    private Space bKing;
    private Space[][] space = BoardController.space;

    public BoardStatus(){
        this.whitePositon = new ArrayList<Space>();
        this.blackPosition = new ArrayList<Space>();
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                if(space[i][j].getPiece() != null) {
                    if (space[i][j].getPiece().getColor().equals(Player.WHITE)) {
                        whitePositon.add(space[i][j]);
                        if(space[i][j].getPiece().getType() == 5)
                            wKing = space[i][j];
                    }
                    else {
                        blackPosition.add(space[i][j]);
                        if(space[i][j].getPiece().getType() == 5)
                            bKing = space[i][j];
                    }
                }
            }
        }
    }

    public void updateStatus(Move move,Player player){
        Space start = space[move.getStartY()][move.getStartX()];
        Space destiny = space[move.getDestY()][move.getDestX()];
        if(player.equals(Player.WHITE)){
            if(destiny.getPiece() != null)
                blackPosition.remove(destiny);
            whitePositon.remove(start);
            whitePositon.add(destiny);
        }
        else{
            if(destiny.getPiece() != null)
                whitePositon.remove(destiny);
            blackPosition.remove(start);
            blackPosition.add(destiny);
        }
    }

    public boolean isInCheck(){
        if(playerTurn.equals(Player.WHITE)){
            
        }
    }

    public boolean isObstructe(Move move,Piece piece){
        if(piece.getType() == 2)
            return true;
        int dirX = move.getXDif(),dirY= move.getYDif();
        int x,y;
        x = move.getStartX();
        y = move.getStartY();
        if(move.getXDif() != 0) {
            dirX = (Math.abs(move.getXDif())) / move.getXDif();
            x += dirX;
        }
        if(move.getYDif() != 0){
            dirY = (Math.abs(move.getYDif()))/move.getYDif();
            y += dirY;
        }
        Position test = new Position(x,y);
        while(!test.equals(move.getDestiny())){
            if(space[y][x].getPiece() != null)
                return false;
            test.setX(x += dirX);
            test.setY(y += dirY);
        }
        return true;
    }
}
