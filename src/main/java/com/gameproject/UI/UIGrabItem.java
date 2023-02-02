package com.gameproject.UI;

import com.gameproject.Items.IItem;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import static com.almasb.fxgl.dsl.FXGLForKtKt.*;

public class UIGrabItem {

    AnchorPane pane = new AnchorPane();

    ImageView item;
    VBox prefixes;
    IItem itemObject;

    GridPane items;
    int chooseItem = -1;

    Button cancel;
    Button save;

    public UIGrabItem(){
        pane.setPrefSize(getAppWidth(),getAppHeight());
        //pane.setCenterShape(true);

        AnchorPane window = new AnchorPane();
        window.setPrefSize(1000,800);
        window.setCenterShape(true);
        AnchorPane.setLeftAnchor(window,(getAppWidth()-1000.d)/2);
        AnchorPane.setTopAnchor(window,(getAppHeight()-800.d)/2);

        item = new ImageView();
        item.setFitWidth(100);
        item.setFitHeight(100);
        AnchorPane.setTopAnchor(item,25.d);
        AnchorPane.setLeftAnchor(item,450.d);
        prefixes = new VBox();
        prefixes.setPrefSize(400,100);
        AnchorPane.setTopAnchor(prefixes,150.d);
        AnchorPane.setLeftAnchor(prefixes,300.d);
        items = new GridPane();
        items.setPrefSize(400,200);
        AnchorPane.setTopAnchor(items,275.d);
        AnchorPane.setLeftAnchor(items,300.d);

        cancel = new Button("Cancel");
        cancel.setPrefWidth(300);
        cancel.setPrefHeight(50);
        AnchorPane.setLeftAnchor(cancel,175.d);
        AnchorPane.setBottomAnchor(cancel,25.d);
        save = new Button("Save");
        save.setPrefWidth(300);
        save.setPrefHeight(50);
        AnchorPane.setRightAnchor(save,175.d);
        AnchorPane.setBottomAnchor(save,25.d);

        window.getChildren().addAll(item,prefixes,items,cancel,save);

        pane.getChildren().add(window);

        pane.setStyle("-fx-background-color: rgba(0,0,0,0.7)");
        window.setStyle("-fx-background-color: white; -fx-border-width: 1px; -fx-border-style: solid; -fx-border-color:gray;");
    }

    public AnchorPane getPane(){
        return pane;
    }
    public Button cancel(){
        return cancel;
    }
    public Button save(){
        return save;
    }
    public void setItem(IItem item){
        itemObject = item;
        this.item.setImage(item.getImage());
        prefixes.getChildren().addAll(item.getPrefixesNodes());
    }
    public IItem getItem(){
        return itemObject;
    }
    public int getIndex(){
        return chooseItem;
    }
}
