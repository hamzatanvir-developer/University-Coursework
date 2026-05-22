package com.muhammadhamza.algoquest.screens;

import com.muhammadhamza.algoquest.GameManager;
import com.muhammadhamza.algoquest.utils.Style;
import com.muhammadhamza.algoquest.utils.UiMotion;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class LevelMapScreen {

    private final BorderPane view;
    private final GameManager gameManager;

    private final boolean arrayLevelCompleted;
    private final boolean linkedListLevelUnlocked;
    private final boolean stackLevelUnlocked;
    private final boolean queueLevelUnlocked;
    private final boolean treesLevelUnlocked;
    private final boolean graphsLevelUnlocked;
    private final boolean heapsLevelUnlocked;
    private final boolean hashMapLevelUnlocked;
    private final boolean hashTableLevelUnlocked;

    public LevelMapScreen(
            GameManager gameManager,
            boolean arrayLevelCompleted,
            boolean linkedListLevelUnlocked,
            boolean stackLevelUnlocked,
            boolean queueLevelUnlocked,
            boolean treesLevelUnlocked,
            boolean graphsLevelUnlocked,
            boolean heapsLevelUnlocked,
            boolean hashMapLevelUnlocked,
            boolean hashTableLevelUnlocked
    ) {
        this.gameManager = gameManager;
        this.arrayLevelCompleted = arrayLevelCompleted;
        this.linkedListLevelUnlocked = linkedListLevelUnlocked;
        this.stackLevelUnlocked = stackLevelUnlocked;
        this.queueLevelUnlocked = queueLevelUnlocked;
        this.treesLevelUnlocked = treesLevelUnlocked;
        this.graphsLevelUnlocked = graphsLevelUnlocked;
        this.heapsLevelUnlocked = heapsLevelUnlocked;
        this.hashMapLevelUnlocked = hashMapLevelUnlocked;
        this.hashTableLevelUnlocked = hashTableLevelUnlocked;

        view = new BorderPane();
        view.setStyle(Style.BG_GRADIENT);

        Pane ambientLayer = UiMotion.createAmbientBackground();
        ambientLayer.prefWidthProperty().bind(view.widthProperty());
        ambientLayer.prefHeightProperty().bind(view.heightProperty());
        view.getChildren().add(0, ambientLayer);

        buildLayout();
    }

    private void buildLayout() {
        VBox topBox = new VBox(8);
        topBox.setAlignment(Pos.CENTER);
        topBox.setPadding(new Insets(22));
        topBox.setFillWidth(true);
        topBox.setMaxWidth(Double.MAX_VALUE);
        topBox.setStyle(panelStyle());

        Label titleLabel = new Label("AlgoQuest Level Map");
        titleLabel.setStyle(
                "-fx-text-fill: " + Style.COLOR_PRIMARY + ";" +
                        "-fx-font-size: 42px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-family: " + Style.FONT_TITLE + ";"
        );

        Label subtitleLabel = new Label("Complete each data structure mission to unlock the next level.");
        subtitleLabel.setWrapText(true);
        subtitleLabel.setMaxWidth(900);
        subtitleLabel.setAlignment(Pos.CENTER);
        subtitleLabel.setStyle(
                "-fx-text-fill: " + Style.COLOR_TEXT + ";" +
                        "-fx-font-size: 19px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-family: " + Style.FONT_BODY + ";"
        );

        Label pathLabel = new Label(
                "Arrays → Linked List → Stack → Queue → Trees → Graphs → Heaps → HashMap → HashTable"
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

        topBox.getChildren().addAll(titleLabel, subtitleLabel, pathLabel);
        view.setTop(topBox);

        VBox centerBox = new VBox(22);
        centerBox.setAlignment(Pos.TOP_CENTER);
        centerBox.setPadding(new Insets(24));
        centerBox.setFillWidth(true);
        centerBox.setMaxWidth(Double.MAX_VALUE);
        centerBox.setStyle(panelStyle());

        HBox progressBox = new HBox(18);
        progressBox.setAlignment(Pos.CENTER);

        progressBox.getChildren().addAll(
                progressCard("Completed", String.valueOf(getCompletedCount()), Style.COLOR_PRIMARY),
                progressCard("Unlocked", String.valueOf(getUnlockedCount()), Style.COLOR_ACCENT),
                progressCard("Total Levels", "9", Style.COLOR_SECONDARY)
        );

        GridPane levelGrid = new GridPane();
        levelGrid.setAlignment(Pos.CENTER);
        levelGrid.setHgap(18);
        levelGrid.setVgap(18);
        levelGrid.setPadding(new Insets(10));

        levelGrid.add(levelCard(
                "1",
                "Arrays Lab",
                "Learn indexing, traversal, search, insert, delete, and shifting.",
                "Array boxes",
                true,
                arrayLevelCompleted,
                0,
                () -> gameManager.showArrayLevel()
        ), 0, 0);

        levelGrid.add(levelCard(
                "2",
                "Linked List Path",
                "Learn nodes, HEAD, NULL, next pointer, insert, delete, and types.",
                "Nodes + arrows",
                linkedListLevelUnlocked,
                stackLevelUnlocked,
                1,
                () -> gameManager.showLinkedListLevel()
        ), 1, 0);

        levelGrid.add(levelCard(
                "3",
                "Stack Tower",
                "Learn LIFO, push, pop, peek, undo, and bracket matching.",
                "Vertical stack",
                stackLevelUnlocked,
                queueLevelUnlocked,
                2,
                () -> gameManager.showStackLevel()
        ), 2, 0);

        levelGrid.add(levelCard(
                "4",
                "Queue Gate",
                "Learn FIFO, enqueue, dequeue, peek, front, rear, and circular queue.",
                "Front → Rear",
                queueLevelUnlocked,
                treesLevelUnlocked,
                3,
                () -> gameManager.showQueueLevel()
        ), 0, 1);

        levelGrid.add(levelCard(
                "5",
                "Tree Kingdom",
                "Learn root, child, leaf, BST rules, search, insert, and traversals.",
                "BST visual",
                treesLevelUnlocked,
                graphsLevelUnlocked,
                4,
                () -> gameManager.showTreesLevel()
        ), 1, 1);

        levelGrid.add(levelCard(
                "6",
                "Graph Realm",
                "Learn vertices, edges, adjacency list, BFS, DFS, and graph types.",
                "Nodes network",
                graphsLevelUnlocked,
                heapsLevelUnlocked,
                5,
                () -> gameManager.showGraphsLevel()
        ), 2, 1);

        levelGrid.add(levelCard(
                "7",
                "Heap Mountain",
                "Learn max heap, min heap, array formulas, bubble up, and bubble down.",
                "Heap tree",
                heapsLevelUnlocked,
                hashMapLevelUnlocked,
                6,
                () -> gameManager.showHeapsLevel()
        ), 0, 2);

        levelGrid.add(levelCard(
                "8",
                "HashMap Vault",
                "Learn key-value pairs, buckets, hashing, put, get, remove, and collision.",
                "Buckets",
                hashMapLevelUnlocked,
                hashTableLevelUnlocked,
                7,
                () -> gameManager.showHashMapLevel()
        ), 1, 2);

        levelGrid.add(levelCard(
                "9",
                "HashTable Chamber",
                "Learn Hashtable, synchronized behavior, null rules, and HashMap difference.",
                "Final chamber",
                hashTableLevelUnlocked,
                false,
                8,
                () -> gameManager.showHashTableLevel()
        ), 2, 2);

        Label helpLabel = new Label(
                "Tip: Every level teaches visually first, then shows built-in Java code, manual implementation, real-life use cases, edge cases, and puzzle missions."
        );
        helpLabel.setWrapText(true);
        helpLabel.setMaxWidth(1000);
        helpLabel.setAlignment(Pos.CENTER);
        helpLabel.setStyle(
                "-fx-text-fill: #C7D2E6;" +
                        "-fx-font-size: 16px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-family: " + Style.FONT_BODY + ";"
        );

        Button backButton = new Button("Back to Start");
        backButton.setStyle(buttonStyle("#2B3447", Style.COLOR_TEXT));
        backButton.setOnAction(e -> gameManager.showStartScreen());

        centerBox.getChildren().addAll(progressBox, levelGrid, helpLabel, backButton);

        UiMotion.applyEntrance(topBox, 60);
        UiMotion.applyEntrance(progressBox, 140);
        UiMotion.applyEntrance(levelGrid, 220);
        UiMotion.applyEntrance(helpLabel, 300);
        UiMotion.applyEntrance(backButton, 360);
        UiMotion.applyHoverScale(backButton, 1.03, 130);

        ScrollPane scrollPane = new ScrollPane(centerBox);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent;");

        view.setCenter(scrollPane);
    }

    private VBox levelCard(
            String levelNumber,
            String title,
            String description,
            String visualType,
            boolean unlocked,
            boolean completed,
            int order,
            Runnable action
    ) {
        VBox card = new VBox(10);
        card.setAlignment(Pos.TOP_CENTER);
        card.setPadding(new Insets(18));
        card.setPrefWidth(310);
        card.setMinHeight(250);

        String borderColor;
        String statusText;
        String statusColor;

        if (completed) {
            borderColor = Style.COLOR_PRIMARY;
            statusText = "COMPLETED";
            statusColor = Style.COLOR_PRIMARY;
        } else if (unlocked) {
            borderColor = Style.COLOR_ACCENT;
            statusText = "UNLOCKED";
            statusColor = Style.COLOR_ACCENT;
        } else {
            borderColor = "#3B4A63";
            statusText = "LOCKED";
            statusColor = Style.COLOR_MUTED;
        }

        card.setStyle(cardStyle(borderColor, unlocked));

        UiMotion.applyEntrance(card, 140 + (order * 70));
        if (unlocked || completed) {
            UiMotion.applyGlowPulse(card, Color.web(borderColor, 0.45));
            UiMotion.applyHoverScale(card, 1.03, 140);
        }

        Label levelBadge = new Label("Level " + levelNumber);
        levelBadge.setStyle(
                "-fx-text-fill: black;" +
                        "-fx-font-size: 14px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-family: " + Style.FONT_BODY + ";" +
                        "-fx-padding: 6 14;" +
                        "-fx-background-color: " + statusColor + ";" +
                        "-fx-background-radius: 20;"
        );

        Label titleLabel = new Label(title);
        titleLabel.setWrapText(true);
        titleLabel.setAlignment(Pos.CENTER);
        titleLabel.setStyle(
                "-fx-text-fill: " + Style.COLOR_TEXT + ";" +
                        "-fx-font-size: 22px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-family: " + Style.FONT_TITLE + ";"
        );

        Label descLabel = new Label(description);
        descLabel.setWrapText(true);
        descLabel.setAlignment(Pos.CENTER);
        descLabel.setMaxWidth(270);
        descLabel.setStyle(
                "-fx-text-fill: #C7D2E6;" +
                        "-fx-font-size: 15px;" +
                        "-fx-line-spacing: 4px;" +
                        "-fx-font-family: " + Style.FONT_BODY + ";"
        );

        Label visualLabel = new Label("Visual: " + visualType);
        visualLabel.setWrapText(true);
        visualLabel.setAlignment(Pos.CENTER);
        visualLabel.setStyle(
                "-fx-text-fill: " + Style.COLOR_SECONDARY + ";" +
                        "-fx-font-size: 14px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-family: " + Style.FONT_BODY + ";"
        );

        Label statusLabel = new Label(statusText);
        statusLabel.setStyle(
                "-fx-text-fill: " + statusColor + ";" +
                        "-fx-font-size: 15px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-family: " + Style.FONT_BODY + ";"
        );

        Button openButton = new Button(completed ? "Review Level" : unlocked ? "Enter Mission" : "Locked");
        openButton.setDisable(!unlocked);
        openButton.setStyle(unlocked ? buttonStyle(Style.COLOR_PRIMARY, "#0B1020") : buttonStyle("#2B3447", Style.COLOR_MUTED));
        openButton.setOnAction(e -> action.run());
        UiMotion.applyHoverScale(openButton, 1.04, 120);

        card.getChildren().addAll(
                levelBadge,
                titleLabel,
                descLabel,
                visualLabel,
                statusLabel,
                openButton
        );

        if (unlocked) {
                        card.setOnMouseEntered(e -> card.setStyle(cardStyle(Style.COLOR_PRIMARY, true)));
            card.setOnMouseExited(e -> card.setStyle(cardStyle(borderColor, true)));
        }

        return card;
    }

    private VBox progressCard(String title, String value, String color) {
        VBox card = new VBox(6);
        card.setAlignment(Pos.CENTER);
        card.setPadding(new Insets(14));
        card.setPrefWidth(190);
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
        UiMotion.applyHoverScale(card, 1.03, 140, Color.web(color, 0.5));
        return card;
    }

    private int getCompletedCount() {
        int count = 0;

        if (arrayLevelCompleted) count++;
        if (stackLevelUnlocked) count++;
        if (queueLevelUnlocked) count++;
        if (treesLevelUnlocked) count++;
        if (graphsLevelUnlocked) count++;
        if (heapsLevelUnlocked) count++;
        if (hashMapLevelUnlocked) count++;
        if (hashTableLevelUnlocked) count++;

        return count;
    }

    private int getUnlockedCount() {
        int count = 1;

        if (linkedListLevelUnlocked) count++;
        if (stackLevelUnlocked) count++;
        if (queueLevelUnlocked) count++;
        if (treesLevelUnlocked) count++;
        if (graphsLevelUnlocked) count++;
        if (heapsLevelUnlocked) count++;
        if (hashMapLevelUnlocked) count++;
        if (hashTableLevelUnlocked) count++;

        return count;
    }

    private String cardStyle(String borderColor, boolean unlocked) {
        return "-fx-background-color: " + (unlocked ? Style.COLOR_CARD : Style.COLOR_CARD_DARK) + ";" +
                "-fx-border-color: " + borderColor + ";" +
                "-fx-border-width: 2;" +
                "-fx-background-radius: 18;" +
                "-fx-border-radius: 18;";
    }

    private String buttonStyle(String bg, String textColor) {
        return "-fx-font-size: 16px;" +
                "-fx-font-weight: bold;" +
                "-fx-font-family: " + Style.FONT_BODY + ";" +
                "-fx-background-color: " + bg + ";" +
                "-fx-text-fill: " + textColor + ";" +
                "-fx-padding: 10 22;" +
                "-fx-background-radius: 12;" +
                "-fx-border-color: rgba(255, 255, 255, 0.16);" +
                "-fx-border-width: 1.2;" +
                "-fx-cursor: hand;";
    }

    private String panelStyle() {
        return "-fx-background-color: rgba(15, 23, 42, 0.68);" +
                "-fx-background-radius: 26;" +
                "-fx-border-color: rgba(255, 255, 255, 0.08);" +
                "-fx-border-width: 1.5;" +
                "-fx-border-radius: 26;";
    }

    public BorderPane getView() {
        return view;
    }
}