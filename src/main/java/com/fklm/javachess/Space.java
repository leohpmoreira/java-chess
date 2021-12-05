package com.fklm.javachess;

import com.fklm.javachess.model.chessmen.*;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

public class Space extends Button {
    public Position position;
    public ArrayList<Space> possPositions;
    public Piece piece;

    public Space (int x, int y){
        super();
        possPositions = new ArrayList<>();
        this.position = new Position(x,y);
    }

    public boolean isOccupied() {
        if (this.piece != null)
            return true;
        return false;
    }

    public void addWay(Space[][] board,BoardStatus boardStatus){
        if(piece != null){
            for(int i=0; i<8; i++){
                for(int j=0; j<8; j++){
                    Move move = new Move(this.position,board[i][j].getPosition());
                    if(piece.isLegalMove(move) && boardStatus.isntObstructe(move,piece)) {
                        possPositions.add(board[i][j]);
                    }
                }
            }
        }
        else
            possPositions.clear();
    }

    public void setPiece(Piece piece){
        this.piece = piece;
    }

    public void clear(){
        this.piece = null;
        this.possPositions.clear();
    }

    public Piece getPiece() {
        return piece;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
}
