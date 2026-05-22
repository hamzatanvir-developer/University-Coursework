package com.muhammadhamza.algoquest.screens;

import com.muhammadhamza.algoquest.GameManager;
import com.muhammadhamza.algoquest.levels.ArrayLevel;
import com.muhammadhamza.algoquest.utils.Style;
import com.muhammadhamza.algoquest.utils.UiMotion;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class ArrayLevelScreen {

    private final BorderPane view;
    private final GameManager gameManager;
    private final ArrayLevel arrayLevel;

    private Label stageLabel;
    private Label instructionLabel;
    private Label feedbackLabel;
    private TextArea codeLabel;
    private Label statusLabel;

    private HBox arrayBox;
    private VBox mainContent;

    private int currentPuzzle = 0;
    private int energy = 3;
    private int keysCollected = 0;
    private int points = 0;
    private int currentQuiz = 0;
    private int quizPoints = 0;

    private boolean puzzleAnswered = false;
    private boolean quizAnswered = false;

    private final String[] puzzleTitles = {
            "Insert at End",
            "Insert at Middle",
            "Insert at Start",
            "Delete at End",
            "Delete at Middle",
            "Delete at Start",
            "Find Element",
            "Access by Index",
            "Insert then Search",
            "Delete then Access",
            "Multiple Inserts",
            "Search Missing Value",
            "Large Array Insert",
            "Fastest Operation",
            "Full Array Edge Case"
    };

    private final String[] puzzleDifficulties = {
            "Easy ⭐", "Easy ⭐", "Easy ⭐", "Easy ⭐", "Easy ⭐",
            "Easy ⭐", "Easy ⭐", "Easy ⭐",
            "Medium ⭐⭐", "Medium ⭐⭐", "Medium ⭐⭐", "Medium ⭐⭐",
            "Hard ⭐⭐⭐", "Hard ⭐⭐⭐", "Hard ⭐⭐⭐"
    };

    private final String[] puzzleChallenges = {
            "Insert 50 at the end of [10, 20, 30].",
            "Insert 25 at index 1 in [10, 20, 30].",
            "Insert 5 at index 0 in [10, 20, 30].",
            "Delete the last element from [10, 20, 30, 40].",
            "Delete index 1 from [10, 20, 30, 40].",
            "Delete index 0 from [10, 20, 30, 40].",
            "Find value 30 in [10, 20, 30, 40].",
            "Access index 2 in [10, 20, 30, 40].",
            "Insert 25 at index 2, then find 25.",
            "Delete index 1, then access index 1.",
            "Insert 5 at start, then insert 50 at end.",
            "Search value 99 in [10, 20, 30, 40].",
            "Insert 35 at index 3 in [10, 20, 30, 40, 50].",
            "Which array operation is fastest?",
            "What happens when inserting into a full fixed-size array?"
    };

    private final String[][] puzzleOptions = {
            {"[10, 20, 30, 50]", "[50, 10, 20, 30]", "[10, 50, 20, 30]"},
            {"[10, 25, 20, 30]", "[25, 10, 20, 30]", "[10, 20, 25, 30]"},
            {"[5, 10, 20, 30]", "[10, 5, 20, 30]", "[10, 20, 30, 5]"},
            {"[10, 20, 30]", "[20, 30, 40]", "[10, 20, 40]"},
            {"[10, 30, 40]", "[20, 30, 40]", "[10, 20, 40]"},
            {"[20, 30, 40]", "[10, 30, 40]", "[10, 20, 30]"},
            {"Index 2", "Index 1", "Index 3"},
            {"30", "20", "40"},
            {"Index 2", "Index 1", "Index 3"},
            {"30", "20", "40"},
            {"[5, 10, 20, 30, 50]", "[10, 5, 20, 30, 50]", "[5, 50, 10, 20, 30]"},
            {"-1 / Not Found", "Index 0", "Index 3"},
            {"[10, 20, 30, 35, 40, 50]", "[10, 20, 35, 30, 40, 50]", "[35, 10, 20, 30, 40, 50]"},
            {"Access by index", "Insert at start", "Delete at start"},
            {"Overflow / cannot insert", "It auto-expands always", "It deletes first item automatically"}
    };

    private final int[] correctPuzzleAnswers = {
            0, 2, 0, 2, 0,
            0, 2, 1, 0, 0,
            0, 3, 0, 0, 0
    };

    private final String[] puzzleExplanations = {
            "Insert at end places the new value after the last element.",
            "Insert at middle shifts elements right, then places 25 at index 1.",
            "Insert at start shifts all elements right, then places 5 at index 0.",
            "Deleting last element removes 40 directly.",
            "Deleting index 1 removes 20, then 30 and 40 shift left.",
            "Deleting index 0 removes 10, then all remaining elements shift left.",
            "30 is at index 2 because indexing starts from 0.",
            "Index 2 contains value 30.",
            "After inserting 25 at index 2, 25 is found at index 2.",
            "After deleting index 1, array becomes [10, 30, 40]. Index 1 is 30.",
            "After insert start and end, final array is [5, 10, 20, 30, 50].",
            "99 is not present, so search returns -1 / Not Found.",
            "35 goes at index 3, so 40 and 50 shift right.",
            "Access by index is O(1), so it is the fastest.",
            "In a fixed-size full array, insertion causes overflow or fails."
    };

    private final String[] quizQuestions = {
            "After inserting 50 at end of [10, 20, 30], pick the correct final array.",
            "After deleting index 1 from [10, 20, 30, 40], pick the correct final array.",
            "Which visual option shows correct access of index 2?",
            "Which operation is O(1) in array?",
            "Which visual option shows wrong insertion at index 1?"
    };

    private final String[][] quizOptions = {
            {"[10, 20, 30, 50]", "[50, 10, 20, 30]", "[10, 50, 20, 30]", "[10, 20, 50, 30]"},
            {"[10, 30, 40]", "[20, 30, 40]", "[10, 20, 40]", "[10, 20, 30]"},
            {"Index 2 → 30", "Index 2 → 20", "Index 2 → 40", "Index 2 → 10"},
            {"Access by index", "Insert at start", "Delete at start", "Search unsorted array"},
            {"[10, 20, 25, 30]", "[10, 25, 20, 30]", "[5, 10, 20, 30]", "[10, 20, 30, 50]"}
    };

    private final int[] correctQuizAnswers = {0, 0, 0, 0, 0};

    public ArrayLevelScreen(GameManager gameManager) {
        this.gameManager = gameManager;
        this.arrayLevel = new ArrayLevel();

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
        Label titleLabel = new Label("Level 1: Arrays Lab");
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

        arrayBox = new HBox(12);
        arrayBox.setAlignment(Pos.CENTER);
        arrayBox.setPadding(new Insets(20));
        arrayBox.setStyle(
            "-fx-background-color: " + Style.COLOR_CARD_DARK + ";" +
                "-fx-border-color: " + Style.COLOR_ACCENT + ";" +
                        "-fx-border-width: 2;" +
                        "-fx-background-radius: 16;" +
                        "-fx-border-radius: 16;"
        );
        UiMotion.applyHoverScale(arrayBox, 1.02, 160);

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

        codeLabel = new TextArea("Click a learning button to view details from ArrayLevel.java.");
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
        stageLabel.setText("Learning Mode: Understand Arrays First");
        mainContent.getChildren().clear();

        instructionLabel.setText(
                "This is the learning area. The screen shows visuals and buttons. The actual array logic and explanations come from ArrayLevel.java."
        );

        drawArray(new int[]{10, 20, 30, 40}, -1);

        Button conceptButton = operationButton("Concept");
        Button accessButton = operationButton("Access by Index");
        Button insertEndButton = operationButton("Insert End");
        Button insertMiddleButton = operationButton("Insert Middle");
        Button insertStartButton = operationButton("Insert Start");
        Button deleteButton = operationButton("Delete");
        Button findButton = operationButton("Find");
        Button edgeButton = operationButton("Edge Cases");

        conceptButton.setOnAction(e -> showConceptLearning());
        accessButton.setOnAction(e -> showAccessLearning());
        insertEndButton.setOnAction(e -> showInsertEndLearning());
        insertMiddleButton.setOnAction(e -> showInsertMiddleLearning());
        insertStartButton.setOnAction(e -> showInsertStartLearning());
        deleteButton.setOnAction(e -> showDeleteLearning());
        findButton.setOnAction(e -> showFindLearning());
        edgeButton.setOnAction(e -> showEdgeCasesLearning());

        HBox row1 = new HBox(12, conceptButton, accessButton, insertEndButton, insertMiddleButton);
        row1.setAlignment(Pos.CENTER);

        HBox row2 = new HBox(12, insertStartButton, deleteButton, findButton, edgeButton);
        row2.setAlignment(Pos.CENTER);

        VBox labelCard = teachingCard(
                "Visual Labels",
                "Every array element shows:\n" +
                        "• VALUE: actual data stored in the box\n" +
                        "• INDEX: position number below the box\n" +
                        "• POSITION: First, Second, Third, etc.\n" +
                        "• SIZE: total number of active elements"
        );

        Button startPuzzlesButton = new Button("Start 15 Visual Puzzles");
        startPuzzlesButton.setStyle(buttonStyle(Style.COLOR_PRIMARY, "#0B1020"));
        startPuzzlesButton.setOnAction(e -> startPuzzles());
        UiMotion.applyHoverScale(startPuzzlesButton, 1.04, 130);

        mainContent.getChildren().addAll(
                instructionLabel,
                arrayBox,
                row1,
                row2,
                statusLabel,
                feedbackLabel,
                labelCard,
                startPuzzlesButton
        );

        showConceptLearning();
    }

    private void showConceptLearning() {
        drawArray(new int[]{10, 20, 30, 40}, -1);

        statusLabel.setText("Array Concept: One variable name stores multiple values in order.");
        feedbackLabel.setText("Learning note: Arrays use indexes. Index starts from 0, not 1.");

        showCode("ARRAY CONCEPT", arrayLevel.getConceptExplanation());
    }

    private void showAccessLearning() {
        drawArray(new int[]{10, 20, 30, 40}, 2);

        statusLabel.setText("Access by Index: Read value directly from a position.");
        feedbackLabel.setText("Learning note: Accessing index 2 gives value 30. Time complexity is O(1).");

        showCode("ACCESS BY INDEX", arrayLevel.getAccessExplanation());
    }

    private void showInsertEndLearning() {
        drawArray(new int[]{10, 20, 30, 50}, 3);

        statusLabel.setText("Insert at End: Add new value after the last element.");
        feedbackLabel.setText("Learning note: Insert at end is usually simple because no shifting is needed if space exists.");

        showCode("INSERT AT END", arrayLevel.getInsertEndExplanation());
    }

    private void showInsertMiddleLearning() {
        drawArray(new int[]{10, 25, 20, 30}, 1);

        statusLabel.setText("Insert at Middle: Shift elements right, then place new value.");
        feedbackLabel.setText("Learning note: Inserting 25 at index 1 shifts 20 and 30 to the right.");

        showCode("INSERT AT MIDDLE", arrayLevel.getInsertMiddleExplanation());
    }

    private void showInsertStartLearning() {
        drawArray(new int[]{5, 10, 20, 30}, 0);

        statusLabel.setText("Insert at Start: Shift all elements right.");
        feedbackLabel.setText("Learning note: Insert at start is expensive because every element moves one step right.");

        showCode("INSERT AT START", arrayLevel.getInsertStartExplanation());
    }

    private void showDeleteLearning() {
        drawArray(new int[]{10, 30, 40}, 1);

        statusLabel.setText("Delete: Remove value and shift left.");
        feedbackLabel.setText("Learning note: Deleting index 1 removes 20. Then 30 and 40 shift left.");

        showCode("DELETE OPERATION", arrayLevel.getDeleteExplanation());
    }

    private void showFindLearning() {
        drawArray(new int[]{10, 20, 30, 40}, 2);

        statusLabel.setText("Find / Linear Search: Check values one by one.");
        feedbackLabel.setText("Learning note: Searching 30 checks from index 0 until value is found at index 2.");

        showCode("LINEAR SEARCH", arrayLevel.getFindExplanation());
    }

    private void showEdgeCasesLearning() {
        drawArray(new int[]{10, 20, 30, 40}, -1);

        statusLabel.setText("Edge Cases: Special conditions where code may fail.");
        feedbackLabel.setText("Learning note: Always check invalid index, full array, empty array, and missing values.");

        showCode("ARRAY EDGE CASES", arrayLevel.getEdgeCases());
    }

    private void startPuzzles() {
        currentPuzzle = 0;
        energy = 3;
        keysCollected = 0;
        points = 0;
        showPuzzle();
    }

    private void showPuzzle() {
        puzzleAnswered = false;

        stageLabel.setText("Puzzle Mode: Visual Challenge");
        mainContent.getChildren().clear();

        drawPuzzleVisual(currentPuzzle);

        Label title = new Label("Array Puzzle " + (currentPuzzle + 1) + "/15: " + puzzleTitles[currentPuzzle]);
        title.setStyle("-fx-text-fill: #00D4FF; -fx-font-size: 31px; -fx-font-weight: bold;");

        Label difficulty = new Label("Difficulty: " + puzzleDifficulties[currentPuzzle] + "    Points Available: 50");
        difficulty.setStyle("-fx-text-fill: #FACC15; -fx-font-size: 18px; -fx-font-weight: bold;");

        Label challenge = new Label("Challenge: " + puzzleChallenges[currentPuzzle]);
        challenge.setWrapText(true);
        challenge.setMaxWidth(900);
        challenge.setAlignment(Pos.CENTER);
        challenge.setStyle("-fx-text-fill: white; -fx-font-size: 21px; -fx-font-weight: bold;");

        HBox stats = new HBox(16);
        stats.setAlignment(Pos.CENTER);
        stats.getChildren().addAll(
                statCard("Energy", getEnergyText(), "#FACC15"),
                statCard("Progress", keysCollected + "/15", "#00FF99"),
                statCard("Points", String.valueOf(points), "#00D4FF")
        );

        HBox options = createPuzzleOptions();

        feedbackLabel.setText("Select the correct visual result. No hints are shown before answering.");

        mainContent.getChildren().addAll(
                title,
                difficulty,
                challenge,
                stats,
                arrayBox,
                options,
                feedbackLabel
        );

        showCode(
                "MISSION CONSOLE",
                "Solve the visual puzzle.\n\n" +
                        "No learning hints are shown before your answer.\n" +
                        "If your answer is wrong, the system will explain after the attempt."
        );
    }

    private HBox createPuzzleOptions() {
        Button optionA = puzzleButton("A: " + puzzleOptions[currentPuzzle][0]);
        Button optionB = puzzleButton("B: " + puzzleOptions[currentPuzzle][1]);
        Button optionC = puzzleButton("C: " + puzzleOptions[currentPuzzle][2]);

        optionA.setOnAction(e -> checkPuzzleAnswer(0));
        optionB.setOnAction(e -> checkPuzzleAnswer(1));
        optionC.setOnAction(e -> checkPuzzleAnswer(2));

        HBox options = new HBox(14, optionA, optionB, optionC);
        options.setAlignment(Pos.CENTER);
        return options;
    }

    private void checkPuzzleAnswer(int selected) {
        if (puzzleAnswered) {
            return;
        }

        puzzleAnswered = true;

        if (selected == correctPuzzleAnswers[currentPuzzle]) {
            keysCollected++;
            points += 50;

            feedbackLabel.setText("✓ Correct! +50 points. " + puzzleExplanations[currentPuzzle]);

            showCode(
                    "CORRECT RESULT",
                    puzzleExplanations[currentPuzzle] +
                            "\n\nPoints: +50" +
                            "\nProgress: " + keysCollected + "/15"
            );

            if (currentPuzzle == puzzleTitles.length - 1) {
                Button quizButton = new Button("Start Visual Quiz");
                quizButton.setStyle(buttonStyle("#00FF99", "black"));
                quizButton.setOnAction(e -> showPuzzleComplete());
                mainContent.getChildren().add(quizButton);
            } else {
                Button nextButton = new Button("Next Puzzle");
                nextButton.setStyle(buttonStyle("#00FF99", "black"));
                nextButton.setOnAction(e -> {
                    currentPuzzle++;
                    showPuzzle();
                });
                mainContent.getChildren().add(nextButton);
            }
        } else {
            energy--;

            feedbackLabel.setText("✗ Wrong. " + puzzleExplanations[currentPuzzle]);

            showCode(
                    "VISUAL EXPLANATION AFTER WRONG ANSWER",
                    "Correct visual answer:\n" +
                            puzzleOptions[currentPuzzle][correctPuzzleAnswers[currentPuzzle]] +
                            "\n\nWhy:\n" +
                            puzzleExplanations[currentPuzzle]
            );

            if (energy <= 0) {
                showGameOver();
            } else {
                Button retryButton = new Button("Try Next Puzzle");
                retryButton.setStyle(buttonStyle("#FACC15", "black"));
                retryButton.setOnAction(e -> {
                    if (currentPuzzle == puzzleTitles.length - 1) {
                        showPuzzleComplete();
                    } else {
                        currentPuzzle++;
                        showPuzzle();
                    }
                });
                mainContent.getChildren().add(retryButton);
            }
        }
    }

    private void showPuzzleComplete() {
        mainContent.getChildren().clear();

        Label title = new Label("All Array Puzzles Completed!");
        title.setStyle("-fx-text-fill: #00FF99; -fx-font-size: 36px; -fx-font-weight: bold;");

        Label message = new Label(
                "You completed 15 visual array puzzles. Now start the concept quiz."
        );
        message.setWrapText(true);
        message.setMaxWidth(900);
        message.setAlignment(Pos.CENTER);
        message.setStyle("-fx-text-fill: white; -fx-font-size: 21px;");

        VBox summary = teachingCard(
                "Puzzle Result",
                "Puzzles Completed: " + keysCollected + "/15\n" +
                        "Puzzle Points: " + points + "\n" +
                        "Next: Visual Concept Quiz"
        );

        Button startQuiz = new Button("Start Visual Quiz");
        startQuiz.setStyle(buttonStyle("#00FF99", "black"));
        startQuiz.setOnAction(e -> startQuiz());

        mainContent.getChildren().addAll(title, message, summary, startQuiz);

        showCode(
                "PUZZLES COMPLETED",
                "Now quiz will test concepts using visual options."
        );
    }

    private void startQuiz() {
        currentQuiz = 0;
        quizPoints = 0;
        showQuiz();
    }

    private void showQuiz() {
        quizAnswered = false;

        stageLabel.setText("Quiz Mode: Visual Concept Test");
        mainContent.getChildren().clear();

        Label title = new Label("Array Visual Quiz " + (currentQuiz + 1) + "/5");
        title.setStyle("-fx-text-fill: #00D4FF; -fx-font-size: 32px; -fx-font-weight: bold;");

        Label question = new Label(quizQuestions[currentQuiz]);
        question.setWrapText(true);
        question.setMaxWidth(900);
        question.setAlignment(Pos.CENTER);
        question.setStyle("-fx-text-fill: white; -fx-font-size: 21px; -fx-font-weight: bold;");

        drawQuizVisual(currentQuiz);

        GridPane optionsGrid = new GridPane();
        optionsGrid.setAlignment(Pos.CENTER);
        optionsGrid.setHgap(14);
        optionsGrid.setVgap(14);

        for (int i = 0; i < 4; i++) {
            int selected = i;
            VBox optionCard = visualOptionCard("Option " + (char) ('A' + i), quizOptions[currentQuiz][i]);
            optionCard.setOnMouseClicked(e -> checkQuizAnswer(selected));
            optionsGrid.add(optionCard, i % 2, i / 2);
        }

        feedbackLabel.setText("Select the correct visual option. No hints are shown before answering.");

        mainContent.getChildren().addAll(
                title,
                question,
                arrayBox,
                optionsGrid,
                feedbackLabel
        );

        showCode(
                "QUIZ CONSOLE",
                "Choose the correct visual option.\n\n" +
                        "No explanation is shown until after you answer."
        );
    }

    private void checkQuizAnswer(int selected) {
        if (quizAnswered) {
            return;
        }

        quizAnswered = true;

        if (selected == correctQuizAnswers[currentQuiz]) {
            quizPoints += 25;
            feedbackLabel.setText("✓ Correct! +25 quiz points.");

            showCode(
                    "CORRECT QUIZ ANSWER",
                    "Correct visual option:\n" +
                            quizOptions[currentQuiz][correctQuizAnswers[currentQuiz]]
            );
        } else {
            feedbackLabel.setText("✗ Wrong. Correct visual option is shown in the console.");

            showCode(
                    "CORRECT ANSWER AFTER ATTEMPT",
                    "Correct visual option:\n" +
                            quizOptions[currentQuiz][correctQuizAnswers[currentQuiz]]
            );
        }

        Button nextButton = new Button(currentQuiz == quizQuestions.length - 1 ? "Finish Array Level" : "Next Quiz");
        nextButton.setStyle(buttonStyle("#00FF99", "black"));

        nextButton.setOnAction(e -> {
            if (currentQuiz == quizQuestions.length - 1) {
                showFinalDoor();
            } else {
                currentQuiz++;
                showQuiz();
            }
        });

        mainContent.getChildren().add(nextButton);
    }

    private void showFinalDoor() {
        mainContent.getChildren().clear();
        stageLabel.setText("Final Door");

        Label title = new Label("Arrays Lab Completed");
        title.setStyle("-fx-text-fill: #00FF99; -fx-font-size: 36px; -fx-font-weight: bold;");

        Label message = new Label(
                "You completed learning, visual puzzles, and the visual concept quiz for Arrays."
        );
        message.setWrapText(true);
        message.setMaxWidth(900);
        message.setAlignment(Pos.CENTER);
        message.setStyle("-fx-text-fill: white; -fx-font-size: 21px;");

        drawArray(new int[]{10, 20, 30, 40}, -1);

        VBox summary = teachingCard(
                "Array Mastery Summary",
                "Puzzle Points: " + points + "\n" +
                        "Quiz Points: " + quizPoints + "\n" +
                        "Total Array Points: " + (points + quizPoints) + "\n\n" +
                        "You learned:\n" +
                        "• Indexing\n" +
                        "• Access\n" +
                        "• Insert\n" +
                        "• Delete\n" +
                        "• Find\n" +
                        "• Edge cases\n" +
                        "• Time complexity"
        );

        Button unlockButton = new Button("Unlock Linked List Level");
        unlockButton.setStyle(buttonStyle("#00FF99", "black"));
        unlockButton.setOnAction(e -> gameManager.completeArrayLevel());

        mainContent.getChildren().addAll(title, message, arrayBox, summary, unlockButton);

        showCode(
                "LEVEL COMPLETED",
                "Arrays completed.\n\nNext: Linked List."
        );
    }

    private void showGameOver() {
        mainContent.getChildren().clear();
        stageLabel.setText("Energy Lost");

        Label title = new Label("Energy Finished");
        title.setStyle("-fx-text-fill: #EF4444; -fx-font-size: 36px; -fx-font-weight: bold;");

        Label message = new Label(
                "Your energy finished. Go back to learning or retry puzzles."
        );
        message.setWrapText(true);
        message.setMaxWidth(850);
        message.setAlignment(Pos.CENTER);
        message.setStyle("-fx-text-fill: white; -fx-font-size: 21px;");

        Button retryButton = new Button("Retry Puzzles");
        retryButton.setStyle(buttonStyle("#FACC15", "black"));
        retryButton.setOnAction(e -> startPuzzles());

        Button learningButton = new Button("Back to Learning");
        learningButton.setStyle(buttonStyle("#00D4FF", "black"));
        learningButton.setOnAction(e -> showLearningStage());

        HBox buttons = new HBox(14, retryButton, learningButton);
        buttons.setAlignment(Pos.CENTER);

        mainContent.getChildren().addAll(title, message, buttons);

        showCode(
                "RETRY",
                "Review learning content first, then try puzzles again."
        );
    }

    private void drawPuzzleVisual(int index) {
        if (index == 0 || index == 1 || index == 2) {
            drawArray(new int[]{10, 20, 30}, -1);
        } else if (index >= 3 && index <= 7) {
            drawArray(new int[]{10, 20, 30, 40}, -1);
        } else if (index == 12) {
            drawArray(new int[]{10, 20, 30, 40, 50}, -1);
        } else {
            drawArray(new int[]{10, 20, 30, 40}, -1);
        }
    }

    private void drawQuizVisual(int index) {
        if (index == 0) {
            drawArray(new int[]{10, 20, 30}, -1);
        } else if (index == 1) {
            drawArray(new int[]{10, 20, 30, 40}, 1);
        } else if (index == 2) {
            drawArray(new int[]{10, 20, 30, 40}, 2);
        } else {
            drawArray(new int[]{10, 20, 30, 40}, -1);
        }
    }

    private void drawArray(int[] values, int highlightIndex) {
        arrayBox.getChildren().clear();

        VBox wrapper = new VBox(10);
        wrapper.setAlignment(Pos.CENTER);

        HBox cells = new HBox(12);
        cells.setAlignment(Pos.CENTER);

        for (int i = 0; i < values.length; i++) {
            VBox cell = new VBox(6);
            cell.setAlignment(Pos.CENTER);

            Label valueTitle = new Label("VALUE");
            valueTitle.setStyle("-fx-text-fill: #94A3B8; -fx-font-size: 11px; -fx-font-weight: bold;");

            Label valueLabel = new Label(String.valueOf(values[i]));
            valueLabel.setStyle(
                    "-fx-text-fill: white;" +
                            "-fx-font-size: 27px;" +
                            "-fx-font-weight: bold;" +
                            "-fx-min-width: 90px;" +
                            "-fx-min-height: 62px;" +
                            "-fx-alignment: center;" +
                            "-fx-background-color: " + (i == highlightIndex ? "#0EA5E9" : "#1E293B") + ";" +
                            "-fx-border-color: #00D4FF;" +
                            "-fx-border-width: 2;" +
                            "-fx-background-radius: 12;" +
                            "-fx-border-radius: 12;"
            );

            Label indexLabel = new Label("Index: " + i);
            indexLabel.setStyle("-fx-text-fill: #CBD5E1; -fx-font-size: 14px;");

            Label positionLabel = new Label(getPositionName(i));
            positionLabel.setStyle("-fx-text-fill: #FACC15; -fx-font-size: 13px; -fx-font-weight: bold;");

            cell.getChildren().addAll(valueTitle, valueLabel, indexLabel, positionLabel);
            cells.getChildren().add(cell);
        }

        Label sizeLabel = new Label("Size: " + values.length + " elements");
        sizeLabel.setStyle("-fx-text-fill: #00FF99; -fx-font-size: 16px; -fx-font-weight: bold;");

        wrapper.getChildren().addAll(cells, sizeLabel);
        arrayBox.getChildren().add(wrapper);
    }

    private String getPositionName(int index) {
        if (index == 0) return "First";
        if (index == 1) return "Second";
        if (index == 2) return "Third";
        if (index == 3) return "Fourth";
        if (index == 4) return "Fifth";
        return "Position " + index;
    }

    private VBox visualOptionCard(String title, String visualText) {
        VBox card = new VBox(8);
        card.setAlignment(Pos.CENTER);
        card.setPadding(new Insets(14));
        card.setPrefWidth(350);
        card.setMinHeight(120);
        card.setStyle(
                "-fx-background-color: #0F172A;" +
                        "-fx-border-color: #334155;" +
                        "-fx-border-width: 2;" +
                        "-fx-background-radius: 14;" +
                        "-fx-border-radius: 14;" +
                        "-fx-cursor: hand;"
        );

        Label titleLabel = new Label(title);
        titleLabel.setStyle("-fx-text-fill: #00D4FF; -fx-font-size: 17px; -fx-font-weight: bold;");

        Label visualLabel = new Label(visualText);
        visualLabel.setWrapText(true);
        visualLabel.setAlignment(Pos.CENTER);
        visualLabel.setStyle("-fx-text-fill: white; -fx-font-size: 18px; -fx-font-weight: bold;");

        card.getChildren().addAll(titleLabel, visualLabel);

        card.setOnMouseEntered(e -> card.setStyle(
                "-fx-background-color: #111827;" +
                        "-fx-border-color: #00D4FF;" +
                        "-fx-border-width: 2;" +
                        "-fx-background-radius: 14;" +
                        "-fx-border-radius: 14;" +
                        "-fx-cursor: hand;"
        ));

        card.setOnMouseExited(e -> card.setStyle(
                "-fx-background-color: #0F172A;" +
                        "-fx-border-color: #334155;" +
                        "-fx-border-width: 2;" +
                        "-fx-background-radius: 14;" +
                        "-fx-border-radius: 14;" +
                        "-fx-cursor: hand;"
        ));

        return card;
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
        button.setMaxWidth(310);
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