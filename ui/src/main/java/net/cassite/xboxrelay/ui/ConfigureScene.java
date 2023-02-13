package net.cassite.xboxrelay.ui;

import io.vertx.core.Vertx;
import io.vproxy.vfx.manager.font.FontManager;
import io.vproxy.vfx.manager.image.ImageManager;
import io.vproxy.vfx.manager.internal_i18n.InternalI18n;
import io.vproxy.vfx.ui.alert.SimpleAlert;
import io.vproxy.vfx.ui.button.FusionButton;
import io.vproxy.vfx.ui.pane.FusionPane;
import io.vproxy.vfx.ui.scene.VScene;
import io.vproxy.vfx.ui.scene.VSceneRole;
import io.vproxy.vfx.ui.wrapper.FusionW;
import io.vproxy.vfx.ui.wrapper.ThemeLabel;
import io.vproxy.vfx.util.FXUtils;
import io.vproxy.vfx.util.Logger;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import net.cassite.xboxrelay.ui.testing.TempTestBinding;
import net.cassite.xboxrelay.ui.testing.TempTestConfigureMessage;

public class ConfigureScene extends VScene {
    private final Vertx vertx;
    private final FusionButton disconnectButton;
    private final FusionButton connectButton;
    private final TextField address;
    private ClientVerticle client;
    private AutoRobot robot;

    public ConfigureScene(Vertx vertx) {
        super(VSceneRole.MAIN);
        enableAutoContentWidthHeight();

        this.vertx = vertx;

        var imageView = new ImageView();
        imageView.setImage(ImageManager.get().load("/net/cassite/xboxrelay/ui/res/xbox.png"));
        imageView.setFitWidth(746);
        imageView.setFitHeight(526);

        var bottomButtons = new HBox();
        bottomButtons.setAlignment(Pos.BOTTOM_RIGHT);
        bottomButtons.setSpacing(10);
        bottomButtons.setAlignment(Pos.CENTER_RIGHT);

        var bottomPane = new FusionPane(bottomButtons);
        FXUtils.observeWidth(getContentPane(), bottomPane.getNode(), -20);
        bottomPane.getNode().setPrefHeight(60);
        bottomPane.getNode().setLayoutX(10);

        FXUtils.observeWidth(bottomPane.getContentPane(), bottomButtons);

        getContentPane().heightProperty().addListener((ob, old, now) -> {
            if (now == null) return;
            var h = now.doubleValue();
            bottomPane.getNode().setLayoutY(h - 10 - 60);
        });

        disconnectButton = new FusionButton(InternalI18n.get().extra("Disconnect"));
        FXUtils.observeHeight(bottomPane.getContentPane(), disconnectButton);
        disconnectButton.setPrefWidth(150);
        disconnectButton.setDisable(true);
        disconnectButton.setOnAction(e -> stop());

        connectButton = new FusionButton(InternalI18n.get().extra("Connect"));
        FXUtils.observeHeight(bottomPane.getContentPane(), connectButton);
        connectButton.setPrefWidth(120);
        connectButton.setOnAction(e -> start());

        var label = new ThemeLabel("Agent Address");
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

        getContentPane().getChildren().addAll(imageView, bottomPane.getNode());
    }

    private void start() {
        connectButton.setDisable(true);
        var robot = new AutoRobot(/*TODO*/new TempTestBinding());
        if (client == null) {
            client = new ClientVerticle(address.getText(), /*TODO*/new TempTestConfigureMessage(), robot,
                () -> FXUtils.runOnFX(this::stopCallback));
            try {
                vertx.deployVerticle(client).toCompletionStage().toCompletableFuture().get();
            } catch (Exception e) {
                Logger.error("failed to deploy client verticle", e);
                client = null;
                connectButton.setDisable(false);
            }
        }
        robot.start();
        this.robot = robot;
        disconnectButton.setDisable(false);
    }

    private void stopCallback() {
        if (this.client == null) { // is already stopped or is stopping
            return;
        }
        stop();
        SimpleAlert.showAndWait(Alert.AlertType.ERROR, InternalI18n.get().extra("The connection is disconnected."));
    }

    public void stop() {
        var client = this.client;
        this.client = null;
        if (client != null) {
            try {
                vertx.undeploy(client.deploymentID()).toCompletionStage().toCompletableFuture().get();
            } catch (Exception e) {
                Logger.error("failed to stop verticle", e);
            }
        }
        var robot = this.robot;
        if (robot != null) {
            robot.stop();
        }
        disconnectButton.setDisable(true);
        connectButton.setDisable(false);
    }
}
