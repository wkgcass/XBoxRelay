package net.cassite.xboxrelay.base;

import io.vertx.core.buffer.Buffer;
import vjson.JSONObject;
import vjson.deserializer.rule.ObjectRule;
import vjson.deserializer.rule.Rule;
import vjson.deserializer.rule.TypeRule;

import java.nio.charset.StandardCharsets;

public abstract class Message implements JSONObject {
    private static Rule<Message> rule;

    public static Rule<Message> messageTypeRule() {
        if (rule != null) {
            return rule;
        }
        synchronized (Message.class) {
            if (rule != null) {
                return rule;
            }
            rule = new TypeRule<Message>()
                .type("configure", (ObjectRule<ConfigureMessage>) ConfigureMessage.rule)
                .type("hb", (ObjectRule<HeartBeatMessage>) HeartBeatMessage.rule)
                .type("event", (ObjectRule<XBoxEvent>) XBoxEvent.rule);
        }
        return rule;
    }

    public Buffer toBuffer() {
        var json = toJson().stringify().getBytes(StandardCharsets.UTF_8);
        int len = json.length;
        var lenBuffer = Buffer.buffer(3);
        byte a = (byte) ((len >> 16) & 0xff);
        byte b = (byte) ((len >> 8) & 0xff);
        byte c = (byte) (len & 0xff);
        lenBuffer.setByte(0, a);
        lenBuffer.setByte(1, b);
        lenBuffer.setByte(2, c);
        return lenBuffer.appendBuffer(Buffer.buffer(json));
    }
}
