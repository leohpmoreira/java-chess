package com.fklm.javachess.controller;

import com.fklm.javachess.ChessApplication;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class StartController {
    @FXML
    TextField player1Name;

    @FXML
    TextField player2Name;

    @FXML
    Button startButton;

    @FXML
    protected void onStartButtonClick() {
        ChessApplication.player1 = player1Name.getText();
        ChessApplication.player2 = player2Name.getText();
        System.out.println("Player 1: " + ChessApplication.player1+ " Player 2: " + ChessApplication.player2);
    }

}
