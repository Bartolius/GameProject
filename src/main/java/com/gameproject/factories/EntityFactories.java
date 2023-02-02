package com.gameproject.factories;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;

import com.gameproject.Enums.EntityType;
import com.gameproject.components.*;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class EntityFactories implements EntityFactory {
    @Spawns("Chunk")
    public Entity newChunk(SpawnData data){
        Point2D size = data.get("size");
        Rectangle rectangle = new Rectangle(0,0,size.getX()*40,size.getY()*40);
        rectangle.setFill(Color.GREEN);
        return FXGL.entityBuilder(data)
                .bbox(new HitBox(BoundingShape.box(size.getX()*40,size.getY()*40)))
                .view(rectangle)
                .with(new ChunkComponent(data))
                .collidable()
                .zIndex(0)
                .build();
    }
    @Spawns("Player")
    public Entity newPlayer(SpawnData data) {
        return FXGL.entityBuilder(data)
                .bbox(new HitBox(BoundingShape.box(40,80)))
                .view(new Rectangle(20,40))
                .with(new PlayerComponent())
                .zIndex(10)
                .type(EntityType.PLAYER)
                .collidable()
                .build();
    }
    @Spawns("Enemy")
    public Entity newEnemy(SpawnData data) {
        return FXGL.entityBuilder(data)
                .bbox(new HitBox(BoundingShape.circle(data.get("radius"))))
                .scale(new Point2D(data.get("scale"),data.get("scale")))
                .with(new EnemyComponent(50))
                .zIndex(11)
                .type(EntityType.ENEMY)
                .anchorFromCenter()
                .collidable()
                .build();
    }
    @Spawns("Chest")
    public Entity newChest(SpawnData data) {
        return FXGL.entityBuilder(data)
                .bbox(new HitBox(BoundingShape.box(40,40)))
                .view("Chest.png")
                .zIndex(10)
                .type(EntityType.CHEST)
                .anchorFromCenter()
                .collidable()
                .build();
    }
    @Spawns("XP")
    public Entity newXP(SpawnData data) {
        Circle circle = new Circle(5);
        circle.setFill(Color.rgb(0,0,255));
        return FXGL.entityBuilder(data)
                .bbox(new HitBox(BoundingShape.circle(2)))
                .view(circle)
                .zIndex(10)
                .type(EntityType.XP)
                .anchorFromCenter()
                .collidable()
                .build();
    }
    @Spawns("Bullet")
    public Entity newBullet(SpawnData data) {
        return FXGL.entityBuilder(data)
                .bbox(new HitBox(BoundingShape.circle(100)))
                .view("Attack.png")
                .scale(new Point2D(data.get("scale"),data.get("scale")))
                .zIndex(20)
                .with(new CollidableComponent(true))
                .with(new BulletComponent(data.get("damage"),data.get("level"),data.get("destination"),data.get("speed"),data.get("type"),data.get("time")))
                .type(EntityType.BULLET)
                .anchorFromCenter()
                .opacity(data.get("opacity"))
                .build();
    }
    @Spawns("AreaAttack")
    public Entity newAreaAttack(SpawnData data){
        return FXGL.entityBuilder(data)
                .bbox(new HitBox(BoundingShape.circle(100)))
                .view("Attack.png")
                .scale(new Point2D(data.get("scale"),data.get("scale")))
                .zIndex(20)
                .with(new CollidableComponent(true))
                .with(new AreaAttackComponent(data.get("damage"),data.get("level"),data.get("type")))
                .type(EntityType.AREAATTACK)
                .anchorFromCenter()
                .opacity(data.get("opacity"))
                .build();
    }
}
