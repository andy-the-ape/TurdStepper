package com.example.turdstepper;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class TurdApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        TurdController controller = new TurdController();
        FXMLLoader fxmlLoader = new FXMLLoader(TurdApplication.class.getResource("game-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(),controller.gameBoard.getBoardHeight(), controller.gameBoard.getBoardWidth(), Color.LIGHTGRAY);
        fxmlLoader.setController(controller);
//        Group root = new Group();
//        Scene scene = new Scene(root,gameBoard.getBoardHeight(), gameBoard.getBoardWidth(), Color.LIGHTGRAY);
        stage.setTitle("Turd Stepper");
        stage.setScene(scene);
        stage.show();

    }




    public static void main(String[] args) {
        launch();
    }
}