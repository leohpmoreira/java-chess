package com.fklm.javachess.controller;


import com.fklm.javachess.*;
import com.fklm.javachess.model.chessmen.*;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class BoardController {
    @FXML
    private StackPane place;
    @FXML
    private GridPane board;

    public Space start,destiny;
    public Player current = Player.WHITE;
    public BoardStatus boardStatus;
    public static Space[][] space = new Space[8][8];


    public void initialize(){
        setBoard();
    }

    public void setBoard() {
        this.start = null;
        this.destiny = null;
        this.boardStatus = new BoardStatus();
        for(int y=0; y<8; y++){
            for(int x=0; x<8; x++){
                board.add(createSpace(x,y), x, 7-y);
                boardStatus.setPieces(space[y][x]);
            }
        }
    }

    public Space createSpace(int x,int y){
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
            selectedSpace((Space) e.getSource());
        });
        return space [y][x];
    }

    public Piece setPiece(int x,int y){
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

    void doMoviment (Space start,Space destiny){
        if(start != null) {
            destiny.setPiece(start.getPiece());
            destiny.setGraphic(start.getGraphic());
            start.setGraphic(null);
            start.setPiece(null);
        }
    }

    public void selectedSpace(Space space){
        if(start == null && space.getPiece() != null){
             start = space;
             start.getStyleClass().add("greenFocusHighlight");
        }else if(destiny == null && space != start && start != null){
            if((space.getPiece() != null && !space.getPiece().getColor().equals(current))
                    || space.getPiece() == null){
                destiny = space;
                if(start.getPiece().isLegalMove(new Move(start.getPosition(),destiny.getPosition())))
                    doMoviment(start, destiny);
                start = null;
                destiny = null;
            }
        }
    }
}
