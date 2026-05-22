package com.muhammadhamza.algoquest.screens;

import com.muhammadhamza.algoquest.GameManager;
import com.muhammadhamza.algoquest.utils.Style;
import com.muhammadhamza.algoquest.utils.UiMotion;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class CompletionScreen {

    private final BorderPane view;
    private final GameManager gameManager;

    public CompletionScreen(GameManager gameManager) {
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
        mainBox.setPadding(new Insets(32));
        mainBox.setMaxWidth(1100);
        mainBox.setStyle(panelStyle());

        Label trophyLabel = new Label("🏆");
        trophyLabel.setStyle("-fx-font-size: 76px;");

        Label titleLabel = new Label("AlgoQuest Completed!");
        titleLabel.setStyle(
                "-fx-text-fill: " + Style.COLOR_PRIMARY + ";" +
                        "-fx-font-size: 48px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-family: " + Style.FONT_TITLE + ";"
        );

        Label subtitleLabel = new Label("You escaped all data structure chambers and mastered the complete learning path.");
        subtitleLabel.setWrapText(true);
        subtitleLabel.setMaxWidth(950);
        subtitleLabel.setAlignment(Pos.CENTER);
        subtitleLabel.setStyle(
                "-fx-text-fill: " + Style.COLOR_TEXT + ";" +
                        "-fx-font-size: 22px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-family: " + Style.FONT_BODY + ";"
        );

        Label storyLabel = new Label(
                "From array indexes to linked nodes, from stack towers to queue gates, from trees and graphs to heaps, hash maps, and hash tables — " +
                        "you completed every mission by solving visual challenges and understanding the logic behind each structure."
        );
        storyLabel.setWrapText(true);
        storyLabel.setMaxWidth(1000);
        storyLabel.setAlignment(Pos.CENTER);
        storyLabel.setStyle(
                "-fx-text-fill: #C7D2E6;" +
                        "-fx-font-size: 18px;" +
                        "-fx-line-spacing: 5px;" +
                        "-fx-font-family: " + Style.FONT_BODY + ";"
        );

        HBox statsBox = new HBox(18);
        statsBox.setAlignment(Pos.CENTER);

        statsBox.getChildren().addAll(
                statCard("Levels Completed", "9/9", Style.COLOR_PRIMARY),
                statCard("Core Structures", "9", Style.COLOR_ACCENT),
                statCard("Game Status", "Victory", Style.COLOR_SECONDARY)
        );

        GridPane skillsGrid = new GridPane();
        skillsGrid.setAlignment(Pos.CENTER);
        skillsGrid.setHgap(14);
        skillsGrid.setVgap(14);
        skillsGrid.setPadding(new Insets(10));

        skillsGrid.add(skillCard("Arrays", "Indexing, insert, delete, search, traversal"), 0, 0);
        skillsGrid.add(skillCard("Linked List", "Nodes, HEAD, next pointer, NULL"), 1, 0);
        skillsGrid.add(skillCard("Stack", "LIFO, push, pop, peek, underflow"), 2, 0);

        skillsGrid.add(skillCard("Queue", "FIFO, front, rear, enqueue, dequeue"), 0, 1);
        skillsGrid.add(skillCard("Trees", "Root, child, BST, insert, search, traversal"), 1, 1);
        skillsGrid.add(skillCard("Graphs", "Vertices, edges, BFS, DFS, adjacency list"), 2, 1);

        skillsGrid.add(skillCard("Heaps", "Max heap, min heap, bubble up, bubble down"), 0, 2);
        skillsGrid.add(skillCard("HashMap", "Keys, values, buckets, collisions"), 1, 2);
        skillsGrid.add(skillCard("HashTable", "Hashing, synchronization, null rules"), 2, 2);

        VBox vivaCard = new VBox(10);
        vivaCard.setAlignment(Pos.CENTER_LEFT);
        vivaCard.setPadding(new Insets(18));
        vivaCard.setMaxWidth(1000);
        vivaCard.setStyle(
                "-fx-background-color: " + Style.COLOR_CARD + ";" +
                        "-fx-border-color: " + Style.COLOR_ACCENT + ";" +
                        "-fx-border-width: 2;" +
                        "-fx-background-radius: 16;" +
                        "-fx-border-radius: 16;"
        );

        Label vivaTitle = new Label("Project Explanation for Viva");
        vivaTitle.setStyle(
                "-fx-text-fill: " + Style.COLOR_PRIMARY + ";" +
                        "-fx-font-size: 22px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-family: " + Style.FONT_TITLE + ";"
        );

        Label vivaText = new Label(
                "AlgoQuest is an interactive JavaFX learning game for data structures. " +
                        "The screens package handles UI, visuals, game rooms, buttons, and navigation. " +
                        "The levels package contains the actual manual data structure implementation logic, such as array operations, linked list nodes, stack push/pop, queue front/rear, BST insertion/search, graph BFS/DFS, heap bubble operations, and hash-based storage. " +
                        "Each level first teaches the topic visually, then tests understanding through escape-room style missions."
        );
        vivaText.setWrapText(true);
        vivaText.setStyle(
                "-fx-text-fill: #C7D2E6;" +
                        "-fx-font-size: 17px;" +
                        "-fx-line-spacing: 5px;" +
                        "-fx-font-family: " + Style.FONT_BODY + ";"
        );

        vivaCard.getChildren().addAll(vivaTitle, vivaText);

        HBox buttonBox = new HBox(16);
        buttonBox.setAlignment(Pos.CENTER);

        Button levelMapButton = new Button("Review Level Map");
        levelMapButton.setStyle(buttonStyle(Style.COLOR_ACCENT, "#0B1020"));
        levelMapButton.setOnAction(e -> gameManager.showLevelMap());
        UiMotion.applyHoverScale(levelMapButton, 1.04, 130);

        Button restartButton = new Button("Restart Adventure");
        restartButton.setStyle(buttonStyle(Style.COLOR_PRIMARY, "#0B1020"));
        restartButton.setOnAction(e -> gameManager.resetProgress());
        UiMotion.applyHoverScale(restartButton, 1.04, 130);

        buttonBox.getChildren().addAll(levelMapButton, restartButton);

        Label finalLine = new Label("Think. Solve. Unlock. Master the structure.");
        finalLine.setStyle(
                "-fx-text-fill: " + Style.COLOR_MUTED + ";" +
                        "-fx-font-size: 16px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-family: " + Style.FONT_BODY + ";"
        );

        mainBox.getChildren().addAll(
                trophyLabel,
                titleLabel,
                subtitleLabel,
                storyLabel,
                statsBox,
                skillsGrid,
                vivaCard,
                buttonBox,
                finalLine
        );

        UiMotion.applyEntrance(mainBox, 80);

        view.setCenter(mainBox);
    }

    private VBox statCard(String title, String value, String color) {
        VBox card = new VBox(6);
        card.setAlignment(Pos.CENTER);
        card.setPadding(new Insets(14));
        card.setPrefWidth(220);
        card.setStyle(
                "-fx-background-color: " + Style.COLOR_CARD + ";" +
                        "-fx-border-color: " + color + ";" +
                        "-fx-border-width: 2;" +
                        "-fx-background-radius: 14;" +
                        "-fx-border-radius: 14;"
        );

        Label valueLabel = new Label(value);
        valueLabel.setStyle(
                "-fx-text-fill: " + color + ";" +
                        "-fx-font-size: 30px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-family: " + Style.FONT_TITLE + ";"
        );

        Label titleLabel = new Label(title);
        titleLabel.setStyle(
                "-fx-text-fill: " + Style.COLOR_TEXT + ";" +
                        "-fx-font-size: 15px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-family: " + Style.FONT_BODY + ";"
        );

        card.getChildren().addAll(valueLabel, titleLabel);
        UiMotion.applyHoverScale(card, 1.03, 130);
        return card;
    }

    private VBox skillCard(String title, String description) {
        VBox card = new VBox(7);
        card.setAlignment(Pos.TOP_CENTER);
        card.setPadding(new Insets(14));
        card.setPrefWidth(300);
        card.setMinHeight(105);
        card.setStyle(
                "-fx-background-color: " + Style.COLOR_CARD + ";" +
                        "-fx-border-color: #23344B;" +
                        "-fx-border-width: 2;" +
                        "-fx-background-radius: 14;" +
                        "-fx-border-radius: 14;"
        );

        Label titleLabel = new Label(title);
        titleLabel.setWrapText(true);
        titleLabel.setAlignment(Pos.CENTER);
        titleLabel.setStyle(
                "-fx-text-fill: " + Style.COLOR_PRIMARY + ";" +
                        "-fx-font-size: 18px;" +
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

        card.getChildren().addAll(titleLabel, descLabel);

        card.setOnMouseEntered(e -> card.setStyle(
                "-fx-background-color: #162337;" +
                        "-fx-border-color: " + Style.COLOR_PRIMARY + ";" +
                        "-fx-border-width: 2;" +
                        "-fx-background-radius: 14;" +
                        "-fx-border-radius: 14;"
        ));

        card.setOnMouseExited(e -> card.setStyle(
                "-fx-background-color: " + Style.COLOR_CARD + ";" +
                        "-fx-border-color: #23344B;" +
                        "-fx-border-width: 2;" +
                        "-fx-background-radius: 14;" +
                        "-fx-border-radius: 14;"
        ));

        UiMotion.applyHoverScale(card, 1.03, 140);

        return card;
    }

    private String buttonStyle(String bg, String textColor) {
        return "-fx-font-size: 18px;" +
                "-fx-font-weight: bold;" +
                "-fx-font-family: " + Style.FONT_BODY + ";" +
                "-fx-background-color: " + bg + ";" +
                "-fx-text-fill: " + textColor + ";" +
                "-fx-padding: 12 26;" +
                "-fx-background-radius: 12;" +
                "-fx-border-color: rgba(255, 255, 255, 0.16);" +
                "-fx-border-width: 1.2;" +
                "-fx-cursor: hand;";
    }

    private String panelStyle() {
        return "-fx-background-color: rgba(15, 23, 42, 0.65);" +
                "-fx-background-radius: 26;" +
                "-fx-border-color: rgba(255, 255, 255, 0.08);" +
                "-fx-border-width: 1.5;" +
                "-fx-border-radius: 26;";
    }

    public BorderPane getView() {
        return view;
    }
}