package net.cassite.xboxrelay.base;

import org.jetbrains.annotations.NotNull;
import vjson.JSON;
import vjson.deserializer.rule.ObjectRule;
import vjson.deserializer.rule.Rule;
import vjson.util.ObjectBuilder;

public class ConfigureMessage extends Message {
    public DeadZoneConfig min = null;
    public DeadZoneConfig max = null;

    public static final Rule<ConfigureMessage> rule = new ObjectRule<>(ConfigureMessage::new)
        .put("min", (o, it) -> o.min = it, DeadZoneConfig.rule)
        .put("max", (o, it) -> o.max = it, DeadZoneConfig.rule);

    public ConfigureMessage() {
    }

    public boolean validForInitialControlMessage() {
        return min != null && !min.isZero() && max != null && !max.isZero();
    }

    @SuppressWarnings("RedundantIfStatement")
    public boolean valid() {
        if (min != null) {
            if (!min.valid()) {
                return false;
            }
        }
        if (max != null) {
            if (!max.valid()) {
                return false;
            }
        }
        return true;
    }

    public void from(ConfigureMessage msg) {
        if (min == null) {
            min = msg.min;
        } else if (msg.min != null) {
            min.from(msg.min);
        }
        if (max == null) {
            max = msg.max;
        } else if (msg.max != null) {
            max.from(msg.max);
        }
    }

    @NotNull
    @Override
    public JSON.Object toJson() {
        var ob = new ObjectBuilder();
        ob.type("configure");
        if (min != null && !min.isZero())
            ob.putInst("min", min.toJson());
        if (max != null && !max.isZero())
            ob.putInst("max", max.toJson());
        return ob.build();
    }

    @Override
    public String toString() {
        return "ConfigureMessage{" +
               "min=" + min +
               ", max=" + max +
               '}';
    }
}
