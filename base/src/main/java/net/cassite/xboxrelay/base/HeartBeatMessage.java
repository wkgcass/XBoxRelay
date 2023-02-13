package net.cassite.xboxrelay.base;

import org.jetbrains.annotations.NotNull;
import vjson.JSON;
import vjson.deserializer.rule.IntRule;
import vjson.deserializer.rule.ObjectRule;
import vjson.deserializer.rule.Rule;
import vjson.util.ObjectBuilder;

public class HeartBeatMessage extends Message {
    public static final int TYPE_PING = 8;
    public static final int TYPE_PONG = 0;
    public int type; // 0: pong, 8: ping

    public static final Rule<HeartBeatMessage> rule = new ObjectRule<>(HeartBeatMessage::new)
        .put("type", (o, it) -> o.type = it, IntRule.get());

    public HeartBeatMessage() {
    }

    public HeartBeatMessage(int type) {
        this.type = type;
    }

    @NotNull
    @Override
    public JSON.Object toJson() {
        return new ObjectBuilder()
            .type("hb")
            .put("type", type)
            .build();
    }

    @Override
    public String toString() {
        return "HeartBeatMessage{" +
               "type=" + type +
               '}';
    }
}
