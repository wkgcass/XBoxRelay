package net.cassite.xboxrelay.ui.entity;

import net.cassite.xboxrelay.base.DeadZoneSettings;
import net.cassite.xboxrelay.ui.Binding;
import org.jetbrains.annotations.NotNull;
import vjson.JSON;
import vjson.JSONObject;
import vjson.deserializer.rule.BoolRule;
import vjson.deserializer.rule.ObjectRule;
import vjson.deserializer.rule.Rule;
import vjson.deserializer.rule.StringRule;
import vjson.util.ObjectBuilder;

public class Plan implements JSONObject {
    public String name;
    public Binding binding;
    public DeadZoneSettings deadZoneSettings;
    public boolean isSystemPreBuilt;
    public boolean isNotDeletable;

    public static final Rule<Plan> rule = new ObjectRule<>(Plan::new)
        .put("name", (o, it) -> o.name = it, StringRule.get())
        .put("binding", (o, it) -> o.binding = it, Binding.rule)
        .put("deadZoneSettings", (o, it) -> o.deadZoneSettings = it, DeadZoneSettings.rule)
        .put("isSystemPreBuilt", (o, it) -> o.isSystemPreBuilt = it, BoolRule.get())
        .put("isNotDeletable", (o, it) -> o.isNotDeletable = it, BoolRule.get());

    public Plan() {
    }

    public Plan(Plan p) {
        this.name = p.name;
        this.binding = new Binding(p.binding);
        this.deadZoneSettings = new DeadZoneSettings(p.deadZoneSettings);
        this.isSystemPreBuilt = p.isSystemPreBuilt;
        this.isNotDeletable = p.isNotDeletable;
    }

    @NotNull
    @Override
    public JSON.Object toJson() {
        return toJson(true);
    }

    public JSON.Object toJson(boolean serializeName) {
        var ob = new ObjectBuilder();
        if (name != null && serializeName) {
            ob.put("name", name);
        }
        ob.putInst("binding", binding.toJson());
        ob.putInst("deadZoneSettings", deadZoneSettings.toJson());
        ob.put("isSystemPreBuilt", isSystemPreBuilt);
        ob.put("isNotDeletable", isNotDeletable);
        return ob.build();
    }
}
