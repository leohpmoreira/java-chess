package com.fklm.javachess.controller;


import com.fklm.javachess.*;
import com.fklm.javachess.model.chessmen.*;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

public class BoardController {
    @FXML
    private StackPane place;
    @FXML
    private GridPane board;

    public Space start,destiny;
    public static Player current = Player.WHITE;
    public BoardStatus boardStatus;
    public static Space[][] space = new Space[8][8];


    public void initialize(){
        this.start = null;
        this.destiny = null;
        for(int y=0; y<8; y++) {
            for (int x = 0; x < 8; x++) {
                board.add(createSpace(x, y), x, 7 - y);
            }
        }
        this.boardStatus = new BoardStatus();
        for(int y=0; y<8; y++){
            for(int x=0; x<8; x++){
                space[y][x].addWay(space,boardStatus);
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
            destiny.addWay(space,boardStatus);
            destiny.setGraphic(start.getGraphic());
            start.setGraphic(null);
            start.clear();
        }
    }

    public void selectedSpace(Space selected){
        if(start == null && selected.getPiece() != null && selected.getPiece().getColor().equals(current)){
            start = selected;
            start.addWay(space,boardStatus);
            highLight(start);
        }else if(destiny == null && selected != start && start != null){
            turnoffHighlight(start);
            if(selected.getPiece() == null ||
            (selected.getPiece() != null && selected.getPiece().getColor().equals(current.opponent()))){
                destiny = selected;
                Move move = new Move(start.getPosition(),destiny.getPosition());
                turnoffHighlight(start);
                if(start.getPiece().isLegalMove(move) && boardStatus.isntObstructe(move,start.getPiece())) {
                    doMoviment(start, destiny);
                    boardStatus.updateStatus(move,current);
                    changePlayer();
                }
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
        String odd = new String("-fx-background-color: black; -fx-background-radius: 0;-fx-padding: 0;-fx-border-width: 3;-fx-border-color: limegreen;");
        String even = new String("-fx-background-color: white; -fx-background-radius: 0;-fx-padding: 0;-fx-border-width: 3;-fx-border-color: limegreen;");
        for(Space space: selected.possPositions){
            Position pos = space.getPosition();
            if(space.getPiece()== null || space.getPiece().getColor().equals(selected.getPiece().getColor().opponent())){
                if((pos.getX()+ pos.getY())%2 == 1)
                    space.setStyle(odd);
                else
                    space.setStyle(even);
            }
        }
    }

    void turnoffHighlight(Space start){
        String odd = new String("-fx-background-color: black; -fx-background-radius: 0;-fx-padding: 0");
        String even = new String("-fx-background-color: white; -fx-background-radius: 0;-fx-padding: 0");
        for(Space space: start.possPositions){
            Position pos = space.getPosition();
            if((pos.getX()+ pos.getY())%2 == 1)
                space.setStyle(odd);
            else
                space.setStyle(even);
        }
    }

    public void changePlayer(){
        current = current.opponent();
    }
}
