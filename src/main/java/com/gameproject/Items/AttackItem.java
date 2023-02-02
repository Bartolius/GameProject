package com.gameproject.Items;
import com.gameproject.Mechanics.IAttack;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;

import java.util.ArrayList;

public class AttackItem implements IItem{

    public ArrayList<IAttack> attacks = new ArrayList<>();
    public Image img;

    public AttackItem(ArrayList<IAttack> attacks, Image texture){
        this.attacks.addAll(attacks);
        img=texture;
    }
    @Override
    public void Attack(double tpf) {
        for(IAttack attack : attacks){
            attack.onUpdate(tpf);
        }
    }

    @Override
    public Image getImage() {
        return img;
    }

    @Override
    public ArrayList<Node> getPrefixesNodes() {
        ArrayList<Node> nodes = new ArrayList<>();
        for(IAttack attack : attacks){
            HBox box = new HBox();
            box.getChildren().addAll(attack.getNodes());
            nodes.add(box);
        }
        return(nodes);
    }
}
