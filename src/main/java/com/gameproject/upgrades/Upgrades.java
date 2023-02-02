package com.gameproject.upgrades;

import com.almasb.fxgl.core.math.FXGLMath;
import com.gameproject.Enums.UpgradeType;

import java.util.ArrayList;

public class Upgrades {
    static ArrayList<Upgrade> AllAvailableUpgrades = new ArrayList<>();
    static ArrayList<Upgrade> ToChooseUpgrade = new ArrayList<>();
    static ArrayList<Upgrade> AvailableUpgrades = new ArrayList<>();
    static ArrayList<Upgrade> Unavailable = new ArrayList<>();

    /*public void AddUpgrade(double time, double delay, double damageFactor, Upgrade upgrade, UpgradeType type){
        Unavailable.add(new Upgrade(time,delay,damageFactor,upgrade,type));
    }*/
    public static ArrayList<Upgrade> getUpgradesByType(UpgradeType type){
        ArrayList<Upgrade> upgrades = new ArrayList<>();
        for (Upgrade allAvailableUpgrade : AllAvailableUpgrades) {
            if (allAvailableUpgrade.type == type) {
                upgrades.add(allAvailableUpgrade);
            }
        }
        return upgrades;
    }
    public static void AddLine(ArrayList<Upgrade> upgrades){
        Unavailable.addAll(upgrades);
    }
    public static void UnlockUpgrade(Upgrade upgrade){
        Upgrade upgradeA;
        int i=0;
        while(i<Unavailable.size()){
            if(Unavailable.get(i).prevUpgrade == upgrade){
                upgradeA = Unavailable.get(i);
                Unavailable.remove(upgradeA);
                AvailableUpgrades.add(upgradeA);
                i--;
            }
            i++;
        }
    }
    public static void getUpgrade(Upgrade upgrade){
        ToChooseUpgrade.remove(upgrade);
        AllAvailableUpgrades.add(upgrade);
        UnlockUpgrade(upgrade);
        clear();
    }
    public static Upgrade getRandomUpgrade(){
        if(AvailableUpgrades.size()<1)return null;
        Upgrade choose = AvailableUpgrades.get(FXGLMath.random(0,AvailableUpgrades.size()-1));
        ToChooseUpgrade.add(choose);
        AvailableUpgrades.remove(choose);
        return choose;
    }
    public static void clear(){
        AvailableUpgrades.addAll(ToChooseUpgrade);
        ToChooseUpgrade.clear();
    }
}
