package com.fklm.javachess.model.chessmen;

import com.fklm.javachess.ChessApplication;
import com.fklm.javachess.BoardStatus;
import com.fklm.javachess.Move;
import com.fklm.javachess.Player;
import javafx.scene.image.Image;

public class Pawn extends Piece{
    private String imagePath;
    private int firstMove;

    public Pawn(Player color,int type) {
        super(color,type);
        this.firstMove = 0;
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
    public boolean isLegalMove(Move move) {
        if(firstMove == 0){
            if(move.vertical() && (move.getDistance() == 2 || move.getDistance() == 1)) {
                return true;
            }
        }
        else {
            if (move.vertical() && move.getDistance() == 1)
                return true;
        }
        return false;
    }
    public void setFirstMove(int i){
        this.firstMove = i;
    }

}
