package net.cassite.xboxrelay.ui.entity;

import io.vproxy.vfx.util.IOUtils;
import net.cassite.xboxrelay.base.Logger;
import net.cassite.xboxrelay.ui.prebuilt.DefaultPlan;
import net.cassite.xboxrelay.ui.prebuilt.TowerOfFantasyPlan;
import vjson.CharStream;
import vjson.JSON;
import vjson.parser.ParserOptions;
import vjson.pl.ScriptifyContext;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class ConfigManager {
    private static final ConfigManager instant = new ConfigManager();

    private static final Path dirPath = Path.of(System.getProperty("user.home"), "AppData", "Local", "XBoxRelay").toAbsolutePath();
    private static final Path filePath = Path.of(dirPath.toString(), "ui.vjson.txt");

    private ConfigManager() {
    }

    public static ConfigManager get() {
        return instant;
    }

    public Config read() throws IOException {
        var dir = dirPath.toFile();
        if (!dir.exists()) {
            var ok = dir.mkdirs();
            if (!ok) {
                Logger.error("failed creating config saving directory: " + dir);
                throw new IOException("failed creating config saving directory: " + dir);
            }
        }
        var file = filePath.toFile();
        if (!file.exists()) {
            return Config.empty();
        }
        var str = Files.readString(filePath);
        Config config;
        try {
            config = JSON.deserialize(CharStream.from(str), Config.rule, ParserOptions.allFeatures());
        } catch (Exception e) {
            Logger.error("failed deserializing config from " + str + ", using empty config instead", e);
            return Config.empty();
        }
        if (config.plans == null || config.plans.isEmpty()) {
            config.plans = new ArrayList<>();
            config.plans.add(new DefaultPlan());
            config.plans.add(new TowerOfFantasyPlan());
        }
        var ite = config.plans.listIterator();
        while (ite.hasNext()) {
            var e = ite.next();
            if (!e.isSystemPreBuilt) {
                continue;
            }
            if (e.name.equals(DefaultPlan.NAME)) {
                ite.set(new DefaultPlan());
            } else if (e.name.equals(TowerOfFantasyPlan.NAME)) {
                ite.set(new TowerOfFantasyPlan());
            }
        }
        if (config.lastPlan == null) {
            config.lastPlan = new DefaultPlan();
        }
        return config;
    }

    public void write(Config config) throws IOException {
        var sb = new StringBuilder();
        config.toJson().scriptify(sb, new ScriptifyContext(2));
        IOUtils.writeFile(filePath, sb.toString());
    }
}
