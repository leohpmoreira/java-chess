package com.fklm.javachess.controller;

import com.fklm.javachess.model.chessmen.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.stage.Stage;

public class PawnPromotionController {
    private static Piece piece;


    @FXML
    public void isQueen(ActionEvent e){
        this.piece = new Queen(BoardController.current,4);
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    public void isBishop(ActionEvent e){
        this.piece = new Bishop(BoardController.current,3);
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    void isKnight(ActionEvent e){
        this.piece = new Knight(BoardController.current,2);
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    void isRook(ActionEvent e){
        this.piece = new Rook(BoardController.current,1);
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.close();
    }

    public static Piece getPiece(){
        return piece;
    }
}
