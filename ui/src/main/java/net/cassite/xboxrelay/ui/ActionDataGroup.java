package net.cassite.xboxrelay.ui;

import net.cassite.xboxrelay.base.TriggerLevel;

public class ActionDataGroup {
    public TriggerLevel level = TriggerLevel.OFF;
    public Action current = null;

    public long lastMouseMovingTs = 0; // nano
    public long lastMouseWheelTs = 0; // nano
}
