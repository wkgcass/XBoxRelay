package net.cassite.xboxrelay.base;

import vjson.JSON;
import vjson.deserializer.rule.ObjectRule;
import vjson.deserializer.rule.Rule;
import vjson.util.ObjectBuilder;

public class ConfigureMessage extends Message {
    public final DeadZoneSettings deadZoneSettings;

    public static final Rule<ConfigureMessage> rule = new ObjectRule<>(ConfigureMessage::new)
        .put("min", (o, it) -> o.deadZoneSettings.min = it, DeadZoneConfig.rule)
        .put("max", (o, it) -> o.deadZoneSettings.max = it, DeadZoneConfig.rule);

    public ConfigureMessage() {
        this.deadZoneSettings = new DeadZoneSettings();
    }

    public ConfigureMessage(DeadZoneSettings deadZoneSettings) {
        this.deadZoneSettings = new DeadZoneSettings(deadZoneSettings);
    }

    public DeadZoneConfig min() {
        return deadZoneSettings.min;
    }

    public DeadZoneConfig max() {
        return deadZoneSettings.max;
    }

    public boolean validForInitialControlMessage() {
        return deadZoneSettings.validForInitialControlMessage();
    }

    public boolean valid() {
        return deadZoneSettings.valid();
    }

    public void from(ConfigureMessage msg) {
        deadZoneSettings.from(msg.deadZoneSettings);
    }

    public void from(DeadZoneSettings settings) {
        deadZoneSettings.from(settings);
    }

    @Override
    public JSON.Object toJson() {
        var ob = new ObjectBuilder();
        ob.type("configure");
        if (deadZoneSettings.min != null && !deadZoneSettings.min.isZero())
            ob.putInst("min", deadZoneSettings.min.toJson());
        if (deadZoneSettings.max != null && !deadZoneSettings.max.isZero())
            ob.putInst("max", deadZoneSettings.max.toJson());
        return ob.build();
    }

    @Override
    public String toString() {
        return "ConfigureMessage{" +
               "deadZoneSettings=" + deadZoneSettings +
               '}';
    }
}
