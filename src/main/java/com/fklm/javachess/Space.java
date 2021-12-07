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

    public void addWay(Space[][] board,BoardStatus boardStatus){
        if(piece != null){
            for(int i=0; i<8; i++){
                for(int j=0; j<8; j++){
                    Move move = new Move(this.position,board[i][j].getPosition());
                    if(piece.isLegalMove(move,boardStatus) && boardStatus.isntObstructe(move,piece) &&
                    (boardStatus.space[move.getDestY()][move.getDestX()].getPiece() == null ||
                    !boardStatus.space[move.getDestY()][move.getDestX()].getPiece().getColor().equals(piece.getColor()))) {
                        possPositions.add(board[i][j]);
                    }
                }
            }
        }
        else
            possPositions.clear();
    }

    public String strPos(){
        char letter;
        letter =(char) (position.getY() + 65);
        System.out.println(letter +""+ (position.getX()+1));
        return (letter +""+ position.getX()+1);
    }

    public void setPiece(Piece piece){
        this.piece = piece;
    }

    public void clear(){
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

    public ArrayList<Space> getPossPositions(){
        return possPositions;
    }

    public void removePossPositions(Space space){
        possPositions.remove(space);
    }
}
