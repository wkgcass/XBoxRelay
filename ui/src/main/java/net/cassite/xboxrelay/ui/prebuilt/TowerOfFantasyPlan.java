package net.cassite.xboxrelay.ui.prebuilt;

import net.cassite.xboxrelay.ui.entity.Plan;

public class TowerOfFantasyPlan extends Plan {
    public static final String NAME = "tower of fantasy";

    public TowerOfFantasyPlan() {
        this.name = NAME;
        this.binding = new TowerOfFantasyBinding();
        this.deadZoneSettings = new DefaultDeadZoneSettings();
        this.isSystemPreBuilt = true;
        this.isNotDeletable = true;
    }
}
