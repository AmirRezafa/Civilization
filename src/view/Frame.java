package view;

import controller.GameController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class Frame extends JFrame {
    private JLayeredPane layeredPane;
    private Ground ground;
    private GameControlPanel GCP;
    private UnitActionPanel actionPanel;

    public Frame(){
        super("Civilization");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setLayout(new BorderLayout());

        ground = new Ground();
        GCP = new GameControlPanel();
        actionPanel = new UnitActionPanel();

        layeredPane = new JLayeredPane();

        layeredPane.add(ground, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(actionPanel, JLayeredPane.PALETTE_LAYER);

        this.add(GCP, BorderLayout.NORTH);
        this.add(layeredPane, BorderLayout.CENTER);

        layeredPane.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                updateLayeredLayoutBounds();
            }
        });

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void updateLayeredLayoutBounds() {
        int paneWidth = layeredPane.getWidth();
        int paneHeight = layeredPane.getHeight();

        ground.setBounds(0, 0, paneWidth, paneHeight);

        int actionHeight = 80;
        int actionY = paneHeight - actionHeight;
        actionPanel.setBounds(0, actionY, paneWidth, actionHeight);

        layeredPane.revalidate();
        layeredPane.repaint();
    }
}
