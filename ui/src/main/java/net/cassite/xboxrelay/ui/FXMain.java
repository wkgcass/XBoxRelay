package net.cassite.xboxrelay.ui;

import io.vertx.core.Vertx;
import io.vproxy.vfx.manager.image.ImageManager;
import io.vproxy.vfx.ui.scene.VSceneGroup;
import io.vproxy.vfx.ui.stage.VStage;
import io.vproxy.vfx.ui.stage.VStageInitParams;
import javafx.application.Application;
import javafx.stage.Stage;

public class FXMain extends Application {
    @Override
    public void start(Stage primaryStage) {
        ImageManager.get().load("/net/cassite/xboxrelay/ui/res/xbox.png");
        var icon = ImageManager.get().load("/net/cassite/xboxrelay/ui/res/icon.png");
        primaryStage.getIcons().add(icon);

        var vertx = Vertx.vertx();

        VSceneGroup[] sceneGroup = new VSceneGroup[]{null};
        var scene = new ConfigureScene(vertx, () -> sceneGroup[0]);
        var stage = new VStage(primaryStage, new VStageInitParams()
            .setInitialScene(scene)) {
            @Override
            public void close() {
                scene.stop();
                vertx.close();
                super.close();
            }
        };
        sceneGroup[0] = stage.getSceneGroup();

        stage.setTitle(I18n.get().title());
        stage.getStage().setWidth(746 + 1);
        stage.getStage().setHeight(526 + VStage.TITLE_BAR_HEIGHT + 80 + 80 + 1);
        stage.getStage().centerOnScreen();
        stage.show();
    }
}
