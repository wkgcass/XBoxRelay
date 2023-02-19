package net.cassite.xboxrelay.ui.prebuilt;

import net.cassite.xboxrelay.ui.entity.Plan;

public class DefaultPlan extends Plan {
    public static final String NAME = "default";

    public DefaultPlan() {
        this.name = NAME;
        this.binding = new DefaultBinding();
        this.deadZoneSettings = new DefaultDeadZoneSettings();
        this.isSystemPreBuilt = true;
        this.isNotDeletable = true;
    }
}
