package net.cassite.xboxrelay.ui.scene;

import io.vproxy.vfx.theme.Theme;
import io.vproxy.vfx.ui.button.FusionButton;
import io.vproxy.vfx.ui.scene.*;
import io.vproxy.vfx.ui.slider.SliderDirection;
import io.vproxy.vfx.ui.slider.VRangeSlider;
import io.vproxy.vfx.ui.wrapper.ThemeLabel;
import io.vproxy.vfx.util.FXUtils;
import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import net.cassite.xboxrelay.ui.I18n;
import net.cassite.xboxrelay.ui.entity.BindingConfiguration;
import net.cassite.xboxrelay.ui.entity.TriggerConfiguration;

public class TriggerConfigurationScene extends VScene {
    private static final int PADDING_0 = 150;
    private static final int SLIDER_LEN = 270;
    private static final int PADDING = 5;
    private static final int RADIUS = 50;
    private static final int MAX_VALUE = 255;

    private final TriggerConfiguration conf;

    public TriggerConfigurationScene(VSceneGroup sceneGroup, String name, TriggerConfiguration conf) {
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
        circle.setRadius(RADIUS);
        circle.setLayoutX(PADDING_0 + RADIUS);
        circle.setLayoutY(SLIDER_LEN + PADDING + RADIUS);
        circle.setFill(Theme.current().sceneBackgroundColor());
        circle.setStrokeWidth(0.5);
        circle.setStroke(Theme.current().borderColor());
        var label = new ThemeLabel(name);
        var labelBounds = FXUtils.calculateTextBounds(label);
        label.setLayoutX(circle.getLayoutX() - labelBounds.getWidth() / 2);
        label.setLayoutY(circle.getLayoutY() - labelBounds.getHeight() / 2);

        var right = new VRangeSlider(SliderDirection.LEFT_TO_RIGHT);
        {
            right.setLength(SLIDER_LEN);
            right.setLayoutX(PADDING_0 + RADIUS + RADIUS + PADDING);
            right.setLayoutY(SLIDER_LEN + PADDING + RADIUS - VRangeSlider.BUTTON_RADIUS);
            right.setMinPercentage(conf.minPosGetter().getAsInt() / (double) MAX_VALUE);
            right.setMaxPercentage(conf.maxPosGetter().getAsInt() / (double) MAX_VALUE);
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
                var scene = new BindingConfigurationScene(sceneGroup, name + "-Min", new BindingConfiguration(
                    conf.minGetter(), conf.minSetter()
                ));
                sceneGroup.addScene(scene, VSceneHideMethod.TO_RIGHT);
                sceneGroup.show(scene, VSceneShowMethod.FROM_RIGHT);
            });
            right.setMaxOnAction(e -> {
                var scene = new BindingConfigurationScene(sceneGroup, name + "-Max", new BindingConfiguration(
                    conf.maxGetter(), conf.maxSetter()
                ));
                sceneGroup.addScene(scene, VSceneHideMethod.TO_RIGHT);
                sceneGroup.show(scene, VSceneShowMethod.FROM_RIGHT);
            });
        }

        pane.getChildren().addAll(circle, label, right, okButton);
    }

    private int transformValue(double v) {
        return (int) (v * MAX_VALUE);
    }

    private void updateRightMin(double v) {
        var min = transformValue(v);
        conf.minPosSetter().accept(min);
    }

    private void updateRightMax(double v) {
        var max = transformValue(v);
        conf.maxPosSetter().accept(max);
    }
}
