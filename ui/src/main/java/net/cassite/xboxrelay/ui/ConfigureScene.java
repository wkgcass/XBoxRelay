package net.cassite.xboxrelay.ui;

import io.vertx.core.Vertx;
import io.vproxy.base.util.LogType;
import io.vproxy.vfx.animation.AnimationGraph;
import io.vproxy.vfx.animation.AnimationGraphBuilder;
import io.vproxy.vfx.animation.AnimationNode;
import io.vproxy.vfx.manager.font.FontManager;
import io.vproxy.vfx.manager.image.ImageManager;
import io.vproxy.vfx.ui.alert.SimpleAlert;
import io.vproxy.vfx.ui.alert.StackTraceAlert;
import io.vproxy.vfx.ui.button.FusionButton;
import io.vproxy.vfx.ui.pane.FusionPane;
import io.vproxy.vfx.ui.scene.*;
import io.vproxy.vfx.ui.wrapper.FusionW;
import io.vproxy.vfx.ui.wrapper.ThemeLabel;
import io.vproxy.vfx.util.FXUtils;
import io.vproxy.base.util.Logger;
import io.vproxy.vfx.util.algebradata.DoubleData;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import net.cassite.xboxrelay.base.ConfigureMessage;
import net.cassite.xboxrelay.base.DeadZoneSettings;
import net.cassite.xboxrelay.ui.entity.*;
import net.cassite.xboxrelay.ui.prebuilt.DefaultPlan;
import net.cassite.xboxrelay.ui.scene.BindingConfigurationScene;
import net.cassite.xboxrelay.ui.scene.StickConfigurationScene;
import net.cassite.xboxrelay.ui.scene.TriggerConfigurationScene;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class ConfigureScene extends VScene {
    private final Vertx vertx;
    private final Supplier<VSceneGroup> sceneGroupGetter;
    private final FusionButton disconnectButton;
    private final FusionButton connectButton;
    private final TextField address;
    private final ComboBox<String> planComboBox;
    private ClientVerticle client;
    private AutoRobot robot;

    private Plan currentPlan = new DefaultPlan();
    private List<Plan> plans = new ArrayList<>();

    private final List<Node> xboxButtons = new ArrayList<>();
    private final AnimationNode<DoubleData> buttonsOpacity0 = new AnimationNode<>("0", new DoubleData(0));
    private final AnimationNode<DoubleData> buttonsOpacity1 = new AnimationNode<>("0.8", new DoubleData(0.8));
    private final AnimationGraph<DoubleData> buttonsOpacityAnimation = AnimationGraphBuilder
        .simpleTwoNodeGraph(buttonsOpacity0, buttonsOpacity1, 300)
        .setApply((from, to, v) -> {
            for (var n : xboxButtons) {
                n.setOpacity(v.value);
            }
        })
        .build(buttonsOpacity0);

    public ConfigureScene(Vertx vertx, Supplier<VSceneGroup> sceneGroupGetter) {
        super(VSceneRole.MAIN);
        enableAutoContentWidthHeight();

        this.vertx = vertx;
        this.sceneGroupGetter = sceneGroupGetter;

        var topPane = new FusionPane();
        {
            var topButtons = new HBox();
            topButtons.setSpacing(10);

            topPane.getContentPane().getChildren().add(topButtons);

            FXUtils.observeWidth(getContentPane(), topPane.getNode(), -20);
            topPane.getNode().setPrefHeight(60);
            topPane.getNode().setLayoutX(10);
            topPane.getNode().setLayoutY(10);

            FXUtils.observeWidth(topPane.getContentPane(), topButtons);

            var planLabel = new ThemeLabel(I18n.get().planLabel());
            FXUtils.observeHeight(topPane.getContentPane(), planLabel);

            planComboBox = new ComboBox<>();
            FXUtils.observeHeight(topPane.getContentPane(), planComboBox);
            planComboBox.setPrefWidth(200);
            planComboBox.setEditable(true);
            planComboBox.setOnAction(e -> {
                var v = planComboBox.getValue();
                if (v == null) return;
                for (var p : plans) {
                    if (v.equals(p.name)) {
                        applyPlan(p);
                        break;
                    }
                }
            });

            var planComboBoxW = new FusionW(planComboBox);

            var saveButton = new FusionButton(I18n.get().savePlanButton());
            saveButton.setPrefWidth(120);
            FXUtils.observeHeight(topPane.getContentPane(), saveButton);
            saveButton.setOnAction(e -> {
                var n = planComboBox.getValue();
                if (n == null || n.isBlank())
                    return;
                var existing = plans.stream().filter(p -> n.equals(p.name)).findAny().orElse(null);
                if (existing != null) {
                    if (existing.isSystemPreBuilt) {
                        SimpleAlert.showAndWait(Alert.AlertType.ERROR, I18n.get().cannotOverwritePrebuiltPlan());
                        return;
                    }
                    existing.binding = new Binding(currentPlan.binding);
                    existing.deadZoneSettings = new DeadZoneSettings(currentPlan.deadZoneSettings);
                    saveConfig();
                    return;
                }
                var newPlan = new Plan();
                newPlan.name = n;
                newPlan.binding = new Binding(currentPlan.binding);
                newPlan.deadZoneSettings = new DeadZoneSettings(currentPlan.deadZoneSettings);
                planComboBox.getItems().add(n);
                plans.add(newPlan);
                saveConfig();
            });

            var delButton = new FusionButton(I18n.get().deletePlanButton());
            delButton.setPrefWidth(120);
            FXUtils.observeHeight(topPane.getContentPane(), delButton);
            delButton.setOnAction(e -> {
                var n = planComboBox.getValue();
                if (n == null || n.isBlank())
                    return;
                var existing = plans.stream().filter(p -> n.equals(p.name)).findAny().orElse(null);
                if (existing == null)
                    return;
                if (existing.isNotDeletable) {
                    SimpleAlert.showAndWait(Alert.AlertType.ERROR, I18n.get().cannotDeletePlan());
                    return;
                }
                plans.remove(existing);
                planComboBox.getItems().remove(n);
                saveConfig();
            });

            var tableButton = new FusionButton(I18n.get().showTableButton());
            tableButton.setPrefWidth(150);
            FXUtils.observeHeight(topPane.getContentPane(), tableButton);
            tableButton.setOnAction(e -> {
                var scene = new ShowConfigTableScene(sceneGroupGetter.get(), currentPlan);
                sceneGroupGetter.get().addScene(scene, VSceneHideMethod.TO_RIGHT);
                sceneGroupGetter.get().show(scene, VSceneShowMethod.FROM_RIGHT);
            });

            topButtons.getChildren().addAll(planLabel, planComboBoxW, saveButton, delButton, tableButton);
        }

        var imagePane = new Pane();
        {
            var imageView = new ImageView();
            imageView.setImage(ImageManager.get().load("/net/cassite/xboxrelay/ui/res/xbox.png"));
            imageView.setFitWidth(746);
            imageView.setFitHeight(526);
            imageView.setPreserveRatio(true);

            imagePane.setPrefWidth(imageView.getFitWidth());
            imagePane.setPrefHeight(imageView.getFitHeight());
            FXUtils.observeWidthHeightCenter(getContentPane(), imagePane);

            imagePane.setOnMouseEntered(e -> buttonsOpacityAnimation.play(buttonsOpacity1));
            imagePane.setOnMouseExited(e -> buttonsOpacityAnimation.play(buttonsOpacity0));

            imagePane.getChildren().add(imageView);

            createLSBButton(imagePane, imageView);
            createRSBButton(imagePane, imageView);
            createLTButton(imagePane, imageView);
            createRTButton(imagePane, imageView);

            createNormalButton(imagePane, "LB", new BindingConfiguration(
                () -> currentPlan.binding.lb,
                v -> currentPlan.binding.lb = v
            ), new Rectangle() {{
                setLayoutX(77 / 746d * imageView.getFitWidth());
                setLayoutY(120 / 526d * imageView.getFitHeight());
                setWidth(44 / 746d * imageView.getFitWidth());
                setHeight(26 / 526d * imageView.getFitHeight());
            }});

            createNormalButton(imagePane, "RB", new BindingConfiguration(
                () -> currentPlan.binding.rb,
                v -> currentPlan.binding.rb = v
            ), new Rectangle() {{
                setLayoutX(600 / 746d * imageView.getFitWidth());
                setLayoutY(120 / 526d * imageView.getFitHeight());
                setWidth(44 / 746d * imageView.getFitWidth());
                setHeight(26 / 526d * imageView.getFitHeight());
            }});

            createNormalButton(imagePane, "Back", new BindingConfiguration(
                () -> currentPlan.binding.back,
                v -> currentPlan.binding.back = v
            ), new Circle(11) {{
                setLayoutX(301 / 746d * imageView.getFitWidth());
                setLayoutY(212 / 526d * imageView.getFitHeight());
            }});

            createNormalButton(imagePane, "Guide", new BindingConfiguration(
                () -> currentPlan.binding.guide,
                v -> currentPlan.binding.guide = v
            ), new Circle(15) {{
                setLayoutX(360 / 746d * imageView.getFitWidth());
                setLayoutY(212 / 526d * imageView.getFitHeight());
            }});

            createNormalButton(imagePane, "Start", new BindingConfiguration(
                () -> currentPlan.binding.start,
                v -> currentPlan.binding.start = v
            ), new Circle(11) {{
                setLayoutX(420 / 746d * imageView.getFitWidth());
                setLayoutY(212 / 526d * imageView.getFitHeight());
            }});

            createNormalButton(imagePane, "DU", new BindingConfiguration(
                () -> currentPlan.binding.du,
                v -> currentPlan.binding.du = v
            ), new Circle(11) {{
                setLayoutX(275 / 746d * imageView.getFitWidth());
                setLayoutY(267 / 526d * imageView.getFitHeight());
            }});

            createNormalButton(imagePane, "DD", new BindingConfiguration(
                () -> currentPlan.binding.dd,
                v -> currentPlan.binding.dd = v
            ), new Circle(11) {{
                setLayoutX(275 / 746d * imageView.getFitWidth());
                setLayoutY(323 / 526d * imageView.getFitHeight());
            }});

            createNormalButton(imagePane, "DL", new BindingConfiguration(
                () -> currentPlan.binding.dl,
                v -> currentPlan.binding.dl = v
            ), new Circle(11) {{
                setLayoutX(247 / 746d * imageView.getFitWidth());
                setLayoutY(295 / 526d * imageView.getFitHeight());
            }});

            createNormalButton(imagePane, "DR", new BindingConfiguration(
                () -> currentPlan.binding.dr,
                v -> currentPlan.binding.dr = v
            ), new Circle(11) {{
                setLayoutX(303 / 746d * imageView.getFitWidth());
                setLayoutY(295 / 526d * imageView.getFitHeight());
            }});

            createNormalButton(imagePane, "X", new BindingConfiguration(
                () -> currentPlan.binding.x,
                v -> currentPlan.binding.x = v
            ), new Circle(21) {{
                setLayoutX(481 / 746d * imageView.getFitWidth());
                setLayoutY(212 / 526d * imageView.getFitHeight());
            }});

            createNormalButton(imagePane, "Y", new BindingConfiguration(
                () -> currentPlan.binding.y,
                v -> currentPlan.binding.y = v
            ), new Circle(21) {{
                setLayoutX(520 / 746d * imageView.getFitWidth());
                setLayoutY(172 / 526d * imageView.getFitHeight());
            }});

            createNormalButton(imagePane, "A", new BindingConfiguration(
                () -> currentPlan.binding.a,
                v -> currentPlan.binding.a = v
            ), new Circle(21) {{
                setLayoutX(520 / 746d * imageView.getFitWidth());
                setLayoutY(251 / 526d * imageView.getFitHeight());
            }});

            createNormalButton(imagePane, "B", new BindingConfiguration(
                () -> currentPlan.binding.b,
                v -> currentPlan.binding.b = v
            ), new Circle(21) {{
                setLayoutX(560 / 746d * imageView.getFitWidth());
                setLayoutY(212 / 526d * imageView.getFitHeight());
            }});
        }

        var bottomPane = new FusionPane();
        {
            var bottomButtons = new HBox();
            bottomButtons.setSpacing(10);
            bottomButtons.setAlignment(Pos.CENTER_RIGHT);

            bottomPane.getContentPane().getChildren().add(bottomButtons);

            FXUtils.observeWidth(getContentPane(), bottomPane.getNode(), -20);
            bottomPane.getNode().setPrefHeight(60);
            bottomPane.getNode().setLayoutX(10);

            FXUtils.observeWidth(bottomPane.getContentPane(), bottomButtons);

            getContentPane().heightProperty().addListener((ob, old, now) -> {
                if (now == null) return;
                var h = now.doubleValue();
                bottomPane.getNode().setLayoutY(h - 10 - 60);
            });

            disconnectButton = new FusionButton(I18n.get().disconnectButton());
            FXUtils.observeHeight(bottomPane.getContentPane(), disconnectButton);
            disconnectButton.setPrefWidth(150);
            disconnectButton.setDisable(true);
            disconnectButton.setOnAction(e -> stop());

            connectButton = new FusionButton(I18n.get().connectButton());
            FXUtils.observeHeight(bottomPane.getContentPane(), connectButton);
            connectButton.setPrefWidth(120);
            connectButton.setOnAction(e -> start());

            var label = new ThemeLabel(I18n.get().agentAddressLabel());
            label.setAlignment(Pos.CENTER_RIGHT);
            label.setPrefWidth(150);
            FXUtils.observeHeight(bottomPane.getContentPane(), label);

            address = new TextField();
            FontManager.get().setFont(address, s -> s.setFamily(FontManager.FONT_NAME_JetBrainsMono));
            address.setPrefWidth(200);
            FXUtils.observeHeight(bottomPane.getContentPane(), address);

            var addressW = new FusionW(address);
            FontManager.get().setFont(addressW.getLabel(), s -> s.setFamily(FontManager.FONT_NAME_JetBrainsMono));

            bottomButtons.getChildren().addAll(label, addressW, disconnectButton, connectButton);
        }

        getContentPane().getChildren().addAll(imagePane, topPane.getNode(), bottomPane.getNode());
    }

    private void createLSBButton(Pane imagePane, ImageView imageView) {
        createStickButton(imagePane, "LSB",
            new StickConfiguration(
                () -> currentPlan.binding.lsbXMin,
                () -> currentPlan.binding.lsbXMax,
                () -> currentPlan.binding.lsbXBMin,
                () -> currentPlan.binding.lsbXBMax,
                () -> currentPlan.binding.lsbYMin,
                () -> currentPlan.binding.lsbYMax,
                () -> currentPlan.binding.lsbYBMin,
                () -> currentPlan.binding.lsbYBMax,
                v -> currentPlan.binding.lsbXMin = v,
                v -> currentPlan.binding.lsbXMax = v,
                v -> currentPlan.binding.lsbXBMin = v,
                v -> currentPlan.binding.lsbXBMax = v,
                v -> currentPlan.binding.lsbYMin = v,
                v -> currentPlan.binding.lsbYMax = v,
                v -> currentPlan.binding.lsbYBMin = v,
                v -> currentPlan.binding.lsbYBMax = v,
                () -> currentPlan.deadZoneSettings.min.lsbX,
                () -> currentPlan.deadZoneSettings.max.lsbX,
                () -> currentPlan.deadZoneSettings.min.lsbXB,
                () -> currentPlan.deadZoneSettings.max.lsbXB,
                () -> currentPlan.deadZoneSettings.min.lsbY,
                () -> currentPlan.deadZoneSettings.max.lsbY,
                () -> currentPlan.deadZoneSettings.min.lsbYB,
                () -> currentPlan.deadZoneSettings.max.lsbYB,
                v -> currentPlan.deadZoneSettings.min.lsbX = v,
                v -> currentPlan.deadZoneSettings.max.lsbX = v,
                v -> currentPlan.deadZoneSettings.min.lsbXB = v,
                v -> currentPlan.deadZoneSettings.max.lsbXB = v,
                v -> currentPlan.deadZoneSettings.min.lsbY = v,
                v -> currentPlan.deadZoneSettings.max.lsbY = v,
                v -> currentPlan.deadZoneSettings.min.lsbYB = v,
                v -> currentPlan.deadZoneSettings.max.lsbYB = v,
                new BindingConfiguration(
                    () -> currentPlan.binding.tl,
                    v -> currentPlan.binding.tl = v
                )),
            new Circle() {{
                setLayoutX(198 / 746d * imageView.getFitWidth());
                setLayoutY(205 / 526d * imageView.getFitHeight());
                setRadius(30 / 746d * imageView.getFitWidth());
            }});
    }

    private void createRSBButton(Pane imagePane, ImageView imageView) {
        createStickButton(imagePane, "RSB",
            new StickConfiguration(
                () -> currentPlan.binding.rsbXMin,
                () -> currentPlan.binding.rsbXMax,
                () -> currentPlan.binding.rsbXBMin,
                () -> currentPlan.binding.rsbXBMax,
                () -> currentPlan.binding.rsbYMin,
                () -> currentPlan.binding.rsbYMax,
                () -> currentPlan.binding.rsbYBMin,
                () -> currentPlan.binding.rsbYBMax,
                v -> currentPlan.binding.rsbXMin = v,
                v -> currentPlan.binding.rsbXMax = v,
                v -> currentPlan.binding.rsbXBMin = v,
                v -> currentPlan.binding.rsbXBMax = v,
                v -> currentPlan.binding.rsbYMin = v,
                v -> currentPlan.binding.rsbYMax = v,
                v -> currentPlan.binding.rsbYBMin = v,
                v -> currentPlan.binding.rsbYBMax = v,
                () -> currentPlan.deadZoneSettings.min.rsbX,
                () -> currentPlan.deadZoneSettings.max.rsbX,
                () -> currentPlan.deadZoneSettings.min.rsbXB,
                () -> currentPlan.deadZoneSettings.max.rsbXB,
                () -> currentPlan.deadZoneSettings.min.rsbY,
                () -> currentPlan.deadZoneSettings.max.rsbY,
                () -> currentPlan.deadZoneSettings.min.rsbYB,
                () -> currentPlan.deadZoneSettings.max.rsbYB,
                v -> currentPlan.deadZoneSettings.min.rsbX = v,
                v -> currentPlan.deadZoneSettings.max.rsbX = v,
                v -> currentPlan.deadZoneSettings.min.rsbXB = v,
                v -> currentPlan.deadZoneSettings.max.rsbXB = v,
                v -> currentPlan.deadZoneSettings.min.rsbY = v,
                v -> currentPlan.deadZoneSettings.max.rsbY = v,
                v -> currentPlan.deadZoneSettings.min.rsbYB = v,
                v -> currentPlan.deadZoneSettings.max.rsbYB = v,
                new BindingConfiguration(
                    () -> currentPlan.binding.tr,
                    v -> currentPlan.binding.tr = v
                )),
            new Circle() {{
                setLayoutX(442 / 746d * imageView.getFitWidth());
                setLayoutY(292 / 526d * imageView.getFitHeight());
                setRadius(30 / 746d * imageView.getFitWidth());
            }});
    }

    private void createLTButton(Pane imagePane, ImageView imageView) {
        createTriggerButton(imagePane, "LT",
            new TriggerConfiguration(
                () -> currentPlan.binding.ltMin,
                () -> currentPlan.binding.ltMax,
                v -> currentPlan.binding.ltMin = v,
                v -> currentPlan.binding.ltMax = v,
                () -> currentPlan.deadZoneSettings.min.lt,
                () -> currentPlan.deadZoneSettings.max.lt,
                v -> currentPlan.deadZoneSettings.min.lt = v,
                v -> currentPlan.deadZoneSettings.max.lt = v
            ),
            new Rectangle() {{
                setLayoutX(174 / 746d * imageView.getFitWidth());
                setLayoutY(48 / 526d * imageView.getFitHeight());
                setWidth(43 / 746d * imageView.getFitWidth());
                setHeight(25 / 526d * imageView.getFitHeight());
            }});
    }

    private void createRTButton(Pane imagePane, ImageView imageView) {
        createTriggerButton(imagePane, "RT",
            new TriggerConfiguration(
                () -> currentPlan.binding.rtMin,
                () -> currentPlan.binding.rtMax,
                v -> currentPlan.binding.rtMin = v,
                v -> currentPlan.binding.rtMax = v,
                () -> currentPlan.deadZoneSettings.min.rt,
                () -> currentPlan.deadZoneSettings.max.rt,
                v -> currentPlan.deadZoneSettings.min.rt = v,
                v -> currentPlan.deadZoneSettings.max.rt = v
            ),
            new Rectangle() {{
                setLayoutX(503 / 746d * imageView.getFitWidth());
                setLayoutY(48 / 526d * imageView.getFitHeight());
                setWidth(43 / 746d * imageView.getFitWidth());
                setHeight(25 / 526d * imageView.getFitHeight());
            }});
    }

    private static final Color buttonColor = new Color(0x59 / 255d, 0xff / 255d, 0xb7 / 255d, 1);

    @SuppressWarnings("SameParameterValue")
    private void createStickButton(Pane imagePane, String name,
                                   StickConfiguration conf,
                                   Shape shape) {
        shape.setStrokeWidth(0);
        shape.setFill(buttonColor);
        shape.setOpacity(0);
        shape.setCursor(Cursor.HAND);
        shape.setOnMouseClicked(e -> {
            var scene = new StickConfigurationScene(sceneGroupGetter.get(), name, conf);
            sceneGroupGetter.get().addScene(scene, VSceneHideMethod.TO_RIGHT);
            sceneGroupGetter.get().show(scene, VSceneShowMethod.FROM_RIGHT);
        });

        imagePane.getChildren().add(shape);
        xboxButtons.add(shape);
    }

    private void createTriggerButton(Pane imagePane, String name,
                                     TriggerConfiguration conf,
                                     Shape shape) {
        shape.setStrokeWidth(0);
        shape.setFill(buttonColor);
        shape.setOpacity(0);
        shape.setCursor(Cursor.HAND);
        shape.setOnMouseClicked(e -> {
            var scene = new TriggerConfigurationScene(sceneGroupGetter.get(), name, conf);
            sceneGroupGetter.get().addScene(scene, VSceneHideMethod.TO_RIGHT);
            sceneGroupGetter.get().show(scene, VSceneShowMethod.FROM_RIGHT);
        });
        imagePane.getChildren().add(shape);
        xboxButtons.add(shape);
    }

    private void createNormalButton(Pane imagePane, String name, BindingConfiguration conf, Shape shape) {
        shape.setStrokeWidth(0);
        shape.setFill(buttonColor);
        shape.setOpacity(0);
        shape.setCursor(Cursor.HAND);
        shape.setOnMouseClicked(e -> {
            var scene = new BindingConfigurationScene(sceneGroupGetter.get(), name, conf);
            sceneGroupGetter.get().addScene(scene, VSceneHideMethod.TO_RIGHT);
            sceneGroupGetter.get().show(scene, VSceneShowMethod.FROM_RIGHT);
        });
        imagePane.getChildren().add(shape);
        xboxButtons.add(shape);
    }

    @Override
    protected boolean checkBeforeShowing() throws Exception {
        var config = ConfigManager.get().read();
        if (config.address != null && !config.address.isBlank()) {
            address.setText(config.address.trim());
        }
        if (config.plans != null) {
            this.plans = config.plans;
            planComboBox.setItems(FXCollections.observableList(new ArrayList<>() {{
                for (var p : config.plans) {
                    add(p.name);
                }
            }}));
            planComboBox.setValue(null);
        }
        applyPlan(config.lastPlan);
        return true;
    }

    private void start() {
        connectButton.setDisable(true);
        var robot = new AutoRobot(currentPlan.binding);
        if (client == null) {
            client = new ClientVerticle(address.getText(),
                new ConfigureMessage(currentPlan.deadZoneSettings), robot,
                () -> FXUtils.runOnFX(this::stopCallback));
            try {
                vertx.deployVerticle(client).toCompletionStage().toCompletableFuture().get();
            } catch (Exception e) {
                Logger.error(LogType.SYS_ERROR, "failed to deploy client verticle", e);
                client = null;
                connectButton.setDisable(false);
                SimpleAlert.showAndWait(Alert.AlertType.ERROR, I18n.get().failedToStart());
                return;
            }
        }
        robot.start();
        this.robot = robot;
        disconnectButton.setDisable(false);
        saveConfig();
    }

    private void stopCallback() {
        if (this.client == null) { // is already stopped or is stopping
            return;
        }
        stop();
        SimpleAlert.showAndWait(Alert.AlertType.ERROR, I18n.get().disconnectedAlert());
    }

    public void stop() {
        var client = this.client;
        this.client = null;
        if (client != null) {
            try {
                vertx.undeploy(client.deploymentID()).toCompletionStage().toCompletableFuture().get();
            } catch (Exception e) {
                Logger.error(LogType.SYS_ERROR, "failed to stop verticle", e);
            }
        }
        var robot = this.robot;
        if (robot != null) {
            robot.stop();
        }
        disconnectButton.setDisable(true);
        connectButton.setDisable(false);
    }

    private void applyPlan(Plan p) {
        this.currentPlan = new Plan(p);
    }

    private void saveConfig() {
        var config = new Config();
        config.address = address.getText();
        config.lastPlan = currentPlan;
        config.plans = plans;
        try {
            ConfigManager.get().write(config);
        } catch (Exception e) {
            StackTraceAlert.showAndWait(I18n.get().savingConfigurationFailed(), e);
        }
    }
}
