package view;

import controller.GameController;

import javax.swing.*;
import java.awt.*;

public class Frame extends JFrame {
    public Frame(){
        super("Civilization");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setLayout(new BorderLayout());

        view.Ground ground = new Ground();
        view.GameControlPanel GCP = new GameControlPanel();
        UnitActionPanel actionPanel = new UnitActionPanel();

        this.add(GCP, BorderLayout.NORTH);
        this.add(ground, BorderLayout.CENTER);

        this.add(actionPanel, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
        setVisible(true);
    }
}
