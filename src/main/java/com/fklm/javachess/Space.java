package com.fklm.javachess;

import com.fklm.javachess.model.chessmen.Pawn;
import com.fklm.javachess.model.chessmen.Piece;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

public class Space extends Button {
    private int x;
    private int y;
    private Piece piece;

    public Space (int x, int y){
        super();
        this.x = x;
        this.y = y;
        this.piece = null;
        this.setPrefWidth(62.5);
        this.setPrefHeight(62.5);
        this.setOpacity(0.25);
    }

    public boolean isOccupied(){
        if(this.piece != null)
            return true;
        return false;
    }
}
