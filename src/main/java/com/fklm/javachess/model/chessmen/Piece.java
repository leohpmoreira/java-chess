package com.fklm.javachess.model.chessmen;

import javafx.scene.image.Image;

public abstract class Piece {
    private final String color;
    private Image image;

    public Piece(String color, Image image) {
        this.color = color;
        this.image = image;
    }

    public String getColor() {
        return color;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}
