package com.gameproject.Mechanics;

import com.almasb.fxgl.app.scene.Viewport;
import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.gameproject.Enums.AttackType;
import com.gameproject.Enums.EntityType;
import com.gameproject.Enums.UpgradeType;
import com.gameproject.Main;
import com.gameproject.components.AreaAttackComponent;
import com.gameproject.components.EnemyComponent;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.almasb.fxgl.dsl.FXGLForKtKt.*;

public class AreaAttacks implements IAttack{
    double radius;
    AttackType attackType;
    double opacity;

    double damage;

    double time;
    double actualTime=0;
    public double delay;
    public double actualDelay=0;

    public boolean active = true;

    int count;
    ArrayList<Entity> areas = new ArrayList<>();

    double level = 1;

    public AreaAttacks(double radius, AttackType attackType, double opacity, double damage, double time, double delay, int count) {
        this.radius = radius;
        this.attackType = attackType;
        this.opacity = opacity;
        this.damage = damage;
        this.time = time;
        this.delay = delay;
        this.count = count;
    }

    @Override
    public void initAttack(){
        Point2D pos;
        SpawnData data;
        Viewport view = getGameScene().getViewport();
        for(int i=0; i<count; i++){
            pos = new Point2D(0,0);
            switch (attackType) {
                case NEAR -> {
                    Optional<Entity> optional = getGameWorld().getClosestEntity(getGameWorld().getEntitiesByType(EntityType.PLAYER).get(0), (Entity entity) -> entity.getType() == EntityType.ENEMY);
                    if(optional.isEmpty()){
                        pos = new Point2D(FXGL.random(view.getX(), view.getX() + view.getWidth()), FXGL.random(view.getY(), view.getY() + view.getHeight()));
                    }else{
                        Entity nearest = optional.get();
                        pos = new Point2D(nearest.getX() + nearest.getWidth()/2 - 100, nearest.getY() + nearest.getHeight()/2 - 100);
                    }
                }
                case RANDOM -> pos = new Point2D(FXGL.random(view.getX(), view.getX() + view.getWidth()), FXGL.random(view.getY(), view.getY() + view.getHeight()));
                case RANDOMONENEMY -> {
                    List<Entity> entities = getGameWorld().getEntitiesByType(EntityType.ENEMY);
                    if(entities.isEmpty()){
                        pos = new Point2D(FXGL.random(view.getX(), view.getX() + view.getWidth()), FXGL.random(view.getY(), view.getY() + view.getHeight()));
                    }else{
                        Entity entity = entities.get(FXGLMath.random(0,entities.size()-1));
                        pos=new Point2D(entity.getX() + entity.getWidth()/2 - 100,entity.getY() + entity.getHeight()/2 - 100);
                    }
                }
            }
            data = new SpawnData(pos);
            data.put("scale",radius/100);
            data.put("opacity", opacity);
            data.put("damage",damage);
            data.put("level",level);
            data.put("type", UpgradeType.AreaDamage);
            areas.add(spawn("AreaAttack",data));
        }
    }

    @Override
    public void onUpdate(double tpf){
        if(!active){
            if(actualTime<time)actualTime+= Duration.seconds(1).toSeconds()*tpf;
            if(actualTime>=time){
                int i=0;
                while(i<areas.size()){
                    areas.get(i).removeFromWorld();
                    areas.remove(i);
                }
                actualDelay+=Duration.seconds(1).toSeconds()*tpf;
            }

            if(actualDelay>=delay){
                actualTime=0;
                actualDelay=0;
                active=true;
            }
        }
        if(active){
            initAttack();
            active=false;
        }

        int j=0;
        while(j<areas.size()){
            List<Entity> enemies = getGameWorld().getCollidingEntities(areas.get(j)).stream().filter(p -> p.getType() == EntityType.ENEMY).toList();
            int i=0;
            while(i<enemies.size()){
                if(!enemies.get(i).hasComponent(EnemyComponent.class)){
                    i++;
                    continue;
                }
                if(enemies.get(i).getComponent(EnemyComponent.class).getDamage(areas.get(j).getComponent(AreaAttackComponent.class).damage()*tpf)){
                    double xp = enemies.get(i).getWidth()/10;
                    Main.addXP(xp<=1?1:(int)xp);
                    enemies.get(i).removeFromWorld();
                }
                i++;
            }
            j++;
        }
    }

    @Override
    public ArrayList<Node> getNodes() {
        ArrayList<Node> nodes = new ArrayList<>();
        nodes.add(new Text("Radius: "+radius));
        nodes.add(new Text("Type: "+attackType.toString()));
        nodes.add(new Text("Damage: "+damage));
        nodes.add(new Text("Time: "+time));
        nodes.add(new Text("Delay: "+delay));
        return(nodes);
    }
}
