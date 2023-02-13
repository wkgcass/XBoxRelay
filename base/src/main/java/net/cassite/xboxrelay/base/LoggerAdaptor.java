package net.cassite.xboxrelay.base;

public interface LoggerAdaptor {
    void _debug(String msg);

    void _info(String msg);

    void _warn(String msg);

    void _error(String msg);

    void _error(String msg, Throwable t);
}
