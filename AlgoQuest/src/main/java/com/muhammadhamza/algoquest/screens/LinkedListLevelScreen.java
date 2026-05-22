package com.muhammadhamza.algoquest.screens;

import com.muhammadhamza.algoquest.GameManager;
import com.muhammadhamza.algoquest.levels.LinkedListLevel;
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

public class LinkedListLevelScreen {

    private final BorderPane view;
    private final GameManager gameManager;
    private final LinkedListLevel linkedListLevel;

    private Label stageLabel;
    private Label instructionLabel;
    private Label feedbackLabel;
    private TextArea codeLabel;
    private Label statusLabel;

    private HBox listBox;
    private VBox mainContent;

    private int currentRoom = 0;
    private int energy = 3;
    private int keysCollected = 0;
    private boolean roomAnswered = false;

    private final String[] roomNames = {
            "Head Gate",
            "Node Bridge",
            "Insert Chamber",
            "Delete Tunnel",
            "Pointer Exit"
    };

    private final String[] roomStories = {
            "The maze begins at HEAD. To open the first gate, identify where the linked list starts.",
            "A broken path shows disconnected nodes. Understand which part connects nodes.",
            "A new node must be inserted between two existing nodes to repair the bridge.",
            "One wrong node blocks the tunnel. Delete it by fixing pointer connections.",
            "The exit door opens only if you understand NULL at the end of the list."
    };

    private final String[] roomQuestions = {
            "In HEAD → 5 → 10 → 15 → NULL, what does HEAD point to?",
            "Which part of a node connects it to the next node?",
            "Insert 8 after 5 in 5 → 10 → 15. What is the correct list?",
            "Delete 10 from 5 → 8 → 10 → 15. What is the correct list?",
            "What does NULL mean at the end of a linked list?"
    };

    private final String[][] roomOptions = {
            {"5", "10", "NULL"},
            {"data", "next pointer", "index"},
            {"5 → 8 → 10 → 15", "8 → 5 → 10 → 15", "5 → 10 → 8 → 15"},
            {"5 → 8 → 15", "5 → 10 → 15", "8 → 10 → 15"},
            {"Start of list", "End of list / no next node", "Middle node"}
    };

    private final int[] correctAnswers = {0, 1, 0, 0, 1};

    private final String[] correctMessages = {
            "Correct! HEAD points to the first node.",
            "Correct! The next pointer connects one node to the next node.",
            "Correct! 8 is placed after 5, then it points to 10.",
            "Correct! 10 is removed, and 8 now points directly to 15.",
            "Correct! NULL means there is no next node."
    };

    private final String[] wrongMessages = {
            "HEAD always points to the first node of the linked list.",
            "A node has data and next. The next pointer creates the chain.",
            "Insert after 5 means 5 points to 8, and 8 points to 10.",
            "Deleting 10 means the previous node skips 10 and points to 15.",
            "NULL means the linked list has ended."
    };

    public LinkedListLevelScreen(GameManager gameManager) {
        this.gameManager = gameManager;
        this.linkedListLevel = new LinkedListLevel();

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
        Label titleLabel = new Label("Level 2: Linked List Path");
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

        listBox = new HBox(10);
        listBox.setAlignment(Pos.CENTER);
        listBox.setPadding(new Insets(20));
        listBox.setStyle(
            "-fx-background-color: " + Style.COLOR_CARD_DARK + ";" +
                "-fx-border-color: " + Style.COLOR_ACCENT + ";" +
                        "-fx-border-width: 2;" +
                        "-fx-background-radius: 16;" +
                        "-fx-border-radius: 16;"
        );
        UiMotion.applyHoverScale(listBox, 1.02, 160);

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

        codeLabel = new TextArea("Click a learning button to view details from LinkedListLevel.java.");
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
        stageLabel.setText("Learning Mode: Understand Linked List First");
        mainContent.getChildren().clear();

        instructionLabel.setText(
                "This is the learning area. The screen shows node visuals and buttons. The actual linked list logic and explanations come from LinkedListLevel.java."
        );

        drawLinkedList(linkedListLevel.getVisibleList(), -1, false, false);

        Button conceptButton = operationButton("Concept");
        Button realLifeButton = operationButton("Real-Life Uses");
        Button nodeButton = operationButton("Node Structure");
        Button traverseButton = operationButton("Search / Traverse");
        Button insertHeadButton = operationButton("Insert HEAD");
        Button insertMiddleButton = operationButton("Insert After 5");
        Button deleteButton = operationButton("Delete 10");
        Button typesButton = operationButton("Types");
        Button edgeCasesButton = operationButton("Edge Cases");

        conceptButton.setOnAction(e -> showConcept());
        realLifeButton.setOnAction(e -> showRealLife());
        nodeButton.setOnAction(e -> showNode());
        traverseButton.setOnAction(e -> showTraverse());
        insertHeadButton.setOnAction(e -> showInsertHead());
        insertMiddleButton.setOnAction(e -> showInsertMiddle());
        deleteButton.setOnAction(e -> showDelete());
        typesButton.setOnAction(e -> showTypes());
        edgeCasesButton.setOnAction(e -> showEdgeCases());

        HBox row1 = new HBox(12, conceptButton, realLifeButton, nodeButton);
        row1.setAlignment(Pos.CENTER);

        HBox row2 = new HBox(12, traverseButton, insertHeadButton, insertMiddleButton);
        row2.setAlignment(Pos.CENTER);

        HBox row3 = new HBox(12, deleteButton, typesButton, edgeCasesButton);
        row3.setAlignment(Pos.CENTER);

        VBox labelCard = teachingCard(
                "Visual Labels",
                "Every linked list visual shows:\n" +
                        "• HEAD: starting point of the list\n" +
                        "• DATA: value stored inside node\n" +
                        "• NEXT: pointer/reference to next node\n" +
                        "• NULL: end of the linked list"
        );

        Button startGameButton = new Button("Enter Pointer Maze");
        startGameButton.setStyle(buttonStyle(Style.COLOR_PRIMARY, "#0B1020"));
        startGameButton.setOnAction(e -> startEscapeGame());
        UiMotion.applyHoverScale(startGameButton, 1.04, 130);

        mainContent.getChildren().addAll(
                instructionLabel,
                listBox,
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
        drawLinkedList(linkedListLevel.getVisibleList(), -1, false, false);

        statusLabel.setText("Concept: Linked List stores data using connected nodes.");
        feedbackLabel.setText("Learning note: Linked List follows node connections instead of direct indexes.");

        showCode("LINKED LIST CONCEPT", linkedListLevel.getConceptExplanation());
    }

    private void showRealLife() {
        drawLinkedList(linkedListLevel.getVisibleList(), -1, false, false);

        statusLabel.setText("Real-Life Uses: Linked lists are useful when items are connected in sequence.");
        feedbackLabel.setText("Examples include playlists, train coaches, browser history, and slideshows.");

        showCode("REAL-LIFE LINKED LIST EXAMPLES", linkedListLevel.getRealLifeExamples());
    }

    private void showNode() {
        drawLinkedList(linkedListLevel.getVisibleList(), 0, false, false);

        statusLabel.setText("Node Structure: Every node has data and next pointer.");
        feedbackLabel.setText("Learning note: data stores value, next stores connection to the next node.");

        showCode("NODE STRUCTURE", linkedListLevel.getNodeCode());
    }

    private void showTraverse() {
        int foundIndex = linkedListLevel.indexOf(10);

        drawLinkedList(linkedListLevel.getVisibleList(), foundIndex, false, false);

        statusLabel.setText("Search / Traverse: Start from HEAD and follow next pointers.");
        feedbackLabel.setText("Learning note: Searching 10 gives position " + foundIndex + " after moving node by node.");

        showCode("SEARCH / TRAVERSE", linkedListLevel.getSearchCode());
    }

    private void showInsertHead() {
        LinkedListLevel demo = new LinkedListLevel();
        demo.insertAtBeginning(3);

        drawLinkedList(demo.getVisibleList(), 0, false, false);

        statusLabel.setText("Insert HEAD: Add new node at the beginning.");
        feedbackLabel.setText("Learning note: New node points to old HEAD, then HEAD moves to new node.");

        showCode("INSERT AT HEAD", linkedListLevel.getInsertAtHeadCode());
    }

    private void showInsertMiddle() {
        LinkedListLevel demo = new LinkedListLevel();
        demo.insertAfterValue(5, 8);

        drawLinkedList(demo.getVisibleList(), 1, false, false);

        statusLabel.setText("Insert After Node: Insert 8 after 5.");
        feedbackLabel.setText("Learning note: Node 5 points to 8, and node 8 points to 10.");

        showCode("INSERT AFTER NODE", linkedListLevel.getInsertAfterCode());
    }

    private void showDelete() {
        LinkedListLevel demo = new LinkedListLevel();
        demo.insertAfterValue(5, 8);
        demo.deleteValue(10);

        drawLinkedList(demo.getVisibleList(), 1, false, false);

        statusLabel.setText("Delete Node: Delete 10.");
        feedbackLabel.setText("Learning note: Node 8 now points directly to 15, so 10 is removed.");

        showCode("DELETE NODE", linkedListLevel.getDeleteCode());
    }

    private void showTypes() {
        drawLinkedList(linkedListLevel.getVisibleList(), -1, true, true);

        statusLabel.setText("Types: Singly, Doubly, and Circular Linked List.");
        feedbackLabel.setText("Learning note: Different linked list types use different pointer connections.");

        showCode("LINKED LIST TYPES", linkedListLevel.getTypesCode());
    }

    private void showEdgeCases() {
        drawLinkedList(linkedListLevel.getVisibleList(), -1, false, false);

        statusLabel.setText("Edge Cases: Empty list, deleting HEAD, missing value, and NULL checks.");
        feedbackLabel.setText("Learning note: Good linked list code always checks HEAD and NULL safely.");

        showCode("LINKED LIST EDGE CASES", linkedListLevel.getEdgeCases());
    }

    private void startEscapeGame() {
        currentRoom = 0;
        energy = 3;
        keysCollected = 0;
        showEscapeRoom();
    }

    private void showEscapeRoom() {
        roomAnswered = false;
        stageLabel.setText("Escape Mode: Pointer Maze");
        mainContent.getChildren().clear();

        Label gameTitle = new Label("Linked List Pointer Maze");
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
                statCard("Maze Locks", (5 - keysCollected) + " left", "#00D4FF")
        );

        Button optionA = puzzleButton(roomOptions[currentRoom][0]);
        Button optionB = puzzleButton(roomOptions[currentRoom][1]);
        Button optionC = puzzleButton(roomOptions[currentRoom][2]);

        optionA.setOnAction(e -> checkEscapeAnswer(0));
        optionB.setOnAction(e -> checkEscapeAnswer(1));
        optionC.setOnAction(e -> checkEscapeAnswer(2));

        HBox options = new HBox(14, optionA, optionB, optionC);
        options.setAlignment(Pos.CENTER);

        feedbackLabel.setText("Choose the correct pointer path. No hints are shown before answering.");

        mainContent.getChildren().addAll(
                gameTitle,
                roomLabel,
                storyLabel,
                gameStats,
                missionLabel,
                listBox,
                options,
                feedbackLabel
        );

        showCode(
                "MISSION CONSOLE",
                "Solve the linked list room.\n\n" +
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

        Label title = new Label("Pointer Maze Completed");
        title.setStyle("-fx-text-fill: #00FF99; -fx-font-size: 36px; -fx-font-weight: bold;");

        Label message = new Label(
                "You collected linked list keys and completed the Pointer Maze."
        );
        message.setWrapText(true);
        message.setMaxWidth(900);
        message.setAlignment(Pos.CENTER);
        message.setStyle("-fx-text-fill: white; -fx-font-size: 21px;");

        drawLinkedList(new int[]{5, 8, 15}, -1, false, false);

        VBox summary = teachingCard(
                "Linked List Mastery Summary",
                "• HEAD points to first node\n" +
                        "• Each node has data and next\n" +
                        "• Last node points to NULL\n" +
                        "• Insert at HEAD = O(1)\n" +
                        "• Search/traverse = O(n)\n" +
                        "• Delete needs pointer adjustment"
        );

        Button unlockButton = new Button("Unlock Stack Level");
        unlockButton.setStyle(buttonStyle("#00FF99", "black"));
        unlockButton.setOnAction(e -> gameManager.completeLinkedListLevel());

        mainContent.getChildren().addAll(title, message, listBox, summary, unlockButton);

        showCode(
                "LEVEL COMPLETED",
                "Linked List completed.\n\nNext: Stack."
        );
    }

    private void showGameOver() {
        mainContent.getChildren().clear();
        stageLabel.setText("Energy Lost");

        Label title = new Label("Energy Finished");
        title.setStyle("-fx-text-fill: #EF4444; -fx-font-size: 36px; -fx-font-weight: bold;");

        Label message = new Label(
                "The Pointer Maze locked again. Review learning first or retry the maze."
        );
        message.setWrapText(true);
        message.setMaxWidth(850);
        message.setAlignment(Pos.CENTER);
        message.setStyle("-fx-text-fill: white; -fx-font-size: 21px;");

        Button retryButton = new Button("Retry Pointer Maze");
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
                "Review linked list learning content first, then try again."
        );
    }

    private void drawRoomVisual() {
        if (currentRoom == 0) {
            drawLinkedList(new int[]{5, 10, 15}, 0, false, false);
        } else if (currentRoom == 1) {
            drawLinkedList(new int[]{5, 10, 15}, 1, false, false);
        } else if (currentRoom == 2) {
            drawLinkedList(new int[]{5, 10, 15}, 0, false, false);
        } else if (currentRoom == 3) {
            drawLinkedList(new int[]{5, 8, 10, 15}, 2, false, false);
        } else {
            drawLinkedList(new int[]{5, 10, 15}, -1, false, false);
        }
    }

    private void drawLinkedList(int[] values, int highlightIndex, boolean showDoubly, boolean showCircular) {
        listBox.getChildren().clear();

        Label headLabel = new Label("HEAD");
        headLabel.setStyle(pointerLabelStyle("#00FF99"));
        listBox.getChildren().add(headLabel);

        Label firstArrow = new Label("→");
        firstArrow.setStyle("-fx-text-fill: #00D4FF; -fx-font-size: 30px; -fx-font-weight: bold;");
        listBox.getChildren().add(firstArrow);

        for (int i = 0; i < values.length; i++) {
            VBox node = new VBox(4);
            node.setAlignment(Pos.CENTER);

            Label dataLabel = new Label(String.valueOf(values[i]));
            dataLabel.setStyle(
                    "-fx-text-fill: white;" +
                            "-fx-font-size: 27px;" +
                            "-fx-font-weight: bold;" +
                            "-fx-min-width: 88px;" +
                            "-fx-min-height: 62px;" +
                            "-fx-alignment: center;" +
                            "-fx-background-color: " + (i == highlightIndex ? "#0EA5E9" : "#1E293B") + ";" +
                            "-fx-border-color: #00D4FF;" +
                            "-fx-border-width: 2;" +
                            "-fx-background-radius: 12;" +
                            "-fx-border-radius: 12;"
            );

            Label nextLabel = new Label(showDoubly ? "prev | next" : "data | next");
            nextLabel.setStyle("-fx-text-fill: #CBD5E1; -fx-font-size: 13px;");

            node.getChildren().addAll(dataLabel, nextLabel);
            listBox.getChildren().add(node);

            if (i < values.length - 1) {
                Label arrow = new Label(showDoubly ? "↔" : "→");
                arrow.setStyle("-fx-text-fill: #00D4FF; -fx-font-size: 30px; -fx-font-weight: bold;");
                listBox.getChildren().add(arrow);
            }
        }

        Label endArrow = new Label(showCircular ? "↺" : "→");
        endArrow.setStyle("-fx-text-fill: #00D4FF; -fx-font-size: 30px; -fx-font-weight: bold;");
        listBox.getChildren().add(endArrow);

        Label nullLabel = new Label(showCircular ? "back to HEAD" : "NULL");
        nullLabel.setStyle(pointerLabelStyle(showCircular ? "#FACC15" : "#EF4444"));
        listBox.getChildren().add(nullLabel);
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