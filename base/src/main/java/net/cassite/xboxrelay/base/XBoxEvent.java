package net.cassite.xboxrelay.base;

import vjson.JSON;
import vjson.deserializer.rule.ObjectRule;
import vjson.deserializer.rule.Rule;
import vjson.deserializer.rule.StringRule;
import vjson.util.ObjectBuilder;

public class XBoxEvent extends Message {
    public XBoxKey key;
    public TriggerLevel level;

    public static final Rule<XBoxEvent> rule = new ObjectRule<>(XBoxEvent::new)
        .put("key", (o, it) -> o.key = XBoxKey.valueOf(it), StringRule.get())
        .put("level", (o, it) -> o.level = TriggerLevel.valueOf(it), StringRule.get());

    public XBoxEvent() {
    }

    public XBoxEvent(XBoxKey key, TriggerLevel level) {
        this.key = key;
        this.level = level;
    }

    @Override
    public JSON.Object toJson() {
        var ob = new ObjectBuilder();
        ob.type("event");
        ob.put("key", key.name());
        ob.put("level", level.name());
        return ob.build();
    }

    @Override
    public String toString() {
        return "XBoxEvent{" +
               "key=" + key +
               ", level=" + level +
               '}';
    }
}
