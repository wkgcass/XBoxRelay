package net.cassite.xboxrelay.ui;

import vjson.JSON;
import vjson.JSONObject;
import vjson.deserializer.rule.ObjectRule;
import vjson.deserializer.rule.Rule;
import vjson.util.ObjectBuilder;

import java.util.ArrayList;
import java.util.List;

public class Binding implements JSONObject {
    public Action lsbXMin;
    public Action lsbXMax;
    public Action lsbYMin;
    public Action lsbYMax;
    public Action lsbXBMin;
    public Action lsbXBMax;
    public Action lsbYBMin;
    public Action lsbYBMax;

    public Action rsbXMin;
    public Action rsbXMax;
    public Action rsbYMin;
    public Action rsbYMax;
    public Action rsbXBMin;
    public Action rsbXBMax;
    public Action rsbYBMin;
    public Action rsbYBMax;

    public Action du;
    public Action dd;
    public Action dl;
    public Action dr;
    public Action back;
    public Action guide;
    public Action start;
    public Action tl;
    public Action tr;
    public Action a;
    public Action b;
    public Action x;
    public Action y;
    public Action lb;
    public Action rb;
    public Action ltMin;
    public Action ltMax;
    public Action rtMin;
    public Action rtMax;

    public static final Rule<Binding> rule = new ObjectRule<>(Binding::new)
        .put("lsbXMin", (o, it) -> o.lsbXMin = it, Action.rule)
        .put("lsbXMax", (o, it) -> o.lsbXMax = it, Action.rule)
        .put("lsbYMin", (o, it) -> o.lsbYMin = it, Action.rule)
        .put("lsbYMax", (o, it) -> o.lsbYMax = it, Action.rule)
        .put("lsbXBMin", (o, it) -> o.lsbXBMin = it, Action.rule)
        .put("lsbXBMax", (o, it) -> o.lsbXBMax = it, Action.rule)
        .put("lsbYBMin", (o, it) -> o.lsbYBMin = it, Action.rule)
        .put("lsbYBMax", (o, it) -> o.lsbYBMax = it, Action.rule)
        .put("rsbXMin", (o, it) -> o.rsbXMin = it, Action.rule)
        .put("rsbXMax", (o, it) -> o.rsbXMax = it, Action.rule)
        .put("rsbYMin", (o, it) -> o.rsbYMin = it, Action.rule)
        .put("rsbYMax", (o, it) -> o.rsbYMax = it, Action.rule)
        .put("rsbXBMin", (o, it) -> o.rsbXBMin = it, Action.rule)
        .put("rsbXBMax", (o, it) -> o.rsbXBMax = it, Action.rule)
        .put("rsbYBMin", (o, it) -> o.rsbYBMin = it, Action.rule)
        .put("rsbYBMax", (o, it) -> o.rsbYBMax = it, Action.rule)
        .put("du", (o, it) -> o.du = it, Action.rule)
        .put("dd", (o, it) -> o.dd = it, Action.rule)
        .put("dl", (o, it) -> o.dl = it, Action.rule)
        .put("dr", (o, it) -> o.dr = it, Action.rule)
        .put("back", (o, it) -> o.back = it, Action.rule)
        .put("guide", (o, it) -> o.guide = it, Action.rule)
        .put("start", (o, it) -> o.start = it, Action.rule)
        .put("tl", (o, it) -> o.tl = it, Action.rule)
        .put("tr", (o, it) -> o.tr = it, Action.rule)
        .put("a", (o, it) -> o.a = it, Action.rule)
        .put("b", (o, it) -> o.b = it, Action.rule)
        .put("x", (o, it) -> o.x = it, Action.rule)
        .put("y", (o, it) -> o.y = it, Action.rule)
        .put("lb", (o, it) -> o.lb = it, Action.rule)
        .put("rb", (o, it) -> o.rb = it, Action.rule)
        .put("ltMin", (o, it) -> o.ltMin = it, Action.rule)
        .put("ltMax", (o, it) -> o.ltMax = it, Action.rule)
        .put("rtMin", (o, it) -> o.rtMin = it, Action.rule)
        .put("rtMax", (o, it) -> o.rtMax = it, Action.rule);

    public Binding() {
    }

    public Binding(Binding that) {
        lsbXMin = Action.copyOf(that.lsbXMin);
        lsbXMax = Action.copyOf(that.lsbXMax);
        lsbYMin = Action.copyOf(that.lsbYMin);
        lsbYMax = Action.copyOf(that.lsbYMax);
        lsbXBMin = Action.copyOf(that.lsbXBMin);
        lsbXBMax = Action.copyOf(that.lsbXBMax);
        lsbYBMin = Action.copyOf(that.lsbYBMin);
        lsbYBMax = Action.copyOf(that.lsbYBMax);

        rsbXMin = Action.copyOf(that.rsbXMin);
        rsbXMax = Action.copyOf(that.rsbXMax);
        rsbYMin = Action.copyOf(that.rsbYMin);
        rsbYMax = Action.copyOf(that.rsbYMax);
        rsbXBMin = Action.copyOf(that.rsbXBMin);
        rsbXBMax = Action.copyOf(that.rsbXBMax);
        rsbYBMin = Action.copyOf(that.rsbYBMin);
        rsbYBMax = Action.copyOf(that.rsbYBMax);

        du = Action.copyOf(that.du);
        dd = Action.copyOf(that.dd);
        dl = Action.copyOf(that.dl);
        dr = Action.copyOf(that.dr);
        back = Action.copyOf(that.back);
        guide = Action.copyOf(that.guide);
        start = Action.copyOf(that.start);
        tl = Action.copyOf(that.tl);
        tr = Action.copyOf(that.tr);
        a = Action.copyOf(that.a);
        b = Action.copyOf(that.b);
        x = Action.copyOf(that.x);
        y = Action.copyOf(that.y);
        lb = Action.copyOf(that.lb);
        rb = Action.copyOf(that.rb);
        ltMin = Action.copyOf(that.ltMin);
        ltMax = Action.copyOf(that.ltMax);
        rtMin = Action.copyOf(that.rtMin);
        rtMax = Action.copyOf(that.rtMax);
    }

    public List<ActionDataGroup> makeDataGroup() {
        var ret = new ArrayList<ActionDataGroup>();

        var lsbX = createGroup(ret);
        assignGroup(lsbXMin, lsbX);
        assignGroup(lsbXMax, lsbX);
        assignGroup(lsbXBMin, lsbX);
        assignGroup(lsbXBMax, lsbX);

        var lsbY = createGroup(ret);
        assignGroup(lsbYMin, lsbY);
        assignGroup(lsbYMax, lsbY);
        assignGroup(lsbYBMin, lsbY);
        assignGroup(lsbYBMax, lsbY);

        var rsbX = createGroup(ret);
        assignGroup(rsbXMin, rsbX);
        assignGroup(rsbXMax, rsbX);
        assignGroup(rsbXBMin, rsbX);
        assignGroup(rsbXBMax, rsbX);

        var rsbY = createGroup(ret);
        assignGroup(rsbYMin, rsbY);
        assignGroup(rsbYMax, rsbY);
        assignGroup(rsbYBMin, rsbY);
        assignGroup(rsbYBMax, rsbY);

        var lt = createGroup(ret);
        assignGroup(ltMin, lt);
        assignGroup(ltMax, lt);

        var rt = createGroup(ret);
        assignGroup(rtMin, rt);
        assignGroup(rtMax, rt);

        assignGroup(du, createGroup(ret));
        assignGroup(dd, createGroup(ret));
        assignGroup(dl, createGroup(ret));
        assignGroup(dr, createGroup(ret));
        assignGroup(back, createGroup(ret));
        assignGroup(guide, createGroup(ret));
        assignGroup(start, createGroup(ret));
        assignGroup(tl, createGroup(ret));
        assignGroup(tr, createGroup(ret));
        assignGroup(a, createGroup(ret));
        assignGroup(b, createGroup(ret));
        assignGroup(x, createGroup(ret));
        assignGroup(y, createGroup(ret));
        assignGroup(lb, createGroup(ret));
        assignGroup(rb, createGroup(ret));

        return ret;
    }

    private static ActionDataGroup createGroup(List<ActionDataGroup> groups) {
        var ret = new ActionDataGroup();
        groups.add(ret);
        return ret;
    }

    private void assignGroup(Action action, ActionDataGroup group) {
        if (action == null) return;
        action.group = group;
    }

    @Override
    public JSON.Object toJson() {
        var ob = new ObjectBuilder();
        if (lsbXMin != null) ob.putInst("lsbXMin", lsbXMin.toJson());
        if (lsbXMax != null) ob.putInst("lsbXMax", lsbXMax.toJson());
        if (lsbYMin != null) ob.putInst("lsbYMin", lsbYMin.toJson());
        if (lsbYMax != null) ob.putInst("lsbYMax", lsbYMax.toJson());
        if (lsbXBMin != null) ob.putInst("lsbXBMin", lsbXBMin.toJson());
        if (lsbXBMax != null) ob.putInst("lsbXBMax", lsbXBMax.toJson());
        if (lsbYBMin != null) ob.putInst("lsbYBMin", lsbYBMin.toJson());
        if (lsbYBMax != null) ob.putInst("lsbYBMax", lsbYBMax.toJson());
        if (rsbXMin != null) ob.putInst("rsbXMin", rsbXMin.toJson());
        if (rsbXMax != null) ob.putInst("rsbXMax", rsbXMax.toJson());
        if (rsbYMin != null) ob.putInst("rsbYMin", rsbYMin.toJson());
        if (rsbYMax != null) ob.putInst("rsbYMax", rsbYMax.toJson());
        if (rsbXBMin != null) ob.putInst("rsbXBMin", rsbXBMin.toJson());
        if (rsbXBMax != null) ob.putInst("rsbXBMax", rsbXBMax.toJson());
        if (rsbYBMin != null) ob.putInst("rsbYBMin", rsbYBMin.toJson());
        if (rsbYBMax != null) ob.putInst("rsbYBMax", rsbYBMax.toJson());
        if (du != null) ob.putInst("du", du.toJson());
        if (dd != null) ob.putInst("dd", dd.toJson());
        if (dl != null) ob.putInst("dl", dl.toJson());
        if (dr != null) ob.putInst("dr", dr.toJson());
        if (back != null) ob.putInst("back", back.toJson());
        if (guide != null) ob.putInst("guide", guide.toJson());
        if (start != null) ob.putInst("start", start.toJson());
        if (tl != null) ob.putInst("tl", tl.toJson());
        if (tr != null) ob.putInst("tr", tr.toJson());
        if (a != null) ob.putInst("a", a.toJson());
        if (b != null) ob.putInst("b", b.toJson());
        if (x != null) ob.putInst("x", x.toJson());
        if (y != null) ob.putInst("y", y.toJson());
        if (lb != null) ob.putInst("lb", lb.toJson());
        if (rb != null) ob.putInst("rb", rb.toJson());
        if (ltMin != null) ob.putInst("ltMin", ltMin.toJson());
        if (ltMax != null) ob.putInst("ltMax", ltMax.toJson());
        if (rtMin != null) ob.putInst("rtMin", rtMin.toJson());
        if (rtMax != null) ob.putInst("rtMax", rtMax.toJson());
        return ob.build();
    }
}
