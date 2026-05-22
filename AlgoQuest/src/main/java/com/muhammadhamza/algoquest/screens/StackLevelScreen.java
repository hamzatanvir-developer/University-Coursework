package com.muhammadhamza.algoquest.screens;

import com.muhammadhamza.algoquest.GameManager;
import com.muhammadhamza.algoquest.levels.StackLevel;
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

public class StackLevelScreen {

    private final BorderPane view;
    private final GameManager gameManager;
    private final StackLevel stackLevel;

    private Label stageLabel;
    private Label instructionLabel;
    private Label feedbackLabel;
    private TextArea codeLabel;
    private Label statusLabel;

    private VBox stackBox;
    private VBox mainContent;

    private int currentRoom = 0;
    private int energy = 3;
    private int keysCollected = 0;
    private boolean roomAnswered = false;

    private final String[] roomNames = {
            "LIFO Gate",
            "Push Lift",
            "Pop Trap",
            "Peek Window",
            "Underflow Door"
    };

    private final String[] roomStories = {
            "A tower gate is locked. It opens only if you know which item leaves first.",
            "A lift can move upward only when a new value is pushed correctly.",
            "A trap removes one block from the top. Choose which value gets removed.",
            "A glass window lets you see the top item without removing it.",
            "The final door checks what happens when stack is empty."
    };

    private final String[] roomQuestions = {
            "Stack has TOP → [30] [20] [10]. If pop is called, which value comes out first?",
            "Stack has TOP → [30] [20] [10]. After push 40, what is the new TOP?",
            "Stack has TOP → [40] [30] [20] [10]. After pop, what remains on TOP?",
            "Stack has TOP → [30] [20] [10]. What does peek return?",
            "What happens if pop is called on an empty stack?"
    };

    private final String[][] roomOptions = {
            {"10", "20", "30"},
            {"10", "30", "40"},
            {"40", "30", "20"},
            {"30 and removes it", "30 without removing it", "10 without removing it"},
            {"Stack Overflow", "Stack Underflow", "Collision"}
    };

    private final int[] correctAnswers = {2, 2, 1, 1, 1};

    private final String[] correctMessages = {
            "Correct! Stack removes the TOP item first, so 30 comes out.",
            "Correct! Push adds the new value at TOP, so 40 becomes TOP.",
            "Correct! Pop removes 40, so 30 becomes the new TOP.",
            "Correct! Peek returns TOP value without removing it.",
            "Correct! Popping from an empty stack causes underflow."
    };

    private final String[] wrongMessages = {
            "Stack follows LIFO. Last inserted item is removed first.",
            "Push always adds the new item at the TOP.",
            "After popping 40, the next item below it becomes TOP.",
            "Peek only reads the TOP item. It does not remove it.",
            "Empty stack has no item to remove. That problem is called underflow."
    };

    public StackLevelScreen(GameManager gameManager) {
        this.gameManager = gameManager;
        this.stackLevel = new StackLevel();

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
        Label titleLabel = new Label("Level 3: Stack Tower");
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

        stackBox = new VBox(8);
        stackBox.setAlignment(Pos.CENTER);
        stackBox.setPadding(new Insets(20));
        stackBox.setMinWidth(380);
        stackBox.setStyle(
            "-fx-background-color: " + Style.COLOR_CARD_DARK + ";" +
                "-fx-border-color: " + Style.COLOR_ACCENT + ";" +
                        "-fx-border-width: 2;" +
                        "-fx-background-radius: 16;" +
                        "-fx-border-radius: 16;"
        );
        UiMotion.applyHoverScale(stackBox, 1.02, 160);

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

        codeLabel = new TextArea("Click a learning button to view details from StackLevel.java.");
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
        stageLabel.setText("Learning Mode: Understand Stack First");
        mainContent.getChildren().clear();

        instructionLabel.setText(
                "This is the learning area. The screen shows stack visuals and buttons. The actual stack logic and explanations come from StackLevel.java."
        );

        drawStack(stackLevel.getVisibleStackTopToBottom(), 0);

        Button conceptButton = operationButton("Concept");
        Button realLifeButton = operationButton("Real-Life Uses");
        Button pushButton = operationButton("Push");
        Button popButton = operationButton("Pop");
        Button peekButton = operationButton("Peek");
        Button displayButton = operationButton("Display");
        Button bracketsButton = operationButton("Bracket Matching");
        Button undoButton = operationButton("Undo Example");
        Button edgeCasesButton = operationButton("Edge Cases");

        conceptButton.setOnAction(e -> showConcept());
        realLifeButton.setOnAction(e -> showRealLife());
        pushButton.setOnAction(e -> showPush());
        popButton.setOnAction(e -> showPop());
        peekButton.setOnAction(e -> showPeek());
        displayButton.setOnAction(e -> showDisplay());
        bracketsButton.setOnAction(e -> showBracketMatching());
        undoButton.setOnAction(e -> showUndo());
        edgeCasesButton.setOnAction(e -> showEdgeCases());

        HBox row1 = new HBox(12, conceptButton, realLifeButton, pushButton);
        row1.setAlignment(Pos.CENTER);

        HBox row2 = new HBox(12, popButton, peekButton, displayButton);
        row2.setAlignment(Pos.CENTER);

        HBox row3 = new HBox(12, bracketsButton, undoButton, edgeCasesButton);
        row3.setAlignment(Pos.CENTER);

        VBox labelCard = teachingCard(
                "Visual Labels",
                "Every stack visual shows:\n" +
                        "• TOP: latest inserted item\n" +
                        "• VALUE: item stored in stack\n" +
                        "• BASE: bottom of stack\n" +
                        "• LIFO: last value goes out first"
        );

        Button startGameButton = new Button("Enter Stack Tower");
        startGameButton.setStyle(buttonStyle(Style.COLOR_PRIMARY, "#0B1020"));
        startGameButton.setOnAction(e -> startEscapeGame());
        UiMotion.applyHoverScale(startGameButton, 1.04, 130);

        mainContent.getChildren().addAll(
                instructionLabel,
                stackBox,
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
        drawStack(stackLevel.getVisibleStackTopToBottom(), 0);

        statusLabel.setText("Concept: Stack follows LIFO.");
        feedbackLabel.setText("Learning note: LIFO means Last In, First Out.");

        showCode("STACK CONCEPT", stackLevel.getConceptExplanation());
    }

    private void showRealLife() {
        drawStack(stackLevel.getVisibleStackTopToBottom(), 0);

        statusLabel.setText("Real-Life Uses: Stack is useful when the last action must happen first.");
        feedbackLabel.setText("Examples include undo, browser back, function calls, and bracket matching.");

        showCode("REAL-LIFE STACK EXAMPLES", stackLevel.getRealLifeExamples());
    }

    private void showPush() {
        StackLevel demo = new StackLevel();
        demo.push(40);

        drawStack(demo.getVisibleStackTopToBottom(), 0);

        statusLabel.setText("Push: Add new value at TOP.");
        feedbackLabel.setText("Learning note: Push increases top and stores the value at stack[top].");

        showCode("PUSH OPERATION", stackLevel.getPushCode());
    }

    private void showPop() {
        StackLevel demo = new StackLevel();
        Integer removed = demo.pop();

        drawStack(demo.getVisibleStackTopToBottom(), 0);

        statusLabel.setText("Pop: Remove the TOP item.");
        feedbackLabel.setText("Learning note: Popped value is " + removed + ". Then top moves one step down.");

        showCode("POP OPERATION", stackLevel.getPopCode());
    }

    private void showPeek() {
        Integer topValue = stackLevel.peek();

        drawStack(stackLevel.getVisibleStackTopToBottom(), 0);

        statusLabel.setText("Peek: Read TOP item.");
        feedbackLabel.setText("Learning note: Peek returns " + topValue + " without removing it.");

        showCode("PEEK OPERATION", stackLevel.getPeekCode());
    }

    private void showDisplay() {
        drawStack(stackLevel.getVisibleStackTopToBottom(), -1);

        statusLabel.setText("Display: Show stack from TOP to bottom.");
        feedbackLabel.setText("Learning note: Display visits each stack item once.");

        showCode("DISPLAY STACK", stackLevel.getDisplayCode());
    }

    private void showBracketMatching() {
        drawStackText(new String[]{"{", "[", "("}, 0);

        statusLabel.setText("Bracket Matching: Stack validates brackets.");
        feedbackLabel.setText("Learning note: Opening brackets are pushed, closing brackets pop.");

        showCode("BRACKET MATCHING", stackLevel.getBracketMatchingCode());
    }

    private void showUndo() {
        drawStackText(new String[]{"Typed C", "Typed B", "Typed A"}, 0);

        statusLabel.setText("Undo Example: Last action is undone first.");
        feedbackLabel.setText("Learning note: If you typed A, B, C, undo removes C first.");

        showCode("UNDO EXAMPLE", stackLevel.getUndoCode());
    }

    private void showEdgeCases() {
        drawStack(stackLevel.getVisibleStackTopToBottom(), 0);

        statusLabel.setText("Edge Cases: Overflow, underflow, and empty stack.");
        feedbackLabel.setText("Learning note: Check isFull before push and isEmpty before pop/peek.");

        showCode("STACK EDGE CASES", stackLevel.getEdgeCases());
    }

    private void startEscapeGame() {
        currentRoom = 0;
        energy = 3;
        keysCollected = 0;
        showEscapeRoom();
    }

    private void showEscapeRoom() {
        roomAnswered = false;
        stageLabel.setText("Escape Mode: Stack Tower");
        mainContent.getChildren().clear();

        Label gameTitle = new Label("Stack Tower Escape");
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
                statCard("Tower Locks", (5 - keysCollected) + " left", "#00D4FF")
        );

        Button optionA = puzzleButton(roomOptions[currentRoom][0]);
        Button optionB = puzzleButton(roomOptions[currentRoom][1]);
        Button optionC = puzzleButton(roomOptions[currentRoom][2]);

        optionA.setOnAction(e -> checkEscapeAnswer(0));
        optionB.setOnAction(e -> checkEscapeAnswer(1));
        optionC.setOnAction(e -> checkEscapeAnswer(2));

        HBox options = new HBox(14, optionA, optionB, optionC);
        options.setAlignment(Pos.CENTER);

        feedbackLabel.setText("Choose the correct stack action. No hints are shown before answering.");

        mainContent.getChildren().addAll(
                gameTitle,
                roomLabel,
                storyLabel,
                gameStats,
                missionLabel,
                stackBox,
                options,
                feedbackLabel
        );

        showCode(
                "MISSION CONSOLE",
                "Solve the stack room.\n\n" +
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
                Button finalButton = new Button("Open Final Door");
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
        stageLabel.setText("Final Door");

        Label title = new Label("Stack Tower Completed");
        title.setStyle("-fx-text-fill: #00FF99; -fx-font-size: 36px; -fx-font-weight: bold;");

        Label message = new Label(
                "You collected stack keys and completed the Stack Tower."
        );
        message.setWrapText(true);
        message.setMaxWidth(900);
        message.setAlignment(Pos.CENTER);
        message.setStyle("-fx-text-fill: white; -fx-font-size: 21px;");

        drawStack(new int[]{30, 20, 10}, 0);

        VBox summary = teachingCard(
                "Stack Mastery Summary",
                "• Stack follows LIFO\n" +
                        "• Push adds at TOP\n" +
                        "• Pop removes from TOP\n" +
                        "• Peek reads TOP only\n" +
                        "• Push/Pop/Peek = O(1)\n" +
                        "• Empty pop causes underflow"
        );

        Button unlockButton = new Button("Unlock Queue Level");
        unlockButton.setStyle(buttonStyle("#00FF99", "black"));
        unlockButton.setOnAction(e -> gameManager.completeStackLevel());

        mainContent.getChildren().addAll(title, message, stackBox, summary, unlockButton);

        showCode(
                "LEVEL COMPLETED",
                "Stack completed.\n\nNext: Queue."
        );
    }

    private void showGameOver() {
        mainContent.getChildren().clear();
        stageLabel.setText("Energy Lost");

        Label title = new Label("Energy Finished");
        title.setStyle("-fx-text-fill: #EF4444; -fx-font-size: 36px; -fx-font-weight: bold;");

        Label message = new Label(
                "Stack Tower locked again. Review learning first or retry the tower."
        );
        message.setWrapText(true);
        message.setMaxWidth(850);
        message.setAlignment(Pos.CENTER);
        message.setStyle("-fx-text-fill: white; -fx-font-size: 21px;");

        Button retryButton = new Button("Retry Stack Tower");
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
                "Review stack learning content first, then try again."
        );
    }

    private void drawRoomVisual() {
        if (currentRoom == 0) {
            drawStack(new int[]{30, 20, 10}, 0);
        } else if (currentRoom == 1) {
            drawStack(new int[]{40, 30, 20, 10}, 0);
        } else if (currentRoom == 2) {
            drawStack(new int[]{40, 30, 20, 10}, 0);
        } else if (currentRoom == 3) {
            drawStack(new int[]{30, 20, 10}, 0);
        } else {
            drawStack(new int[]{}, -1);
        }
    }

    private void drawStack(int[] valuesTopToBottom, int highlightIndex) {
        stackBox.getChildren().clear();

        Label topLabel = new Label("TOP");
        topLabel.setStyle(pointerLabelStyle("#00FF99"));
        stackBox.getChildren().add(topLabel);

        if (valuesTopToBottom.length == 0) {
            Label emptyLabel = new Label("[ Empty Stack ]");
            emptyLabel.setStyle("-fx-text-fill: #CBD5E1; -fx-font-size: 22px;");
            stackBox.getChildren().add(emptyLabel);
            return;
        }

        for (int i = 0; i < valuesTopToBottom.length; i++) {
            Label valueLabel = new Label(String.valueOf(valuesTopToBottom[i]));
            valueLabel.setStyle(stackCellStyle(i == highlightIndex));
            stackBox.getChildren().add(valueLabel);
        }

        Label baseLabel = new Label("STACK BASE");
        baseLabel.setStyle("-fx-text-fill: #CBD5E1; -fx-font-size: 15px; -fx-font-weight: bold;");
        stackBox.getChildren().add(baseLabel);
    }

    private void drawStackText(String[] valuesTopToBottom, int highlightIndex) {
        stackBox.getChildren().clear();

        Label topLabel = new Label("TOP");
        topLabel.setStyle(pointerLabelStyle("#00FF99"));
        stackBox.getChildren().add(topLabel);

        for (int i = 0; i < valuesTopToBottom.length; i++) {
            Label valueLabel = new Label(valuesTopToBottom[i]);
            valueLabel.setStyle(stackCellStyle(i == highlightIndex));
            stackBox.getChildren().add(valueLabel);
        }

        Label baseLabel = new Label("STACK BASE");
        baseLabel.setStyle("-fx-text-fill: #CBD5E1; -fx-font-size: 15px; -fx-font-weight: bold;");
        stackBox.getChildren().add(baseLabel);
    }

    private String stackCellStyle(boolean highlight) {
        return "-fx-text-fill: white;" +
                "-fx-font-size: 24px;" +
                "-fx-font-weight: bold;" +
                "-fx-min-width: 220px;" +
                "-fx-min-height: 54px;" +
                "-fx-alignment: center;" +
                "-fx-background-color: " + (highlight ? "#0EA5E9" : "#1E293B") + ";" +
                "-fx-border-color: #00D4FF;" +
                "-fx-border-width: 2;" +
                "-fx-background-radius: 12;" +
                "-fx-border-radius: 12;";
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

    private String pointerLabelStyle(String color) {
        return "-fx-text-fill: " + color + ";" +
                "-fx-font-size: 17px;" +
                "-fx-font-weight: bold;" +
                "-fx-padding: 9;" +
                "-fx-background-color: #111827;" +
                "-fx-background-radius: 8;" +
                "-fx-border-color: " + color + ";" +
                "-fx-border-radius: 8;";
    }

    public BorderPane getView() {
        return view;
    }
}