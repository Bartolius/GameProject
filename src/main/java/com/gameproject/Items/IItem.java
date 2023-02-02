package com.gameproject.Items;

import javafx.scene.Node;
import javafx.scene.image.Image;

import java.util.ArrayList;

public interface IItem {
    void Attack(double tpf);
    Image getImage();

    ArrayList<Node> getPrefixesNodes();
}
