module net.cassite.xboxrelay.ui {
    requires net.cassite.xboxrelay.base;
    requires javafx.controls;
    requires vjson;
    requires io.vproxy.base;
    requires io.vproxy.vfx;
    requires io.vertx.core;
    requires com.sun.jna;
    requires com.sun.jna.platform;

    opens net.cassite.xboxrelay.ui.res;
    exports net.cassite.xboxrelay.ui;
    exports net.cassite.xboxrelay.ui.entity;
    exports net.cassite.xboxrelay.ui.prebuilt;
    exports net.cassite.xboxrelay.ui.scene;
}
