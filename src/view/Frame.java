package view;

import controller.GameController;

import javax.swing.*;

public class Frame extends JFrame {
    public Frame(){
        super("Civilization");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(new Ground());
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
