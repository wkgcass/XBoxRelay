package net.cassite.xboxrelay.ui;

import net.cassite.xboxrelay.base.TriggerLevel;

public class KeyOrMouseDataGroup {
    public TriggerLevel level = TriggerLevel.OFF;
    public KeyOrMouse current = null;

    public long lastMouseMovingTs = 0; // nano
    public long lastMouseWheelTs = 0; // nano
}
