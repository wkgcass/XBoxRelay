package net.cassite.xboxrelay.ui;

import io.vproxy.vfx.util.Logger;
import net.cassite.xboxrelay.base.LoggerAdaptor;

public class VFXLoggerAdaptor implements LoggerAdaptor {
    @Override
    public void _debug(String msg) {
        Logger.debug(msg);
    }

    @Override
    public void _info(String msg) {
        Logger.info(msg);
    }

    @Override
    public void _warn(String msg) {
        Logger.warn(msg);
    }

    @Override
    public void _error(String msg) {
        Logger.error(msg);
    }

    @Override
    public void _error(String msg, Throwable t) {
        Logger.error(msg, t);
    }
}
