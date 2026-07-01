package controller;

import model.TerrainType;
import model.Tile;
import view.Ground;
import view.Hex;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Random;

public class GameController {
    private GameController instance;
    private Ground ground;
    private Camera camera;

    final static int ROWS = 100, COLS = 100;

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

        double h = Math.sqrt(3);

        for (int col = 0; col < COLS; col++) {
            double x = 1.5 + col * 1.5;

            for (int row = 0; row < ROWS; row++) {
                double y = 1.5 + row * h;

                if (col % 2 == 1)
                    y += h / 2.0;

                Random random = new Random();
                TerrainType[] types = TerrainType.values();
                TerrainType randomType = types[random.nextInt(types.length)];

                Tiles.add(new Tile(x, y, randomType));
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
