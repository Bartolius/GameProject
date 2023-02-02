package com.gameproject.components;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import com.gameproject.Enums.EntityType;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGLForKtKt.*;

public class EnemyComponent extends Component {

    Point2D dest;

    double speed;

    double lifeTime = 0;

    double life = 100;
    double damage = 10;
    AnimationChannel animIdle;
    AnimatedTexture texture;

    public EnemyComponent(double speed){
        this.speed=speed;
        Image img = image("Enemy.png");
        animIdle = new AnimationChannel(img,(int)img.getHeight()/4,40,40,Duration.seconds(1),0,3);

        texture = new AnimatedTexture(animIdle);
        texture.loopAnimationChannel(animIdle);
    }

    @Override
    public void onAdded() {
        entity.getViewComponent().addChild(texture);
    }

    @Override
    public void onUpdate(double tpf) {
        if(lifeTime>15)entity.removeFromWorld();
        if(entity.isWithin(getGameScene().getViewport().getVisibleArea()))lifeTime=0;
        lifeTime += Duration.seconds(1).toSeconds()*tpf;

        Entity player = getGameWorld().getEntitiesByType(EntityType.PLAYER).get(0);
        dest = new Point2D(player.getX() - entity.getX(), player.getY()+ 20 - entity.getY());

        double speedScl = Math.sqrt(Math.pow(speed,2)/(Math.pow(dest.getX(),2)+Math.pow(dest.getY(),2)));

        double speedX = dest.getX() * speedScl;
        double speedY = dest.getY() * speedScl;
        entity.translate(speedX*tpf,speedY*tpf);
    }

    @Override
    public void onRemoved() {
        super.onRemoved();
    }

    public double dealDamage() {
        return damage;
    }
    public boolean getDamage(double damage){
        life-=damage;
        return life <= 0;
    }
}
