package com.gameproject.UI;

import com.almasb.fxgl.ui.ProgressBar;
import com.gameproject.Enums.EntityType;
import com.gameproject.Items.IItem;
import com.gameproject.components.PlayerComponent;
import com.gameproject.upgrades.Upgrade;
import com.gameproject.upgrades.Upgrades;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.util.ArrayList;

import static com.almasb.fxgl.dsl.FXGLForKtKt.*;

public class UIBase {
    AnchorPane pane = new AnchorPane();
    GridPane upgrades = new GridPane();

    ArrayList<Upgrade> upgradesList = new ArrayList<>();
    int chooseUpgrades = 0;

    ArrayList<IItem> itemList = new ArrayList<>();
    GridPane items = new GridPane();
    static ProgressBar life = new ProgressBar(false);

    public UIBase(){
        Text level = getUIFactoryService().newText("", Color.BLACK,22);
        Text XP = getUIFactoryService().newText("", Color.BLACK,22);
        Text toNext = getUIFactoryService().newText("", Color.BLACK,22);
        Text timer = getUIFactoryService().newText("", Color.BLACK,22);

        level.textProperty().bind(getip("level").asString("Level: %d"));
        XP.textProperty().bind(getip("xp").asString("XP: %d"));
        toNext.textProperty().bind(getip("toNext").asString("To next level: %d"));
        timer.textProperty().bind(getip("time").asString("Time: %d"));

        pane.setPrefWidth(getAppWidth());
        pane.setPrefHeight(200);

        pane.getChildren().add(level);
        AnchorPane.setLeftAnchor(level,0.d);
        AnchorPane.setTopAnchor(level,0.d);
        pane.getChildren().add(XP);
        AnchorPane.setLeftAnchor(XP,0.d);
        AnchorPane.setTopAnchor(XP,40.d);
        pane.getChildren().add(toNext);
        AnchorPane.setLeftAnchor(toNext,0.d);
        AnchorPane.setTopAnchor(toNext,80.d);
        pane.getChildren().add(timer);
        AnchorPane.setLeftAnchor(timer,0.d);
        AnchorPane.setTopAnchor(timer,120.d);

        pane.getChildren().add(upgrades);
        upgrades.setPrefWidth(400);
        upgrades.setPrefHeight(100);
        AnchorPane.setTopAnchor(upgrades,50.d);
        AnchorPane.setLeftAnchor(upgrades, (getAppWidth()-360)/2.d);

        pane.getChildren().add(items);
        items.setPrefWidth(400);
        items.setPrefHeight(200);
        AnchorPane.setTopAnchor(items,0.d);
        AnchorPane.setRightAnchor(items, 0.d);

        pane.getChildren().add(life);
        life.setHeight(10);
        life.setWidth(400);
        life.setLabelVisible(false);
        life.setFill(Color.RED);
        life.setBackgroundFill(Color.BLACK);
        life.setTraceFill(Color.BLUE);
        life.setMaxValue(getGameWorld().getEntitiesByType(EntityType.PLAYER).get(0).getComponent(PlayerComponent.class).maxLife);
        life.setCurrentValue(getGameWorld().getEntitiesByType(EntityType.PLAYER).get(0).getComponent(PlayerComponent.class).life);
        AnchorPane.setTopAnchor(life,0.d);
        AnchorPane.setLeftAnchor(life,(getAppWidth()-400)/2.d);

        pane.setStyle("-fx-background-color:white;");
        upgrades.setStyle("-fx-border-width: 1px; -fx-border-style: solid; -fx-border-color: black;");
        items.setStyle("-fx-border-width: 1px; -fx-border-style: solid; -fx-border-color: black;");
    }

    public AnchorPane getPane(){
        return pane;
    }
    public void SetUpgrades(){
        Upgrade upgrade;
        for(int i=0;i<4;i++){
            upgrade = Upgrades.getRandomUpgrade();
            if(upgrade==null)break;

            upgradesList.add(upgrade);
            Rectangle rect = new Rectangle(100,100,Color.BLUE);
            rect.setTranslateZ(i);
            rect.setOnMouseClicked(mouseEvent -> UpgradePlayer((int)rect.getTranslateZ()));
            upgrades.add(rect,i,0);
        }
    }
    public void UpgradePlayer(int number){
        Upgrades.getUpgrade(upgradesList.get(number));
        for(int i=0;i<upgradesList.size();i++){
            upgrades.add(new Rectangle(100,100, Color.WHITE),i,0);
        }
        upgradesList.clear();
        chooseUpgrades++;
    }
    public void update(){
        if(geti("level")>chooseUpgrades+1 && upgradesList.size()==0){
            SetUpgrades();
        }
        for (int i = 0; i < getGameWorld().getEntitiesByType(EntityType.PLAYER).get(0).getComponent(PlayerComponent.class).items.size(); i++) {
            if(!itemList.contains(getGameWorld().getEntitiesByType(EntityType.PLAYER).get(0).getComponent(PlayerComponent.class).items.get(i))){
                itemList.add(getGameWorld().getEntitiesByType(EntityType.PLAYER).get(0).getComponent(PlayerComponent.class).items.get(i));
                ImageView image = new ImageView();
                image.setImage(getGameWorld().getEntitiesByType(EntityType.PLAYER).get(0).getComponent(PlayerComponent.class).items.get(i).getImage());
                items.add(image,i%4, i /4);
            }
        }
    }
    public static void setLife(double maxLife, double actLife){
        life.setMaxValue(maxLife);
        life.setCurrentValue(actLife);
    }
}
