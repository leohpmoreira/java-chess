package com.fklm.javachess.controller;

import com.fklm.javachess.ChessApplication;
import com.fklm.javachess.Player;
import com.fklm.javachess.Space;
import com.fklm.javachess.model.chessmen.*;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.IOException;

public class GameSimulationController {
    File game = ChessApplication.game;
    Space[][] space = new Space[8][8];

    public Space createSpace(int x, int y){
        ImageView pieceImage;
        space [y][x] = new Space(x,y);
        space [y][x].setPrefHeight(62.5);
        space [y][x].setPrefWidth(62.5);
        space [y][x].setPiece(setPiece(x,y));
        if((x+y)%2 == 1) {
            space [y][x].setStyle("-fx-background-color: black; -fx-background-radius: 0;-fx-padding: 0");
        }
        else
            space [y][x].setStyle("-fx-background-color: white; -fx-background-radius: 0;-fx-padding: 0");
        if(space[y][x].getPiece() != null){
            pieceImage = new ImageView(space[y][x].getPiece().getImage());
            pieceImage.setFitHeight(50);
            pieceImage.setFitWidth(50);
            space[y][x].setGraphic(pieceImage);
        }
        space[y][x].setOnAction(e->{
            try {
                selectedSpace((Space) e.getSource());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        return space [y][x];
    }

    public Piece setPiece(int x, int y){
        Piece[] pieces = {
                new Rook(Player.BLACK,1),
                new Rook(Player.WHITE,1),
                new Knight(Player.BLACK,2),
                new Knight(Player.WHITE,2),
                new Bishop(Player.BLACK,3),
                new Bishop(Player.WHITE,3),
                new Queen(Player.BLACK,4),
                new Queen(Player.WHITE,4),
                new King(Player.BLACK,5),
                new King(Player.WHITE,5)
        };
        int [] wOrder = {1,3,5,7,9,5,3,1};
        int [] bOrder = {0,2,4,6,8,4,2,0};
        if(y>1 && y <6){
            return null;
        }
        Piece p;
        switch (y){
            case 6: p = new Pawn(Player.BLACK,0);
                break;
            case 1: p = new Pawn(Player.WHITE,0);
                break;
            case 7: p = pieces[bOrder[x]];
                break;
            case 0: p = pieces[wOrder[x]];
                break;
            default: p=null;
        }
        return p;
    }
}
