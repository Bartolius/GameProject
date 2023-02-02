package com.gameproject.collisionHandler;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import com.gameproject.Enums.EntityType;
import com.gameproject.components.EnemyComponent;
import com.gameproject.components.PlayerComponent;

public class EnemyHandler extends CollisionHandler {
    public EnemyHandler() {
        super(EntityType.PLAYER,EntityType.ENEMY);
    }

    @Override
    protected void onCollision(Entity player, Entity enemy) {
        if(player.getComponent(PlayerComponent.class).iframe==0){
            player.getComponent(PlayerComponent.class).getDamage(enemy.getComponent(EnemyComponent.class).dealDamage());
            player.getComponent(PlayerComponent.class).iframe=2;
        }
    }
}
