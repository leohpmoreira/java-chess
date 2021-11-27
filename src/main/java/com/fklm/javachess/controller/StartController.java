package com.fklm.javachess.controller;

import com.fklm.javachess.ChessApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class StartController {
    @FXML
    TextField player1Name;

    @FXML
    TextField player2Name;

    @FXML
    Button startButton;

    @FXML
    protected void onStartButtonClick(ActionEvent event){
        ChessApplication.player1 = player1Name.getText();
        ChessApplication.player2 = player2Name.getText();
        System.out.println("Player 1: " + ChessApplication.player1+ " Player 2: " + ChessApplication.player2);
        try {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(ChessApplication.class.getResource("board.fxml"));
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.show();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

}
