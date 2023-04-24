package net.cassite.xboxrelay.ui.entity;

import net.cassite.xboxrelay.ui.prebuilt.DefaultPlan;
import net.cassite.xboxrelay.ui.prebuilt.TowerOfFantasyPlan;
import vjson.JSON;
import vjson.JSONObject;
import vjson.deserializer.rule.ObjectRule;
import vjson.deserializer.rule.Rule;
import vjson.deserializer.rule.StringRule;
import vjson.util.ObjectBuilder;

import java.util.ArrayList;
import java.util.List;

public class Config implements JSONObject {
    public String address;
    public Plan lastPlan;
    public List<Plan> plans;

    public static final Rule<Config> rule = new ObjectRule<>(Config::new)
        .put("address", (o, it) -> o.address = it, StringRule.get())
        .put("lastPlan", (o, it) -> o.lastPlan = it, Plan.rule)
        .put("plans", (o, it) -> o.plans = it, JSONObject.buildArrayRule(Plan.rule));

    public Config() {
    }

    public static Config empty() {
        var ret = new Config();
        ret.plans = new ArrayList<>();
        ret.plans.add(new DefaultPlan());
        ret.plans.add(new TowerOfFantasyPlan());
        ret.lastPlan = new DefaultPlan();
        return ret;
    }

    @Override
    public JSON.Object toJson() {
        var ob = new ObjectBuilder();
        if (address != null && !address.isBlank()) {
            ob.put("address", address.trim());
        }
        if (lastPlan != null) {
            ob.putInst("lastPlan", lastPlan.toJson(false));
        }
        if (plans != null && !plans.isEmpty()) {
            ob.putArray("plans", arr -> plans.forEach(e -> arr.addInst(e.toJson())));
        }
        return ob.build();
    }
}
