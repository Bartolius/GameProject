package com.gameproject.Chunks;

import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.gameproject.Enums.EntityType;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;

import java.util.ArrayList;
import java.util.List;

import static com.almasb.fxgl.dsl.FXGLForKtKt.*;

public class Chunks {

    public int posX, posY;
    Point2D size;
    public Entity chunk;
    double LifeTime = 0;

    public Chunks(int x, int y, Point2D size) {
        posX = x;
        posY = y;
        this.size = size;

        SpawnData data = new SpawnData(posX*size.getX()*40,posY*size.getY()*40);
        data.put("size",this.size);

        chunk = spawn("Chunk", data);
        initXP();
        initChest();
    }
    public void initXP(){
        int count = FXGLMath.random(1,5);
        for(int i=0;i<count;i++){
            SpawnData data = new SpawnData(posX*size.getX()*40+FXGLMath.random(0,size.getX()*40),posY*size.getY()*40+FXGLMath.random(0,size.getY()*40));
            spawn("XP",data);
        }
    }
    public void initChest(){
        boolean c = FXGLMath.randomBoolean(0.1);
        if(c){
            spawn("Chest",new SpawnData(posX*size.getX()*40+FXGLMath.random(0,size.getX()*40),posY*size.getY()*40+FXGLMath.random(0,size.getY()*40)));
        }
    }
    public boolean checkLifeTime(double tpf){
        Rectangle2D view = getGameScene().getViewport().getVisibleArea();
        if(chunk.isWithin(view)) LifeTime=0;
        if(LifeTime>5) return true;
        //LifeTime += Duration.seconds(1).toSeconds()*tpf;
        return false;
    }
    public void remove(){
        List<Entity> entities = new ArrayList<>(getGameWorld().getCollidingEntities(chunk).stream().filter(p -> p.getType() == EntityType.XP||p.getType() == EntityType.CHEST).toList());
        int i=0;
        while(i<entities.size()){
            entities.get(i).removeFromWorld();
            entities.remove(i);
        }
        chunk.removeFromWorld();
    }
}
