package com.gameproject.collisionHandler;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import com.gameproject.Enums.EntityType;
import com.gameproject.components.BulletComponent;
import com.gameproject.components.EnemyComponent;

public class BulletHandler extends CollisionHandler {
    public BulletHandler() {
        super(EntityType.BULLET,EntityType.ENEMY);
    }

    @Override
    protected void onCollision(Entity bullet, Entity entity) {
        entity.getComponent(EnemyComponent.class).getDamage(bullet.getComponent(BulletComponent.class).getDamage());
        bullet.removeFromWorld();
    }
}
