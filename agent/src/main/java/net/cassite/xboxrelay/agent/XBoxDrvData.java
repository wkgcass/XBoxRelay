package net.cassite.xboxrelay.agent;

public class XBoxDrvData {
    // X1:-22016 Y1:-31744  X2:   256 Y2:  -512  du:0 dd:0 dl:0 dr:0  back:0 guide:0 start:0  TL:0 TR:0  A:0 B:0 X:0 Y:0  LB:0 RB:0  LT:  0 RT:  0
    public int x1;
    public int y1;
    public int x2;
    public int y2;
    public boolean du;
    public boolean dd;
    public boolean dl;
    public boolean dr;
    public boolean back;
    public boolean guide;
    public boolean start;
    public boolean tl;
    public boolean tr;
    public boolean a;
    public boolean b;
    public boolean x;
    public boolean y;
    public boolean lb;
    public boolean rb;
    public int lt;
    public int rt;

    public boolean fromLine(String line) {
        while (true) {
            var colonIndex = line.indexOf(":");
            if (colonIndex == -1) {
                return false;
            }
            var key = line.substring(0, colonIndex).trim();
            line = line.substring(colonIndex + 1).trim();
            boolean ends = false;
            var spaceIndex = line.indexOf(" ");
            String value;
            if (spaceIndex == -1) {
                ends = true;
                value = line;
            } else {
                value = line.substring(0, spaceIndex).trim();
                line = line.substring(spaceIndex + 1).trim();
            }
            var ok = fromKV(key, value);
            if (!ok) {
                return false;
            }
            if (ends) {
                break;
            }
        }
        return true;
    }

    private boolean fromKV(String key, String value) {
        int v;
        try {
            v = Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return false;
        }
        switch (key) {
            case "X1" -> x1 = v;
            case "Y1" -> y1 = v;
            case "X2" -> x2 = v;
            case "Y2" -> y2 = v;
            case "du" -> du = v != 0;
            case "dd" -> dd = v != 0;
            case "dl" -> dl = v != 0;
            case "dr" -> dr = v != 0;
            case "back" -> back = v != 0;
            case "guide" -> guide = v != 0;
            case "start" -> start = v != 0;
            case "TL" -> tl = v != 0;
            case "TR" -> tr = v != 0;
            case "A" -> a = v != 0;
            case "B" -> b = v != 0;
            case "X" -> x = v != 0;
            case "Y" -> y = v != 0;
            case "LB" -> lb = v != 0;
            case "RB" -> rb = v != 0;
            case "LT" -> lt = v;
            case "RT" -> rt = v;
        }
        return true;
    }

    private String boolStr(boolean v) {
        return v ? "1" : "0";
    }

    @Override
    public String toString() {
        return "x1:" + x1 +
               "  y1:" + y1 +
               "  x2:" + x2 +
               "  y2:" + y2 +
               "  du:" + boolStr(du) +
               "  dd:" + boolStr(dd) +
               "  dl:" + boolStr(dl) +
               "  dr:" + boolStr(dr) +
               "  back:" + boolStr(back) +
               "  guide:" + boolStr(guide) +
               "  start:" + boolStr(start) +
               "  tl:" + boolStr(tl) +
               "  tr:" + boolStr(tr) +
               "  a:" + boolStr(a) +
               "  b:" + boolStr(b) +
               "  x:" + boolStr(x) +
               "  y:" + boolStr(y) +
               "  lb:" + boolStr(lb) +
               "  rb:" + boolStr(rb) +
               "  lt:" + lt +
               "  rt:" + rt;
    }
}
