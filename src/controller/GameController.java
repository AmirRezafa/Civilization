package controller;

import model.TerrainType;
import model.Tile;
import model.Unit;
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
    private ArrayList<Unit> units = new ArrayList<>();

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

        for (int col = 0; col < COLS; col++) {
            for (int row = 0; row < ROWS; row++) {
                Random random = new Random();
                TerrainType[] types = TerrainType.values();
                TerrainType randomType = types[random.nextInt(types.length)];

                Tiles.add(new Tile(col, row, randomType));
            }
        }
        units.add(new Unit(5, 5));
    }

    private void updateFog() {
        int visionRadius = 2;
        for (Tile tile : Tiles) {
            boolean visible = false;
            for (Unit unit : units) {
                if (Math.abs(tile.getCol() - unit.getCol()) <= visionRadius &&
                        Math.abs(tile.getRow() - unit.getRow()) <= visionRadius) {
                    visible = true;
                    break;
                }
            }
            tile.setVisible(visible);
        }
    }

    public GameController getInstance() {
        return instance;
    }

    private void frameGenerator() {
        camera.run();
        updateFog();
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
