package com.fklm.javachess.controller;

import com.fklm.javachess.BoardStatus;
import com.fklm.javachess.ChessApplication;
import com.fklm.javachess.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class MessageController implements Initializable {
    private BoardStatus board = BoardController.boardStatus;
    private Player player = BoardController.current;
    private String player1 = ChessApplication.player1;
    private String player2 = ChessApplication.player2;

    @FXML
    private Label drawMessage = new Label();;

    @FXML
    private Label surrenderMessage = new Label();

    @FXML
    private Label winnerMessage = new Label();

    @FXML
    private Label drawGame = new Label();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        winnerMessage.setText((player == Player.WHITE? player1 : player2) + " ganhou");
        drawGame.setText("EMPATE");
        drawMessage.setText("Deseja aceitar o empate "+(player == Player.WHITE? player2 : player1) +"?");
        surrenderMessage.setText("Deseja desistir " + (player == Player.WHITE? player1 : player2) + "?");
    }

    @FXML
    void yesDraw(){
        board.disableSpace();
        System.out.println("Empate");
    }

    @FXML
    void notDraw(ActionEvent e){
        Stage stage = (Stage) ((Node)e.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    void yesSurrender(){
        board.disableSpace();
        System.out.println("Derrotado");
    }

    @FXML
    void notSurrender(ActionEvent e){
        Stage stage = (Stage) ((Node)e.getSource()).getScene().getWindow();
        stage.close();
    }
}
