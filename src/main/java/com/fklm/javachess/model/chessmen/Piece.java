package com.fklm.javachess.model.chessmen;

import com.fklm.javachess.ChessApplication;
import javafx.scene.image.Image;

import java.io.IOException;

public abstract class Piece {
    private final String color;
    private Image image;
    private String type;

    public Piece(String color,String type) {
        this.color = color;
        this.type = type;
        this.setImage(color,type);
    }

    public String getColor() {
        return color;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(String color,String type) {
        String path;
        if(color.equals("black")){
            path = new String("b_" + type +"_1x.png");
        }
        else{
            path = new String("w_" + type +"_1x.png");
        }
        this.image = new Image(ChessApplication.class.getResourceAsStream("images/"+path));
    }

    abstract void movement();
}
