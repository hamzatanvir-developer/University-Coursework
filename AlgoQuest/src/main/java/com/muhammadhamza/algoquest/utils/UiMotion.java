package com.muhammadhamza.algoquest.utils;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public final class UiMotion {

    private UiMotion() {
    }

    public static Pane createAmbientBackground() {
        Pane layer = new Pane();
        layer.setManaged(false);
        layer.setMouseTransparent(true);

        Circle blob1 = new Circle(240, Color.web("#2ED6C1", 0.18));
        blob1.setLayoutX(180);
        blob1.setLayoutY(140);
        blob1.setEffect(new GaussianBlur(70));

        Circle blob2 = new Circle(200, Color.web("#FFD166", 0.20));
        blob2.setLayoutX(980);
        blob2.setLayoutY(120);
        blob2.setEffect(new GaussianBlur(80));

        Circle blob3 = new Circle(230, Color.web("#4EA8DE", 0.18));
        blob3.setLayoutX(980);
        blob3.setLayoutY(640);
        blob3.setEffect(new GaussianBlur(85));

        Circle blob4 = new Circle(180, Color.web("#FF6B6B", 0.14));
        blob4.setLayoutX(220);
        blob4.setLayoutY(640);
        blob4.setEffect(new GaussianBlur(70));

        layer.getChildren().addAll(blob1, blob2, blob3, blob4);

        floatNode(blob1, 36, 28, 12, 0.0, 0.35, 0.75);
        floatNode(blob2, -30, 24, 14, 0.6, 0.30, 0.70);
        floatNode(blob3, 26, -34, 16, 0.4, 0.28, 0.68);
        floatNode(blob4, -24, -20, 13, 0.8, 0.25, 0.62);

        addBubbles(layer);

        return layer;
    }

    private static void addBubbles(Pane layer) {
        Circle bubble1 = bubble(120, 420, 10, 0.16);
        Circle bubble2 = bubble(360, 240, 8, 0.15);
        Circle bubble3 = bubble(720, 520, 12, 0.16);
        Circle bubble4 = bubble(860, 300, 9, 0.14);
        Circle bubble5 = bubble(540, 140, 7, 0.12);
        Circle bubble6 = bubble(1040, 420, 11, 0.15);

        layer.getChildren().addAll(bubble1, bubble2, bubble3, bubble4, bubble5, bubble6);

        floatNode(bubble1, 10, -14, 9, 0.0, 0.10, 0.30);
        floatNode(bubble2, -8, 12, 8, 0.4, 0.10, 0.28);
        floatNode(bubble3, 12, -10, 10, 0.2, 0.12, 0.32);
        floatNode(bubble4, -10, 10, 9, 0.6, 0.10, 0.26);
        floatNode(bubble5, 8, -12, 11, 0.8, 0.08, 0.24);
        floatNode(bubble6, -12, 8, 10, 0.3, 0.10, 0.28);
    }

    private static Circle bubble(double x, double y, double radius, double opacity) {
        Circle bubble = new Circle(radius, Color.web("#FFFFFF", opacity));
        bubble.setLayoutX(x);
        bubble.setLayoutY(y);
        bubble.setEffect(new GaussianBlur(8));
        return bubble;
    }

    private static void floatNode(Node node, double x, double y, double seconds, double delaySeconds,
                                  double minOpacity, double maxOpacity) {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO,
                        new KeyValue(node.translateXProperty(), 0, Interpolator.EASE_BOTH),
                        new KeyValue(node.translateYProperty(), 0, Interpolator.EASE_BOTH),
                        new KeyValue(node.opacityProperty(), minOpacity, Interpolator.EASE_BOTH)
                ),
                new KeyFrame(Duration.seconds(seconds),
                        new KeyValue(node.translateXProperty(), x, Interpolator.EASE_BOTH),
                        new KeyValue(node.translateYProperty(), y, Interpolator.EASE_BOTH),
                        new KeyValue(node.opacityProperty(), maxOpacity, Interpolator.EASE_BOTH)
                )
        );
        timeline.setAutoReverse(true);
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.setDelay(Duration.seconds(delaySeconds));
        timeline.play();
    }

    public static void applyEntrance(Node node, double delayMs) {
        node.setOpacity(0);
        node.setTranslateY(12);

        FadeTransition fade = new FadeTransition(Duration.millis(520), node);
        fade.setFromValue(0);
        fade.setToValue(1);
        fade.setInterpolator(Interpolator.EASE_OUT);

        TranslateTransition slide = new TranslateTransition(Duration.millis(520), node);
        slide.setFromY(12);
        slide.setToY(0);
        slide.setInterpolator(Interpolator.EASE_OUT);

        ParallelTransition transition = new ParallelTransition(fade, slide);
        transition.setDelay(Duration.millis(delayMs));
        transition.play();
    }

    public static void applySoftPop(Node node) {
        ScaleTransition scale = new ScaleTransition(Duration.millis(180), node);
        scale.setFromX(0.98);
        scale.setFromY(0.98);
        scale.setToX(1.0);
        scale.setToY(1.0);
        scale.setInterpolator(Interpolator.EASE_OUT);

        FadeTransition fade = new FadeTransition(Duration.millis(180), node);
        fade.setFromValue(0.85);
        fade.setToValue(1.0);
        fade.setInterpolator(Interpolator.EASE_OUT);

        new ParallelTransition(scale, fade).play();
    }

    public static void applyFloat(Node node, double amplitude, double seconds, double delayMs) {
        TranslateTransition floaty = new TranslateTransition(Duration.seconds(seconds), node);
        floaty.setFromY(-amplitude);
        floaty.setToY(amplitude);
        floaty.setInterpolator(Interpolator.EASE_BOTH);
        floaty.setAutoReverse(true);
        floaty.setCycleCount(Animation.INDEFINITE);
        floaty.setDelay(Duration.millis(delayMs));
        floaty.play();
    }

    public static void applyPulse(Node node, double minScale, double maxScale, double seconds) {
        ScaleTransition pulse = new ScaleTransition(Duration.seconds(seconds), node);
        pulse.setFromX(minScale);
        pulse.setFromY(minScale);
        pulse.setToX(maxScale);
        pulse.setToY(maxScale);
        pulse.setAutoReverse(true);
        pulse.setCycleCount(Animation.INDEFINITE);
        pulse.setInterpolator(Interpolator.EASE_BOTH);
        pulse.play();
    }

    public static void applyHoverScale(Node node, double scale, double durationMs) {
        applyHoverScale(node, scale, durationMs, null);
    }

    public static void applyHoverScale(Node node, double scale, double durationMs, Color glowColor) {
        node.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> {
            if (node.isDisabled()) {
                return;
            }

            if (glowColor != null) {
                DropShadow shadow = new DropShadow(18, glowColor);
                shadow.setSpread(0.18);
                node.getProperties().put("uiMotion.prevEffect", node.getEffect());
                node.setEffect(shadow);
            }

            animateScale(node, scale, durationMs);
        });

        node.addEventHandler(MouseEvent.MOUSE_EXITED, event -> {
            if (node.isDisabled()) {
                return;
            }

            Object previous = node.getProperties().get("uiMotion.prevEffect");
            if (previous instanceof Effect) {
                node.setEffect((Effect) previous);
            } else if (glowColor != null) {
                node.setEffect(null);
            }

            animateScale(node, 1.0, durationMs);
        });
    }

    public static void applyGlowPulse(Node node, Color color) {
        DropShadow shadow = new DropShadow(16, color);
        shadow.setSpread(0.12);
        node.setEffect(shadow);

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO,
                        new KeyValue(shadow.radiusProperty(), 12, Interpolator.EASE_BOTH),
                        new KeyValue(shadow.spreadProperty(), 0.12, Interpolator.EASE_BOTH)
                ),
                new KeyFrame(Duration.seconds(1.6),
                        new KeyValue(shadow.radiusProperty(), 20, Interpolator.EASE_BOTH),
                        new KeyValue(shadow.spreadProperty(), 0.22, Interpolator.EASE_BOTH)
                )
        );
        timeline.setAutoReverse(true);
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private static void animateScale(Node node, double scale, double durationMs) {
        ScaleTransition transition = new ScaleTransition(Duration.millis(durationMs), node);
        transition.setToX(scale);
        transition.setToY(scale);
        transition.setInterpolator(Interpolator.EASE_OUT);
        transition.play();
    }
}
