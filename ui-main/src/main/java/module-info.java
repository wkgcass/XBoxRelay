module net.cassite.xboxrelay.uimain {
    requires javafx.controls;
    requires net.cassite.xboxrelay.base;
    requires net.cassite.xboxrelay.ui;
    requires io.vproxy.base;
    requires io.vproxy.vfx;
    requires io.vertx.core;

    opens net.cassite.xboxrelay.uimain.dll;

    exports net.cassite.xboxrelay.uimain;
}
