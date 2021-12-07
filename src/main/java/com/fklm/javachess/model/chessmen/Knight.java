package com.fklm.javachess.model.chessmen;

import com.fklm.javachess.ChessApplication;
import com.fklm.javachess.BoardStatus;
import com.fklm.javachess.Move;
import com.fklm.javachess.Player;
import javafx.scene.image.Image;

public class Knight extends Piece{
    private String imagePath;

    public Knight(Player color, int type) {
        super(color,type);
        putImage(color);
    }

    @Override
    public boolean isLegalMove(Move move,BoardStatus boardStatus) {
        return move.lMove();
    }


    @Override
    public void putImage(Player color) {
        Image image;
        if (color.equals(Player.WHITE)){
            image = new Image(ChessApplication.class.getResourceAsStream("images/w_knight_1x.png"));
        }
        else
            image = new Image(ChessApplication.class.getResourceAsStream("images/b_knight_1x.png"));
        setImage(image);
    }

}
