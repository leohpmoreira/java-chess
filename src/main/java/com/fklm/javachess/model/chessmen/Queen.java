package com.fklm.javachess.model.chessmen;

import com.fklm.javachess.ChessApplication;
import com.fklm.javachess.BoardStatus;
import com.fklm.javachess.Move;
import com.fklm.javachess.Player;
import javafx.scene.image.Image;

public class Queen extends Piece{
    private String imagePath;

    public Queen(Player color, int type) {
        super(color,type);
        putImage(color);
    }

    @Override
    public boolean isLegalMove(Move move,BoardStatus boardStatus) {
        if(move.diagonal() || move.horizontal() || move.vertical())
            return true;
        else
            return false;
    }


    @Override
    public void putImage(Player color) {
        Image image;
        if (color.equals(Player.WHITE)){
            image = new Image(ChessApplication.class.getResourceAsStream("images/w_queen_1x.png"));
        }
        else
            image = new Image(ChessApplication.class.getResourceAsStream("images/b_queen_1x.png"));
        setImage(image);
    }

}
