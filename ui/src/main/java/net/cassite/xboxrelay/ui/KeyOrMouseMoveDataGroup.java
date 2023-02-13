package net.cassite.xboxrelay.ui;

import net.cassite.xboxrelay.base.TriggerLevel;

public class KeyOrMouseMoveDataGroup {
    public TriggerLevel level = TriggerLevel.OFF;
    public KeyOrMouseMove current = null;

    public long lastTs = 0; // nano
}
