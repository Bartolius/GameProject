package com.gameproject.Mechanics;

import javafx.scene.Node;

import java.util.ArrayList;

public interface IAttack {
    void initAttack();
    void onUpdate(double tpf);
    ArrayList<Node> getNodes();
}
