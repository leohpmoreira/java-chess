package com.fklm.javachess.controller;


import com.fklm.javachess.*;
import com.fklm.javachess.model.chessmen.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.*;

public class BoardController {
    @FXML
    private GridPane board;

    @FXML
    Label currentLabel = new Label();

    public Space start,destiny;
    public static Player current = Player.WHITE;
    public static BoardStatus boardStatus;
    public static Space[][] space = new Space[8][8];
    public boolean rewrite;


    public void initialize() {
        this.start = null;
        this.destiny = null;
        rewrite = false;
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                board.add(createSpace(x, y), x, 7 - y);
            }
        }
        this.boardStatus = new BoardStatus();
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                space[y][x].addWay(space, boardStatus);
            }
        }
        if (ChessApplication.player1.isEmpty()){
            currentLabel.setText("Vez do jogador " + current.toString());
        }else {
            currentLabel.setText("Vez do jogador " + ChessApplication.player1);
        }
    }


    public Space createSpace(int x,int y){
        ImageView pieceImage;
        space [y][x] = new Space(x,y);
        space [y][x].setPrefHeight(62.5);
        space [y][x].setPrefWidth(62.5);
        space [y][x].setPiece(setPiece(x,y));
        if((x+y)%2 == 1) {
            space [y][x].setStyle("-fx-background-color: #573a2e; -fx-background-radius: 0;-fx-padding: 0");
        }
        else
            space [y][x].setStyle("-fx-background-color: #8a785d; -fx-background-radius: 0;-fx-padding: 0");
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

    void doMovement (Space start,Space destiny){
        start.getPiece().setFirstMove(1);
        destiny.setPiece(start.getPiece());
        destiny.setGraphic(start.getGraphic());
        start.setGraphic(null);
        start.setPiece(null);
        start.clear();
    }

    public void selectedSpace(Space selected) throws IOException {
        if(start == null && selected.getPiece() != null && selected.getPiece().getColor().equals(current)){
            start = selected;
            highLight(start);
        }else if(destiny == null && selected != start && start != null){
            turnoffHighlight(start);
            if(selected.getPiece() == null ||
            (selected.getPiece() != null && selected.getPiece().getColor().equals(current.opponent()))){
                destiny = selected;
                Move move = new Move(start.getPosition(),destiny.getPosition());
                turnoffHighlight(start);
                if(start.getPossPositions().contains(destiny)) {
                    writeMove(start,destiny);
                    doMovement(start, destiny);
                    boardStatus.updateStatus(move,current);
                    changePlayer();
                }
                if(destiny.getPiece() != null && destiny.getPiece().getType() == 0)
                    pawnPromotion(destiny);
                start = null;
                destiny = null;
            }
            else{
                turnoffHighlight(start);
                start = selected;
                highLight(start);
            }
        }
    }

    void highLight(Space selected){
        String odd = new String("-fx-background-color: #573a2e; -fx-background-radius: 0;-fx-padding: 0;-fx-border-width: 3;-fx-border-color: limegreen;");
        String even = new String("-fx-background-color: #8a785d; -fx-background-radius: 0;-fx-padding: 0;-fx-border-width: 3;-fx-border-color: limegreen;");
        for(Space space: selected.possPositions){
            Position pos = space.getPosition();
                if((pos.getX()+ pos.getY())%2 == 1)
                    space.setStyle(odd);
                else
                    space.setStyle(even);
        }
    }

    void turnoffHighlight(Space start){
        String odd = new String("-fx-background-color: #573a2e; -fx-background-radius: 0;-fx-padding: 0");
        String even = new String("-fx-background-color: #8a785d; -fx-background-radius: 0;-fx-padding: 0");
        for(Space space: start.possPositions){
            Position pos = space.getPosition();
            if((pos.getX()+ pos.getY())%2 == 1)
                space.setStyle(odd);
            else
                space.setStyle(even);
        }
    }



    @FXML
    void draw(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ChessApplication.class.getResource("draw-view.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.showAndWait();
    }

    @FXML
    void surrender() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ChessApplication.class.getResource("surrender-view.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.showAndWait();
    }

    void writeMove(Space start,Space destiny){
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter(ChessApplication.game,rewrite));
            String move = new String(start.strPos() +";"+destiny.strPos());
            writer.newLine();
            writer.write(move);
            writer.close();
            rewrite = true;
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    void pawnPromotion(Space pawn) throws IOException {
        if(pawn.getPiece().getColor().equals(Player.WHITE)){
            if(pawn.getPosition().getY() == 7) {
                pawn.setPiece(PawnPromotionController.getPiece());
                putImage(pawn);
                pawn.addWay(space,boardStatus);
                writePromotion(pawn);
            }
        }
        else{
            if(pawn.getPosition().getY() == 0) {
                pawn.setPiece(PawnPromotionController.getPiece());
                putImage(pawn);
                pawn.addWay(space,boardStatus);
                writePromotion(pawn);
            }
        }
    }

    void putImage(Space space){
        ImageView image = new ImageView(space.getPiece().getImage());
        image.setFitHeight(50);
        image.setFitWidth(50);
        space.setGraphic(image);
    }

    void writePromotion(Space destiny){
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter(ChessApplication.game,true));
            String move = new String(";"+ destiny.getPiece().getType());
            writer.write(move);
            writer.close();
            rewrite = true;
        }catch (IOException e){
            e.printStackTrace();
        }
    }


    public void changePlayer(){
        current = current.opponent();
        if (current.equals(Player.WHITE)){
            if (ChessApplication.player1.isEmpty()){
                currentLabel.setText("Vez do jogador " + current.toString());
            } else{
                currentLabel.setText("Vez do jogador " + ChessApplication.player1);
            }
        }else {
            if (ChessApplication.player2.isEmpty()){
                currentLabel.setText("Vez do jogador " + current.toString());
            } else{
                currentLabel.setText("Vez do jogador " + ChessApplication.player2);
            }
        }
    }
}
