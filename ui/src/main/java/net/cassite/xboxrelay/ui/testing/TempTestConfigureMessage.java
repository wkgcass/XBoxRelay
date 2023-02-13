package net.cassite.xboxrelay.ui.testing;

import net.cassite.xboxrelay.base.ConfigureMessage;
import net.cassite.xboxrelay.base.DeadZoneConfig;

public class TempTestConfigureMessage extends ConfigureMessage {
    private static final int XY_MIN = 10000;
    private static final int XY_MAX = 30000;
    private static final int T_MIN = 50;
    private static final int T_MAX = 200;

    public TempTestConfigureMessage() {
        min = new DeadZoneConfig();
        min.lsbX = XY_MIN;
        min.lsbY = XY_MIN;
        min.rsbX = XY_MIN;
        min.rsbY = XY_MIN;
        min.lt = T_MIN;
        min.rt = T_MIN;
        max = new DeadZoneConfig();
        max.lsbX = XY_MAX;
        max.lsbY = XY_MAX;
        max.rsbX = XY_MAX;
        max.rsbY = XY_MAX;
        max.lt = T_MAX;
        max.rt = T_MAX;
    }
}
