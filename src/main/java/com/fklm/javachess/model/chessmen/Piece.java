package com.fklm.javachess.model.chessmen;

import com.fklm.javachess.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;

public abstract class Piece {
    private final Player color;
    public Image image;
    private int type;

    public Piece(Player color,int type) {
        this.color = color;
        this.type = type;
    }

    public Piece createPiece(Player color,int type){
        Piece p;
        switch (type){
            case 0 : p = new Pawn(color,type);
                     break;
            case 1: p = new Rook(color,type);
                    break;
            case 2: p = new Knight(color,type);
                    break;
            case 3: p = new Bishop(color,type);
                    break;
            case 4: p = new Queen(color,type);
                    break;
            case 5: p = new King(color,type);
                    break;
            default: p = null;
        }
        return p;
    }

    public Player getColor() {
        return color;
    }

    public Image getImage() {
        return image;
    }


    public void setImage(Image image){
        this.image = image;
    };

    public abstract boolean isLegalMove(GameState gameState, Move move);
    public abstract void makeMove(GameState gameState, Move move);
    public abstract void putImage(Player color);

    public int getType() {
        return type;
    }
}