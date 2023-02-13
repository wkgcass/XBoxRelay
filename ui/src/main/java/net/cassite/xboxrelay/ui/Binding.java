package net.cassite.xboxrelay.ui;

import java.util.HashSet;
import java.util.Set;

public class Binding {
    public KeyOrMouseMove lsbXMin;
    public KeyOrMouseMove lsbXMax;
    public KeyOrMouseMove lsbYMin;
    public KeyOrMouseMove lsbYMax;
    public KeyOrMouseMove lsbXBMin;
    public KeyOrMouseMove lsbXBMax;
    public KeyOrMouseMove lsbYBMin;
    public KeyOrMouseMove lsbYBMax;

    public KeyOrMouseMove rsbXMin;
    public KeyOrMouseMove rsbXMax;
    public KeyOrMouseMove rsbYMin;
    public KeyOrMouseMove rsbYMax;
    public KeyOrMouseMove rsbXBMin;
    public KeyOrMouseMove rsbXBMax;
    public KeyOrMouseMove rsbYBMin;
    public KeyOrMouseMove rsbYBMax;

    public KeyOrMouseMove du;
    public KeyOrMouseMove dd;
    public KeyOrMouseMove dl;
    public KeyOrMouseMove dr;
    public KeyOrMouseMove back;
    public KeyOrMouseMove guide;
    public KeyOrMouseMove start;
    public KeyOrMouseMove tl;
    public KeyOrMouseMove tr;
    public KeyOrMouseMove a;
    public KeyOrMouseMove b;
    public KeyOrMouseMove x;
    public KeyOrMouseMove y;
    public KeyOrMouseMove lb;
    public KeyOrMouseMove rb;
    public KeyOrMouseMove ltMin;
    public KeyOrMouseMove ltMax;
    public KeyOrMouseMove rtMin;
    public KeyOrMouseMove rtMax;

    public Binding() {
    }

    public Binding(Binding that) {
        lsbXMin = KeyOrMouseMove.copyOf(that.lsbXMin);
        lsbXMax = KeyOrMouseMove.copyOf(that.lsbXMax);
        lsbYMin = KeyOrMouseMove.copyOf(that.lsbYMin);
        lsbYMax = KeyOrMouseMove.copyOf(that.lsbYMax);
        lsbXBMin = KeyOrMouseMove.copyOf(that.lsbXBMin);
        lsbXBMax = KeyOrMouseMove.copyOf(that.lsbXBMax);
        lsbYBMin = KeyOrMouseMove.copyOf(that.lsbYBMin);
        lsbYBMax = KeyOrMouseMove.copyOf(that.lsbYBMax);

        rsbXMin = KeyOrMouseMove.copyOf(that.rsbXMin);
        rsbXMax = KeyOrMouseMove.copyOf(that.rsbXMax);
        rsbYMin = KeyOrMouseMove.copyOf(that.rsbYMin);
        rsbYMax = KeyOrMouseMove.copyOf(that.rsbYMax);
        rsbXBMin = KeyOrMouseMove.copyOf(that.rsbXBMin);
        rsbXBMax = KeyOrMouseMove.copyOf(that.rsbXBMax);
        rsbYBMin = KeyOrMouseMove.copyOf(that.rsbYBMin);
        rsbYBMax = KeyOrMouseMove.copyOf(that.rsbYBMax);

        du = KeyOrMouseMove.copyOf(that.du);
        dd = KeyOrMouseMove.copyOf(that.dd);
        dl = KeyOrMouseMove.copyOf(that.dl);
        dr = KeyOrMouseMove.copyOf(that.dr);
        back = KeyOrMouseMove.copyOf(that.back);
        guide = KeyOrMouseMove.copyOf(that.guide);
        start = KeyOrMouseMove.copyOf(that.start);
        tl = KeyOrMouseMove.copyOf(that.tl);
        tr = KeyOrMouseMove.copyOf(that.tr);
        a = KeyOrMouseMove.copyOf(that.a);
        b = KeyOrMouseMove.copyOf(that.b);
        x = KeyOrMouseMove.copyOf(that.x);
        y = KeyOrMouseMove.copyOf(that.y);
        lb = KeyOrMouseMove.copyOf(that.lb);
        rb = KeyOrMouseMove.copyOf(that.rb);
        ltMin = KeyOrMouseMove.copyOf(that.ltMin);
        ltMax = KeyOrMouseMove.copyOf(that.ltMax);
        rtMin = KeyOrMouseMove.copyOf(that.rtMin);
        rtMax = KeyOrMouseMove.copyOf(that.rtMax);
    }

    public Set<KeyOrMouseMoveDataGroup> makeDataGroup() {
        var ret = new HashSet<KeyOrMouseMoveDataGroup>();

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

    private static KeyOrMouseMoveDataGroup createGroup(Set<KeyOrMouseMoveDataGroup> groups) {
        var ret = new KeyOrMouseMoveDataGroup();
        groups.add(ret);
        return ret;
    }

    private void assignGroup(KeyOrMouseMove km, KeyOrMouseMoveDataGroup group) {
        if (km == null) return;
        km.group = group;
    }
}
