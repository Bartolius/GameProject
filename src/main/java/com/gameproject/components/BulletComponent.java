package com.gameproject.components;

import com.almasb.fxgl.entity.component.Component;
import com.gameproject.Enums.UpgradeType;
import com.gameproject.upgrades.Upgrade;
import com.gameproject.upgrades.Upgrades;
import javafx.geometry.Point2D;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Comparator;

public class BulletComponent extends Component {
    double damage;
    double level;
    Point2D destination;
    double speed;
    Point2D dest = new Point2D(0,0);
    double speedScl=0;
    UpgradeType type;

    double actualTime=0;
    double time;


    public BulletComponent(double damage, double level, Point2D destination, double speed, UpgradeType type, double time) {
        this.damage = damage;
        this.level = level;
        this.destination = destination;
        this.speed=speed;
        this.type=type;
        this.time=time;
    }

    @Override
    public void onUpdate(double tpf) {
        double speedX = dest.getX() * speedScl;
        double speedY = dest.getY() * speedScl;
        entity.translate(speedX*tpf,speedY*tpf);

        if(actualTime>=time){
            entity.removeFromWorld();
        }
        actualTime += Duration.seconds(1).toSeconds()*tpf;
    }

    @Override
    public void onAdded() {
        dest = new Point2D(destination.getX() - entity.getX(), destination.getY() - entity.getY());

        speedScl = Math.sqrt(Math.pow(speed,2)/(Math.pow(dest.getX(),2)+Math.pow(dest.getY(),2)));
    }

    public double getDamage() {
        double damage = this.damage*level;
        ArrayList<Upgrade> upgrades = Upgrades.getUpgradesByType(type);
        upgrades.sort(Comparator.comparingInt(p -> (int) p.damageFactor));
        if(upgrades.size()>0)damage*=upgrades.get(upgrades.size()-1).damageFactor;
        return damage;
    }
}
