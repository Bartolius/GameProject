package com.gameproject.components;

import com.almasb.fxgl.entity.component.Component;
import com.gameproject.Enums.UpgradeType;
import com.gameproject.upgrades.Upgrade;
import com.gameproject.upgrades.Upgrades;

import java.util.ArrayList;
import java.util.Comparator;

public class AreaAttackComponent extends Component {
    double damage;
    double level;
    UpgradeType type;

    public AreaAttackComponent(double damage, double level, UpgradeType type) {
        this.damage = damage;
        this.level = level;
        this.type=type;
    }

    public double damage(){
        double damage = this.damage*level;
        ArrayList<Upgrade> upgrades = Upgrades.getUpgradesByType(type);
        upgrades.sort(Comparator.comparingInt(p -> (int) p.damageFactor));
        if(upgrades.size()>0)damage*=upgrades.get(upgrades.size()-1).damageFactor;
        return damage;
    }
}
