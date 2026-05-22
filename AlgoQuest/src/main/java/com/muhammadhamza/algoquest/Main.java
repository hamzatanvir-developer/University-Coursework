package com.muhammadhamza.algoquest;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        GameManager gameManager = new GameManager(primaryStage);
        gameManager.showStartScreen();

        primaryStage.setTitle("AlgoQuest - Data Structures Learning Game");
        primaryStage.setWidth(1200);
        primaryStage.setHeight(800);
        primaryStage.setResizable(true);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}