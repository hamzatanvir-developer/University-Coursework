package com.muhammadhamza.algoquest.screens;

import com.muhammadhamza.algoquest.GameManager;
import com.muhammadhamza.algoquest.utils.Style;
import com.muhammadhamza.algoquest.utils.UiMotion;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class StartScreen {

    private final BorderPane view;
    private final GameManager gameManager;

    public StartScreen(GameManager gameManager) {
        this.gameManager = gameManager;

        view = new BorderPane();
        view.setStyle(Style.BG_GRADIENT);

        Pane ambientLayer = UiMotion.createAmbientBackground();
        ambientLayer.prefWidthProperty().bind(view.widthProperty());
        ambientLayer.prefHeightProperty().bind(view.heightProperty());
        view.getChildren().add(0, ambientLayer);

        buildLayout();
    }

    private void buildLayout() {
        VBox mainBox = new VBox(22);
        mainBox.setAlignment(Pos.CENTER);
        mainBox.setPadding(new Insets(36));
        mainBox.setMaxWidth(1100);
        mainBox.setStyle(panelStyle());

        Label logoLabel = new Label("⚔");
        logoLabel.setStyle(
                "-fx-text-fill: " + Style.COLOR_SECONDARY + ";" +
                        "-fx-font-size: 72px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-family: " + Style.FONT_TITLE + ";"
        );

        Label titleLabel = new Label("AlgoQuest");
        titleLabel.setStyle(
                "-fx-text-fill: " + Style.COLOR_PRIMARY + ";" +
                        "-fx-font-size: 58px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-family: " + Style.FONT_TITLE + ";"
        );

        Label subtitleLabel = new Label("A Visual Adventure for Learning Data Structures");
        subtitleLabel.setWrapText(true);
        subtitleLabel.setMaxWidth(900);
        subtitleLabel.setAlignment(Pos.CENTER);
        subtitleLabel.setStyle(
                "-fx-text-fill: " + Style.COLOR_TEXT + ";" +
                        "-fx-font-size: 24px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-family: " + Style.FONT_BODY + ";"
        );

        Label descriptionLabel = new Label(
                  "Enter a world where every data structure becomes a mission. " +
                        "Learn each topic visually, understand how it works internally, " +
                        "then solve interactive escape-room challenges to unlock the next level."
        );
        descriptionLabel.setWrapText(true);
        descriptionLabel.setMaxWidth(980);
        descriptionLabel.setAlignment(Pos.CENTER);
        descriptionLabel.setStyle(
                "-fx-text-fill: #C7D2E6;" +
                        "-fx-font-size: 18px;" +
                        "-fx-line-spacing: 6px;" +
                        "-fx-font-family: " + Style.FONT_BODY + ";"
        );

        HBox featureBox = new HBox(18);
        featureBox.setAlignment(Pos.CENTER);

        VBox feature1 = featureCard("🎮", "Mission-Based Levels", "Each topic becomes a game level with locked rooms and challenges.");
        VBox feature2 = featureCard("🧠", "Visual Concepts", "Learn with boxes, nodes, arrows, trees, graphs, buckets, and paths.");
        VBox feature3 = featureCard("💻", "Code Understanding", "See built-in Java usage and manual internal implementation.");
        VBox feature4 = featureCard("🔓", "Escape Challenges", "Solve puzzles, unlock doors, and progress step by step.");

        featureBox.getChildren().addAll(feature1, feature2, feature3, feature4);

        Label pathLabel = new Label(
                "Adventure Path: Arrays → Linked List → Stack → Queue → Trees → Graphs → Heaps → HashMap → HashTable"
        );
        pathLabel.setWrapText(true);
        pathLabel.setMaxWidth(1000);
        pathLabel.setAlignment(Pos.CENTER);
        pathLabel.setStyle(
                "-fx-text-fill: " + Style.COLOR_SECONDARY + ";" +
                        "-fx-font-size: 16px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-family: " + Style.FONT_BODY + ";"
        );

        Button startButton = new Button("Start Adventure");
        startButton.setStyle(startButtonStyle(Style.COLOR_SECONDARY, "#0B1020"));
        startButton.setOnMouseEntered(e -> startButton.setStyle(startButtonStyle("#FFE08A", "#0B1020")));
        startButton.setOnMouseExited(e -> startButton.setStyle(startButtonStyle(Style.COLOR_SECONDARY, "#0B1020")));
        startButton.setOnAction(e -> gameManager.showLevelMap());

        Label taglineLabel = new Label("Think. Solve. Unlock. Master the structure.");
        taglineLabel.setStyle(
                "-fx-text-fill: " + Style.COLOR_MUTED + ";" +
                        "-fx-font-size: 15px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-family: " + Style.FONT_BODY + ";"
        );

        mainBox.getChildren().addAll(
                logoLabel,
                titleLabel,
                subtitleLabel,
                descriptionLabel,
                featureBox,
                pathLabel,
                startButton,
                taglineLabel
        );

        UiMotion.applyEntrance(logoLabel, 40);
        UiMotion.applyEntrance(titleLabel, 90);
        UiMotion.applyEntrance(subtitleLabel, 160);
        UiMotion.applyEntrance(descriptionLabel, 220);
        UiMotion.applyEntrance(featureBox, 300);
        UiMotion.applyEntrance(pathLabel, 380);
        UiMotion.applyEntrance(startButton, 440);
        UiMotion.applyEntrance(taglineLabel, 500);

        UiMotion.applyFloat(logoLabel, 8, 4.6, 700);
        UiMotion.applyGlowPulse(logoLabel, Color.web(Style.COLOR_SECONDARY, 0.55));
        UiMotion.applyHoverScale(startButton, 1.04, 140);
        UiMotion.applyGlowPulse(startButton, Color.web(Style.COLOR_PRIMARY, 0.6));

        UiMotion.applyHoverScale(feature1, 1.04, 140, Color.web(Style.COLOR_ACCENT, 0.6));
        UiMotion.applyHoverScale(feature2, 1.04, 140, Color.web(Style.COLOR_ACCENT, 0.6));
        UiMotion.applyHoverScale(feature3, 1.04, 140, Color.web(Style.COLOR_ACCENT, 0.6));
        UiMotion.applyHoverScale(feature4, 1.04, 140, Color.web(Style.COLOR_ACCENT, 0.6));

        view.setCenter(mainBox);
    }

    private VBox featureCard(String icon, String title, String description) {
        VBox card = new VBox(10);
        card.setAlignment(Pos.TOP_CENTER);
        card.setPadding(new Insets(18));
        card.setPrefWidth(225);
        card.setMinHeight(175);
        card.setStyle(cardStyle("#263449"));

        Label iconLabel = new Label(icon);
        iconLabel.setStyle("-fx-font-size: 34px;");

        Label titleLabel = new Label(title);
        titleLabel.setWrapText(true);
        titleLabel.setAlignment(Pos.CENTER);
        titleLabel.setStyle(
                "-fx-text-fill: " + Style.COLOR_PRIMARY + ";" +
                        "-fx-font-size: 17px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-family: " + Style.FONT_TITLE + ";"
        );

        Label descLabel = new Label(description);
        descLabel.setWrapText(true);
        descLabel.setAlignment(Pos.CENTER);
        descLabel.setStyle(
                "-fx-text-fill: #C7D2E6;" +
                        "-fx-font-size: 14px;" +
                        "-fx-font-family: " + Style.FONT_BODY + ";"
        );

        card.getChildren().addAll(iconLabel, titleLabel, descLabel);

        card.setOnMouseEntered(e -> card.setStyle(cardStyle(Style.COLOR_PRIMARY)));
        card.setOnMouseExited(e -> card.setStyle(cardStyle("#263449")));

        return card;
    }

    private String cardStyle(String borderColor) {
        return "-fx-background-color: #111C2D;" +
                "-fx-border-color: " + borderColor + ";" +
                "-fx-border-width: 2;" +
                "-fx-background-radius: 16;" +
                "-fx-border-radius: 16;";
    }

    private String startButtonStyle(String color, String textColor) {
        return "-fx-font-size: 20px;" +
                "-fx-font-weight: bold;" +
                "-fx-font-family: " + Style.FONT_TITLE + ";" +
                "-fx-background-color: " + color + ";" +
                "-fx-text-fill: " + textColor + ";" +
                "-fx-padding: 14 38;" +
                "-fx-background-radius: 16;" +
                "-fx-border-color: rgba(255, 255, 255, 0.18);" +
                "-fx-border-width: 1.5;" +
                "-fx-cursor: hand;";
    }

    private String panelStyle() {
        return "-fx-background-color: rgba(15, 23, 42, 0.65);" +
                "-fx-background-radius: 28;" +
                "-fx-border-color: rgba(255, 255, 255, 0.08);" +
                "-fx-border-width: 1.5;" +
                "-fx-border-radius: 28;";
    }

    public BorderPane getView() {
        return view;
    }
}