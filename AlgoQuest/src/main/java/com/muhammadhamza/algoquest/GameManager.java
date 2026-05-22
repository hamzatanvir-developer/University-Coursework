package com.muhammadhamza.algoquest;

import com.muhammadhamza.algoquest.screens.ArrayLevelScreen;
import com.muhammadhamza.algoquest.screens.CompletionScreen;
import com.muhammadhamza.algoquest.screens.GraphsLevelScreen;
import com.muhammadhamza.algoquest.screens.HashMapLevelScreen;
import com.muhammadhamza.algoquest.screens.HashTableLevelScreen;
import com.muhammadhamza.algoquest.screens.HeapsLevelScreen;
import com.muhammadhamza.algoquest.screens.LevelMapScreen;
import com.muhammadhamza.algoquest.screens.LinkedListLevelScreen;
import com.muhammadhamza.algoquest.screens.QueueLevelScreen;
import com.muhammadhamza.algoquest.screens.StackLevelScreen;
import com.muhammadhamza.algoquest.screens.StartScreen;
import com.muhammadhamza.algoquest.screens.TreesLevelScreen;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GameManager {

    private final Stage stage;

    private boolean arrayLevelCompleted = false;
    private boolean linkedListLevelUnlocked = false;
    private boolean stackLevelUnlocked = false;
    private boolean queueLevelUnlocked = false;
    private boolean treesLevelUnlocked = false;
    private boolean graphsLevelUnlocked = false;
    private boolean heapsLevelUnlocked = false;
    private boolean hashMapLevelUnlocked = false;
    private boolean hashTableLevelUnlocked = false;

    private static final int SCREEN_WIDTH = 1200;
    private static final int SCREEN_HEIGHT = 800;

    public GameManager(Stage stage) {
        this.stage = stage;
    }

    public void showStartScreen() {
        StartScreen startScreen = new StartScreen(this);
        Scene scene = new Scene(startScreen.getView(), SCREEN_WIDTH, SCREEN_HEIGHT);
        stage.setTitle("AlgoQuest - Data Structures Learning Game");
        stage.setScene(scene);
        stage.show();
    }

    public void showLevelMap() {
        LevelMapScreen levelMapScreen = new LevelMapScreen(
                this,
                arrayLevelCompleted,
                linkedListLevelUnlocked,
                stackLevelUnlocked,
                queueLevelUnlocked,
                treesLevelUnlocked,
                graphsLevelUnlocked,
                heapsLevelUnlocked,
                hashMapLevelUnlocked,
                hashTableLevelUnlocked
        );

        Scene scene = new Scene(levelMapScreen.getView(), SCREEN_WIDTH, SCREEN_HEIGHT);
        stage.setTitle("AlgoQuest - Level Map");
        stage.setScene(scene);
        stage.show();
    }

    public void showArrayLevel() {
        ArrayLevelScreen arrayLevelScreen = new ArrayLevelScreen(this);
        Scene scene = new Scene(arrayLevelScreen.getView(), SCREEN_WIDTH, SCREEN_HEIGHT);
        stage.setTitle("AlgoQuest - Arrays Lab");
        stage.setScene(scene);
        stage.show();
    }

    public void showLinkedListLevel() {
        LinkedListLevelScreen linkedListLevelScreen = new LinkedListLevelScreen(this);
        Scene scene = new Scene(linkedListLevelScreen.getView(), SCREEN_WIDTH, SCREEN_HEIGHT);
        stage.setTitle("AlgoQuest - Linked List Path");
        stage.setScene(scene);
        stage.show();
    }

    public void showStackLevel() {
        StackLevelScreen stackLevelScreen = new StackLevelScreen(this);
        Scene scene = new Scene(stackLevelScreen.getView(), SCREEN_WIDTH, SCREEN_HEIGHT);
        stage.setTitle("AlgoQuest - Stack Tower");
        stage.setScene(scene);
        stage.show();
    }

    public void showQueueLevel() {
        QueueLevelScreen queueLevelScreen = new QueueLevelScreen(this);
        Scene scene = new Scene(queueLevelScreen.getView(), SCREEN_WIDTH, SCREEN_HEIGHT);
        stage.setTitle("AlgoQuest - Queue Gate");
        stage.setScene(scene);
        stage.show();
    }

    public void showTreesLevel() {
        TreesLevelScreen treesLevelScreen = new TreesLevelScreen(this);
        Scene scene = new Scene(treesLevelScreen.getView(), SCREEN_WIDTH, SCREEN_HEIGHT);
        stage.setTitle("AlgoQuest - Tree Kingdom");
        stage.setScene(scene);
        stage.show();
    }

    public void showGraphsLevel() {
        GraphsLevelScreen graphsLevelScreen = new GraphsLevelScreen(this);
        Scene scene = new Scene(graphsLevelScreen.getView(), SCREEN_WIDTH, SCREEN_HEIGHT);
        stage.setTitle("AlgoQuest - Graph Realm");
        stage.setScene(scene);
        stage.show();
    }

    public void showHeapsLevel() {
        HeapsLevelScreen heapsLevelScreen = new HeapsLevelScreen(this);
        Scene scene = new Scene(heapsLevelScreen.getView(), SCREEN_WIDTH, SCREEN_HEIGHT);
        stage.setTitle("AlgoQuest - Heap Mountain");
        stage.setScene(scene);
        stage.show();
    }

    public void showHashMapLevel() {
        HashMapLevelScreen hashMapLevelScreen = new HashMapLevelScreen(this);
        Scene scene = new Scene(hashMapLevelScreen.getView(), SCREEN_WIDTH, SCREEN_HEIGHT);
        stage.setTitle("AlgoQuest - HashMap Vault");
        stage.setScene(scene);
        stage.show();
    }

    public void showHashTableLevel() {
        HashTableLevelScreen hashTableLevelScreen = new HashTableLevelScreen(this);
        Scene scene = new Scene(hashTableLevelScreen.getView(), SCREEN_WIDTH, SCREEN_HEIGHT);
        stage.setTitle("AlgoQuest - HashTable Chamber");
        stage.setScene(scene);
        stage.show();
    }

    public void showCompletionScreen() {
        CompletionScreen completionScreen = new CompletionScreen(this);
        Scene scene = new Scene(completionScreen.getView(), SCREEN_WIDTH, SCREEN_HEIGHT);
        stage.setTitle("AlgoQuest - Completed");
        stage.setScene(scene);
        stage.show();
    }

    public void completeArrayLevel() {
        arrayLevelCompleted = true;
        linkedListLevelUnlocked = true;
        showLevelMap();
    }

    public void completeLinkedListLevel() {
        linkedListLevelUnlocked = true;
        stackLevelUnlocked = true;
        showLevelMap();
    }

    public void completeStackLevel() {
        stackLevelUnlocked = true;
        queueLevelUnlocked = true;
        showLevelMap();
    }

    public void completeQueueLevel() {
        queueLevelUnlocked = true;
        treesLevelUnlocked = true;
        showLevelMap();
    }

    public void completeTreesLevel() {
        treesLevelUnlocked = true;
        graphsLevelUnlocked = true;
        showLevelMap();
    }

    public void completeGraphsLevel() {
        graphsLevelUnlocked = true;
        heapsLevelUnlocked = true;
        showLevelMap();
    }

    public void completeHeapsLevel() {
        heapsLevelUnlocked = true;
        hashMapLevelUnlocked = true;
        showLevelMap();
    }

    public void completeHashMapLevel() {
        hashMapLevelUnlocked = true;
        hashTableLevelUnlocked = true;
        showLevelMap();
    }

    public void completeHashTableLevel() {
        hashTableLevelUnlocked = true;
        showCompletionScreen();
    }

    public void resetProgress() {
        arrayLevelCompleted = false;
        linkedListLevelUnlocked = false;
        stackLevelUnlocked = false;
        queueLevelUnlocked = false;
        treesLevelUnlocked = false;
        graphsLevelUnlocked = false;
        heapsLevelUnlocked = false;
        hashMapLevelUnlocked = false;
        hashTableLevelUnlocked = false;

        showStartScreen();
    }
}