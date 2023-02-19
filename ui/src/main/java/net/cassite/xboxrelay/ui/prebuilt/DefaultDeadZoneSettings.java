package net.cassite.xboxrelay.ui.prebuilt;

import net.cassite.xboxrelay.base.DeadZoneConfig;
import net.cassite.xboxrelay.base.DeadZoneSettings;

public class DefaultDeadZoneSettings extends DeadZoneSettings {
    private static final int XY_MIN = 10923;
    private static final int XY_MAX = 21846;
    private static final int T_MIN = 85;
    private static final int T_MAX = 220;

    public DefaultDeadZoneSettings() {
        min = new DeadZoneConfig();
        min.lsbX = XY_MIN;
        min.lsbXB = XY_MIN;
        min.lsbY = XY_MIN;
        min.lsbYB = XY_MIN;
        min.rsbX = XY_MIN;
        min.rsbXB = XY_MIN;
        min.rsbY = XY_MIN;
        min.rsbYB = XY_MIN;
        min.lt = T_MIN;
        min.rt = T_MIN;
        max = new DeadZoneConfig();
        max.lsbX = XY_MAX;
        max.lsbXB = XY_MAX;
        max.lsbY = XY_MAX;
        max.lsbYB = XY_MAX;
        max.rsbX = XY_MAX;
        max.rsbXB = XY_MAX;
        max.rsbY = XY_MAX;
        max.rsbYB = XY_MAX;
        max.lt = T_MAX;
        max.rt = T_MAX;
    }
}
