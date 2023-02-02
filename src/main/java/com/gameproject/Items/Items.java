package com.gameproject.Items;

import com.almasb.fxgl.core.math.FXGLMath;
import com.gameproject.Enums.AttackType;
import com.gameproject.Mechanics.AreaAttacks;
import com.gameproject.Mechanics.BulletAttack;
import com.gameproject.Mechanics.IAttack;

import java.util.ArrayList;

import static com.almasb.fxgl.dsl.FXGLForKtKt.image;

public class Items {

    static ArrayList<IItem> items = new ArrayList<>();

    public static void initItems(){
        ArrayList<IItem> item = new ArrayList<>();
        ArrayList<IAttack> attacks = new ArrayList<>();
        attacks.add(new BulletAttack(3, AttackType.NEAR,0.9,10,2,1,1,1000));
        item.add(new AttackItem(attacks,image("Items/Item1Texture.png")));

        attacks = new ArrayList<>();
        attacks.add(new BulletAttack(5, AttackType.RANDOMONENEMY, 1,100,10,5,1,50));
        item.add(new AttackItem(attacks,image("Items/Item2Texture.png")));

        attacks = new ArrayList<>();
        attacks.add(new AreaAttacks(20, AttackType.NEAR,0.7,25,5,1,2));
        item.add(new AttackItem(attacks,image("Items/Item3Texture.png")));

        items.addAll(item);
    }

    public static IItem getRandomItem(){
        if(items.size()-1<0)return null;
        return items.get(FXGLMath.random(0,items.size()-1));
    }
    public static void removeItem(IItem item){
        items.remove(item);
    }
    public static void addItem(IItem item){
        items.add(item);
    }
}
