package com.gameproject.upgrades;

import com.gameproject.Enums.UpgradeType;

public class Upgrade {
    public double time;
    public double delay;
    public double damageFactor;
    public Upgrade prevUpgrade;
    public UpgradeType type;

    public Upgrade(double time, double delay, double damageFactor, Upgrade prevUpgrade, UpgradeType type) {
        this.time = time;
        this.delay = delay;
        this.damageFactor = damageFactor;
        this.prevUpgrade = prevUpgrade;
        this.type=type;
    }
}
