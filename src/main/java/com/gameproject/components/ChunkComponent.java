package com.gameproject.components;

import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGLForKtKt.image;

public class ChunkComponent extends Component {

    GridPane pane;
    Image textures;
    Point2D size;
    public ChunkComponent(SpawnData data){
        size = data.get("size");

        textures = image("Terrain.png");
        pane = new GridPane();

        GenerateTerrain();
    }

    public void GenerateTerrain(){
        for(int x=0;x<size.getX();x++){
            for(int y=0;y<size.getY();y++){
                AnimationChannel animIdle = new AnimationChannel(textures, (int)Math.floor(textures.getWidth()/40),40,40,Duration.seconds(1),0,0);
                AnimatedTexture texture = new AnimatedTexture(animIdle);
                pane.add(texture,x,y);
            }
        }
    }

    @Override
    public void onAdded() {
        entity.getViewComponent().addChild(pane);
    }

    @Override
    public void onUpdate(double tpf) {
        super.onUpdate(tpf);
    }

    @Override
    public void onRemoved() {
        super.onRemoved();
    }
}
