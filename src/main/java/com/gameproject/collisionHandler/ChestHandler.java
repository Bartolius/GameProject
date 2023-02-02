package com.gameproject.collisionHandler;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import com.gameproject.Enums.EntityType;
import com.gameproject.Items.IItem;
import com.gameproject.Items.Items;
import com.gameproject.UI.UIGrabItem;
import com.gameproject.components.PlayerComponent;

import static com.almasb.fxgl.dsl.FXGLForKtKt.*;

public class ChestHandler extends CollisionHandler {
    public ChestHandler() {
        super(EntityType.CHEST,EntityType.PLAYER);
    }

    @Override
    protected void onCollisionBegin(Entity chest, Entity player) {
        chest.removeFromWorld();
        IItem item = Items.getRandomItem();
        if(item==null)return;
        UIGrabItem ui = new UIGrabItem();
        ui.setItem(item);
        addUINode(ui.getPane());
        ui.cancel().setOnMouseClicked(mouseEvent -> removeUINode(ui.getPane()));
        ui.save().setOnMouseClicked(mouseEvent -> {
            removeUINode(ui.getPane());
            int index = ui.getIndex();
            if(index>-1&&index<8){
                IItem ReturnedItem = player.getComponent(PlayerComponent.class).setItem(index,ui.getItem());
                Items.removeItem(item);
                Items.addItem(ReturnedItem);
            }else{
                if(player.getComponent(PlayerComponent.class).items.size()<8){
                    player.getComponent(PlayerComponent.class).setItem(ui.getItem());
                    Items.removeItem(item);
                }
            }
        });
    }
}
