package com.fklm.javachess.model.chessmen;

import com.fklm.javachess.ChessApplication;
import com.fklm.javachess.BoardStatus;
import com.fklm.javachess.Move;
import com.fklm.javachess.Player;
import javafx.scene.image.Image;

public class Pawn extends Piece{
    private String imagePath;

    public Pawn(Player color,int type) {
        super(color,type);
        putImage(color);
    }

    public void putImage(Player color){
        Image image;
        if (color.equals(Player.WHITE)){
            image = new Image(ChessApplication.class.getResourceAsStream("images/w_pawn_1x.png"));
        }
        else
            image = new Image(ChessApplication.class.getResourceAsStream("images/b_pawn_1x.png"));
        setImage(image);
    }

    @Override
    public boolean isLegalMove(Move move,BoardStatus boardStatus) {
        if(this.getColor().equals(Player.WHITE)){
            if(firstMove == 0){
                if(move.vertical() && (move.getYDif()<=2 && move.getYDif()>0)&&
                    boardStatus.space[move.getDestY()][move.getDestX()].getPiece() == null)
                    return true;
            }
            else if (move.vertical() && move.getYDif() == 1 &&
                    boardStatus.space[move.getDestY()][move.getDestX()].getPiece() == null)
                    return true;
            else return move.diagonal() && move.getYDif()==1 &&
                boardStatus.space[move.getDestY()][move.getDestX()].getPiece() != null &&
                boardStatus.space[move.getDestY()][move.getDestX()].getPiece().getColor().equals(Player.BLACK);
        }
        else{
            if(firstMove == 0){
                if(move.vertical() && (move.getYDif()>=-2 && move.getYDif()<0) &&
                    boardStatus.space[move.getDestY()][move.getDestX()].getPiece() == null)
                    return true;
            }
            else if (move.vertical() && move.getYDif() == -1 &&
                    boardStatus.space[move.getDestY()][move.getDestX()].getPiece() == null)
                return true;
            else return move.diagonal() && move.getYDif()== -1 &&
                boardStatus.space[move.getDestY()][move.getDestX()].getPiece() != null &&
                boardStatus.space[move.getDestY()][move.getDestX()].getPiece().getColor().equals(Player.WHITE);
        }
        return false;
    }

}
