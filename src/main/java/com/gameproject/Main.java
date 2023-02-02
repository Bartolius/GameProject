package com.gameproject;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.entity.*;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.physics.PhysicsWorld;

import com.gameproject.Chunks.ChunkMap;
import com.gameproject.Enums.UpgradeType;
import com.gameproject.Items.IItem;
import com.gameproject.Items.Items;
import com.gameproject.UI.UIBase;
import com.gameproject.collisionHandler.*;
import com.gameproject.components.PlayerComponent;
import com.gameproject.config.GameConfig;
import com.gameproject.factories.EntityFactories;
//import com.gameproject.factories.MenuFactory;
import com.gameproject.upgrades.*;

import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Map;

import static com.almasb.fxgl.dsl.FXGLForKtKt.*;

public class Main extends GameApplication {
    public static void main(String[] args) {
        launch(args);
    }
    Entity Player;
    public ChunkMap map;
    double time=0;
    UIBase UIBase;
    public static ArrayList<IItem> items = new ArrayList<>();

    @Override
    protected void initSettings(GameSettings gameSettings) {
        gameSettings.setTitle("GameProject");
        gameSettings.setWidth(1920);
        gameSettings.setHeight(1080);
        gameSettings.setVersion("Alfa 0.8");
        gameSettings.setFullScreenAllowed(true);
        gameSettings.setConfigClass(GameConfig.class);
        gameSettings.setFullScreenFromStart(true);
        gameSettings.setTicksPerSecond(60);
        //gameSettings.setSceneFactory(new MenuFactory());
        //gameSettings.setMainMenuEnabled(true);

    }
    @Override
    protected void initGameVars(Map<String, Object> vars) {
        vars.put("level",1);
        vars.put("xp",0);
        vars.put("toNext",5);
        vars.put("time",0);
        vars.put("FPS",0);
        vars.put("maxLife",150);
        vars.put("life",150);
    }

    @Override
    protected void initUI() {
        UIBase = new UIBase();

        Text FPS = getUIFactoryService().newText("", Color.BLUE,20);
        FPS.textProperty().bind(getip("FPS").asString("FPS count: %d"));

        addUINode(UIBase.getPane(),0,getAppHeight()-200);
        addUINode(FPS,10,30);
    }


    @Override
    protected void initPhysics() {
        PhysicsWorld physics = getPhysicsWorld();
        physics.setGravity(0, 0);
        physics.addCollisionHandler(new XPHandler());
        physics.addCollisionHandler(new EnemyHandler());
        physics.addCollisionHandler(new BulletHandler());
        physics.addCollisionHandler(new AreaAttackHandler());
        physics.addCollisionHandler(new ChestHandler());
    }

    public static void addXP(int count){
        inc("xp",+count);
        while(geti("xp")>=geti("toNext")){
            inc("xp",-geti("toNext"));
            inc("toNext", geti("toNext")/2);
            inc("level", +1);
        }
    }

    @Override
    protected void initInput() {
        getInput().addAction(new UserAction("Left") {
            @Override
            protected void onAction() {
                Player.getComponent(PlayerComponent.class).moveLeft();
            }
        },KeyCode.A);
        getInput().addAction(new UserAction("Right") {
            @Override
            protected void onAction() {
                Player.getComponent(PlayerComponent.class).moveRight();
            }
        },KeyCode.D);
        getInput().addAction(new UserAction("Top") {
            @Override
            protected void onAction() {
                Player.getComponent(PlayerComponent.class).moveTop();
            }
        },KeyCode.W);
        getInput().addAction(new UserAction("Bottom") {
            @Override
            protected void onAction() {
                Player.getComponent(PlayerComponent.class).moveBottom();
            }
        },KeyCode.S);
    }
    private void initMap() {
        Player = spawn("Player",new SpawnData(8*40-20,8*40-40));

        getGameScene().getViewport().setX(Player.getX()-(double)getAppWidth()/2);
        getGameScene().getViewport().setY(Player.getY()-(double)getAppHeight()/2);
        getGameScene().getViewport().bindToEntity(Player,(double)getAppWidth()/2-20,(double)getAppHeight()/2-40);
        map = new ChunkMap(Player.getComponent(PlayerComponent.class).lastPosition);
        spawn("Chest",-100,-100);
    }
    private void initUpgrades(){
        Upgrade Upgrade1 = new Upgrade(1,1,1,null, UpgradeType.AreaDamage);
        Upgrade Upgrade2 = new Upgrade(1,1,2,Upgrade1, UpgradeType.AreaDamage);
        Upgrade Upgrade3 = new Upgrade(1,1,4,Upgrade2, UpgradeType.AreaDamage);
        Upgrade Upgrade4 = new Upgrade(1,1,5.5,Upgrade3, UpgradeType.AreaDamage);
        Upgrade Upgrade5 = new Upgrade(1,1,7,Upgrade4, UpgradeType.AreaDamage);
        Upgrade Upgrade6_1 = new Upgrade(1,0.5,8,Upgrade5, UpgradeType.AreaDamage);
        Upgrade Upgrade6_2 = new Upgrade(1,0.6,9,Upgrade5, UpgradeType.AreaDamage);
        Upgrade Upgrade6_3 = new Upgrade(1,0.7,10,Upgrade5, UpgradeType.AreaDamage);
        Upgrade Upgrade6_4 = new Upgrade(1,0.8,11,Upgrade5, UpgradeType.AreaDamage);
        Upgrade Upgrade6_5 = new Upgrade(1,0.9,12,Upgrade5, UpgradeType.AreaDamage);
        Upgrade Upgrade6_6 = new Upgrade(1,1,13,Upgrade5, UpgradeType.AreaDamage);

        ArrayList<Upgrade> upgrades = new ArrayList<>();
        upgrades.add(Upgrade1);
        upgrades.add(Upgrade2);
        upgrades.add(Upgrade3);
        upgrades.add(Upgrade4);
        upgrades.add(Upgrade5);
        upgrades.add(Upgrade6_1);
        upgrades.add(Upgrade6_2);
        upgrades.add(Upgrade6_3);
        upgrades.add(Upgrade6_4);
        upgrades.add(Upgrade6_5);
        upgrades.add(Upgrade6_6);

        Upgrades.AddLine(upgrades);
        Upgrades.UnlockUpgrade(Upgrade1);
    }
    @Override
    protected void initGame() {
        getGameWorld().addEntityFactory(new EntityFactories());
        initMap();
        initUpgrades();
        Items.initItems();
    }

    @Override
    protected void onUpdate(double tpf) {
        int x = FXGLMath.floor((float)Player.getX()/(16*40));
        int y = FXGLMath.floor((float)Player.getY()/(16*40));

        time+=Duration.seconds(1).toSeconds()*tpf;
        inc("time",(int)time-geti("time"));
        set("FPS",(int)(1.d/tpf));


        if(x!=Player.getComponent(PlayerComponent.class).lastPosition.getX() || y!=Player.getComponent(PlayerComponent.class).lastPosition.getY()){
            Player.getComponent(PlayerComponent.class).changePosition(x,y);
            map.initChunks(Player.getComponent(PlayerComponent.class).lastPosition);
        }
        map.update(tpf);
        UIBase.update();
    }
}