package net.cassite.xboxrelay.ui.scene;

import io.vproxy.vfx.animation.AnimationGraph;
import io.vproxy.vfx.animation.AnimationGraphBuilder;
import io.vproxy.vfx.animation.AnimationNode;
import io.vproxy.vfx.theme.Theme;
import io.vproxy.vfx.ui.button.FusionButton;
import io.vproxy.vfx.ui.scene.*;
import io.vproxy.vfx.ui.slider.SliderDirection;
import io.vproxy.vfx.ui.slider.VRangeSlider;
import io.vproxy.vfx.ui.wrapper.ThemeLabel;
import io.vproxy.vfx.util.FXUtils;
import io.vproxy.vfx.util.algebradata.ColorData;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import net.cassite.xboxrelay.ui.I18n;
import net.cassite.xboxrelay.ui.entity.BindingConfiguration;
import net.cassite.xboxrelay.ui.entity.StickConfiguration;

public class StickConfigurationScene extends VScene {
    private static final Color buttonColor = new Color(0x4e / 255d, 0xd4 / 255d, 0x9b / 255d, 1);
    private static final int SLIDER_LEN = 270;
    private static final int PADDING = 5;
    private static final int RADIUS = 50;
    private static final int MAX_VALUE = 32768;

    private final StickConfiguration conf;
    private final AnimationNode<ColorData> normalNode = new AnimationNode<>("normal", new ColorData(Theme.current().sceneBackgroundColor()));
    private final AnimationNode<ColorData> greenNode = new AnimationNode<>("green", new ColorData(buttonColor));
    private final AnimationGraph<ColorData> animation;

    public StickConfigurationScene(VSceneGroup sceneGroup, String name, StickConfiguration conf) {
        super(VSceneRole.TEMPORARY);
        enableAutoContentWidthHeight();
        getNode().setBackground(new Background(new BackgroundFill(
            Theme.current().sceneBackgroundColor(),
            CornerRadii.EMPTY,
            Insets.EMPTY
        )));

        this.conf = conf;

        var pane = new Pane();
        pane.setPrefWidth((SLIDER_LEN + PADDING + RADIUS) * 2);
        pane.setPrefHeight((SLIDER_LEN + PADDING + RADIUS) * 2);

        FXUtils.observeWidthHeightCenter(getContentPane(), pane);
        getContentPane().getChildren().add(pane);

        var okButton = new FusionButton();
        okButton.setText(I18n.get().applyConfButton());
        okButton.setOnAction(e -> {
            sceneGroup.hide(this, VSceneHideMethod.TO_RIGHT);
            FXUtils.runDelay(VScene.ANIMATION_DURATION_MILLIS, () -> sceneGroup.removeScene(this));
        });
        okButton.setPrefWidth(120);
        okButton.setPrefHeight(60);
        okButton.setLayoutX(pane.getPrefWidth() - 120);
        okButton.setLayoutY(pane.getPrefHeight() - 60);

        var circle = new Circle();

        animation = AnimationGraphBuilder
            .simpleTwoNodeGraph(normalNode, greenNode, 300)
            .setApply((from, to, d) -> circle.setFill(d.getColor()))
            .build(normalNode);

        circle.setRadius(RADIUS);
        circle.setLayoutX(SLIDER_LEN + PADDING + RADIUS);
        circle.setLayoutY(SLIDER_LEN + PADDING + RADIUS);
        circle.setStrokeWidth(0.5);
        circle.setStroke(Theme.current().borderColor());
        circle.setCursor(Cursor.HAND);
        circle.setOnMouseEntered(e -> animation.play(greenNode));
        circle.setOnMouseExited(e -> animation.play(normalNode));
        circle.setOnMouseClicked(e -> {
            var scene = new BindingConfigurationScene(sceneGroup, name, conf.binding());
            sceneGroup.addScene(scene, VSceneHideMethod.TO_RIGHT);
            sceneGroup.show(scene, VSceneShowMethod.FROM_RIGHT);
        });

        var label = new ThemeLabel(name);
        label.setMouseTransparent(true);
        FXUtils.observeWidthHeightCenter(pane, label);

        var top = new VRangeSlider(SliderDirection.BOTTOM_TO_TOP);
        {
            top.setLength(SLIDER_LEN);
            top.setLayoutX(SLIDER_LEN + PADDING + RADIUS - VRangeSlider.BUTTON_RADIUS);
            top.setLayoutY(0);
            top.setMinPercentage(conf.yMinPosGetter().getAsInt() / (double) MAX_VALUE);
            top.setMaxPercentage(conf.yMaxPosGetter().getAsInt() / (double) MAX_VALUE);
            top.setMinValueTransform(v -> "" + transformValue(v));
            top.setMaxValueTransform(v -> "" + transformValue(v));
            top.minPercentageProperty().addListener((ob, old, now) -> {
                if (now == null) {
                    return;
                }
                updateTopMin(now.doubleValue());
            });
            top.maxPercentageProperty().addListener((ob, old, now) -> {
                if (now == null) {
                    return;
                }
                updateTopMax(now.doubleValue());
            });

            top.setMinOnAction(e -> {
                var scene = new BindingConfigurationScene(sceneGroup, name + "-Top-Min", new BindingConfiguration(
                    conf.yMinGetter(), conf.yMinSetter()
                ));
                sceneGroup.addScene(scene, VSceneHideMethod.TO_RIGHT);
                sceneGroup.show(scene, VSceneShowMethod.FROM_RIGHT);
            });
            top.setMaxOnAction(e -> {
                var scene = new BindingConfigurationScene(sceneGroup, name + "-Top-Max", new BindingConfiguration(
                    conf.yMaxGetter(), conf.yMaxSetter()
                ));
                sceneGroup.addScene(scene, VSceneHideMethod.TO_RIGHT);
                sceneGroup.show(scene, VSceneShowMethod.FROM_RIGHT);
            });
        }

        var bottom = new VRangeSlider(SliderDirection.TOP_TO_BOTTOM);
        {
            bottom.setLength(SLIDER_LEN);
            bottom.setLayoutX(SLIDER_LEN + PADDING + RADIUS - VRangeSlider.BUTTON_RADIUS);
            bottom.setLayoutY(SLIDER_LEN + PADDING + RADIUS + RADIUS + PADDING);
            bottom.setMinPercentage(conf.yBMinPosGetter().getAsInt() / (double) MAX_VALUE);
            bottom.setMaxPercentage(conf.yBMaxPosGetter().getAsInt() / (double) MAX_VALUE);
            bottom.setMinValueTransform(v -> "" + transformValue(v));
            bottom.setMaxValueTransform(v -> "" + transformValue(v));
            bottom.minPercentageProperty().addListener((ob, old, now) -> {
                if (now == null) {
                    return;
                }
                updateBottomMin(now.doubleValue());
            });
            bottom.maxPercentageProperty().addListener((ob, old, now) -> {
                if (now == null) {
                    return;
                }
                updateBottomMax(now.doubleValue());
            });

            bottom.setMinOnAction(e -> {
                var scene = new BindingConfigurationScene(sceneGroup, name + "-Bottom-Min", new BindingConfiguration(
                    conf.yBMinGetter(), conf.yBMinSetter()
                ));
                sceneGroup.addScene(scene, VSceneHideMethod.TO_RIGHT);
                sceneGroup.show(scene, VSceneShowMethod.FROM_RIGHT);
            });
            bottom.setMaxOnAction(e -> {
                var scene = new BindingConfigurationScene(sceneGroup, name + "-Bottom-Max", new BindingConfiguration(
                    conf.yBMaxGetter(), conf.yBMaxSetter()
                ));
                sceneGroup.addScene(scene, VSceneHideMethod.TO_RIGHT);
                sceneGroup.show(scene, VSceneShowMethod.FROM_RIGHT);
            });
        }

        var left = new VRangeSlider(SliderDirection.RIGHT_TO_LEFT);
        {
            left.setLength(SLIDER_LEN);
            left.setLayoutX(0);
            left.setLayoutY(SLIDER_LEN + PADDING + RADIUS - VRangeSlider.BUTTON_RADIUS);
            left.setMinPercentage(conf.xBMinPosGetter().getAsInt() / (double) MAX_VALUE);
            left.setMaxPercentage(conf.xBMaxPosGetter().getAsInt() / (double) MAX_VALUE);
            left.setMinValueTransform(v -> "" + transformValue(v));
            left.setMaxValueTransform(v -> "" + transformValue(v));
            left.minPercentageProperty().addListener((ob, old, now) -> {
                if (now == null) {
                    return;
                }
                updateLeftMin(now.doubleValue());
            });
            left.maxPercentageProperty().addListener((ob, old, now) -> {
                if (now == null) {
                    return;
                }
                updateLeftMax(now.doubleValue());
            });

            left.setMinOnAction(e -> {
                var scene = new BindingConfigurationScene(sceneGroup, name + "-Left-Min", new BindingConfiguration(
                    conf.xBMinGetter(), conf.xBMinSetter()
                ));
                sceneGroup.addScene(scene, VSceneHideMethod.TO_RIGHT);
                sceneGroup.show(scene, VSceneShowMethod.FROM_RIGHT);
            });
            left.setMaxOnAction(e -> {
                var scene = new BindingConfigurationScene(sceneGroup, name + "-Left-Max", new BindingConfiguration(
                    conf.xBMaxGetter(), conf.xBMaxSetter()
                ));
                sceneGroup.addScene(scene, VSceneHideMethod.TO_RIGHT);
                sceneGroup.show(scene, VSceneShowMethod.FROM_RIGHT);
            });
        }

        var right = new VRangeSlider(SliderDirection.LEFT_TO_RIGHT);
        {
            right.setLength(SLIDER_LEN);
            right.setLayoutX(SLIDER_LEN + PADDING + RADIUS + RADIUS + PADDING);
            right.setLayoutY(SLIDER_LEN + PADDING + RADIUS - VRangeSlider.BUTTON_RADIUS);
            right.setMinPercentage(conf.xMinPosGetter().getAsInt() / (double) MAX_VALUE);
            right.setMaxPercentage(conf.xMaxPosGetter().getAsInt() / (double) MAX_VALUE);
            right.setMinValueTransform(v -> "" + transformValue(v));
            right.setMaxValueTransform(v -> "" + transformValue(v));
            right.minPercentageProperty().addListener((ob, old, now) -> {
                if (now == null) {
                    return;
                }
                updateRightMin(now.doubleValue());
            });
            right.maxPercentageProperty().addListener((ob, old, now) -> {
                if (now == null) {
                    return;
                }
                updateRightMax(now.doubleValue());
            });

            right.setMinOnAction(e -> {
                var scene = new BindingConfigurationScene(sceneGroup, name + "-Right-Min", new BindingConfiguration(
                    conf.xMinGetter(), conf.xMinSetter()
                ));
                sceneGroup.addScene(scene, VSceneHideMethod.TO_RIGHT);
                sceneGroup.show(scene, VSceneShowMethod.FROM_RIGHT);
            });
            right.setMaxOnAction(e -> {
                var scene = new BindingConfigurationScene(sceneGroup, name + "-Right-Max", new BindingConfiguration(
                    conf.xMaxGetter(), conf.xMaxSetter()
                ));
                sceneGroup.addScene(scene, VSceneHideMethod.TO_RIGHT);
                sceneGroup.show(scene, VSceneShowMethod.FROM_RIGHT);
            });
        }

        pane.getChildren().addAll(circle, label, top, bottom, left, right, okButton);
    }

    private int transformValue(double v) {
        return (int) (v * MAX_VALUE);
    }

    private void updateTopMin(double v) {
        var min = transformValue(v);
        conf.yMinPosSetter().accept(min);
    }

    private void updateTopMax(double v) {
        var max = transformValue(v);
        conf.yMaxPosSetter().accept(max);
    }

    private void updateBottomMin(double v) {
        var min = transformValue(v);
        conf.yBMinPosSetter().accept(min);
    }

    private void updateBottomMax(double v) {
        var max = transformValue(v);
        conf.yBMaxPosSetter().accept(max);
    }

    private void updateLeftMin(double v) {
        var min = transformValue(v);
        conf.xBMinPosSetter().accept(min);
    }

    private void updateLeftMax(double v) {
        var max = transformValue(v);
        conf.xBMaxPosSetter().accept(max);
    }

    private void updateRightMin(double v) {
        var min = transformValue(v);
        conf.xMinPosSetter().accept(min);
    }

    private void updateRightMax(double v) {
        var max = transformValue(v);
        conf.xMaxPosSetter().accept(max);
    }
}
