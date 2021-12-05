package com.fklm.javachess.model.chessmen;

import com.fklm.javachess.*;
import javafx.scene.image.Image;

public class King extends Piece{
    private int firstMove;

    public King(Player color, int type) {
        super(color, type);
        this.firstMove =0;
        putImage(color);
    }

    @Override
    public boolean isLegalMove( Move move) {
        if(move.getDistance() == 1)
            return true;
        else
            return false;
    }


    @Override
    public void putImage(Player color) {
        Image image;
        if (color.equals(Player.WHITE)){
            image = new Image(ChessApplication.class.getResourceAsStream("images/w_king_1x.png"));
        }
        else
            image = new Image(ChessApplication.class.getResourceAsStream("images/b_king_1x.png"));
        setImage(image);
    }

    public void setFirstMove(int i){
        this.firstMove = i;
    }
}
