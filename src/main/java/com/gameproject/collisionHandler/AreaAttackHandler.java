package com.gameproject.collisionHandler;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import com.gameproject.Enums.EntityType;
import com.gameproject.components.AreaAttackComponent;
import com.gameproject.components.EnemyComponent;

import static com.almasb.fxgl.dsl.FXGLForKtKt.*;

public class AreaAttackHandler extends CollisionHandler {

    public AreaAttackHandler() {
        super(EntityType.AREAATTACK,EntityType.ENEMY);
    }

    @Override
    protected void onCollision(Entity areaAttack, Entity enemy){
        enemy.getComponent(EnemyComponent.class).getDamage(areaAttack.getComponent(AreaAttackComponent.class).damage()*(1.d/getSettings().getTicksPerSecond()));
    }
}
