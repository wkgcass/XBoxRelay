module net.cassite.xboxrelay.ui {
    requires net.cassite.xboxrelay.base;
    requires javafx.controls;
    requires io.vproxy.vfx;
    requires io.vertx.core;
    requires com.sun.jna;
    requires com.sun.jna.platform;

    exports net.cassite.xboxrelay.ui;
    opens net.cassite.xboxrelay.ui.res;
    exports net.cassite.xboxrelay.ui.testing;
}
