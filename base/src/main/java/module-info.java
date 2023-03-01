module net.cassite.xboxrelay.base {
    requires transitive kotlin.stdlib;
    requires vjson;
    requires io.vproxy.base;
    requires io.vertx.core;
    requires annotations;

    exports net.cassite.xboxrelay.base;
}
