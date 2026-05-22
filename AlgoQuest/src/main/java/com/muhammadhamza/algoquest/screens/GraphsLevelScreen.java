package com.muhammadhamza.algoquest.screens;

import com.muhammadhamza.algoquest.GameManager;
import com.muhammadhamza.algoquest.levels.GraphsLevel;
import com.muhammadhamza.algoquest.utils.Style;
import com.muhammadhamza.algoquest.utils.UiMotion;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class GraphsLevelScreen {

    private final BorderPane view;
    private final GameManager gameManager;
    private final GraphsLevel graphsLevel;

    private Label stageLabel;
    private Label instructionLabel;
    private Label feedbackLabel;
    private TextArea codeLabel;
    private Label statusLabel;

    private VBox graphBox;
    private VBox mainContent;

    private int currentRoom = 0;
    private int energy = 3;
    private int keysCollected = 0;
    private boolean roomAnswered = false;

    private final String[] roomNames = {
            "Vertex Gate",
            "Edge Bridge",
            "BFS Scanner",
            "DFS Tunnel",
            "Path Portal"
    };

    private final String[] roomStories = {
            "The Graph Realm gate shows many circles. You must identify what each circle represents.",
            "Two cities must be connected with a road. Choose the graph part that creates a connection.",
            "A scanner must search nearby nodes first. Choose the traversal that works level by level.",
            "A deep tunnel goes as far as possible before returning. Choose the traversal that goes deep first.",
            "The final portal asks how graph traversal avoids getting stuck in cycles."
    };

    private final String[] roomQuestions = {
            "In a graph, what is each node/point called?",
            "What connects two vertices in a graph?",
            "Which graph traversal uses a Queue and visits level by level?",
            "Which graph traversal uses recursion or stack and goes deep first?",
            "Why do BFS and DFS use a visited list/array?"
    };

    private final String[][] roomOptions = {
            {"Vertex", "Root", "Index"},
            {"Edge", "Array Size", "Stack Top"},
            {"BFS", "DFS", "Heapify"},
            {"DFS", "BFS", "Enqueue"},
            {"To avoid visiting same node again", "To sort the array", "To remove root node"}
    };

    private final int[] correctAnswers = {0, 0, 0, 0, 0};

    private final String[] correctMessages = {
            "Correct! A graph node is called a vertex.",
            "Correct! An edge connects two vertices.",
            "Correct! BFS uses Queue and visits nearby nodes first.",
            "Correct! DFS uses recursion or stack and goes deep first.",
            "Correct! visited prevents infinite loops and repeated visits."
    };

    private final String[] wrongMessages = {
            "A point or node in a graph is called a vertex.",
            "An edge is the connection between two vertices.",
            "BFS means Breadth First Search. It uses Queue.",
            "DFS means Depth First Search. It uses recursion or stack.",
            "Graphs can have cycles, so visited array prevents repeated visits."
    };

    public GraphsLevelScreen(GameManager gameManager) {
        this.gameManager = gameManager;
        this.graphsLevel = new GraphsLevel();

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
        Label titleLabel = new Label("Level 6: Graph Realm");
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

        graphBox = new VBox(8);
        graphBox.setAlignment(Pos.CENTER);
        graphBox.setPadding(new Insets(20));
        graphBox.setMinWidth(600);
        graphBox.setMinHeight(315);
        graphBox.setStyle(
            "-fx-background-color: " + Style.COLOR_CARD_DARK + ";" +
                "-fx-border-color: " + Style.COLOR_ACCENT + ";" +
                        "-fx-border-width: 2;" +
                        "-fx-background-radius: 16;" +
                        "-fx-border-radius: 16;"
        );
        UiMotion.applyHoverScale(graphBox, 1.02, 160);

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

        codeLabel = new TextArea("Click a learning button to view details from GraphsLevel.java.");
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
        stageLabel.setText("Learning Mode: Understand Graphs First");
        mainContent.getChildren().clear();

        instructionLabel.setText(
                "This is the learning area. The screen shows graph visuals and buttons. The actual graph logic and explanations come from GraphsLevel.java."
        );

        drawGraph("normal");

        Button conceptButton = operationButton("Concept");
        Button realLifeButton = operationButton("Real-Life Uses");
        Button addVertexButton = operationButton("Add Vertex");
        Button addEdgeButton = operationButton("Add Edge");
        Button bfsButton = operationButton("BFS");
        Button dfsButton = operationButton("DFS");
        Button adjacencyButton = operationButton("Adjacency List");
        Button typesButton = operationButton("Types");
        Button edgeCasesButton = operationButton("Edge Cases");

        conceptButton.setOnAction(e -> showConcept());
        realLifeButton.setOnAction(e -> showRealLife());
        addVertexButton.setOnAction(e -> showAddVertex());
        addEdgeButton.setOnAction(e -> showAddEdge());
        bfsButton.setOnAction(e -> showBfs());
        dfsButton.setOnAction(e -> showDfs());
        adjacencyButton.setOnAction(e -> showAdjacency());
        typesButton.setOnAction(e -> showTypes());
        edgeCasesButton.setOnAction(e -> showEdgeCases());

        HBox row1 = new HBox(12, conceptButton, realLifeButton, addVertexButton);
        row1.setAlignment(Pos.CENTER);

        HBox row2 = new HBox(12, addEdgeButton, bfsButton, dfsButton);
        row2.setAlignment(Pos.CENTER);

        HBox row3 = new HBox(12, adjacencyButton, typesButton, edgeCasesButton);
        row3.setAlignment(Pos.CENTER);

        VBox labelCard = teachingCard(
                "Visual Labels",
                "Every graph visual shows:\n" +
                        "• VERTEX: node/point in graph\n" +
                        "• EDGE: connection between vertices\n" +
                        "• BFS: visits nearby nodes first using Queue\n" +
                        "• DFS: goes deep first using recursion/Stack\n" +
                        "• VISITED: prevents repeated visits"
        );

        Button startGameButton = new Button("Enter Graph Realm");
        startGameButton.setStyle(buttonStyle(Style.COLOR_PRIMARY, "#0B1020"));
        startGameButton.setOnAction(e -> startEscapeGame());
        UiMotion.applyHoverScale(startGameButton, 1.04, 130);
        UiMotion.applyGlowPulse(startGameButton, Color.web(Style.COLOR_PRIMARY, 0.5));

        mainContent.getChildren().addAll(
                instructionLabel,
                graphBox,
                row1,
                row2,
                row3,
                statusLabel,
                feedbackLabel,
                labelCard,
                startGameButton
        );

        animateStack(mainContent, 80);

        showConcept();
    }

    private void showConcept() {
        drawGraph("normal");

        statusLabel.setText("Concept: Graph stores relationships.");
        feedbackLabel.setText("Learning note: Graph has vertices and edges.");

        showCode("GRAPH CONCEPT", graphsLevel.getConceptExplanation());
    }

    private void showRealLife() {
        drawGraph("types");

        statusLabel.setText("Real-Life Uses: Graphs are used for connected systems.");
        feedbackLabel.setText("Examples include maps, social media, networks, and airline routes.");

        showCode("REAL-LIFE GRAPH EXAMPLES", graphsLevel.getRealLifeExamples());
    }

    private void showAddVertex() {
        drawGraph("vertex");

        statusLabel.setText("Add Vertex: Create a new node.");
        feedbackLabel.setText("Learning note: Adding a vertex means adding a point with an empty neighbor list.");

        showCode("ADD VERTEX", graphsLevel.getAddVertexCode());
    }

    private void showAddEdge() {
        drawGraph("edge");

        statusLabel.setText("Add Edge: Connect two vertices.");
        feedbackLabel.setText("Learning note: In an undirected graph, A -- B means A connects to B and B connects to A.");

        showCode("ADD EDGE", graphsLevel.getAddEdgeCode());
    }

    private void showBfs() {
        drawGraph("bfs");

        statusLabel.setText("BFS: Breadth First Search.");
        feedbackLabel.setText("Learning note: BFS from A gives " + graphsLevel.bfs("A") + ". BFS uses Queue.");

        showCode("BFS TRAVERSAL", graphsLevel.getBfsCode());
    }

    private void showDfs() {
        drawGraph("dfs");

        statusLabel.setText("DFS: Depth First Search.");
        feedbackLabel.setText("Learning note: DFS from A gives " + graphsLevel.dfs("A") + ". DFS uses recursion or Stack.");

        showCode("DFS TRAVERSAL", graphsLevel.getDfsCode());
    }

    private void showAdjacency() {
        drawGraph("storage");

        statusLabel.setText("Adjacency List: Store neighbors of each vertex.");
        feedbackLabel.setText("Learning note: Adjacency list is memory-friendly for graphs with fewer edges.");

        showCode(
                "ADJACENCY LIST",
                graphsLevel.getAdjacencyListCode() +
                        "\nCurrent graph:\n" +
                        graphsLevel.getAdjacencyListText()
        );
    }

    private void showTypes() {
        drawGraph("types");

        statusLabel.setText("Graph Types: Directed, undirected, weighted, and unweighted.");
        feedbackLabel.setText("Learning note: Different graph types are used for different problems.");

        showCode("GRAPH TYPES", graphsLevel.getGraphTypesCode());
    }

    private void showEdgeCases() {
        drawGraph("normal");

        statusLabel.setText("Edge Cases: Missing vertex, duplicate edge, cycle, and disconnected graph.");
        feedbackLabel.setText("Learning note: Good graph algorithms use visited array/list to avoid infinite loops.");

        showCode("GRAPH EDGE CASES", graphsLevel.getEdgeCases());
    }

    private void startEscapeGame() {
        currentRoom = 0;
        energy = 3;
        keysCollected = 0;
        showEscapeRoom();
    }

    private void showEscapeRoom() {
        roomAnswered = false;
        stageLabel.setText("Escape Mode: Graph Realm");
        mainContent.getChildren().clear();

        Label gameTitle = new Label("Graph Realm Escape");
        gameTitle.setStyle(
            "-fx-text-fill: " + Style.COLOR_PRIMARY + ";" +
                "-fx-font-size: 34px;" +
                "-fx-font-weight: bold;" +
                "-fx-font-family: " + Style.FONT_TITLE + ";"
        );

        Label roomLabel = new Label("Room " + (currentRoom + 1) + "/5: " + roomNames[currentRoom]);
        roomLabel.setStyle(
            "-fx-text-fill: " + Style.COLOR_SECONDARY + ";" +
                "-fx-font-size: 24px;" +
                "-fx-font-weight: bold;" +
                "-fx-font-family: " + Style.FONT_BODY + ";"
        );

        Label storyLabel = new Label(roomStories[currentRoom]);
        storyLabel.setWrapText(true);
        storyLabel.setMaxWidth(900);
        storyLabel.setAlignment(Pos.CENTER);
        storyLabel.setStyle(
            "-fx-text-fill: " + Style.COLOR_TEXT + ";" +
                "-fx-font-size: 19px;" +
                "-fx-font-family: " + Style.FONT_BODY + ";"
        );

        Label missionLabel = new Label("Mission: " + roomQuestions[currentRoom]);
        missionLabel.setWrapText(true);
        missionLabel.setMaxWidth(900);
        missionLabel.setAlignment(Pos.CENTER);
        missionLabel.setStyle(
            "-fx-text-fill: " + Style.COLOR_SECONDARY + ";" +
                "-fx-font-size: 21px;" +
                "-fx-font-weight: bold;" +
                "-fx-font-family: " + Style.FONT_BODY + ";"
        );

        drawRoomVisual();

        HBox gameStats = new HBox(16);
        gameStats.setAlignment(Pos.CENTER);
        gameStats.getChildren().addAll(
            statCard("Energy", getEnergyText(), Style.COLOR_SECONDARY),
            statCard("Keys", keysCollected + "/5", Style.COLOR_PRIMARY),
            statCard("Realm Locks", (5 - keysCollected) + " left", Style.COLOR_ACCENT)
        );

        Button optionA = puzzleButton(roomOptions[currentRoom][0]);
        Button optionB = puzzleButton(roomOptions[currentRoom][1]);
        Button optionC = puzzleButton(roomOptions[currentRoom][2]);

        optionA.setOnAction(e -> checkEscapeAnswer(0));
        optionB.setOnAction(e -> checkEscapeAnswer(1));
        optionC.setOnAction(e -> checkEscapeAnswer(2));

        HBox options = new HBox(14, optionA, optionB, optionC);
        options.setAlignment(Pos.CENTER);

        feedbackLabel.setText("Choose the correct graph concept. No hints are shown before answering.");

        mainContent.getChildren().addAll(
                gameTitle,
                roomLabel,
                storyLabel,
                gameStats,
                missionLabel,
                graphBox,
                options,
                feedbackLabel
        );

        animateStack(mainContent, 80);

        showCode(
                "MISSION CONSOLE",
                "Solve the graph room.\n\n" +
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
                Button finalButton = new Button("Open Final Portal");
                finalButton.setStyle(buttonStyle(Style.COLOR_PRIMARY, "#0B1020"));
                finalButton.setOnAction(e -> showFinalDoor());
                UiMotion.applyHoverScale(finalButton, 1.04, 120);
                mainContent.getChildren().add(finalButton);
            } else {
                Button nextButton = new Button("Next Room");
                nextButton.setStyle(buttonStyle(Style.COLOR_PRIMARY, "#0B1020"));
                nextButton.setOnAction(e -> {
                    currentRoom++;
                    showEscapeRoom();
                });
                UiMotion.applyHoverScale(nextButton, 1.04, 120);
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
                nextButton.setStyle(buttonStyle(Style.COLOR_SECONDARY, "#0B1020"));
                nextButton.setOnAction(e -> {
                    if (currentRoom == roomNames.length - 1) {
                        showFinalDoor();
                    } else {
                        currentRoom++;
                        showEscapeRoom();
                    }
                });
                UiMotion.applyHoverScale(nextButton, 1.04, 120);
                mainContent.getChildren().add(nextButton);
            }
        }
    }

    private void showFinalDoor() {
        mainContent.getChildren().clear();
        stageLabel.setText("Final Portal");

        Label title = new Label("Graph Realm Completed");
        title.setStyle(
            "-fx-text-fill: " + Style.COLOR_PRIMARY + ";" +
                "-fx-font-size: 36px;" +
                "-fx-font-weight: bold;" +
                "-fx-font-family: " + Style.FONT_TITLE + ";"
        );

        Label message = new Label(
                "You collected graph keys and completed the Graph Realm."
        );
        message.setWrapText(true);
        message.setMaxWidth(900);
        message.setAlignment(Pos.CENTER);
        message.setStyle(
            "-fx-text-fill: " + Style.COLOR_TEXT + ";" +
                "-fx-font-size: 21px;" +
                "-fx-font-family: " + Style.FONT_BODY + ";"
        );

        drawGraph("normal");

        VBox summary = teachingCard(
                "Graph Mastery Summary",
                "• Vertex means node\n" +
                        "• Edge means connection\n" +
                        "• Adjacency list stores neighbors\n" +
                        "• BFS uses Queue\n" +
                        "• DFS uses Recursion/Stack\n" +
                        "• BFS/DFS = O(V + E)\n" +
                        "• visited prevents repeated visits"
        );

        Button unlockButton = new Button("Unlock Heaps Level");
        unlockButton.setStyle(buttonStyle(Style.COLOR_PRIMARY, "#0B1020"));
        unlockButton.setOnAction(e -> gameManager.completeGraphsLevel());
        UiMotion.applyHoverScale(unlockButton, 1.04, 130);

        mainContent.getChildren().addAll(title, message, graphBox, summary, unlockButton);

        animateStack(mainContent, 80);

        showCode(
                "LEVEL COMPLETED",
                "Graphs completed.\n\nNext: Heaps."
        );
    }

    private void showGameOver() {
        mainContent.getChildren().clear();
        stageLabel.setText("Energy Lost");

        Label title = new Label("Energy Finished");
        title.setStyle(
            "-fx-text-fill: " + Style.COLOR_DANGER + ";" +
                "-fx-font-size: 36px;" +
                "-fx-font-weight: bold;" +
                "-fx-font-family: " + Style.FONT_TITLE + ";"
        );

        Label message = new Label(
                "Graph Realm locked again. Review learning first or retry the realm."
        );
        message.setWrapText(true);
        message.setMaxWidth(850);
        message.setAlignment(Pos.CENTER);
        message.setStyle(
            "-fx-text-fill: " + Style.COLOR_TEXT + ";" +
                "-fx-font-size: 21px;" +
                "-fx-font-family: " + Style.FONT_BODY + ";"
        );

        Button retryButton = new Button("Retry Graph Realm");
        retryButton.setStyle(buttonStyle(Style.COLOR_SECONDARY, "#0B1020"));
        retryButton.setOnAction(e -> startEscapeGame());

        Button learningButton = new Button("Back to Learning");
        learningButton.setStyle(buttonStyle(Style.COLOR_ACCENT, "#0B1020"));
        learningButton.setOnAction(e -> showLearningStage());

        UiMotion.applyHoverScale(retryButton, 1.04, 130);
        UiMotion.applyHoverScale(learningButton, 1.04, 130);

        HBox buttons = new HBox(14, retryButton, learningButton);
        buttons.setAlignment(Pos.CENTER);

        mainContent.getChildren().addAll(title, message, buttons);

        animateStack(mainContent, 80);

        showCode(
                "RETRY",
                "Review graph learning content first, then try again."
        );
    }

    private void drawRoomVisual() {
        if (currentRoom == 0) {
            drawGraph("vertex");
        } else if (currentRoom == 1) {
            drawGraph("edge");
        } else if (currentRoom == 2) {
            drawGraph("bfs");
        } else if (currentRoom == 3) {
            drawGraph("dfs");
        } else {
            drawGraph("storage");
        }
    }

    private void drawGraph(String mode) {
        graphBox.getChildren().clear();

        HBox topRow = new HBox(90);
        topRow.setAlignment(Pos.CENTER);

        topRow.getChildren().addAll(
                graphNode("A", isHighlighted(mode, "A")),
                graphNode("B", isHighlighted(mode, "B")),
                graphNode("C", isHighlighted(mode, "C"))
        );

        Label edgeRow = new Label("A -------- B -------- C");
        edgeRow.setStyle(
            "-fx-text-fill: " + Style.COLOR_ACCENT + ";" +
                "-fx-font-size: 21px;" +
                "-fx-font-weight: bold;" +
                "-fx-font-family: " + Style.FONT_BODY + ";"
        );

        Label middleEdges = new Label("|          |          |");
        middleEdges.setStyle(
            "-fx-text-fill: " + Style.COLOR_ACCENT + ";" +
                "-fx-font-size: 21px;" +
                "-fx-font-weight: bold;" +
                "-fx-font-family: " + Style.FONT_BODY + ";"
        );

        HBox bottomRow = new HBox(140);
        bottomRow.setAlignment(Pos.CENTER);

        bottomRow.getChildren().addAll(
                graphNode("D", isHighlighted(mode, "D")),
                graphNode("E", isHighlighted(mode, "E"))
        );

        Label description = new Label(getGraphDescription(mode));
        description.setWrapText(true);
        description.setMaxWidth(570);
        description.setAlignment(Pos.CENTER);
        description.setStyle(
            "-fx-text-fill: #C7D2E6;" +
                "-fx-font-size: 16px;" +
                "-fx-font-family: " + Style.FONT_BODY + ";"
        );

        graphBox.getChildren().addAll(topRow, edgeRow, middleEdges, bottomRow, description);
        UiMotion.applySoftPop(graphBox);
    }

    private boolean isHighlighted(String mode, String node) {
        if (mode.equals("bfs")) {
            return node.equals("A") || node.equals("B") || node.equals("C") || node.equals("D") || node.equals("E");
        }

        if (mode.equals("dfs")) {
            return node.equals("A") || node.equals("B") || node.equals("C") || node.equals("E");
        }

        if (mode.equals("edge")) {
            return node.equals("A") || node.equals("B");
        }

        if (mode.equals("vertex")) {
            return node.equals("E");
        }

        if (mode.equals("storage")) {
            return node.equals("A");
        }

        if (mode.equals("types")) {
            return node.equals("A") || node.equals("C");
        }

        return node.equals("A");
    }

    private String getGraphDescription(String mode) {
        if (mode.equals("bfs")) {
            return "BFS order from A: " + graphsLevel.bfs("A") + " | BFS uses Queue.";
        }

        if (mode.equals("dfs")) {
            return "DFS order from A: " + graphsLevel.dfs("A") + " | DFS uses Recursion/Stack.";
        }

        if (mode.equals("edge")) {
            return "Highlighted edge: A-B. Edge means connection.";
        }

        if (mode.equals("vertex")) {
            return "Highlighted E is a vertex. Vertex means graph node.";
        }

        if (mode.equals("storage")) {
            return "Adjacency list stores each vertex with its neighbors.";
        }

        if (mode.equals("types")) {
            return "Graph can be directed, undirected, weighted, or unweighted.";
        }

        return "Graph contains vertices A, B, C, D, E and edges between connected nodes.";
    }

    private VBox graphNode(String value, boolean highlight) {
        VBox node = new VBox(4);
        node.setAlignment(Pos.CENTER);

        Label circle = new Label(value);
        circle.setStyle(
            "-fx-text-fill: " + Style.COLOR_TEXT + ";" +
                "-fx-font-size: 24px;" +
                "-fx-font-weight: bold;" +
                "-fx-font-family: " + Style.FONT_TITLE + ";" +
                "-fx-min-width: 78px;" +
                "-fx-min-height: 78px;" +
                "-fx-alignment: center;" +
                "-fx-background-color: " + (highlight ? Style.COLOR_PRIMARY : Style.COLOR_CARD) + ";" +
                "-fx-border-color: " + Style.COLOR_ACCENT + ";" +
                "-fx-border-width: 2;" +
                "-fx-background-radius: 50;" +
                "-fx-border-radius: 50;"
        );

        Label label = new Label("vertex");
        label.setStyle(
            "-fx-text-fill: " + Style.COLOR_MUTED + ";" +
                "-fx-font-size: 13px;" +
                "-fx-font-family: " + Style.FONT_BODY + ";"
        );

        if (highlight) {
            UiMotion.applyPulse(circle, 0.96, 1.05, 1.6);
        }

        node.getChildren().addAll(circle, label);
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
        UiMotion.applyHoverScale(card, 1.03, 130, Color.web(color, 0.5));
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

    private void animateStack(VBox container, double baseDelay) {
        int index = 0;
        for (Node child : container.getChildren()) {
            UiMotion.applyEntrance(child, baseDelay + (index * 70));
            index++;
        }
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