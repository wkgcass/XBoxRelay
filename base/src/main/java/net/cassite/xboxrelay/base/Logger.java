package net.cassite.xboxrelay.base;

public class Logger {
    private static LoggerAdaptor adaptor;

    private Logger() {
    }

    public static void setLoggerAdaptor(LoggerAdaptor adaptor) {
        Logger.adaptor = adaptor;
    }

    public static void debug(String msg) {
        if (adaptor == null) {
            System.out.println(msg);
        } else {
            adaptor._debug(msg);
        }
    }

    public static void info(String msg) {
        if (adaptor == null) {
            System.out.println(msg);
        } else {
            adaptor._info(msg);
        }
    }

    public static void warn(String msg) {
        if (adaptor == null) {
            System.out.println(msg);
        } else {
            adaptor._warn(msg);
        }
    }

    public static void error(String msg) {
        if (adaptor == null) {
            System.out.println(msg);
        } else {
            adaptor._error(msg);
        }
    }

    public static void error(String msg, Throwable t) {
        if (adaptor == null) {
            System.out.println(msg);
            t.printStackTrace(System.out);
        } else {
            adaptor._error(msg, t);
        }
    }
}
