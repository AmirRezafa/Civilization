package controller;

import model.Tile;
import view.Ground;
import view.Hex;

import javax.swing.*;
import java.util.ArrayList;

public class GameController {
    private GameController instance;
    private Ground ground;
    private Camera camera;

    private ArrayList<Tile> Tiles = new ArrayList<>();

    public GameController(Ground ground) {
        camera = new Camera();
        Timer timer = new Timer(
                8,
                e -> frameGenerator()
        );
        timer.start();
        this.ground = ground;
        ground.addMouseMotionListener(camera);
        ground.addMouseListener(camera);

        int cols = 200, rows = 200;

        double h = Math.sqrt(3);

        for (int col = 0; col < cols; col++) {
            double x = 1 + col * 1.5;

            for (int row = 0; row < rows; row++) {
                double y = 1 + row * h;

                if (col % 2 == 1)
                    y += h / 2.0;

                Tiles.add(new Tile(x, y));
            }
        }
    }

    public GameController getInstance() {
        return instance;
    }

    private void frameGenerator() {
        camera.run();
        ground.repaint();
    }

    public int getXOffset(){
        return camera.getXOffset();
    }

    public int getYOffset(){
        return camera.getYOffset();
    }

    public ArrayList<Tile> getTiles() {
        return Tiles;
    }
}
