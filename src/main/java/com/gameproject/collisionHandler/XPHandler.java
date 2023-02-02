package com.gameproject.collisionHandler;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import com.gameproject.Enums.EntityType;
import com.gameproject.Main;

public class XPHandler extends CollisionHandler {
    public XPHandler() {
        super(EntityType.PLAYER,EntityType.XP);
    }

    @Override
    protected void onCollision(Entity player, Entity XP) {
        XP.removeFromWorld();
        Main.addXP(5);
    }
}
