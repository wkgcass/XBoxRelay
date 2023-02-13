package net.cassite.xboxrelay.ui;

import io.vertx.core.Vertx;
import io.vproxy.vfx.manager.image.ImageManager;
import io.vproxy.vfx.manager.internal_i18n.InternalI18n;
import io.vproxy.vfx.ui.stage.VStage;
import io.vproxy.vfx.ui.stage.VStageInitParams;
import javafx.application.Application;
import javafx.stage.Stage;

public class FXMain extends Application {
    @Override
    public void start(Stage primaryStage) {
        ImageManager.get().load("/net/cassite/xboxrelay/ui/res/xbox.png");

        var vertx = Vertx.vertx();

        var scene = new ConfigureScene(vertx);
        var stage = new VStage(primaryStage, new VStageInitParams()
            .setInitialScene(scene)
            .setMaximizeAndResetButton(false)
            .setResizable(false)) {
            @Override
            public void close() {
                scene.stop();
                vertx.close();
                super.close();
            }
        };

        stage.setTitle(InternalI18n.get().extra("XBox Relay UI"));
        stage.getStage().setWidth(746 + 1);
        stage.getStage().setHeight(526 + VStage.TITLE_BAR_HEIGHT + 80 + 1);
        stage.getStage().centerOnScreen();
        stage.show();
    }
}
