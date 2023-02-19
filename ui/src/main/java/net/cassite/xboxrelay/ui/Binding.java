package net.cassite.xboxrelay.ui;

import org.jetbrains.annotations.NotNull;
import vjson.JSON;
import vjson.JSONObject;
import vjson.deserializer.rule.ObjectRule;
import vjson.deserializer.rule.Rule;
import vjson.util.ObjectBuilder;

import java.util.ArrayList;
import java.util.List;

public class Binding implements JSONObject {
    public KeyOrMouse lsbXMin;
    public KeyOrMouse lsbXMax;
    public KeyOrMouse lsbYMin;
    public KeyOrMouse lsbYMax;
    public KeyOrMouse lsbXBMin;
    public KeyOrMouse lsbXBMax;
    public KeyOrMouse lsbYBMin;
    public KeyOrMouse lsbYBMax;

    public KeyOrMouse rsbXMin;
    public KeyOrMouse rsbXMax;
    public KeyOrMouse rsbYMin;
    public KeyOrMouse rsbYMax;
    public KeyOrMouse rsbXBMin;
    public KeyOrMouse rsbXBMax;
    public KeyOrMouse rsbYBMin;
    public KeyOrMouse rsbYBMax;

    public KeyOrMouse du;
    public KeyOrMouse dd;
    public KeyOrMouse dl;
    public KeyOrMouse dr;
    public KeyOrMouse back;
    public KeyOrMouse guide;
    public KeyOrMouse start;
    public KeyOrMouse tl;
    public KeyOrMouse tr;
    public KeyOrMouse a;
    public KeyOrMouse b;
    public KeyOrMouse x;
    public KeyOrMouse y;
    public KeyOrMouse lb;
    public KeyOrMouse rb;
    public KeyOrMouse ltMin;
    public KeyOrMouse ltMax;
    public KeyOrMouse rtMin;
    public KeyOrMouse rtMax;

    public static final Rule<Binding> rule = new ObjectRule<>(Binding::new)
        .put("lsbXMin", (o, it) -> o.lsbXMin = it, KeyOrMouse.rule)
        .put("lsbXMax", (o, it) -> o.lsbXMax = it, KeyOrMouse.rule)
        .put("lsbYMin", (o, it) -> o.lsbYMin = it, KeyOrMouse.rule)
        .put("lsbYMax", (o, it) -> o.lsbYMax = it, KeyOrMouse.rule)
        .put("lsbXBMin", (o, it) -> o.lsbXBMin = it, KeyOrMouse.rule)
        .put("lsbXBMax", (o, it) -> o.lsbXBMax = it, KeyOrMouse.rule)
        .put("lsbYBMin", (o, it) -> o.lsbYBMin = it, KeyOrMouse.rule)
        .put("lsbYBMax", (o, it) -> o.lsbYBMax = it, KeyOrMouse.rule)
        .put("rsbXMin", (o, it) -> o.rsbXMin = it, KeyOrMouse.rule)
        .put("rsbXMax", (o, it) -> o.rsbXMax = it, KeyOrMouse.rule)
        .put("rsbYMin", (o, it) -> o.rsbYMin = it, KeyOrMouse.rule)
        .put("rsbYMax", (o, it) -> o.rsbYMax = it, KeyOrMouse.rule)
        .put("rsbXBMin", (o, it) -> o.rsbXBMin = it, KeyOrMouse.rule)
        .put("rsbXBMax", (o, it) -> o.rsbXBMax = it, KeyOrMouse.rule)
        .put("rsbYBMin", (o, it) -> o.rsbYBMin = it, KeyOrMouse.rule)
        .put("rsbYBMax", (o, it) -> o.rsbYBMax = it, KeyOrMouse.rule)
        .put("du", (o, it) -> o.du = it, KeyOrMouse.rule)
        .put("dd", (o, it) -> o.dd = it, KeyOrMouse.rule)
        .put("dl", (o, it) -> o.dl = it, KeyOrMouse.rule)
        .put("dr", (o, it) -> o.dr = it, KeyOrMouse.rule)
        .put("back", (o, it) -> o.back = it, KeyOrMouse.rule)
        .put("guide", (o, it) -> o.guide = it, KeyOrMouse.rule)
        .put("start", (o, it) -> o.start = it, KeyOrMouse.rule)
        .put("tl", (o, it) -> o.tl = it, KeyOrMouse.rule)
        .put("tr", (o, it) -> o.tr = it, KeyOrMouse.rule)
        .put("a", (o, it) -> o.a = it, KeyOrMouse.rule)
        .put("b", (o, it) -> o.b = it, KeyOrMouse.rule)
        .put("x", (o, it) -> o.x = it, KeyOrMouse.rule)
        .put("y", (o, it) -> o.y = it, KeyOrMouse.rule)
        .put("lb", (o, it) -> o.lb = it, KeyOrMouse.rule)
        .put("rb", (o, it) -> o.rb = it, KeyOrMouse.rule)
        .put("ltMin", (o, it) -> o.ltMin = it, KeyOrMouse.rule)
        .put("ltMax", (o, it) -> o.ltMax = it, KeyOrMouse.rule)
        .put("rtMin", (o, it) -> o.rtMin = it, KeyOrMouse.rule)
        .put("rtMax", (o, it) -> o.rtMax = it, KeyOrMouse.rule);

    public Binding() {
    }

    public Binding(Binding that) {
        lsbXMin = KeyOrMouse.copyOf(that.lsbXMin);
        lsbXMax = KeyOrMouse.copyOf(that.lsbXMax);
        lsbYMin = KeyOrMouse.copyOf(that.lsbYMin);
        lsbYMax = KeyOrMouse.copyOf(that.lsbYMax);
        lsbXBMin = KeyOrMouse.copyOf(that.lsbXBMin);
        lsbXBMax = KeyOrMouse.copyOf(that.lsbXBMax);
        lsbYBMin = KeyOrMouse.copyOf(that.lsbYBMin);
        lsbYBMax = KeyOrMouse.copyOf(that.lsbYBMax);

        rsbXMin = KeyOrMouse.copyOf(that.rsbXMin);
        rsbXMax = KeyOrMouse.copyOf(that.rsbXMax);
        rsbYMin = KeyOrMouse.copyOf(that.rsbYMin);
        rsbYMax = KeyOrMouse.copyOf(that.rsbYMax);
        rsbXBMin = KeyOrMouse.copyOf(that.rsbXBMin);
        rsbXBMax = KeyOrMouse.copyOf(that.rsbXBMax);
        rsbYBMin = KeyOrMouse.copyOf(that.rsbYBMin);
        rsbYBMax = KeyOrMouse.copyOf(that.rsbYBMax);

        du = KeyOrMouse.copyOf(that.du);
        dd = KeyOrMouse.copyOf(that.dd);
        dl = KeyOrMouse.copyOf(that.dl);
        dr = KeyOrMouse.copyOf(that.dr);
        back = KeyOrMouse.copyOf(that.back);
        guide = KeyOrMouse.copyOf(that.guide);
        start = KeyOrMouse.copyOf(that.start);
        tl = KeyOrMouse.copyOf(that.tl);
        tr = KeyOrMouse.copyOf(that.tr);
        a = KeyOrMouse.copyOf(that.a);
        b = KeyOrMouse.copyOf(that.b);
        x = KeyOrMouse.copyOf(that.x);
        y = KeyOrMouse.copyOf(that.y);
        lb = KeyOrMouse.copyOf(that.lb);
        rb = KeyOrMouse.copyOf(that.rb);
        ltMin = KeyOrMouse.copyOf(that.ltMin);
        ltMax = KeyOrMouse.copyOf(that.ltMax);
        rtMin = KeyOrMouse.copyOf(that.rtMin);
        rtMax = KeyOrMouse.copyOf(that.rtMax);
    }

    public List<KeyOrMouseDataGroup> makeDataGroup() {
        var ret = new ArrayList<KeyOrMouseDataGroup>();

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

    private static KeyOrMouseDataGroup createGroup(List<KeyOrMouseDataGroup> groups) {
        var ret = new KeyOrMouseDataGroup();
        groups.add(ret);
        return ret;
    }

    private void assignGroup(KeyOrMouse km, KeyOrMouseDataGroup group) {
        if (km == null) return;
        km.group = group;
    }

    @NotNull
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
