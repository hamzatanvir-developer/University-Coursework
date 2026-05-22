package com.muhammadhamza.algoquest.screens;

import com.muhammadhamza.algoquest.GameManager;
import com.muhammadhamza.algoquest.levels.TreesLevel;
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

public class TreesLevelScreen {

    private final BorderPane view;
    private final GameManager gameManager;
    private final TreesLevel treesLevel;

    private Label stageLabel;
    private Label instructionLabel;
    private Label feedbackLabel;
    private TextArea codeLabel;
    private Label statusLabel;

    private VBox treeBox;
    private VBox mainContent;

    private int currentRoom = 0;
    private int energy = 3;
    private int keysCollected = 0;
    private boolean roomAnswered = false;

    private final String[] roomNames = {
            "Root Gate",
            "BST Bridge",
            "Insert Forest",
            "Search Cave",
            "Traversal Temple"
    };

    private final String[] roomStories = {
            "The Tree Kingdom gate asks you to identify the top node before entering.",
            "A bridge splits into left and right paths. Choose the correct BST direction.",
            "A new value must be inserted into the correct position to grow the tree.",
            "A hidden value is inside the tree. Follow the BST search path to find it.",
            "The temple door opens only when you know the correct traversal order."
    };

    private final String[] roomQuestions = {
            "In a tree, what is the top node called?",
            "In a Binary Search Tree, where do smaller values go?",
            "Insert 7 into BST: root 10, left child 5. Where does 7 go?",
            "Search 12 in BST: root 10, right child 15, left child of 15 is 12. What path is correct?",
            "For BST with root 10, left 5, right 15, what is in-order traversal?"
    };

    private final String[][] roomOptions = {
            {"Root", "Leaf", "Edge"},
            {"Left side", "Right side", "Anywhere"},
            {"Left of 5", "Right of 5", "Right of 10 directly"},
            {"10 → 15 → 12", "10 → 5 → 12", "12 directly"},
            {"5, 10, 15", "10, 5, 15", "5, 15, 10"}
    };

    private final int[] correctAnswers = {0, 0, 1, 0, 0};

    private final String[] correctMessages = {
            "Correct! The top node is called the root.",
            "Correct! In BST, smaller values go to the left side.",
            "Correct! 7 is smaller than 10 but greater than 5, so it goes right of 5.",
            "Correct! Search starts at 10, goes right to 15, then left to 12.",
            "Correct! In-order traversal is left, root, right."
    };

    private final String[] wrongMessages = {
            "The top node of a tree is called root.",
            "BST rule: smaller values go left, greater values go right.",
            "Compare step by step: 7 < 10, so go left. Then 7 > 5, so go right of 5.",
            "BST search follows comparisons from the root.",
            "In-order traversal means left, root, right."
    };

    public TreesLevelScreen(GameManager gameManager) {
        this.gameManager = gameManager;
        this.treesLevel = new TreesLevel();

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
        Label titleLabel = new Label("Level 5: Tree Kingdom");
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

        treeBox = new VBox(8);
        treeBox.setAlignment(Pos.CENTER);
        treeBox.setPadding(new Insets(20));
        treeBox.setMinWidth(600);
        treeBox.setMinHeight(315);
        treeBox.setStyle(
            "-fx-background-color: " + Style.COLOR_CARD_DARK + ";" +
                "-fx-border-color: " + Style.COLOR_ACCENT + ";" +
                        "-fx-border-width: 2;" +
                        "-fx-background-radius: 16;" +
                        "-fx-border-radius: 16;"
        );
        UiMotion.applyHoverScale(treeBox, 1.02, 160);

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

        codeLabel = new TextArea("Click a learning button to view details from TreesLevel.java.");
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
        stageLabel.setText("Learning Mode: Understand Trees First");
        mainContent.getChildren().clear();

        instructionLabel.setText(
                "This is the learning area. The screen shows tree visuals and buttons. The actual tree/BST logic and explanations come from TreesLevel.java."
        );

        drawTree("10", "5", "15", "3", "7", "12", "20", "root");

        Button conceptButton = operationButton("Concept");
        Button realLifeButton = operationButton("Real-Life Uses");
        Button nodeButton = operationButton("Node Structure");
        Button bstButton = operationButton("BST Rule");
        Button insertButton = operationButton("Insert");
        Button searchButton = operationButton("Search");
        Button traversalButton = operationButton("Traversal");
        Button deleteButton = operationButton("Delete Idea");
        Button edgeCasesButton = operationButton("Edge Cases");

        conceptButton.setOnAction(e -> showConcept());
        realLifeButton.setOnAction(e -> showRealLife());
        nodeButton.setOnAction(e -> showNode());
        bstButton.setOnAction(e -> showBstRule());
        insertButton.setOnAction(e -> showInsert());
        searchButton.setOnAction(e -> showSearch());
        traversalButton.setOnAction(e -> showTraversal());
        deleteButton.setOnAction(e -> showDelete());
        edgeCasesButton.setOnAction(e -> showEdgeCases());

        HBox row1 = new HBox(12, conceptButton, realLifeButton, nodeButton);
        row1.setAlignment(Pos.CENTER);

        HBox row2 = new HBox(12, bstButton, insertButton, searchButton);
        row2.setAlignment(Pos.CENTER);

        HBox row3 = new HBox(12, traversalButton, deleteButton, edgeCasesButton);
        row3.setAlignment(Pos.CENTER);

        VBox labelCard = teachingCard(
                "Visual Labels",
                "Every tree visual shows:\n" +
                        "• ROOT: top node of the tree\n" +
                        "• LEFT CHILD: smaller side in BST\n" +
                        "• RIGHT CHILD: greater side in BST\n" +
                        "• LEAF: node with no child\n" +
                        "• EDGE: connection between nodes"
        );

        Button startGameButton = new Button("Enter Tree Kingdom");
        startGameButton.setStyle(buttonStyle(Style.COLOR_PRIMARY, "#0B1020"));
        startGameButton.setOnAction(e -> startEscapeGame());
        UiMotion.applyHoverScale(startGameButton, 1.04, 130);

        mainContent.getChildren().addAll(
                instructionLabel,
                treeBox,
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
        drawTree("10", "5", "15", "3", "7", "12", "20", "root");

        statusLabel.setText("Concept: Tree stores data in hierarchy.");
        feedbackLabel.setText("Learning note: Tree is not linear. It has root, branches, and child nodes.");

        showCode("TREE CONCEPT", treesLevel.getConceptExplanation());
    }

    private void showRealLife() {
        drawTree("CEO", "Manager", "Manager", "Emp", "Emp", "Emp", "Emp", "root");

        statusLabel.setText("Real-Life Uses: Trees represent hierarchy.");
        feedbackLabel.setText("Examples include family tree, folders, organization chart, and decision trees.");

        showCode("REAL-LIFE TREE EXAMPLES", treesLevel.getRealLifeExamples());
    }

    private void showNode() {
        drawTree("10", "5", "15", "", "", "", "", "root");

        statusLabel.setText("Node Structure: Tree node has data, left, and right.");
        feedbackLabel.setText("Learning note: In binary tree, each node has at most two children.");

        showCode("TREE NODE STRUCTURE", treesLevel.getNodeCode());
    }

    private void showBstRule() {
        drawTree("10", "5", "15", "", "", "", "", "root");

        statusLabel.setText("BST Rule: left < root < right.");
        feedbackLabel.setText("Learning note: Smaller values go left. Greater values go right.");

        showCode("BST RULE", treesLevel.getImportantPoints());
    }

    private void showInsert() {
        drawTree("10", "5", "15", "", "7", "", "", "7");

        statusLabel.setText("Insert: Insert 7 into BST.");
        feedbackLabel.setText("Learning note: 7 < 10, go left. 7 > 5, go right of 5.");

        showCode("INSERT IN BST", treesLevel.getInsertCode());
    }

    private void showSearch() {
        drawTree("10", "5", "15", "", "", "12", "20", "12");

        statusLabel.setText("Search: Search 12 in BST.");
        feedbackLabel.setText("Learning note: Start from root, compare, and follow left/right path.");

        showCode("SEARCH IN BST", treesLevel.getSearchCode());
    }

    private void showTraversal() {
        drawTree("10", "5", "15", "", "", "", "", "root");

        statusLabel.setText("Traversal: Visit tree nodes in order.");
        feedbackLabel.setText("Learning note: In-order traversal of BST gives sorted output: " + treesLevel.inOrderTraversal());

        showCode("TREE TRAVERSALS", treesLevel.getTraversalCode());
    }

    private void showDelete() {
        drawTree("10", "5", "15", "3", "7", "12", "20", "root");

        statusLabel.setText("Delete: Delete node from BST.");
        feedbackLabel.setText("Learning note: Delete has three cases: leaf, one child, two children.");

        showCode("DELETE IDEA", treesLevel.getDeleteCode());
    }

    private void showEdgeCases() {
        drawTree("10", "5", "15", "3", "7", "12", "20", "root");

        statusLabel.setText("Edge Cases: Empty tree, duplicates, skewed tree, and deleting root.");
        feedbackLabel.setText("Learning note: Always check null nodes before moving left or right.");

        showCode("TREE EDGE CASES", treesLevel.getEdgeCases());
    }

    private void startEscapeGame() {
        currentRoom = 0;
        energy = 3;
        keysCollected = 0;
        showEscapeRoom();
    }

    private void showEscapeRoom() {
        roomAnswered = false;
        stageLabel.setText("Escape Mode: Tree Kingdom");
        mainContent.getChildren().clear();

        Label gameTitle = new Label("Tree Kingdom Escape");
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
                statCard("Kingdom Locks", (5 - keysCollected) + " left", "#00D4FF")
        );

        Button optionA = puzzleButton(roomOptions[currentRoom][0]);
        Button optionB = puzzleButton(roomOptions[currentRoom][1]);
        Button optionC = puzzleButton(roomOptions[currentRoom][2]);

        optionA.setOnAction(e -> checkEscapeAnswer(0));
        optionB.setOnAction(e -> checkEscapeAnswer(1));
        optionC.setOnAction(e -> checkEscapeAnswer(2));

        HBox options = new HBox(14, optionA, optionB, optionC);
        options.setAlignment(Pos.CENTER);

        feedbackLabel.setText("Choose the correct tree path. No hints are shown before answering.");

        mainContent.getChildren().addAll(
                gameTitle,
                roomLabel,
                storyLabel,
                gameStats,
                missionLabel,
                treeBox,
                options,
                feedbackLabel
        );

        showCode(
                "MISSION CONSOLE",
                "Solve the tree room.\n\n" +
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

        Label title = new Label("Tree Kingdom Completed");
        title.setStyle("-fx-text-fill: #00FF99; -fx-font-size: 36px; -fx-font-weight: bold;");

        Label message = new Label(
                "You collected tree keys and completed the Tree Kingdom."
        );
        message.setWrapText(true);
        message.setMaxWidth(900);
        message.setAlignment(Pos.CENTER);
        message.setStyle("-fx-text-fill: white; -fx-font-size: 21px;");

        drawTree("10", "5", "15", "3", "7", "12", "20", "root");

        VBox summary = teachingCard(
                "Tree Mastery Summary",
                "• Root is the top node\n" +
                        "• Leaf node has no child\n" +
                        "• Binary tree has at most two children\n" +
                        "• BST rule: left < root < right\n" +
                        "• In-order traversal gives sorted order\n" +
                        "• Balanced BST search = O(log n)"
        );

        Button unlockButton = new Button("Unlock Graphs Level");
        unlockButton.setStyle(buttonStyle("#00FF99", "black"));
        unlockButton.setOnAction(e -> gameManager.completeTreesLevel());

        mainContent.getChildren().addAll(title, message, treeBox, summary, unlockButton);

        showCode(
                "LEVEL COMPLETED",
                "Trees completed.\n\nNext: Graphs."
        );
    }

    private void showGameOver() {
        mainContent.getChildren().clear();
        stageLabel.setText("Energy Lost");

        Label title = new Label("Energy Finished");
        title.setStyle("-fx-text-fill: #EF4444; -fx-font-size: 36px; -fx-font-weight: bold;");

        Label message = new Label(
                "Tree Kingdom locked again. Review learning first or retry the kingdom."
        );
        message.setWrapText(true);
        message.setMaxWidth(850);
        message.setAlignment(Pos.CENTER);
        message.setStyle("-fx-text-fill: white; -fx-font-size: 21px;");

        Button retryButton = new Button("Retry Tree Kingdom");
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
                "Review tree learning content first, then try again."
        );
    }

    private void drawRoomVisual() {
        if (currentRoom == 0) {
            drawTree("10", "5", "15", "", "", "", "", "root");
        } else if (currentRoom == 1) {
            drawTree("10", "5", "15", "", "", "", "", "5");
        } else if (currentRoom == 2) {
            drawTree("10", "5", "15", "", "7", "", "", "7");
        } else if (currentRoom == 3) {
            drawTree("10", "5", "15", "", "", "12", "20", "12");
        } else {
            drawTree("10", "5", "15", "", "", "", "", "root");
        }
    }

    private void drawTree(String root, String left, String right, String leftLeft, String leftRight, String rightLeft, String rightRight, String highlightValue) {
        treeBox.getChildren().clear();

        HBox level1 = new HBox();
        level1.setAlignment(Pos.CENTER);
        level1.getChildren().add(treeNode(root, "ROOT", root.equals(highlightValue) || "root".equals(highlightValue)));

        Label connectors1 = new Label("        /                    \\");
        connectors1.setStyle("-fx-text-fill: #00D4FF; -fx-font-size: 25px; -fx-font-weight: bold;");

        HBox level2 = new HBox(120);
        level2.setAlignment(Pos.CENTER);
        level2.getChildren().addAll(
                treeNode(left, "LEFT CHILD", left.equals(highlightValue)),
                treeNode(right, "RIGHT CHILD", right.equals(highlightValue))
        );

        Label connectors2 = new Label("      /        \\                         /        \\");
        connectors2.setStyle("-fx-text-fill: #00D4FF; -fx-font-size: 18px; -fx-font-weight: bold;");

        HBox level3 = new HBox(30);
        level3.setAlignment(Pos.CENTER);

        if (!leftLeft.isEmpty() || !leftRight.isEmpty() || !rightLeft.isEmpty() || !rightRight.isEmpty()) {
            level3.getChildren().addAll(
                    treeNode(emptyToDash(leftLeft), "leaf", leftLeft.equals(highlightValue)),
                    treeNode(emptyToDash(leftRight), "leaf", leftRight.equals(highlightValue)),
                    treeNode(emptyToDash(rightLeft), "leaf", rightLeft.equals(highlightValue)),
                    treeNode(emptyToDash(rightRight), "leaf", rightRight.equals(highlightValue))
            );

            treeBox.getChildren().addAll(level1, connectors1, level2, connectors2, level3);
        } else {
            treeBox.getChildren().addAll(level1, connectors1, level2);
        }
    }

    private String emptyToDash(String value) {
        return value.isEmpty() ? "empty" : value;
    }

    private VBox treeNode(String value, String label, boolean highlight) {
        VBox node = new VBox(5);
        node.setAlignment(Pos.CENTER);

        Label valueLabel = new Label(value);
        valueLabel.setStyle(
                "-fx-text-fill: white;" +
                        "-fx-font-size: 24px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-min-width: 105px;" +
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