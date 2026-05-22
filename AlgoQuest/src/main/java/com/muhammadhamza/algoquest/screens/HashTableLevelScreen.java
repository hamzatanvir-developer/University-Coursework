package com.muhammadhamza.algoquest.screens;

import com.muhammadhamza.algoquest.GameManager;
import com.muhammadhamza.algoquest.levels.HashTableLevel;
import com.muhammadhamza.algoquest.utils.Style;
import com.muhammadhamza.algoquest.utils.UiMotion;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class HashTableLevelScreen {

    private final BorderPane view;
    private final GameManager gameManager;
    private final HashTableLevel hashTableLevel;

    private Label stageLabel;
    private Label instructionLabel;
    private Label feedbackLabel;
    private TextArea codeLabel;
    private Label statusLabel;

    private VBox tableBox;
    private VBox mainContent;

    private int currentRoom = 0;
    private int energy = 3;
    private int keysCollected = 0;
    private boolean roomAnswered = false;

    private final String[] roomNames = {
            "Pair Gate",
            "Hash Engine",
            "Collision Cell",
            "Null Trap",
            "Final Difference Door"
    };

    private final String[] roomStories = {
            "The final chamber starts with a key-value lock. Identify how Hashtable stores data.",
            "The hash engine converts keys into bucket positions. Choose the correct mechanism.",
            "Two keys reached the same bucket. Understand collision handling to continue.",
            "A dangerous null key is detected. Java Hashtable has strict rules about nulls.",
            "The final door asks the main difference between HashMap and Hashtable."
    };

    private final String[] roomQuestions = {
            "Hashtable stores data in which form?",
            "What converts a key into a bucket/index location?",
            "What happens when two keys go to the same bucket?",
            "Does Java Hashtable allow null key or null value?",
            "What is the main difference between HashMap and Hashtable?"
    };

    private final String[][] roomOptions = {
            {"Key-Value Pairs", "Only Stack Items", "Only Queue Items"},
            {"Hash Function", "Stack Pop", "Tree Root"},
            {"Collision", "Bubble Up", "Traversal"},
            {"No", "Yes, always", "Only null values"},
            {"Hashtable is synchronized", "HashMap is a stack", "Hashtable follows FIFO"}
    };

    private final int[] correctAnswers = {0, 0, 0, 0, 0};

    private final String[] correctMessages = {
            "Correct! Hashtable stores key-value pairs.",
            "Correct! Hash function converts key into bucket/index location.",
            "Correct! Same bucket conflict is called collision.",
            "Correct! Java Hashtable does not allow null key or null value.",
            "Correct! Hashtable is synchronized, while HashMap is usually not synchronized."
    };

    private final String[] wrongMessages = {
            "Hashtable stores data as key-value pairs.",
            "Hash function converts a key into a bucket location.",
            "Two keys going to the same bucket is called collision.",
            "Java Hashtable does not allow null key or null value.",
            "Main difference: Hashtable is synchronized, HashMap is usually not synchronized."
    };

    public HashTableLevelScreen(GameManager gameManager) {
        this.gameManager = gameManager;
        this.hashTableLevel = new HashTableLevel();

        view = new BorderPane();
        view.setStyle(Style.BG_GRADIENT);

        Pane ambientLayer = UiMotion.createAmbientBackground();
        ambientLayer.prefWidthProperty().bind(view.widthProperty());
        ambientLayer.prefHeightProperty().bind(view.heightProperty());
        view.getChildren().add(0, ambientLayer);

        buildLayout();
        showLearningStage();
    }

    private void buildLayout() {
        Label titleLabel = new Label("Level 9: HashTable Chamber");
        titleLabel.setStyle(
            "-fx-text-fill: " + Style.COLOR_PRIMARY + ";" +
                "-fx-font-size: 40px;" +
                "-fx-font-weight: bold;" +
                "-fx-font-family: " + Style.FONT_TITLE + ";"
        );

        stageLabel = new Label("Learning Mode");
        stageLabel.setStyle(
            "-fx-text-fill: " + Style.COLOR_SECONDARY + ";" +
                "-fx-font-size: 23px;" +
                "-fx-font-weight: bold;" +
                "-fx-font-family: " + Style.FONT_BODY + ";"
        );

        VBox topBox = new VBox(8, titleLabel, stageLabel);
        topBox.setAlignment(Pos.CENTER);
        topBox.setPadding(new Insets(18));
        topBox.setFillWidth(true);
        topBox.setMaxWidth(Double.MAX_VALUE);
        topBox.setStyle(panelStyle());
        view.setTop(topBox);

        instructionLabel = new Label();
        instructionLabel.setWrapText(true);
        instructionLabel.setMaxWidth(900);
        instructionLabel.setStyle(
            "-fx-text-fill: " + Style.COLOR_TEXT + ";" +
                "-fx-font-size: 19px;" +
                "-fx-font-family: " + Style.FONT_BODY + ";"
        );

        feedbackLabel = new Label();
        feedbackLabel.setWrapText(true);
        feedbackLabel.setMaxWidth(900);
        feedbackLabel.setStyle(
            "-fx-text-fill: " + Style.COLOR_SECONDARY + ";" +
                "-fx-font-size: 18px;" +
                "-fx-font-weight: bold;" +
                "-fx-font-family: " + Style.FONT_BODY + ";"
        );

        statusLabel = new Label();
        statusLabel.setWrapText(true);
        statusLabel.setMaxWidth(900);
        statusLabel.setStyle(
            "-fx-text-fill: " + Style.COLOR_PRIMARY + ";" +
                "-fx-font-size: 18px;" +
                "-fx-font-weight: bold;" +
                "-fx-font-family: " + Style.FONT_BODY + ";"
        );

        tableBox = new VBox(8);
        tableBox.setAlignment(Pos.CENTER);
        tableBox.setPadding(new Insets(20));
        tableBox.setMinWidth(600);
        tableBox.setMinHeight(315);
        tableBox.setStyle(
            "-fx-background-color: " + Style.COLOR_CARD_DARK + ";" +
                "-fx-border-color: " + Style.COLOR_ACCENT + ";" +
                        "-fx-border-width: 2;" +
                        "-fx-background-radius: 16;" +
                        "-fx-border-radius: 16;"
        );
        UiMotion.applyHoverScale(tableBox, 1.02, 160);

        mainContent = new VBox(18);
        mainContent.setAlignment(Pos.TOP_CENTER);
        mainContent.setPadding(new Insets(25));

        ScrollPane scrollPane = new ScrollPane(mainContent);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
        view.setCenter(scrollPane);

        VBox sidePanel = new VBox(16);
        sidePanel.setPadding(new Insets(20));
        sidePanel.setPrefWidth(820);
        sidePanel.setMinWidth(820);
        sidePanel.setStyle(
            "-fx-background-color: " + Style.COLOR_CARD + ";" +
                "-fx-border-color: #23344B;" +
                        "-fx-border-width: 0 0 0 2;"
        );

        Label codeTitle = new Label("Learning Console");
        codeTitle.setStyle(
            "-fx-text-fill: " + Style.COLOR_PRIMARY + ";" +
                "-fx-font-size: 24px;" +
                "-fx-font-weight: bold;" +
                "-fx-font-family: " + Style.FONT_TITLE + ";"
        );

        codeLabel = new TextArea("Click a learning button to view details from HashTableLevel.java.");
        codeLabel.setWrapText(true);
        codeLabel.setEditable(false);
        codeLabel.setFocusTraversable(false);
        codeLabel.setPrefWidth(760);
        codeLabel.setMinWidth(760);
        codeLabel.setPrefHeight(700);
        codeLabel.setMaxHeight(Double.MAX_VALUE);
        codeLabel.setStyle(codeBoxStyle(false));

        codeLabel.setOnMouseEntered(e -> codeLabel.setStyle(codeBoxStyle(true)));
        codeLabel.setOnMouseExited(e -> codeLabel.setStyle(codeBoxStyle(false)));

        Button backButton = new Button("Back to Level Map");
        backButton.setStyle(buttonStyle("#2B3447", Style.COLOR_TEXT));
        backButton.setOnAction(e -> gameManager.showLevelMap());
        UiMotion.applyHoverScale(backButton, 1.03, 130);

        sidePanel.getChildren().addAll(codeTitle, codeLabel, backButton);
        view.setRight(sidePanel);

        UiMotion.applyEntrance(topBox, 60);
        UiMotion.applyEntrance(sidePanel, 120);
    }

    private void showLearningStage() {
        stageLabel.setText("Learning Mode: Understand Hashtable First");
        mainContent.getChildren().clear();

        instructionLabel.setText(
                "This is the learning area. The screen shows Hashtable visuals and buttons. The actual Hashtable logic and explanations come from HashTableLevel.java."
        );

        drawHashTable("normal");

        Button conceptButton = operationButton("Concept");
        Button realLifeButton = operationButton("Real-Life Uses");
        Button putButton = operationButton("Put");
        Button getButton = operationButton("Get");
        Button removeButton = operationButton("Remove");
        Button hashButton = operationButton("Hash Function");
        Button collisionButton = operationButton("Collision");
        Button differenceButton = operationButton("HashMap vs Hashtable");
        Button edgeCasesButton = operationButton("Edge Cases");

        conceptButton.setOnAction(e -> showConcept());
        realLifeButton.setOnAction(e -> showRealLife());
        putButton.setOnAction(e -> showPut());
        getButton.setOnAction(e -> showGet());
        removeButton.setOnAction(e -> showRemove());
        hashButton.setOnAction(e -> showHashFunction());
        collisionButton.setOnAction(e -> showCollision());
        differenceButton.setOnAction(e -> showDifference());
        edgeCasesButton.setOnAction(e -> showEdgeCases());

        HBox row1 = new HBox(12, conceptButton, realLifeButton, putButton);
        row1.setAlignment(Pos.CENTER);

        HBox row2 = new HBox(12, getButton, removeButton, hashButton);
        row2.setAlignment(Pos.CENTER);

        HBox row3 = new HBox(12, collisionButton, differenceButton, edgeCasesButton);
        row3.setAlignment(Pos.CENTER);

        VBox labelCard = teachingCard(
                "Visual Labels",
                "Every Hashtable visual shows:\n" +
                        "• KEY: identifier used to find value\n" +
                        "• VALUE: actual stored data\n" +
                        "• HASH FUNCTION: converts key into bucket index\n" +
                        "• BUCKET: storage location\n" +
                        "• COLLISION: same bucket conflict\n" +
                        "• NULL RULE: Hashtable does not allow null key/value"
        );

        Button startGameButton = new Button("Enter Final Chamber");
        startGameButton.setStyle(buttonStyle(Style.COLOR_PRIMARY, "#0B1020"));
        startGameButton.setOnAction(e -> startEscapeGame());
        UiMotion.applyHoverScale(startGameButton, 1.04, 130);

        mainContent.getChildren().addAll(
                instructionLabel,
                tableBox,
                row1,
                row2,
                row3,
                statusLabel,
                feedbackLabel,
                labelCard,
                startGameButton
        );

        showConcept();
    }

    private void showConcept() {
        drawHashTable("normal");

        statusLabel.setText("Concept: Hashtable stores key-value pairs using hashing.");
        feedbackLabel.setText("Learning note: Hashtable is synchronized and does not allow null key/value.");

        showCode("HASHTABLE CONCEPT", hashTableLevel.getConceptExplanation());
    }

    private void showRealLife() {
        drawHashTable("normal");

        statusLabel.setText("Real-Life Uses: Hashtable is used for fast key-based lookup.");
        feedbackLabel.setText("Examples include student records, employee systems, inventory, and login systems.");

        showCode("REAL-LIFE HASHTABLE EXAMPLES", hashTableLevel.getRealLifeExamples());
    }

    private void showPut() {
        drawHashTable("put");

        statusLabel.setText("Put: Store id → 101.");
        feedbackLabel.setText("Learning note: put() rejects null key/value, hashes the key, then stores entry.");

        showCode("PUT OPERATION", hashTableLevel.getPutCode());
    }

    private void showGet() {
        String value = hashTableLevel.get("course");

        drawHashTable("get");

        statusLabel.setText("Get: Retrieve value by key course.");
        feedbackLabel.setText("Learning note: get(\"course\") returns " + value + ".");

        showCode("GET OPERATION", hashTableLevel.getGetCode());
    }

    private void showRemove() {
        drawHashTable("remove");

        statusLabel.setText("Remove: Delete key-value pair.");
        feedbackLabel.setText("Learning note: remove() hashes key, finds bucket, and removes matching entry.");

        showCode("REMOVE OPERATION", hashTableLevel.getRemoveCode());
    }

    private void showHashFunction() {
        int bucket = hashTableLevel.getBucketIndex("course");

        drawHashTable("hash");

        statusLabel.setText("Hash Function: Convert key into bucket index.");
        feedbackLabel.setText("Learning note: For key \"course\", bucket index is " + bucket + ".");

        showCode("HASH FUNCTION", hashTableLevel.getHashFunctionCode());
    }

    private void showCollision() {
        drawHashTable("collision");

        statusLabel.setText("Collision: Two keys go to same bucket.");
        feedbackLabel.setText("Learning note: Collision can be handled using chaining.");

        showCode("COLLISION HANDLING", hashTableLevel.getCollisionCode());
    }

    private void showDifference() {
        drawHashTable("difference");

        statusLabel.setText("HashMap vs Hashtable");
        feedbackLabel.setText("Learning note: Hashtable is synchronized and stricter. HashMap is commonly used in modern Java.");

        showCode("HASHMAP VS HASHTABLE", hashTableLevel.getDifferenceCode());
    }

    private void showEdgeCases() {
        drawHashTable("null");

        statusLabel.setText("Edge Cases: Null key/value, missing key, duplicate key, and collisions.");
        feedbackLabel.setText("Learning note: Java Hashtable does not allow null key or null value.");

        showCode("HASHTABLE EDGE CASES", hashTableLevel.getEdgeCases());
    }

    private void startEscapeGame() {
        currentRoom = 0;
        energy = 3;
        keysCollected = 0;
        showEscapeRoom();
    }

    private void showEscapeRoom() {
        roomAnswered = false;
        stageLabel.setText("Escape Mode: HashTable Chamber");
        mainContent.getChildren().clear();

        Label gameTitle = new Label("HashTable Chamber Escape");
        gameTitle.setStyle("-fx-text-fill: #00D4FF; -fx-font-size: 34px; -fx-font-weight: bold;");

        Label roomLabel = new Label("Room " + (currentRoom + 1) + "/5: " + roomNames[currentRoom]);
        roomLabel.setStyle("-fx-text-fill: #00FF99; -fx-font-size: 24px; -fx-font-weight: bold;");

        Label storyLabel = new Label(roomStories[currentRoom]);
        storyLabel.setWrapText(true);
        storyLabel.setMaxWidth(900);
        storyLabel.setAlignment(Pos.CENTER);
        storyLabel.setStyle("-fx-text-fill: white; -fx-font-size: 19px;");

        Label missionLabel = new Label("Mission: " + roomQuestions[currentRoom]);
        missionLabel.setWrapText(true);
        missionLabel.setMaxWidth(900);
        missionLabel.setAlignment(Pos.CENTER);
        missionLabel.setStyle("-fx-text-fill: #FACC15; -fx-font-size: 21px; -fx-font-weight: bold;");

        drawRoomVisual();

        HBox gameStats = new HBox(16);
        gameStats.setAlignment(Pos.CENTER);
        gameStats.getChildren().addAll(
                statCard("Energy", getEnergyText(), "#FACC15"),
                statCard("Keys", keysCollected + "/5", "#00FF99"),
                statCard("Final Locks", (5 - keysCollected) + " left", "#00D4FF")
        );

        Button optionA = puzzleButton(roomOptions[currentRoom][0]);
        Button optionB = puzzleButton(roomOptions[currentRoom][1]);
        Button optionC = puzzleButton(roomOptions[currentRoom][2]);

        optionA.setOnAction(e -> checkEscapeAnswer(0));
        optionB.setOnAction(e -> checkEscapeAnswer(1));
        optionC.setOnAction(e -> checkEscapeAnswer(2));

        HBox options = new HBox(14, optionA, optionB, optionC);
        options.setAlignment(Pos.CENTER);

        feedbackLabel.setText("Choose the correct Hashtable concept. No hints are shown before answering.");

        mainContent.getChildren().addAll(
                gameTitle,
                roomLabel,
                storyLabel,
                gameStats,
                missionLabel,
                tableBox,
                options,
                feedbackLabel
        );

        showCode(
                "MISSION CONSOLE",
                "Solve the Hashtable room.\n\n" +
                        "Learning hints are not shown before answer.\n" +
                        "Explanation appears after your attempt."
        );
    }

    private void checkEscapeAnswer(int selected) {
        if (roomAnswered) {
            return;
        }

        roomAnswered = true;

        if (selected == correctAnswers[currentRoom]) {
            keysCollected++;
            feedbackLabel.setText("✓ " + correctMessages[currentRoom] + " Key collected!");

            showCode(
                    "CORRECT ANSWER",
                    correctMessages[currentRoom] +
                            "\n\nProgress: " + keysCollected + "/5"
            );

            if (currentRoom == roomNames.length - 1) {
                Button finishButton = new Button("Finish AlgoQuest");
                finishButton.setStyle(buttonStyle("#00FF99", "black"));
                finishButton.setOnAction(e -> showFinalDoor());
                mainContent.getChildren().add(finishButton);
            } else {
                Button nextButton = new Button("Next Room");
                nextButton.setStyle(buttonStyle("#00FF99", "black"));
                nextButton.setOnAction(e -> {
                    currentRoom++;
                    showEscapeRoom();
                });
                mainContent.getChildren().add(nextButton);
            }
        } else {
            energy--;
            feedbackLabel.setText("✗ Wrong. " + wrongMessages[currentRoom]);

            showCode(
                    "EXPLANATION AFTER WRONG ANSWER",
                    "Correct idea:\n" +
                            wrongMessages[currentRoom]
            );

            if (energy <= 0) {
                showGameOver();
            } else {
                Button nextButton = new Button("Try Next Room");
                nextButton.setStyle(buttonStyle("#FACC15", "black"));
                nextButton.setOnAction(e -> {
                    if (currentRoom == roomNames.length - 1) {
                        showFinalDoor();
                    } else {
                        currentRoom++;
                        showEscapeRoom();
                    }
                });
                mainContent.getChildren().add(nextButton);
            }
        }
    }

    private void showFinalDoor() {
        mainContent.getChildren().clear();
        stageLabel.setText("Final Completion Door");

        Label title = new Label("HashTable Chamber Completed");
        title.setStyle("-fx-text-fill: #00FF99; -fx-font-size: 36px; -fx-font-weight: bold;");

        Label message = new Label(
                "You collected final Hashtable keys and completed the last AlgoQuest chamber."
        );
        message.setWrapText(true);
        message.setMaxWidth(900);
        message.setAlignment(Pos.CENTER);
        message.setStyle("-fx-text-fill: white; -fx-font-size: 21px;");

        drawHashTable("normal");

        VBox summary = teachingCard(
                "HashTable Mastery Summary",
                "• Hashtable stores key-value pairs\n" +
                        "• Hash function finds bucket index\n" +
                        "• Collision means same bucket conflict\n" +
                        "• Java Hashtable is synchronized\n" +
                        "• Java Hashtable does not allow null key/value\n" +
                        "• Average put/get/remove = O(1)\n" +
                        "• Worst case can become O(n)"
        );

        Button finishButton = new Button("Go to Victory Screen");
        finishButton.setStyle(buttonStyle("#00FF99", "black"));
        finishButton.setOnAction(e -> gameManager.completeHashTableLevel());

        mainContent.getChildren().addAll(title, message, tableBox, summary, finishButton);

        showCode(
                "FINAL LEVEL COMPLETED",
                "Hashtable completed.\n\nAll AlgoQuest data structure levels are completed."
        );
    }

    private void showGameOver() {
        mainContent.getChildren().clear();
        stageLabel.setText("Energy Lost");

        Label title = new Label("Energy Finished");
        title.setStyle("-fx-text-fill: #EF4444; -fx-font-size: 36px; -fx-font-weight: bold;");

        Label message = new Label(
                "HashTable Chamber locked again. Review learning first or retry the final chamber."
        );
        message.setWrapText(true);
        message.setMaxWidth(850);
        message.setAlignment(Pos.CENTER);
        message.setStyle("-fx-text-fill: white; -fx-font-size: 21px;");

        Button retryButton = new Button("Retry Final Chamber");
        retryButton.setStyle(buttonStyle("#FACC15", "black"));
        retryButton.setOnAction(e -> startEscapeGame());

        Button learningButton = new Button("Back to Learning");
        learningButton.setStyle(buttonStyle("#00D4FF", "black"));
        learningButton.setOnAction(e -> showLearningStage());

        HBox buttons = new HBox(14, retryButton, learningButton);
        buttons.setAlignment(Pos.CENTER);

        mainContent.getChildren().addAll(title, message, buttons);

        showCode(
                "RETRY",
                "Review Hashtable learning content first, then try again."
        );
    }

    private void drawRoomVisual() {
        if (currentRoom == 0) {
            drawHashTable("normal");
        } else if (currentRoom == 1) {
            drawHashTable("hash");
        } else if (currentRoom == 2) {
            drawHashTable("collision");
        } else if (currentRoom == 3) {
            drawHashTable("null");
        } else {
            drawHashTable("difference");
        }
    }

    private void drawHashTable(String mode) {
        tableBox.getChildren().clear();

        Label caption = new Label(getCaption(mode));
        caption.setStyle("-fx-text-fill: #00FF99; -fx-font-size: 18px; -fx-font-weight: bold;");

        VBox buckets = new VBox(8);
        buckets.setAlignment(Pos.CENTER);

        if (mode.equals("put")) {
            buckets.getChildren().addAll(
                    bucketRow("Bucket 0", "empty", false),
                    bucketRow("Bucket 1", "id → 101", true),
                    bucketRow("Bucket 2", "empty", false),
                    bucketRow("Bucket 3", "empty", false)
            );
        } else if (mode.equals("get")) {
            buckets.getChildren().addAll(
                    bucketRow("Bucket 0", "empty", false),
                    bucketRow("Bucket 1", "id → 101", false),
                    bucketRow("Bucket 2", "course → DSA", true),
                    bucketRow("Bucket 3", "name → Ali", false)
            );
        } else if (mode.equals("remove")) {
            buckets.getChildren().addAll(
                    bucketRow("Bucket 0", "empty", false),
                    bucketRow("Bucket 1", "id → 101", false),
                    bucketRow("Bucket 2", "course → DSA", false),
                    bucketRow("Bucket 3", "removed name", true)
            );
        } else if (mode.equals("hash")) {
            buckets.getChildren().addAll(
                    bucketRow("Key", "course", true),
                    bucketRow("Hash", "hash(course)", true),
                    bucketRow("Bucket", "calculated bucket", true),
                    bucketRow("Stored", "course → DSA", false)
            );
        } else if (mode.equals("collision")) {
            buckets.getChildren().addAll(
                    bucketRow("Bucket 0", "empty", false),
                    bucketRow("Bucket 1", "id → 101 | code → SE101", true),
                    bucketRow("Bucket 2", "course → DSA", false),
                    bucketRow("Bucket 3", "name → Ali", false)
            );
        } else if (mode.equals("difference")) {
            buckets.getChildren().addAll(
                    bucketRow("HashMap", "not synchronized by default", false),
                    bucketRow("HashMap", "allows one null key", false),
                    bucketRow("Hashtable", "synchronized", true),
                    bucketRow("Hashtable", "no null key/value", true)
            );
        } else if (mode.equals("null")) {
            buckets.getChildren().addAll(
                    bucketRow("Null Key", "not allowed", true),
                    bucketRow("Null Value", "not allowed", true),
                    bucketRow("Reason", "Hashtable is stricter", false),
                    bucketRow("Safe Use", "use valid key and value", false)
            );
        } else {
            buckets.getChildren().addAll(
                    bucketRow("Bucket 0", "empty", false),
                    bucketRow("Bucket 1", "id → 101", true),
                    bucketRow("Bucket 2", "course → DSA", true),
                    bucketRow("Bucket 3", "name → Ali", false)
            );
        }

        tableBox.getChildren().addAll(caption, buckets);
    }

    private String getCaption(String mode) {
        if (mode.equals("put")) return "Put Operation";
        if (mode.equals("get")) return "Get Operation";
        if (mode.equals("remove")) return "Remove Operation";
        if (mode.equals("hash")) return "Hash Function Flow";
        if (mode.equals("collision")) return "Collision using Chaining";
        if (mode.equals("difference")) return "HashMap vs Hashtable";
        if (mode.equals("null")) return "Hashtable Null Rule";
        return "Hashtable Buckets";
    }

    private HBox bucketRow(String bucket, String content, boolean highlight) {
        HBox row = new HBox(12);
        row.setAlignment(Pos.CENTER);

        Label bucketLabel = new Label(bucket);
        bucketLabel.setStyle(
                "-fx-text-fill: white;" +
                        "-fx-font-size: 16px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-min-width: 125px;" +
                        "-fx-min-height: 44px;" +
                        "-fx-alignment: center;" +
                        "-fx-background-color: #111827;" +
                        "-fx-border-color: #334155;" +
                        "-fx-background-radius: 8;" +
                        "-fx-border-radius: 8;"
        );

        Label contentLabel = new Label(content);
        contentLabel.setStyle(
                "-fx-text-fill: white;" +
                        "-fx-font-size: 17px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-min-width: 365px;" +
                        "-fx-min-height: 44px;" +
                        "-fx-alignment: center;" +
                        "-fx-background-color: " + (highlight ? "#0EA5E9" : "#1E293B") + ";" +
                        "-fx-border-color: #00D4FF;" +
                        "-fx-border-width: 2;" +
                        "-fx-background-radius: 8;" +
                        "-fx-border-radius: 8;"
        );

        row.getChildren().addAll(bucketLabel, contentLabel);
        return row;
    }

    private VBox teachingCard(String title, String body) {
        VBox card = new VBox(8);
        card.setPadding(new Insets(18));
        card.setAlignment(Pos.CENTER_LEFT);
        card.setMaxWidth(900);
        card.setStyle(
            "-fx-background-color: " + Style.COLOR_CARD + ";" +
                "-fx-border-color: #23344B;" +
                        "-fx-border-width: 2;" +
                        "-fx-background-radius: 14;" +
                        "-fx-border-radius: 14;"
        );

        Label titleLabel = new Label(title);
        titleLabel.setStyle(
            "-fx-text-fill: " + Style.COLOR_PRIMARY + ";" +
                "-fx-font-size: 21px;" +
                "-fx-font-weight: bold;" +
                "-fx-font-family: " + Style.FONT_TITLE + ";"
        );

        Label bodyLabel = new Label(body);
        bodyLabel.setWrapText(true);
        bodyLabel.setStyle(
            "-fx-text-fill: #C7D2E6;" +
                "-fx-font-size: 17px;" +
                "-fx-font-family: " + Style.FONT_BODY + ";"
        );

        card.getChildren().addAll(titleLabel, bodyLabel);
        return card;
    }

    private VBox statCard(String title, String value, String color) {
        VBox card = new VBox(4);
        card.setAlignment(Pos.CENTER);
        card.setPadding(new Insets(12));
        card.setPrefWidth(170);
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
                "-fx-font-size: 24px;" +
                "-fx-font-weight: bold;" +
                "-fx-font-family: " + Style.FONT_TITLE + ";"
        );

        Label titleLabel = new Label(title);
        titleLabel.setStyle(
            "-fx-text-fill: " + Style.COLOR_TEXT + ";" +
                "-fx-font-size: 14px;" +
                "-fx-font-weight: bold;" +
                "-fx-font-family: " + Style.FONT_BODY + ";"
        );

        card.getChildren().addAll(valueLabel, titleLabel);
        UiMotion.applyHoverScale(card, 1.03, 130);
        return card;
    }

    private String getEnergyText() {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < energy; i++) {
            result.append("♥");
        }

        return result.toString();
    }

    private void showCode(String title, String code) {
        codeLabel.setText(title + "\n\n" + code);
        codeLabel.positionCaret(0);
    }

    private Button operationButton(String text) {
        Button button = new Button(text);
        button.setStyle(buttonStyle(Style.COLOR_ACCENT, "#0B1020"));
        UiMotion.applyHoverScale(button, 1.04, 120);
        return button;
    }

    private Button puzzleButton(String text) {
        Button button = new Button(text);
        button.setWrapText(true);
        button.setMinWidth(245);
        button.setMaxWidth(300);
        button.setStyle(buttonStyle("#2B3447", Style.COLOR_TEXT));
        UiMotion.applyHoverScale(button, 1.04, 120);
        return button;
    }

    private String buttonStyle(String bg, String textColor) {
        return "-fx-font-size: 16px;" +
            "-fx-font-weight: bold;" +
            "-fx-font-family: " + Style.FONT_BODY + ";" +
                "-fx-background-color: " + bg + ";" +
                "-fx-text-fill: " + textColor + ";" +
                "-fx-padding: 11 18;" +
            "-fx-background-radius: 12;" +
            "-fx-border-color: rgba(255, 255, 255, 0.16);" +
            "-fx-border-width: 1.2;" +
                "-fx-cursor: hand;";
    }

    private String codeBoxStyle(boolean hover) {
        return "-fx-text-fill: #E2E8F0;" +
            "-fx-font-size: 17px;" +
            "-fx-font-family: Consolas;" +
            "-fx-control-inner-background: #0B1020;" +
            "-fx-highlight-fill: " + Style.COLOR_ACCENT + ";" +
            "-fx-highlight-text-fill: #0B1020;" +
            "-fx-background-color: " + (hover ? "#111A2A" : "#0B1020") + ";" +
            "-fx-padding: 15;" +
            "-fx-background-radius: 12;" +
            "-fx-border-color: " + (hover ? Style.COLOR_ACCENT : "#2B3447") + ";" +
            "-fx-border-radius: 12;";
    }

        private String panelStyle() {
        return "-fx-background-color: rgba(15, 23, 42, 0.65);" +
            "-fx-background-radius: 22;" +
            "-fx-border-color: rgba(255, 255, 255, 0.08);" +
            "-fx-border-width: 1.5;" +
            "-fx-border-radius: 22;";
        }

    public BorderPane getView() {
        return view;
    }
}