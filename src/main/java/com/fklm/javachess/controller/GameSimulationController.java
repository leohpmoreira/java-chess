package com.fklm.javachess.controller;

import com.fklm.javachess.Move;
import com.fklm.javachess.Player;
import com.fklm.javachess.Position;
import com.fklm.javachess.Space;
import com.fklm.javachess.model.chessmen.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class GameSimulationController implements Initializable {
    @FXML
    private GridPane board;

    BufferedReader reader;
    Space[][] space = new Space[8][8];

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            this.reader = new BufferedReader(new FileReader("game.txt"));
            String s = reader.readLine();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for(int y=0; y<8; y++) {
            for (int x = 0; x < 8; x++) {
                board.add(createSpace(x, y), x, 7 - y);
            }
        }
    }

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

    @FXML
    void nextMove() throws IOException {
       Space start,destiny;
        if(reader.ready()){
            String s= reader.readLine();
            String[] move = s.split(";");
            String strStart = move[0];
            String strDestiny = move[1];
            start = translate(strStart);
            destiny = translate(strDestiny);

            doMovement(start,destiny);
            if(move.length == 3){
                pawnPromotion(move[1],move[2]);
            }
        }
    }

    void doMovement (Space start,Space destiny){
        destiny.setGraphic(start.getGraphic());
        destiny.setPiece(start.getPiece());
        start.setGraphic(null);
        start.setPiece(null);
    }

    Space translate(String move){
        int x = move.charAt(0) - 65;
        int y = move.charAt(1) - 49;

        return space[y][x];
    }

    void pawnPromotion(String destiny,String type){
        Space promoted = translate(destiny);
        int piece = Integer.valueOf(type);
        putPiece(piece,promoted);
    }

    void putPiece(int type,Space promoted){
        switch (type){
            case 1: promoted.setPiece(new Rook(promoted.getPiece().getColor(),type));
                    break;
            case 2: promoted.setPiece(new Knight(promoted.getPiece().getColor(),type));
                    break;
            case 3: promoted.setPiece(new Bishop(promoted.getPiece().getColor(),type));
                    break;
            case 4: promoted.setPiece(new Queen(promoted.getPiece().getColor(),type));
        }
        ImageView image = new ImageView(promoted.getPiece().getImage());
        image.setFitHeight(50);
        image.setFitWidth(50);
        promoted.setGraphic(image);
    }
}
