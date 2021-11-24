package com.fklm.javachess.model.chessmen;

import com.fklm.javachess.ChessApplication;
import com.fklm.javachess.GameState;
import com.fklm.javachess.Move;
import com.fklm.javachess.Player;
import javafx.scene.image.Image;

public class Rook extends Piece{
    private String imagePath;

    public Rook(Player color, int type) {
        super(color,type);
        putImage(color);
    }

    public void putImage(Player color){
        Image image;
        if (color.equals(Player.WHITE)){
            image = new Image(ChessApplication.class.getResourceAsStream("images/w_rook_1x.png"));
        }
        else
            image = new Image(ChessApplication.class.getResourceAsStream("images/b_rook_1x.png"));
        setImage(image);
    }

    @Override
    public boolean isLegalMove(GameState gameState, Move move) {
        return false;
    }

    @Override
    public void makeMove(GameState gameState, Move move) {

    }

}