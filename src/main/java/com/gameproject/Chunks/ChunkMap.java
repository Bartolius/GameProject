package com.gameproject.Chunks;

import com.almasb.fxgl.app.scene.Viewport;
import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.entity.SpawnData;
import javafx.geometry.Point2D;
import javafx.util.Duration;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameScene;
import static com.almasb.fxgl.dsl.FXGLForKtKt.spawn;

public class ChunkMap {
    public static ArrayList<Chunks>chunks = new ArrayList<>();

    int RenderDistance = 1;
    Point2D ChunkSize = new Point2D(16,16);

    double EnemySpawnTime = 5;
    double AlreadySpawnTime = 0;

    int count = 5;


    public ChunkMap(Point2D pos){
        initChunks(pos);
    }
    public void initChunks(@NotNull Point2D pos){
        int posX = (int) pos.getX();
        int posY = (int) pos.getY();
        /*for(int x = posX-RenderDistance-1; x <= posX+RenderDistance+1; x++){
            for(int y = posY-RenderDistance-1; y <= posY+RenderDistance+1; y++){
                if(!this.chunkAtPos(x, y)) chunks.add(new Chunks(x, y, ChunkSize));
            }
        }*/
        for(int x=posX-RenderDistance-1; x<=posX+RenderDistance+1; x++){ //Good work
            for(int y=posY-RenderDistance; y<=posY+RenderDistance; y++){ //bad work
                /*if(this.chunkAtPos(x, y))
                    System.out.println(x+":"+y);*/
                if(!this.chunkAtPos(x,y)){
                    chunks.add(new Chunks(x,y,ChunkSize));
                }
            }
        }
    }
    public void update(double tpf){
        for(int i=0; i<chunks.size(); i++){
            if(chunks.get(i).checkLifeTime(tpf)){
                chunks.get(i).remove();
                chunks.remove(i);
                i--;
            }
        }
        AlreadySpawnTime += Duration.seconds(1).toSeconds()*tpf;

        if(AlreadySpawnTime>=EnemySpawnTime){
            Viewport view = getGameScene().getViewport();
            AlreadySpawnTime-=EnemySpawnTime;
            for(int i=0;i<count;i++){
                double scale = FXGLMath.random(0.5,5.5);
                int side = FXGLMath.random(0,3);
                double posX=0;
                double posY=0;
                double radius = 20;

                switch(side){
                    case 0 ->{
                        posX = FXGLMath.random(view.getX()-radius*2*scale,view.getX()+view.getWidth()+radius*2*scale);
                        posY = view.getY()-radius*2*scale;
                    }
                    case 1 ->{
                        posX = view.getX()-radius*2*scale;
                        posY = FXGLMath.random(view.getY()-radius*2*scale,view.getY()+view.getHeight()+radius*2*scale);
                    }
                    case 2 ->{
                        posX = FXGLMath.random(view.getX()-radius*2*scale,view.getX()+view.getWidth()+radius*2*scale);
                        posY = view.getY()+view.getHeight()+radius*2*scale;
                    }
                    case 3 ->{
                        posX = view.getX()+view.getWidth()+radius*2*scale;
                        posY = FXGLMath.random(view.getY()-radius*2*scale,view.getY()+view.getHeight()+radius*2*scale);
                    }
                }
                SpawnData data = new SpawnData(posX,posY);
                data.put("radius",radius);
                data.put("scale",scale);
                spawn("Enemy",data);
            }
        }
    }
    private boolean chunkAtPos(int x, int y){
        for(Chunks chunk : chunks){
            if(chunk.posX==x && chunk.posY==y)return true;
        }
        return false;
    }
}
