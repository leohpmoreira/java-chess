package com.fklm.javachess;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class ChessApplication extends Application {
    public static String player1, player2;
    public static  File game;

    @Override
    public void start(Stage stage) throws IOException {
        game = new File("game.txt");
        try {
            if (!game.exists())
                game.createNewFile();
        }catch (IOException e){
            e.printStackTrace();
        }
        FXMLLoader fxmlLoader = new FXMLLoader(ChessApplication.class.getResource("start-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);
        stage.setTitle("Chess");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}