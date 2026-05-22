package com.muhammadhamza.algoquest.screens;

import com.muhammadhamza.algoquest.GameManager;
import com.muhammadhamza.algoquest.levels.HeapsLevel;
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

public class HeapsLevelScreen {

    private final BorderPane view;
    private final GameManager gameManager;
    private final HeapsLevel heapsLevel;

    private Label stageLabel;
    private Label instructionLabel;
    private Label feedbackLabel;
    private TextArea codeLabel;
    private Label statusLabel;

    private VBox heapBox;
    private VBox mainContent;

    private int currentRoom = 0;
    private int energy = 3;
    private int keysCollected = 0;
    private boolean roomAnswered = false;

    private final String[] roomNames = {
            "Max Root Gate",
            "Min Root Cave",
            "Bubble Up Lift",
            "Bubble Down Tunnel",
            "Priority Peak"
    };

    private final String[] roomStories = {
            "The mountain gate checks whether you know which value stays at the root of a Max Heap.",
            "A cave shows a Min Heap. You must identify the smallest value at the root.",
            "A new powerful value enters the heap and must climb upward to restore heap order.",
            "The root is extracted. The last value moves to root and must move downward correctly.",
            "At the mountain peak, only the highest priority item can be served first."
    };

    private final String[] roomQuestions = {
            "In Max Heap [90, 50, 70], which value should be at the root?",
            "In Min Heap [10, 30, 20], which value should be at the root?",
            "In Max Heap, after inserting 95, what should happen if 95 is greater than its parent?",
            "After extracting max from a heap, what happens next?",
            "Heap is mainly useful for which type of access?"
    };

    private final String[][] roomOptions = {
            {"90", "50", "70"},
            {"10", "30", "20"},
            {"Bubble up", "Delete all nodes", "Convert to queue"},
            {"Last item moves to root and bubbles down", "Nothing changes", "All values are sorted"},
            {"Priority-based access", "Only direct indexing", "Only pointer traversal"}
    };

    private final int[] correctAnswers = {0, 0, 0, 0, 0};

    private final String[] correctMessages = {
            "Correct! In Max Heap, the maximum value stays at root.",
            "Correct! In Min Heap, the minimum value stays at root.",
            "Correct! Bubble up moves the new value upward until heap rule is fixed.",
            "Correct! Last item moves to root, then bubble down fixes heap order.",
            "Correct! Heap is used for priority-based access."
    };

    private final String[] wrongMessages = {
            "Max Heap keeps the largest value at the root.",
            "Min Heap keeps the smallest value at the root.",
            "When inserted value is greater than parent in Max Heap, it bubbles up.",
            "After extracting root, last item replaces root and bubbles down.",
            "Heap is mainly used when priority matters."
    };

    public HeapsLevelScreen(GameManager gameManager) {
        this.gameManager = gameManager;
        this.heapsLevel = new HeapsLevel();

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
        Label titleLabel = new Label("Level 7: Heap Mountain");
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

        heapBox = new VBox(8);
        heapBox.setAlignment(Pos.CENTER);
        heapBox.setPadding(new Insets(20));
        heapBox.setMinWidth(600);
        heapBox.setMinHeight(315);
        heapBox.setStyle(
            "-fx-background-color: " + Style.COLOR_CARD_DARK + ";" +
                "-fx-border-color: " + Style.COLOR_ACCENT + ";" +
                        "-fx-border-width: 2;" +
                        "-fx-background-radius: 16;" +
                        "-fx-border-radius: 16;"
        );
        UiMotion.applyHoverScale(heapBox, 1.02, 160);

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

        codeLabel = new TextArea("Click a learning button to view details from HeapsLevel.java.");
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
        stageLabel.setText("Learning Mode: Understand Heaps First");
        mainContent.getChildren().clear();

        instructionLabel.setText(
                "This is the learning area. The screen shows heap visuals and buttons. The actual heap logic and explanations come from HeapsLevel.java."
        );

        drawHeap("90", "50", "70", "Max Heap", "root");

        Button conceptButton = operationButton("Concept");
        Button realLifeButton = operationButton("Real-Life Uses");
        Button maxMinButton = operationButton("Max vs Min");
        Button insertButton = operationButton("Insert");
        Button extractButton = operationButton("Extract");
        Button peekButton = operationButton("Peek Root");
        Button arrayButton = operationButton("Array Formula");
        Button edgeCasesButton = operationButton("Edge Cases");

        conceptButton.setOnAction(e -> showConcept());
        realLifeButton.setOnAction(e -> showRealLife());
        maxMinButton.setOnAction(e -> showMaxMin());
        insertButton.setOnAction(e -> showInsert());
        extractButton.setOnAction(e -> showExtract());
        peekButton.setOnAction(e -> showPeek());
        arrayButton.setOnAction(e -> showArrayFormula());
        edgeCasesButton.setOnAction(e -> showEdgeCases());

        HBox row1 = new HBox(12, conceptButton, realLifeButton, maxMinButton, insertButton);
        row1.setAlignment(Pos.CENTER);

        HBox row2 = new HBox(12, extractButton, peekButton, arrayButton, edgeCasesButton);
        row2.setAlignment(Pos.CENTER);

        VBox labelCard = teachingCard(
                "Visual Labels",
                "Every heap visual shows:\n" +
                        "• ROOT: highest priority in Max Heap\n" +
                        "• LEFT/RIGHT CHILD: child positions\n" +
                        "• BUBBLE UP: inserted value moves upward\n" +
                        "• BUBBLE DOWN: root replacement moves downward\n" +
                        "• ARRAY FORM: heap stored level by level"
        );

        Button startGameButton = new Button("Enter Heap Mountain");
        startGameButton.setStyle(buttonStyle(Style.COLOR_PRIMARY, "#0B1020"));
        startGameButton.setOnAction(e -> startEscapeGame());
        UiMotion.applyHoverScale(startGameButton, 1.04, 130);

        mainContent.getChildren().addAll(
                instructionLabel,
                heapBox,
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
        drawHeap("90", "50", "70", "Max Heap", "root");

        statusLabel.setText("Concept: Heap is a complete binary tree used for priority.");
        feedbackLabel.setText("Learning note: Heap looks like a tree but is usually stored in an array.");

        showCode("HEAP CONCEPT", heapsLevel.getConceptExplanation());
    }

    private void showRealLife() {
        drawHeap("Emergency", "Normal", "Low", "Priority Example", "root");

        statusLabel.setText("Real-Life Uses: Heap is used when priority matters.");
        feedbackLabel.setText("Examples include emergency room, CPU scheduling, leaderboard, and priority queue.");

        showCode("REAL-LIFE HEAP EXAMPLES", heapsLevel.getRealLifeExamples());
    }

    private void showMaxMin() {
        drawHeap("10", "30", "20", "Min Heap Example", "root");

        statusLabel.setText("Max Heap vs Min Heap");
        feedbackLabel.setText("Learning note: Max Heap root is maximum. Min Heap root is minimum.");

        showCode("MAX HEAP VS MIN HEAP", heapsLevel.getMaxMinHeapCode());
    }

    private void showInsert() {
        drawHeap("95", "90", "70", "Insert 95 / Bubble Up", "root");

        statusLabel.setText("Insert: Insert 95 into Max Heap.");
        feedbackLabel.setText("Learning note: 95 is greater than its parent, so it bubbles up to root.");

        showCode("INSERT / BUBBLE UP", heapsLevel.getInsertCode());
    }

    private void showExtract() {
        HeapsLevel demo = new HeapsLevel();
        Integer removed = demo.extractMax();

        drawHeap("70", "50", "40", "After Extract Max", "root");

        statusLabel.setText("Extract: Remove max value.");
        feedbackLabel.setText("Learning note: Extracted value is " + removed + ". Then last item moves to root and bubbles down.");

        showCode("EXTRACT / BUBBLE DOWN", heapsLevel.getExtractCode());
    }

    private void showPeek() {
        Integer max = heapsLevel.peekMax();

        drawHeap("90", "50", "70", "Peek Root", "root");

        statusLabel.setText("Peek: Read root value.");
        feedbackLabel.setText("Learning note: Peek returns " + max + " without removing it.");

        showCode("PEEK ROOT", heapsLevel.getPeekCode());
    }

    private void showArrayFormula() {
        drawHeap("90", "50", "70", "Array Form: [90, 50, 70]", "root");

        statusLabel.setText("Array Formula: Heap is stored level by level.");
        feedbackLabel.setText("Learning note: Parent and child positions can be calculated using index formulas.");

        showCode("HEAP ARRAY FORMULAS", heapsLevel.getArrayFormulaCode());
    }

    private void showEdgeCases() {
        drawHeap("90", "50", "70", "Heap Edge Cases", "root");

        statusLabel.setText("Edge Cases: Empty heap, single item heap, duplicates, and index safety.");
        feedbackLabel.setText("Learning note: Good heap code checks empty heap before peek/extract.");

        showCode("HEAP EDGE CASES", heapsLevel.getEdgeCases());
    }

    private void startEscapeGame() {
        currentRoom = 0;
        energy = 3;
        keysCollected = 0;
        showEscapeRoom();
    }

    private void showEscapeRoom() {
        roomAnswered = false;
        stageLabel.setText("Escape Mode: Heap Mountain");
        mainContent.getChildren().clear();

        Label gameTitle = new Label("Heap Mountain Escape");
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
                statCard("Mountain Locks", (5 - keysCollected) + " left", "#00D4FF")
        );

        Button optionA = puzzleButton(roomOptions[currentRoom][0]);
        Button optionB = puzzleButton(roomOptions[currentRoom][1]);
        Button optionC = puzzleButton(roomOptions[currentRoom][2]);

        optionA.setOnAction(e -> checkEscapeAnswer(0));
        optionB.setOnAction(e -> checkEscapeAnswer(1));
        optionC.setOnAction(e -> checkEscapeAnswer(2));

        HBox options = new HBox(14, optionA, optionB, optionC);
        options.setAlignment(Pos.CENTER);

        feedbackLabel.setText("Choose the correct heap action. No hints are shown before answering.");

        mainContent.getChildren().addAll(
                gameTitle,
                roomLabel,
                storyLabel,
                gameStats,
                missionLabel,
                heapBox,
                options,
                feedbackLabel
        );

        showCode(
                "MISSION CONSOLE",
                "Solve the heap room.\n\n" +
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
                Button finalButton = new Button("Open Mountain Gate");
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
        stageLabel.setText("Final Gate");

        Label title = new Label("Heap Mountain Completed");
        title.setStyle("-fx-text-fill: #00FF99; -fx-font-size: 36px; -fx-font-weight: bold;");

        Label message = new Label(
                "You collected heap keys and completed Heap Mountain."
        );
        message.setWrapText(true);
        message.setMaxWidth(900);
        message.setAlignment(Pos.CENTER);
        message.setStyle("-fx-text-fill: white; -fx-font-size: 21px;");

        drawHeap("90", "50", "70", "Max Heap", "root");

        VBox summary = teachingCard(
                "Heap Mastery Summary",
                "• Heap is a complete binary tree\n" +
                        "• Max Heap root = maximum\n" +
                        "• Min Heap root = minimum\n" +
                        "• Insert uses bubble up = O(log n)\n" +
                        "• Extract uses bubble down = O(log n)\n" +
                        "• Peek root = O(1)\n" +
                        "• Heap is useful for priority queues"
        );

        Button unlockButton = new Button("Unlock HashMap Level");
        unlockButton.setStyle(buttonStyle("#00FF99", "black"));
        unlockButton.setOnAction(e -> gameManager.completeHeapsLevel());

        mainContent.getChildren().addAll(title, message, heapBox, summary, unlockButton);

        showCode(
                "LEVEL COMPLETED",
                "Heaps completed.\n\nNext: HashMap."
        );
    }

    private void showGameOver() {
        mainContent.getChildren().clear();
        stageLabel.setText("Energy Lost");

        Label title = new Label("Energy Finished");
        title.setStyle("-fx-text-fill: #EF4444; -fx-font-size: 36px; -fx-font-weight: bold;");

        Label message = new Label(
                "Heap Mountain locked again. Review learning first or retry the mountain."
        );
        message.setWrapText(true);
        message.setMaxWidth(850);
        message.setAlignment(Pos.CENTER);
        message.setStyle("-fx-text-fill: white; -fx-font-size: 21px;");

        Button retryButton = new Button("Retry Heap Mountain");
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
                "Review heap learning content first, then try again."
        );
    }

    private void drawRoomVisual() {
        if (currentRoom == 0) {
            drawHeap("90", "50", "70", "Max Heap", "root");
        } else if (currentRoom == 1) {
            drawHeap("10", "30", "20", "Min Heap", "root");
        } else if (currentRoom == 2) {
            drawHeap("95", "90", "70", "Bubble Up", "root");
        } else if (currentRoom == 3) {
            drawHeap("70", "50", "40", "Bubble Down", "root");
        } else {
            drawHeap("Emergency", "Normal", "Low", "Priority Example", "root");
        }
    }

    private void drawHeap(String root, String left, String right, String caption, String highlightValue) {
        heapBox.getChildren().clear();

        Label captionLabel = new Label(caption);
        captionLabel.setStyle("-fx-text-fill: #00FF99; -fx-font-size: 18px; -fx-font-weight: bold;");

        HBox level1 = new HBox();
        level1.setAlignment(Pos.CENTER);
        level1.getChildren().add(heapNode(root, "ROOT", root.equals(highlightValue) || "root".equals(highlightValue)));

        Label connectors = new Label("        /                    \\");
        connectors.setStyle("-fx-text-fill: #00D4FF; -fx-font-size: 25px; -fx-font-weight: bold;");

        HBox level2 = new HBox(120);
        level2.setAlignment(Pos.CENTER);
        level2.getChildren().addAll(
                heapNode(left, "LEFT CHILD", left.equals(highlightValue)),
                heapNode(right, "RIGHT CHILD", right.equals(highlightValue))
        );

        Label arrayForm = new Label("Array Form: [" + root + ", " + left + ", " + right + "]");
        arrayForm.setStyle("-fx-text-fill: #CBD5E1; -fx-font-size: 16px;");

        heapBox.getChildren().addAll(captionLabel, level1, connectors, level2, arrayForm);
    }

    private VBox heapNode(String value, String label, boolean highlight) {
        VBox node = new VBox(5);
        node.setAlignment(Pos.CENTER);

        Label valueLabel = new Label(value);
        valueLabel.setStyle(
                "-fx-text-fill: white;" +
                        "-fx-font-size: 24px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-min-width: 115px;" +
                        "-fx-min-height: 58px;" +
                        "-fx-alignment: center;" +
                        "-fx-background-color: " + (highlight ? "#0EA5E9" : "#1E293B") + ";" +
                        "-fx-border-color: #00D4FF;" +
                        "-fx-border-width: 2;" +
                        "-fx-background-radius: 30;" +
                        "-fx-border-radius: 30;"
        );

        Label typeLabel = new Label(label);
        typeLabel.setStyle("-fx-text-fill: #CBD5E1; -fx-font-size: 13px;");

        node.getChildren().addAll(valueLabel, typeLabel);
        return node;
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