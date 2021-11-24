package com.fklm.javachess;

import com.fklm.javachess.model.chessmen.*;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Space extends Button {
    public Position position;
    public Piece piece;

    public Space (int x, int y){
        super();
        this.position = new Position(x,y);
    }

    public boolean isOccupied() {
        if (this.piece != null)
            return true;
        return false;
    }

    public void setPiece(Piece piece){
        this.piece = piece;
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
