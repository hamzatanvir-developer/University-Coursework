package com.muhammadhamza.algoquest.screens;

import com.muhammadhamza.algoquest.GameManager;
import com.muhammadhamza.algoquest.levels.QueueLevel;
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

public class QueueLevelScreen {

    private final BorderPane view;
    private final GameManager gameManager;
    private final QueueLevel queueLevel;

    private Label stageLabel;
    private Label instructionLabel;
    private Label feedbackLabel;
    private TextArea codeLabel;
    private Label statusLabel;

    private HBox queueBox;
    private VBox mainContent;

    private int currentRoom = 0;
    private int energy = 3;
    private int keysCollected = 0;
    private boolean roomAnswered = false;

    private final String[] roomNames = {
            "FIFO Gate",
            "Enqueue Dock",
            "Dequeue Bridge",
            "Peek Window",
            "Circular Portal"
    };

    private final String[] roomStories = {
            "The gate opens only for the first person in line. Understand FIFO to pass.",
            "A new traveler must enter the queue from the correct side.",
            "The bridge removes the first item waiting at the front.",
            "A glass window shows the first item, but it must not remove it.",
            "The final portal wraps around to reuse empty spaces in a fixed queue."
    };

    private final String[] roomQuestions = {
            "Queue has FRONT → [5] [10] [15] ← REAR. If dequeue is called, which value leaves first?",
            "Queue has FRONT → [5] [10] ← REAR. After enqueue 15, where does 15 go?",
            "Queue has FRONT → [5] [10] [15] ← REAR. After dequeue, what is the new FRONT?",
            "Queue has FRONT → [5] [10] [15] ← REAR. What does peek return?",
            "Which queue reuses empty spaces by wrapping rear/front using modulo?"
    };

    private final String[][] roomOptions = {
            {"5", "10", "15"},
            {"At FRONT", "At REAR", "In the middle"},
            {"5", "10", "15"},
            {"5 without removing it", "5 and removes it", "15 without removing it"},
            {"Simple Queue", "Circular Queue", "Stack"}
    };

    private final int[] correctAnswers = {0, 1, 1, 0, 1};

    private final String[] correctMessages = {
            "Correct! Queue removes the FRONT value first.",
            "Correct! Enqueue adds the new value at REAR.",
            "Correct! After removing 5, value 10 becomes the new FRONT.",
            "Correct! Peek returns FRONT value without removing it.",
            "Correct! Circular Queue wraps around and reuses empty spaces."
    };

    private final String[] wrongMessages = {
            "Queue follows FIFO. First inserted value leaves first.",
            "Enqueue always inserts at the REAR side.",
            "After dequeue, the next value becomes FRONT.",
            "Peek only reads the FRONT value. It does not remove it.",
            "Circular Queue uses modulo to wrap around."
    };

    public QueueLevelScreen(GameManager gameManager) {
        this.gameManager = gameManager;
        this.queueLevel = new QueueLevel();

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
        Label titleLabel = new Label("Level 4: Queue Gate");
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

        queueBox = new HBox(10);
        queueBox.setAlignment(Pos.CENTER);
        queueBox.setPadding(new Insets(20));
        queueBox.setMinHeight(155);
        queueBox.setStyle(
            "-fx-background-color: " + Style.COLOR_CARD_DARK + ";" +
                "-fx-border-color: " + Style.COLOR_ACCENT + ";" +
                        "-fx-border-width: 2;" +
                        "-fx-background-radius: 16;" +
                        "-fx-border-radius: 16;"
        );
        UiMotion.applyHoverScale(queueBox, 1.02, 160);

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

        codeLabel = new TextArea("Click a learning button to view details from QueueLevel.java.");
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
        stageLabel.setText("Learning Mode: Understand Queue First");
        mainContent.getChildren().clear();

        instructionLabel.setText(
                "This is the learning area. The screen shows queue visuals and buttons. The actual queue logic and explanations come from QueueLevel.java."
        );

        int[] visible = queueLevel.getVisibleQueueFrontToRear();
        drawQueue(visible, 0, visible.length - 1);

        Button conceptButton = operationButton("Concept");
        Button realLifeButton = operationButton("Real-Life Uses");
        Button enqueueButton = operationButton("Enqueue");
        Button dequeueButton = operationButton("Dequeue");
        Button peekButton = operationButton("Peek");
        Button displayButton = operationButton("Display");
        Button circularButton = operationButton("Circular Queue");
        Button edgeCasesButton = operationButton("Edge Cases");

        conceptButton.setOnAction(e -> showConcept());
        realLifeButton.setOnAction(e -> showRealLife());
        enqueueButton.setOnAction(e -> showEnqueue());
        dequeueButton.setOnAction(e -> showDequeue());
        peekButton.setOnAction(e -> showPeek());
        displayButton.setOnAction(e -> showDisplay());
        circularButton.setOnAction(e -> showCircular());
        edgeCasesButton.setOnAction(e -> showEdgeCases());

        HBox row1 = new HBox(12, conceptButton, realLifeButton, enqueueButton, dequeueButton);
        row1.setAlignment(Pos.CENTER);

        HBox row2 = new HBox(12, peekButton, displayButton, circularButton, edgeCasesButton);
        row2.setAlignment(Pos.CENTER);

        VBox labelCard = teachingCard(
                "Visual Labels",
                "Every queue visual shows:\n" +
                        "• FRONT: item removed first\n" +
                        "• REAR: side where new item is added\n" +
                        "• FIFO: first item goes out first\n" +
                        "• Circular Queue: reuses empty spaces using modulo"
        );

        Button startGameButton = new Button("Enter Queue Gate");
        startGameButton.setStyle(buttonStyle(Style.COLOR_PRIMARY, "#0B1020"));
        startGameButton.setOnAction(e -> startEscapeGame());
        UiMotion.applyHoverScale(startGameButton, 1.04, 130);

        mainContent.getChildren().addAll(
                instructionLabel,
                queueBox,
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
        int[] visible = queueLevel.getVisibleQueueFrontToRear();
        drawQueue(visible, 0, visible.length - 1);

        statusLabel.setText("Concept: Queue follows FIFO.");
        feedbackLabel.setText("Learning note: FIFO means First In, First Out.");

        showCode("QUEUE CONCEPT", queueLevel.getConceptExplanation());
    }

    private void showRealLife() {
        int[] visible = queueLevel.getVisibleQueueFrontToRear();
        drawQueue(visible, 0, visible.length - 1);

        statusLabel.setText("Real-Life Uses: Queue is useful when first arrival should be served first.");
        feedbackLabel.setText("Examples include ticket lines, printer jobs, support calls, and scheduling.");

        showCode("REAL-LIFE QUEUE EXAMPLES", queueLevel.getRealLifeExamples());
    }

    private void showEnqueue() {
        QueueLevel demo = new QueueLevel();
        demo.enqueue(20);

        int[] visible = demo.getVisibleQueueFrontToRear();
        drawQueue(visible, 0, visible.length - 1);

        statusLabel.setText("Enqueue: Add new value at REAR.");
        feedbackLabel.setText("Learning note: Enqueue moves rear forward and stores the value at queue[rear].");

        showCode("ENQUEUE OPERATION", queueLevel.getEnqueueCode());
    }

    private void showDequeue() {
        QueueLevel demo = new QueueLevel();
        Integer removed = demo.dequeue();

        int[] visible = demo.getVisibleQueueFrontToRear();
        drawQueue(visible, 0, visible.length - 1);

        statusLabel.setText("Dequeue: Remove FRONT item.");
        feedbackLabel.setText("Learning note: Dequeued value is " + removed + ". Then front moves one step forward.");

        showCode("DEQUEUE OPERATION", queueLevel.getDequeueCode());
    }

    private void showPeek() {
        Integer frontValue = queueLevel.peek();

        int[] visible = queueLevel.getVisibleQueueFrontToRear();
        drawQueue(visible, 0, visible.length - 1);

        statusLabel.setText("Peek: Read FRONT item.");
        feedbackLabel.setText("Learning note: Peek returns " + frontValue + " without removing it.");

        showCode("PEEK OPERATION", queueLevel.getPeekCode());
    }

    private void showDisplay() {
        int[] visible = queueLevel.getVisibleQueueFrontToRear();
        drawQueue(visible, -1, -1);

        statusLabel.setText("Display: Show queue from FRONT to REAR.");
        feedbackLabel.setText("Learning note: Display visits each queue item once.");

        showCode("DISPLAY QUEUE", queueLevel.getDisplayCode());
    }

    private void showCircular() {
        drawQueueText(new String[]{"30", "40", "10", "20"}, 2, 1);

        statusLabel.setText("Circular Queue: Reuse empty spaces.");
        feedbackLabel.setText("Learning note: Circular Queue wraps rear/front using modulo.");

        showCode("CIRCULAR QUEUE", queueLevel.getCircularQueueCode());
    }

    private void showEdgeCases() {
        int[] visible = queueLevel.getVisibleQueueFrontToRear();
        drawQueue(visible, 0, visible.length - 1);

        statusLabel.setText("Edge Cases: Overflow, underflow, and empty queue.");
        feedbackLabel.setText("Learning note: Check isFull before enqueue and isEmpty before dequeue/peek.");

        showCode("QUEUE EDGE CASES", queueLevel.getEdgeCases());
    }

    private void startEscapeGame() {
        currentRoom = 0;
        energy = 3;
        keysCollected = 0;
        showEscapeRoom();
    }

    private void showEscapeRoom() {
        roomAnswered = false;
        stageLabel.setText("Escape Mode: Queue Gate");
        mainContent.getChildren().clear();

        Label gameTitle = new Label("Queue Gate Escape");
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
                statCard("Gate Locks", (5 - keysCollected) + " left", "#00D4FF")
        );

        Button optionA = puzzleButton(roomOptions[currentRoom][0]);
        Button optionB = puzzleButton(roomOptions[currentRoom][1]);
        Button optionC = puzzleButton(roomOptions[currentRoom][2]);

        optionA.setOnAction(e -> checkEscapeAnswer(0));
        optionB.setOnAction(e -> checkEscapeAnswer(1));
        optionC.setOnAction(e -> checkEscapeAnswer(2));

        HBox options = new HBox(14, optionA, optionB, optionC);
        options.setAlignment(Pos.CENTER);

        feedbackLabel.setText("Choose the correct queue action. No hints are shown before answering.");

        mainContent.getChildren().addAll(
                gameTitle,
                roomLabel,
                storyLabel,
                gameStats,
                missionLabel,
                queueBox,
                options,
                feedbackLabel
        );

        showCode(
                "MISSION CONSOLE",
                "Solve the queue room.\n\n" +
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

        Label title = new Label("Queue Gate Opened");
        title.setStyle("-fx-text-fill: #00FF99; -fx-font-size: 36px; -fx-font-weight: bold;");

        Label message = new Label(
                "You collected queue keys and completed the Queue Gate."
        );
        message.setWrapText(true);
        message.setMaxWidth(900);
        message.setAlignment(Pos.CENTER);
        message.setStyle("-fx-text-fill: white; -fx-font-size: 21px;");

        drawQueue(new int[]{5, 10, 15}, 0, 2);

        VBox summary = teachingCard(
                "Queue Mastery Summary",
                "• Queue follows FIFO\n" +
                        "• Enqueue adds at REAR\n" +
                        "• Dequeue removes from FRONT\n" +
                        "• Peek reads FRONT only\n" +
                        "• Enqueue/Dequeue/Peek = O(1)\n" +
                        "• Circular Queue reuses empty spaces"
        );

        Button unlockButton = new Button("Unlock Trees Level");
        unlockButton.setStyle(buttonStyle("#00FF99", "black"));
        unlockButton.setOnAction(e -> gameManager.completeQueueLevel());

        mainContent.getChildren().addAll(title, message, queueBox, summary, unlockButton);

        showCode(
                "LEVEL COMPLETED",
                "Queue completed.\n\nNext: Trees."
        );
    }

    private void showGameOver() {
        mainContent.getChildren().clear();
        stageLabel.setText("Energy Lost");

        Label title = new Label("Energy Finished");
        title.setStyle("-fx-text-fill: #EF4444; -fx-font-size: 36px; -fx-font-weight: bold;");

        Label message = new Label(
                "Queue Gate locked again. Review learning first or retry the gate."
        );
        message.setWrapText(true);
        message.setMaxWidth(850);
        message.setAlignment(Pos.CENTER);
        message.setStyle("-fx-text-fill: white; -fx-font-size: 21px;");

        Button retryButton = new Button("Retry Queue Gate");
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
                "Review queue learning content first, then try again."
        );
    }

    private void drawRoomVisual() {
        if (currentRoom == 4) {
            drawQueueText(new String[]{"30", "40", "10", "20"}, 2, 1);
        } else {
            drawQueue(new int[]{5, 10, 15}, 0, 2);
        }
    }

    private void drawQueue(int[] values, int frontIndex, int rearIndex) {
        queueBox.getChildren().clear();

        Label frontLabel = new Label("FRONT");
        frontLabel.setStyle(pointerLabelStyle("#00FF99"));
        queueBox.getChildren().add(frontLabel);

        Label frontArrow = new Label("→");
        frontArrow.setStyle("-fx-text-fill: #00D4FF; -fx-font-size: 30px; -fx-font-weight: bold;");
        queueBox.getChildren().add(frontArrow);

        if (values.length == 0) {
            Label emptyLabel = new Label("[ Empty Queue ]");
            emptyLabel.setStyle("-fx-text-fill: #CBD5E1; -fx-font-size: 22px;");
            queueBox.getChildren().add(emptyLabel);
        }

        for (int i = 0; i < values.length; i++) {
            VBox cell = new VBox(6);
            cell.setAlignment(Pos.CENTER);

            String label;
            if (i == frontIndex) {
                label = "front";
            } else if (i == rearIndex) {
                label = "rear";
            } else {
                label = "item";
            }

            Label valueLabel = new Label(String.valueOf(values[i]));
            valueLabel.setStyle(queueCellStyle(i == frontIndex || i == rearIndex));

            Label indexLabel = new Label(label);
            indexLabel.setStyle("-fx-text-fill: #CBD5E1; -fx-font-size: 14px;");

            cell.getChildren().addAll(valueLabel, indexLabel);
            queueBox.getChildren().add(cell);

            if (i < values.length - 1) {
                Label arrow = new Label("→");
                arrow.setStyle("-fx-text-fill: #00D4FF; -fx-font-size: 30px; -fx-font-weight: bold;");
                queueBox.getChildren().add(arrow);
            }
        }

        Label rearArrow = new Label("←");
        rearArrow.setStyle("-fx-text-fill: #00D4FF; -fx-font-size: 30px; -fx-font-weight: bold;");
        queueBox.getChildren().add(rearArrow);

        Label rearLabel = new Label("REAR");
        rearLabel.setStyle(pointerLabelStyle("#FACC15"));
        queueBox.getChildren().add(rearLabel);
    }

    private void drawQueueText(String[] values, int frontIndex, int rearIndex) {
        queueBox.getChildren().clear();

        Label frontLabel = new Label("FRONT");
        frontLabel.setStyle(pointerLabelStyle("#00FF99"));
        queueBox.getChildren().add(frontLabel);

        Label frontArrow = new Label("→");
        frontArrow.setStyle("-fx-text-fill: #00D4FF; -fx-font-size: 30px; -fx-font-weight: bold;");
        queueBox.getChildren().add(frontArrow);

        for (int i = 0; i < values.length; i++) {
            VBox cell = new VBox(6);
            cell.setAlignment(Pos.CENTER);

            String label;
            if (i == frontIndex) {
                label = "front";
            } else if (i == rearIndex) {
                label = "rear";
            } else {
                label = "item";
            }

            Label valueLabel = new Label(values[i]);
            valueLabel.setStyle(queueCellStyle(i == frontIndex || i == rearIndex));

            Label indexLabel = new Label(label);
            indexLabel.setStyle("-fx-text-fill: #CBD5E1; -fx-font-size: 14px;");

            cell.getChildren().addAll(valueLabel, indexLabel);
            queueBox.getChildren().add(cell);

            if (i < values.length - 1) {
                Label arrow = new Label("→");
                arrow.setStyle("-fx-text-fill: #00D4FF; -fx-font-size: 30px; -fx-font-weight: bold;");
                queueBox.getChildren().add(arrow);
            }
        }

        Label rearArrow = new Label("←");
        rearArrow.setStyle("-fx-text-fill: #00D4FF; -fx-font-size: 30px; -fx-font-weight: bold;");
        queueBox.getChildren().add(rearArrow);

        Label rearLabel = new Label("REAR");
        rearLabel.setStyle(pointerLabelStyle("#FACC15"));
        queueBox.getChildren().add(rearLabel);
    }

    private String queueCellStyle(boolean highlight) {
        return "-fx-text-fill: white;" +
                "-fx-font-size: 24px;" +
                "-fx-font-weight: bold;" +
                "-fx-min-width: 96px;" +
                "-fx-min-height: 60px;" +
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