package net.cassite.xboxrelay.agent;

import io.vertx.core.Vertx;
import io.vertx.core.net.NetSocket;
import net.cassite.xboxrelay.base.BaseVersion;
import net.cassite.xboxrelay.base.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Set;

public class Main {
    private static final String HELP_STR = """
        Usage:
            --listen={}                  listen port, default 15992
            --log-level={}               off,debug,info,warn,error,all, default info
        """;
    private static final Set<String> availableLogLevels = Set.of(
        "off", "debug", "info", "warn", "error", "all"
    );

    public static void main(String[] args) {
        int listen = 15992;
        var logLevel = "info";
        for (var arg : args) {
            if (arg.equals("-h") || arg.equals("--help") || arg.equals("-help") || arg.equals("help")) {
                System.out.println(HELP_STR);
                return;
            } else if (arg.equals("-v") || arg.equals("--version") || arg.equals("-version") || arg.equals("version")) {
                System.out.println("base:  " + BaseVersion.VERSION);
                System.out.println("agent: " + AgentVersion.VERSION);
                return;
            } else if (arg.startsWith("--listen=")) {
                arg = arg.substring("--listen=".length()).trim();
                int v;
                try {
                    v = Integer.parseInt(arg);
                } catch (NumberFormatException e) {
                    System.err.println("invalid value for --listen: " + arg + " is not an integer");
                    System.exit(1);
                    return;
                }
                if (v < 1 || v > 65535) {
                    System.err.println("invalid value for --listen: " + v + " out of range");
                    System.exit(1);
                    return;
                }
                listen = v;
            } else if (arg.startsWith("--log-level=")) {
                arg = arg.substring("--log-level=".length()).trim();
                if (!availableLogLevels.contains(arg)) {
                    System.err.println("invalid value for --log-level: " + arg);
                    return;
                }
                if (arg.equals("off")) {
                    logLevel = "trace";
                } else if (arg.equals("all")) {
                    logLevel = "error";
                } else {
                    logLevel = arg;
                }
            } else {
                System.err.println("unknown argument: " + arg + ", use --help to show available parameters");
                System.exit(1);
                return;
            }
        }

        System.setProperty(org.slf4j.impl.SimpleLogger.DEFAULT_LOG_LEVEL_KEY, logLevel);
        var logger = LoggerFactory.getLogger("agent");
        Logger.setLoggerAdaptor(new SLF4JLoggerAdaptor(logger));

        var manager = new NetSocketManager();
        var vertx = Vertx.vertx();

        // test listening socket
        {
            var tmp = vertx.createNetServer();
            tmp.connectHandler(NetSocket::close);
            try {
                tmp.listen(listen).toCompletionStage().toCompletableFuture().get();
            } catch (Exception t) {
                System.err.println("unable to listen on " + listen);
                t.printStackTrace();
                System.exit(1);
                return;
            }
            try {
                tmp.close().toCompletionStage().toCompletableFuture().get();
            } catch (Exception ignore) {
            }
        }

        startAgentServers(vertx, manager, listen);

        try {
            startAndWaitXBoxDrvProcess(manager);
        } catch (Exception e) {
            System.err.println("failed to start xbox drv");
            System.exit(1);
            return;
        }
        // should not reach here!!!
        System.exit(1);
    }

    private static void startAgentServers(Vertx vertx, NetSocketManager manager, int listen) {
        for (var i = 0; i < Math.min(1, Runtime.getRuntime().availableProcessors()); ++i) {
            vertx.deployVerticle(new AgentServer(manager, listen));
        }
    }

    private static void startAndWaitXBoxDrvProcess(NetSocketManager manager) throws Exception {
        var process = new ProcessBuilder()
            .command("xboxdrv", "--no-uinput", "--detach-kernel-driver")
            .start();
        var stdout = new BufferedReader(new InputStreamReader(process.getInputStream()));
        var stderr = new InputStreamReader(process.getErrorStream());
        var outThread = new Thread(() -> {
            while (true) {
                String line;
                try {
                    line = stdout.readLine();
                } catch (IOException e) {
                    Logger.error("stdout stream received exception when reading", e);
                    return;
                }
                if (line == null) {
                    // eof
                    Logger.warn("stdout reaches eof");
                    return;
                }
                line = line.trim();
                // Logger.debug("xboxdrv produced line: " + line);
                var data = new XBoxDrvData();
                var ok = data.fromLine(line);
                if (ok) {
                    // Logger.debug(data.toString());
                    manager.onXBoxDrvData(data);
                }
            }
        });
        var errThread = new Thread(() -> {
            var buf = new char[16384];
            int n;
            try {
                n = stderr.read(buf);
            } catch (IOException e) {
                Logger.error("stderr stream received exception when reading", e);
                return;
            }
            if (n == -1) {
                // eof
                Logger.warn("stderr reaches eof");
                return;
            }
            for (int i = 0; i < n; ++i) {
                System.err.print(buf[i]);
            }
        });
        outThread.start();
        errThread.start();
        Logger.info("xboxdrv launched");
        outThread.join();
        errThread.join();
    }
}
