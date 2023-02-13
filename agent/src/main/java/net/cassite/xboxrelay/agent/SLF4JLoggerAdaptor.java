package net.cassite.xboxrelay.agent;

import net.cassite.xboxrelay.base.LoggerAdaptor;
import org.slf4j.Logger;

public class SLF4JLoggerAdaptor implements LoggerAdaptor {
    private final Logger logger;

    public SLF4JLoggerAdaptor(Logger logger) {
        this.logger = logger;
    }

    @Override
    public void _debug(String msg) {
        logger.debug(msg);
    }

    @Override
    public void _info(String msg) {
        logger.info(msg);
    }

    @Override
    public void _warn(String msg) {
        logger.warn(msg);
    }

    @Override
    public void _error(String msg) {
        logger.error(msg);
    }

    @Override
    public void _error(String msg, Throwable t) {
        logger.error(msg, t);
    }
}
