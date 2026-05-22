package com.muhammadhamza.algoquest.screens;

import com.muhammadhamza.algoquest.GameManager;
import com.muhammadhamza.algoquest.levels.HashMapLevel;
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

public class HashMapLevelScreen {

    private final BorderPane view;
    private final GameManager gameManager;
    private final HashMapLevel hashMapLevel;

    private Label stageLabel;
    private Label instructionLabel;
    private Label feedbackLabel;
    private TextArea codeLabel;
    private Label statusLabel;

    private VBox mapBox;
    private VBox mainContent;

    private int currentRoom = 0;
    private int energy = 3;
    private int keysCollected = 0;
    private boolean roomAnswered = false;

    private final String[] roomNames = {
            "Key Gate",
            "Value Vault",
            "Hash Machine",
            "Bucket Room",
            "Collision Chamber"
    };

    private final String[] roomStories = {
            "The vault gate asks for the secret identifier used to find data quickly.",
            "A record is locked inside the vault. You must identify what is stored against the key.",
            "The hash machine converts a key into a bucket location.",
            "The storage room has many buckets. You must know where key-value pairs are placed.",
            "Two keys reached the same bucket. Solve the collision chamber to escape."
    };

    private final String[] roomQuestions = {
            "In name → Ali, what is the key?",
            "In name → Ali, what is the value?",
            "What converts a key into a bucket/index location?",
            "HashMap stores data inside what structure?",
            "What happens when two different keys go to the same bucket?"
    };

    private final String[][] roomOptions = {
            {"name", "Ali", "arrow"},
            {"name", "Ali", "key"},
            {"Hash function", "Stack top", "Tree root"},
            {"Buckets", "Only stack blocks", "Only queue line"},
            {"Collision", "Traversal", "Bubble up"}
    };

    private final int[] correctAnswers = {0, 1, 0, 0, 0};

    private final String[] correctMessages = {
            "Correct! The key is name. It is used to find the value.",
            "Correct! Ali is the value stored against the key name.",
            "Correct! A hash function converts a key into a bucket index.",
            "Correct! HashMap stores entries inside buckets.",
            "Correct! Same bucket conflict is called collision."
    };

    private final String[] wrongMessages = {
            "The key is used to find the value. In name → Ali, name is the key.",
            "The value is the data stored against the key. In name → Ali, Ali is the value.",
            "Hash function changes the key into a bucket/index location.",
            "HashMap uses buckets internally to store entries.",
            "When two keys go to the same bucket, it is called collision."
    };

    public HashMapLevelScreen(GameManager gameManager) {
        this.gameManager = gameManager;
        this.hashMapLevel = new HashMapLevel();

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
        Label titleLabel = new Label("Level 8: HashMap Vault");
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

        mapBox = new VBox(8);
        mapBox.setAlignment(Pos.CENTER);
        mapBox.setPadding(new Insets(20));
        mapBox.setMinWidth(600);
        mapBox.setMinHeight(315);
        mapBox.setStyle(
            "-fx-background-color: " + Style.COLOR_CARD_DARK + ";" +
                "-fx-border-color: " + Style.COLOR_ACCENT + ";" +
                        "-fx-border-width: 2;" +
                        "-fx-background-radius: 16;" +
                        "-fx-border-radius: 16;"
        );
        UiMotion.applyHoverScale(mapBox, 1.02, 160);

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

        codeLabel = new TextArea("Click a learning button to view details from HashMapLevel.java.");
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
        stageLabel.setText("Learning Mode: Understand HashMap First");
        mainContent.getChildren().clear();

        instructionLabel.setText(
                "This is the learning area. The screen shows HashMap visuals and buttons. The actual HashMap logic and explanations come from HashMapLevel.java."
        );

        drawHashMap("normal");

        Button conceptButton = operationButton("Concept");
        Button realLifeButton = operationButton("Real-Life Uses");
        Button putButton = operationButton("Put");
        Button getButton = operationButton("Get");
        Button removeButton = operationButton("Remove");
        Button hashButton = operationButton("Hash Function");
        Button collisionButton = operationButton("Collision");
        Button edgeCasesButton = operationButton("Edge Cases");

        conceptButton.setOnAction(e -> showConcept());
        realLifeButton.setOnAction(e -> showRealLife());
        putButton.setOnAction(e -> showPut());
        getButton.setOnAction(e -> showGet());
        removeButton.setOnAction(e -> showRemove());
        hashButton.setOnAction(e -> showHashFunction());
        collisionButton.setOnAction(e -> showCollision());
        edgeCasesButton.setOnAction(e -> showEdgeCases());

        HBox row1 = new HBox(12, conceptButton, realLifeButton, putButton, getButton);
        row1.setAlignment(Pos.CENTER);

        HBox row2 = new HBox(12, removeButton, hashButton, collisionButton, edgeCasesButton);
        row2.setAlignment(Pos.CENTER);

        VBox labelCard = teachingCard(
                "Visual Labels",
                "Every HashMap visual shows:\n" +
                        "• KEY: identifier used to find value\n" +
                        "• VALUE: actual stored data\n" +
                        "• HASH FUNCTION: converts key into bucket index\n" +
                        "• BUCKET: internal storage position\n" +
                        "• COLLISION: two keys in same bucket"
        );

        Button startGameButton = new Button("Enter HashMap Vault");
        startGameButton.setStyle(buttonStyle(Style.COLOR_PRIMARY, "#0B1020"));
        startGameButton.setOnAction(e -> startEscapeGame());
        UiMotion.applyHoverScale(startGameButton, 1.04, 130);

        mainContent.getChildren().addAll(
                instructionLabel,
                mapBox,
                row1,
                row2,
                statusLabel,
                feedbackLabel,
                labelCard,
                startGameButton
        );

        showConcept();
    }

    private void showConcept() {
        drawHashMap("normal");

        statusLabel.setText("Concept: HashMap stores key-value pairs.");
        feedbackLabel.setText("Learning note: A key is used to quickly find its value.");

        showCode("HASHMAP CONCEPT", hashMapLevel.getConceptExplanation());
    }

    private void showRealLife() {
        drawHashMap("normal");

        statusLabel.setText("Real-Life Uses: HashMap is useful for fast lookup by key.");
        feedbackLabel.setText("Examples include contacts, student records, dictionary, and product catalog.");

        showCode("REAL-LIFE HASHMAP EXAMPLES", hashMapLevel.getRealLifeExamples());
    }

    private void showPut() {
        drawHashMap("put");

        statusLabel.setText("Put: Store name → Ali.");
        feedbackLabel.setText("Learning note: put() hashes the key, finds bucket, and stores or updates entry.");

        showCode("PUT OPERATION", hashMapLevel.getPutCode());
    }

    private void showGet() {
        String value = hashMapLevel.get("name");

        drawHashMap("get");

        statusLabel.setText("Get: Retrieve value by key name.");
        feedbackLabel.setText("Learning note: get(\"name\") returns " + value + ".");

        showCode("GET OPERATION", hashMapLevel.getGetCode());
    }

    private void showRemove() {
        drawHashMap("remove");

        statusLabel.setText("Remove: Delete a key-value pair.");
        feedbackLabel.setText("Learning note: remove() hashes the key, finds bucket, then removes matching entry.");

        showCode("REMOVE OPERATION", hashMapLevel.getRemoveCode());
    }

    private void showHashFunction() {
        int bucket = hashMapLevel.getBucketIndex("name");

        drawHashMap("hash");

        statusLabel.setText("Hash Function: Convert key into bucket index.");
        feedbackLabel.setText("Learning note: For key \"name\", bucket index is " + bucket + ".");

        showCode("HASH FUNCTION", hashMapLevel.getHashFunctionCode());
    }

    private void showCollision() {
        drawHashMap("collision");

        statusLabel.setText("Collision: Two keys go to same bucket.");
        feedbackLabel.setText("Learning note: Collision can be handled using chaining.");

        showCode("COLLISION HANDLING", hashMapLevel.getCollisionCode());
    }

    private void showEdgeCases() {
        drawHashMap("normal");

        statusLabel.setText("Edge Cases: Missing key, duplicate key, empty bucket, and collision.");
        feedbackLabel.setText("Learning note: Good HashMap code handles missing keys and collisions safely.");

        showCode("HASHMAP EDGE CASES", hashMapLevel.getEdgeCases());
    }

    private void startEscapeGame() {
        currentRoom = 0;
        energy = 3;
        keysCollected = 0;
        showEscapeRoom();
    }

    private void showEscapeRoom() {
        roomAnswered = false;
        stageLabel.setText("Escape Mode: HashMap Vault");
        mainContent.getChildren().clear();

        Label gameTitle = new Label("HashMap Vault Escape");
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
                statCard("Vault Locks", (5 - keysCollected) + " left", "#00D4FF")
        );

        Button optionA = puzzleButton(roomOptions[currentRoom][0]);
        Button optionB = puzzleButton(roomOptions[currentRoom][1]);
        Button optionC = puzzleButton(roomOptions[currentRoom][2]);

        optionA.setOnAction(e -> checkEscapeAnswer(0));
        optionB.setOnAction(e -> checkEscapeAnswer(1));
        optionC.setOnAction(e -> checkEscapeAnswer(2));

        HBox options = new HBox(14, optionA, optionB, optionC);
        options.setAlignment(Pos.CENTER);

        feedbackLabel.setText("Choose the correct HashMap concept. No hints are shown before answering.");

        mainContent.getChildren().addAll(
                gameTitle,
                roomLabel,
                storyLabel,
                gameStats,
                missionLabel,
                mapBox,
                options,
                feedbackLabel
        );

        showCode(
                "MISSION CONSOLE",
                "Solve the HashMap room.\n\n" +
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
                Button finalButton = new Button("Open Vault Door");
                finalButton.setStyle(buttonStyle("#00FF99", "black"));
                finalButton.setOnAction(e -> showFinalDoor());
                mainContent.getChildren().add(finalButton);
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
        stageLabel.setText("Final Vault Door");

        Label title = new Label("HashMap Vault Opened");
        title.setStyle("-fx-text-fill: #00FF99; -fx-font-size: 36px; -fx-font-weight: bold;");

        Label message = new Label(
                "You collected HashMap keys and completed the HashMap Vault."
        );
        message.setWrapText(true);
        message.setMaxWidth(900);
        message.setAlignment(Pos.CENTER);
        message.setStyle("-fx-text-fill: white; -fx-font-size: 21px;");

        drawHashMap("normal");

        VBox summary = teachingCard(
                "HashMap Mastery Summary",
                "• HashMap stores key-value pairs\n" +
                        "• Key is used to access value\n" +
                        "• Hash function finds bucket index\n" +
                        "• put() stores or updates data\n" +
                        "• get() retrieves data by key\n" +
                        "• Collision means same bucket\n" +
                        "• Average put/get/remove = O(1)"
        );

        Button unlockButton = new Button("Unlock HashTable Level");
        unlockButton.setStyle(buttonStyle("#00FF99", "black"));
        unlockButton.setOnAction(e -> gameManager.completeHashMapLevel());

        mainContent.getChildren().addAll(title, message, mapBox, summary, unlockButton);

        showCode(
                "LEVEL COMPLETED",
                "HashMap completed.\n\nNext: HashTable."
        );
    }

    private void showGameOver() {
        mainContent.getChildren().clear();
        stageLabel.setText("Energy Lost");

        Label title = new Label("Energy Finished");
        title.setStyle("-fx-text-fill: #EF4444; -fx-font-size: 36px; -fx-font-weight: bold;");

        Label message = new Label(
                "HashMap Vault locked again. Review learning first or retry the vault."
        );
        message.setWrapText(true);
        message.setMaxWidth(850);
        message.setAlignment(Pos.CENTER);
        message.setStyle("-fx-text-fill: white; -fx-font-size: 21px;");

        Button retryButton = new Button("Retry HashMap Vault");
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
                "Review HashMap learning content first, then try again."
        );
    }

    private void drawRoomVisual() {
        if (currentRoom == 0) {
            drawHashMap("key");
        } else if (currentRoom == 1) {
            drawHashMap("value");
        } else if (currentRoom == 2) {
            drawHashMap("hash");
        } else if (currentRoom == 3) {
            drawHashMap("normal");
        } else {
            drawHashMap("collision");
        }
    }

    private void drawHashMap(String mode) {
        mapBox.getChildren().clear();

        Label caption = new Label(getCaption(mode));
        caption.setStyle("-fx-text-fill: #00FF99; -fx-font-size: 18px; -fx-font-weight: bold;");

        VBox buckets = new VBox(8);
        buckets.setAlignment(Pos.CENTER);

        if (mode.equals("key")) {
            buckets.getChildren().addAll(
                    bucketRow("Pair", "name → Ali", true),
                    bucketRow("Key", "name", true),
                    bucketRow("Value", "Ali", false),
                    bucketRow("Meaning", "Use key to find value", false)
            );
        } else if (mode.equals("value")) {
            buckets.getChildren().addAll(
                    bucketRow("Pair", "name → Ali", true),
                    bucketRow("Key", "name", false),
                    bucketRow("Value", "Ali", true),
                    bucketRow("Meaning", "Value is stored data", false)
            );
        } else if (mode.equals("collision")) {
            buckets.getChildren().addAll(
                    bucketRow("Bucket 0", "empty", false),
                    bucketRow("Bucket 1", "age → 20", false),
                    bucketRow("Bucket 2", "name → Ali | city → Lahore", true),
                    bucketRow("Bucket 3", "course → DSA", false)
            );
        } else if (mode.equals("hash")) {
            buckets.getChildren().addAll(
                    bucketRow("Key", "name", true),
                    bucketRow("Hash", "hash(name)", true),
                    bucketRow("Bucket", "calculated bucket", true),
                    bucketRow("Stored", "name → Ali", false)
            );
        } else if (mode.equals("put")) {
            buckets.getChildren().addAll(
                    bucketRow("Bucket 0", "empty", false),
                    bucketRow("Bucket 1", "empty", false),
                    bucketRow("Bucket 2", "name → Ali", true),
                    bucketRow("Bucket 3", "empty", false)
            );
        } else if (mode.equals("get")) {
            buckets.getChildren().addAll(
                    bucketRow("Bucket 0", "empty", false),
                    bucketRow("Bucket 1", "age → 20", false),
                    bucketRow("Bucket 2", "name → Ali", true),
                    bucketRow("Bucket 3", "course → DSA", false)
            );
        } else if (mode.equals("remove")) {
            buckets.getChildren().addAll(
                    bucketRow("Bucket 0", "empty", false),
                    bucketRow("Bucket 1", "removed age", true),
                    bucketRow("Bucket 2", "name → Ali", false),
                    bucketRow("Bucket 3", "course → DSA", false)
            );
        } else {
            buckets.getChildren().addAll(
                    bucketRow("Bucket 0", "empty", false),
                    bucketRow("Bucket 1", "age → 20", false),
                    bucketRow("Bucket 2", "name → Ali", true),
                    bucketRow("Bucket 3", "course → DSA", false)
            );
        }

        mapBox.getChildren().addAll(caption, buckets);
    }

    private String getCaption(String mode) {
        if (mode.equals("key")) return "Key Identification";
        if (mode.equals("value")) return "Value Identification";
        if (mode.equals("collision")) return "Collision using Chaining";
        if (mode.equals("hash")) return "Hash Function Flow";
        if (mode.equals("put")) return "Put Operation";
        if (mode.equals("get")) return "Get Operation";
        if (mode.equals("remove")) return "Remove Operation";
        return "HashMap Buckets";
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