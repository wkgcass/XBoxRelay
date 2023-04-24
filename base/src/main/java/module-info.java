module net.cassite.xboxrelay.base {
    requires transitive kotlin.stdlib;
    requires vjson;
    requires io.vproxy.base;
    requires io.vertx.core;

    exports net.cassite.xboxrelay.base;
}
