package com.gameproject.components;

import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import com.gameproject.Enums.AttackType;
import com.gameproject.Items.IItem;
import com.gameproject.Mechanics.AreaAttacks;
import com.gameproject.Mechanics.BulletAttack;
import com.gameproject.Mechanics.IAttack;
import com.gameproject.UI.UIBase;
import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.util.Duration;

import java.util.ArrayList;

import static com.almasb.fxgl.dsl.FXGLForKtKt.*;

public class PlayerComponent extends Component {

    private double speedX = 0, speedY = 0;
    AnimatedTexture texture;
    AnimationChannel animIdle;
    AnimationChannel animX, animXBottom, animBottom, animXTop, animTop;
    public Point2D lastPosition;

    public double life = 150;
    public double maxLife = 150;
    double lifeRegen = 0;
    public double iframe = 0;

    public ArrayList<IAttack> attacks = new ArrayList<>();
    public ArrayList<IItem> items = new ArrayList<>();

    public PlayerComponent(){
        animIdle = new AnimationChannel(image("Player1/Texture.png"),4,40,80, Duration.seconds(1),0,3);

        animX = new AnimationChannel(image("Player1/TextureMove.png"),4,40,80, Duration.seconds(1),0,3);
        animXBottom = new AnimationChannel(image("Player1/TextureMove.png"),4,40,80, Duration.seconds(1),4,7);
        animBottom = new AnimationChannel(image("Player1/TextureMove.png"),4,40,80, Duration.seconds(1),8,11);
        animXTop = new AnimationChannel(image("Player1/TextureMove.png"),4,40,80, Duration.seconds(1),12,15);
        animTop = new AnimationChannel(image("Player1/TextureMove.png"),4,40,80, Duration.seconds(1),16,19);

        texture = new AnimatedTexture(animIdle);
        texture.loopAnimationChannel(animIdle);
    }

    @Override
    public void onAdded() {
        entity.getTransformComponent().setScaleOrigin(new Point2D(20,40));
        entity.getViewComponent().addChild(texture);
        lastPosition = new Point2D(FXGLMath.floor((float)entity.getX()/(16*40)), FXGLMath.floor((float)entity.getY()/(16*40)));

        attacks.add(new AreaAttacks(50, AttackType.NEAR,0.7,10,5,2,1));
        attacks.add(new BulletAttack(5, AttackType.NEAR,1,25,1,0.1,1,500));
    }

    public void changePosition(int x,int y){
        lastPosition = new Point2D(x, y);
    }
    @Override
    public void onUpdate(double tpf) {
        entity.translate(speedX*tpf, speedY*tpf);

        if(FXGLMath.abs(speedX) < 1){
            speedX=0;
            getEntity().setScaleX(FXGLMath.abs(entity.getScaleX()));
        }
        if(FXGLMath.abs(speedY) < 1) speedY=0;

        if(FXGLMath.abs(speedX)>=1 && speedY<=-1 && texture.getAnimationChannel()!=animXTop) texture.loopAnimationChannel(animXTop);
        if(FXGLMath.abs(speedX)>=1 && speedY>=1 && texture.getAnimationChannel()!=animXBottom) texture.loopAnimationChannel(animXBottom);
        if(FXGLMath.abs(speedX)>=1 && FXGLMath.abs(speedY)<1 && texture.getAnimationChannel()!=animX) texture.loopAnimationChannel(animX);
        if(FXGLMath.abs(speedX)<1 && speedY>=1 && texture.getAnimationChannel()!=animBottom) texture.loopAnimationChannel(animBottom);
        if(FXGLMath.abs(speedX)<1 && speedY<=-1 && texture.getAnimationChannel()!=animTop) texture.loopAnimationChannel(animTop);

        if(speedX==0 && speedY==0 && texture.getAnimationChannel()!=animIdle){
            texture.loopAnimationChannel(animIdle);
        }

        speedX*=0.8;
        speedY*=0.8;

        for(IAttack attack : attacks){
            attack.onUpdate(tpf);
        }
        for(IItem item : items){
            item.Attack(tpf);
        }

        if(iframe>0){
            iframe-=Duration.seconds(1).toSeconds()*tpf;
            if(iframe<0){
                iframe=0;
            }
        }

        if(life<maxLife){
            lifeRegen+=Duration.seconds(1).toSeconds()*tpf;
            if(lifeRegen>=1){
                lifeRegen-=1;
                life+=1;
                UIBase.setLife(maxLife,life);
            }
        }
    }
    public void getDamage(double damage){
        life-=damage;
        UIBase.setLife(maxLife,life);
        if(life<=0) {Platform.exit();System.exit(0);}//getGameController().pauseEngine();//Platform.exit();
    }
    public void moveLeft(){
        speedX = -150;
        getEntity().setScaleX(-1 * FXGLMath.abs(getEntity().getScaleX()));
    }
    public void moveRight(){
        speedX = 150;
        getEntity().setScaleX(FXGLMath.abs(getEntity().getScaleX()));
    }
    public void moveTop(){
        speedY = -150;
    }
    public void moveBottom(){
        speedY = 150;
    }

    public void setItem(IItem item) {
        items.add(item);
    }
    public IItem setItem(int index,IItem item){
        IItem itemToReturn = items.get(index);
        items.set(index,item);
        return itemToReturn;
    }
}
