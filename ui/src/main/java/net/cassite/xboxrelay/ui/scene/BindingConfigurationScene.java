package net.cassite.xboxrelay.ui.scene;

import io.vproxy.vfx.component.keychooser.KeyChooser;
import io.vproxy.vfx.entity.input.InputData;
import io.vproxy.vfx.entity.input.Key;
import io.vproxy.vfx.manager.font.FontManager;
import io.vproxy.vfx.theme.Theme;
import io.vproxy.vfx.ui.alert.SimpleAlert;
import io.vproxy.vfx.ui.button.FusionButton;
import io.vproxy.vfx.ui.layout.HPadding;
import io.vproxy.vfx.ui.layout.VPadding;
import io.vproxy.vfx.ui.scene.VScene;
import io.vproxy.vfx.ui.scene.VSceneGroup;
import io.vproxy.vfx.ui.scene.VSceneHideMethod;
import io.vproxy.vfx.ui.scene.VSceneRole;
import io.vproxy.vfx.ui.wrapper.FusionW;
import io.vproxy.vfx.ui.wrapper.ThemeLabel;
import io.vproxy.vfx.util.FXUtils;
import io.vproxy.vfx.util.MiscUtils;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import net.cassite.xboxrelay.ui.I18n;
import net.cassite.xboxrelay.ui.Action;
import net.cassite.xboxrelay.ui.MouseMove;
import net.cassite.xboxrelay.ui.MouseWheel;
import net.cassite.xboxrelay.ui.entity.BindingConfiguration;

import java.util.Arrays;

public class BindingConfigurationScene extends VScene {
    private static final int PADDING = 40;
    private static final int PADDING_TOP = 20;
    private static final int PADDING_V = 50;

    private final CheckBox enableKeyPress;
    private final Label chosenKey;
    private final CheckBox enableMouseMove;
    private final TextField moveX;
    private final TextField moveY;
    private final CheckBox enableMouseWheel;
    private final TextField scroll;
    private final CheckBox enableFN;
    private final CheckBox enableFnInput;
    private final CheckBox fnInputCtrl;
    private final CheckBox fnInputAlt;
    private final CheckBox fnInputShift;
    private final Label fnInputChosenKey;

    public BindingConfigurationScene(VSceneGroup sceneGroup, String name,
                                     BindingConfiguration conf) {
        super(VSceneRole.TEMPORARY);
        enableAutoContentWidth();
        getNode().setBackground(new Background(new BackgroundFill(
            Theme.current().sceneBackgroundColor(),
            CornerRadii.EMPTY,
            Insets.EMPTY
        )));

        var vbox = new VBox();
        FXUtils.observeWidth(getContentPane(), vbox, -PADDING * 2);
        vbox.getChildren().add(new VPadding(PADDING_TOP));

        {
            var label = new ThemeLabel(name) {{
                FontManager.get().setFont(this, s -> s.setSize(48));
            }};
            label.setAlignment(Pos.CENTER);
            FXUtils.observeWidth(vbox, label);
            vbox.getChildren().add(label);
            vbox.getChildren().add(new VPadding(PADDING_V));
        }

        var config = conf.getter().get();

        {
            var hbox = new HBox();
            hbox.setSpacing(20);
            hbox.setAlignment(Pos.CENTER_LEFT);
            hbox.setPrefHeight(40);
            vbox.getChildren().add(hbox);
            vbox.getChildren().add(new VPadding(PADDING_V));

            enableKeyPress = new CheckBox(I18n.get().enableKeyPress()) {{
                setTextFill(Theme.current().normalTextColor());
                FontManager.get().setFont(this);
                FXUtils.disableFocusColor(this);
            }};
            enableKeyPress.setPrefWidth(150);
            chosenKey = new Label("Unknown") {{
                setTextFill(Theme.current().normalTextColor());
                FontManager.get().setFont(this);
                setPrefWidth(200);
                setPrefHeight(30);
                setAlignment(Pos.CENTER);
                setBackground(new Background(new BackgroundFill(Color.GRAY,
                    new CornerRadii(4), Insets.EMPTY)));
                setCursor(Cursor.HAND);
            }};
            chosenKey.setOnMouseClicked(e -> {
                var key = new KeyChooser().choose();
                key.ifPresent(value -> chosenKey.setText(value.toString()));
            });

            if (config != null && config.key != null) {
                enableKeyPress.setSelected(true);
                chosenKey.setText(config.key.toString());
            }

            hbox.getChildren().addAll(enableKeyPress, chosenKey);
        }

        {
            var hbox = new HBox();
            hbox.setAlignment(Pos.CENTER_LEFT);
            hbox.setPrefHeight(40);
            vbox.getChildren().add(hbox);
            vbox.getChildren().add(new VPadding(PADDING_V));

            enableMouseMove = new CheckBox(I18n.get().enableMouseMove()) {{
                setTextFill(Theme.current().normalTextColor());
                FontManager.get().setFont(this);
                FXUtils.disableFocusColor(this);
            }};
            enableMouseMove.setPrefWidth(150);
            moveX = new TextField("0") {{
                setPrefWidth(80);
            }};
            moveY = new TextField("0") {{
                setPrefWidth(80);
            }};

            if (config != null && config.mouseMove != null) {
                enableMouseMove.setSelected(true);
                moveX.setText(MiscUtils.roughFloatValueFormat.format(config.mouseMove.x));
                moveY.setText(MiscUtils.roughFloatValueFormat.format(config.mouseMove.y));
            }

            hbox.getChildren().addAll(enableMouseMove,
                new HPadding(20),
                new ThemeLabel("X:"),
                new HPadding(5),
                new FusionW(moveX) {{
                    FontManager.get().setFont(getLabel(), s -> s.setFamily(FontManager.FONT_NAME_JetBrainsMono));
                    setAlignment(Pos.CENTER);
                }},
                new HPadding(20),
                new ThemeLabel("Y:"),
                new HPadding(5),
                new FusionW(moveY) {{
                    FontManager.get().setFont(getLabel(), s -> s.setFamily(FontManager.FONT_NAME_JetBrainsMono));
                    setAlignment(Pos.CENTER);
                }},
                new HPadding(20),
                new ThemeLabel("pixels/sec") {{
                    FontManager.get().setFont(this, s -> s.setFamily(FontManager.FONT_NAME_JetBrainsMono));
                }});
        }

        {
            var hbox = new HBox();
            hbox.setSpacing(20);
            hbox.setAlignment(Pos.CENTER_LEFT);
            hbox.setPrefHeight(40);
            vbox.getChildren().add(hbox);
            vbox.getChildren().add(new VPadding(PADDING_V));

            enableMouseWheel = new CheckBox(I18n.get().enableMouseWheel()) {{
                setTextFill(Theme.current().normalTextColor());
                FontManager.get().setFont(this);
                FXUtils.disableFocusColor(this);
            }};
            enableMouseWheel.setPrefWidth(150);
            scroll = new TextField("0") {{
                setPrefWidth(80);
            }};

            if (config != null && config.mouseWheel != null) {
                enableMouseWheel.setSelected(true);
                scroll.setText(MiscUtils.roughFloatValueFormat.format(config.mouseWheel.wheelAmt));
            }

            hbox.getChildren().addAll(enableMouseWheel,
                new FusionW(scroll) {{
                    FontManager.get().setFont(getLabel(), s -> s.setFamily(FontManager.FONT_NAME_JetBrainsMono));
                    setAlignment(Pos.CENTER);
                }},
                new ThemeLabel("clicks/sec") {{
                    FontManager.get().setFont(this, s -> s.setFamily(FontManager.FONT_NAME_JetBrainsMono));
                }});
        }

        {
            var hbox = new HBox();
            hbox.setSpacing(20);
            hbox.setAlignment(Pos.CENTER_LEFT);
            hbox.setPrefHeight(40);
            vbox.getChildren().add(hbox);
            vbox.getChildren().add(new VPadding(PADDING_V));

            enableFN = new CheckBox(I18n.get().enableFN()) {{
                setTextFill(Theme.current().normalTextColor());
                FontManager.get().setFont(this);
                FXUtils.disableFocusColor(this);
            }};
            enableFN.setPrefWidth(200);

            if (config != null && config.fn) {
                enableFN.setSelected(true);
            }

            hbox.getChildren().addAll(enableFN);
        }

        {
            var hbox = new HBox();
            hbox.setSpacing(20);
            hbox.setAlignment(Pos.CENTER_LEFT);
            hbox.setPrefHeight(40);
            vbox.getChildren().add(hbox);
            vbox.getChildren().add(new VPadding(PADDING_V));

            enableFnInput = new CheckBox(I18n.get().enableFnInput()) {{
                setTextFill(Theme.current().normalTextColor());
                FontManager.get().setFont(this);
                FXUtils.disableFocusColor(this);
            }};
            enableFnInput.setPrefWidth(150);

            fnInputCtrl = new CheckBox("ctrl") {{
                setTextFill(Theme.current().normalTextColor());
                FontManager.get().setFont(this);
                FXUtils.disableFocusColor(this);
            }};
            fnInputAlt = new CheckBox("alt") {{
                setTextFill(Theme.current().normalTextColor());
                FontManager.get().setFont(this);
                FXUtils.disableFocusColor(this);
            }};
            fnInputShift = new CheckBox("shift") {{
                setTextFill(Theme.current().normalTextColor());
                FontManager.get().setFont(this);
                FXUtils.disableFocusColor(this);
            }};

            fnInputChosenKey = new Label("Unknown") {{
                setTextFill(Theme.current().normalTextColor());
                FontManager.get().setFont(this);
                setPrefWidth(200);
                setPrefHeight(30);
                setAlignment(Pos.CENTER);
                setBackground(new Background(new BackgroundFill(Color.GRAY,
                    new CornerRadii(4), Insets.EMPTY)));
                setCursor(Cursor.HAND);
            }};
            fnInputChosenKey.setOnMouseClicked(e -> {
                var key = new KeyChooser().choose();
                key.ifPresent(value -> fnInputChosenKey.setText(value.toString()));
            });

            if (config != null && config.fnInput != null) {
                enableFnInput.setSelected(true);
                fnInputCtrl.setSelected(config.fnInput.ctrl);
                fnInputAlt.setSelected(config.fnInput.alt);
                fnInputShift.setSelected(config.fnInput.shift);
                fnInputChosenKey.setText(config.fnInput.key.toString());
            }

            hbox.getChildren().addAll(enableFnInput,
                fnInputCtrl, fnInputAlt, fnInputShift,
                fnInputChosenKey);
        }

        {
            var allChecks = Arrays.asList(
                enableKeyPress,
                enableMouseMove,
                enableMouseWheel,
                enableFN
            );
            for (var c : allChecks) {
                c.setOnAction(e -> {
                    if (!c.isSelected()) {
                        return;
                    }
                    for (var cc : allChecks) {
                        if (c == cc) continue;
                        cc.setSelected(false);
                    }
                });
            }
        }

        var okButton = new FusionButton(I18n.get().applyConfButton());
        okButton.setPrefWidth(120);
        okButton.setPrefHeight(60);
        okButton.setOnAction(e -> {
            Action action;
            if (enableKeyPress.isSelected()) {
                var text = chosenKey.getText();
                var key = new Key(text);
                if (!key.isValid()) {
                    SimpleAlert.showAndWait(Alert.AlertType.ERROR, I18n.get().invalidKey());
                    return;
                }
                action = new Action(key);
            } else if (enableMouseMove.isSelected()) {
                var xText = moveX.getText();
                var yText = moveY.getText();
                double x;
                try {
                    x = Double.parseDouble(xText);
                } catch (NumberFormatException ex) {
                    SimpleAlert.showAndWait(Alert.AlertType.ERROR, I18n.get().invalidMoveX());
                    return;
                }
                double y;
                try {
                    y = Double.parseDouble(yText);
                } catch (NumberFormatException ex) {
                    SimpleAlert.showAndWait(Alert.AlertType.ERROR, I18n.get().invalidMoveY());
                    return;
                }
                action = new Action(new MouseMove(x, y));
            } else if (enableMouseWheel.isSelected()) {
                var amtText = scroll.getText();
                double amt;
                try {
                    amt = Double.parseDouble(amtText);
                } catch (NumberFormatException ex) {
                    SimpleAlert.showAndWait(Alert.AlertType.ERROR, I18n.get().invalidMouseWheel());
                    return;
                }
                action = new Action(new MouseWheel(amt));
            } else if (enableFN.isSelected()) {
                action = Action.newFn();
            } else {
                action = null;
            }
            if (enableFnInput.isSelected()) {
                if (action == null) {
                    action = Action.newEmpty();
                }
                action.fnInput = new InputData();
                action.fnInput.ctrl = fnInputCtrl.isSelected();
                action.fnInput.alt = fnInputAlt.isSelected();
                action.fnInput.shift = fnInputShift.isSelected();
                action.fnInput.key = new Key(fnInputChosenKey.getText());
            }
            conf.setter().accept(action);

            sceneGroup.hide(this, VSceneHideMethod.TO_RIGHT);
            FXUtils.runDelay(VScene.ANIMATION_DURATION_MILLIS, () -> sceneGroup.removeScene(this));
        });

        var okButtonHBoxPadding = new Pane();
        FXUtils.observeWidth(vbox, okButtonHBoxPadding, -120);
        vbox.getChildren().add(new HBox(okButtonHBoxPadding, okButton));

        getContentPane().getChildren().add(new HBox(
            new HPadding(PADDING),
            vbox
        ));
    }
}
