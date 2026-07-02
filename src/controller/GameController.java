package controller;

import model.TerrainType;
import model.Tile;
import model.Unit;
import model.UnitType;
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

        Random random = new Random();
        TerrainType[] types = TerrainType.values();

        int seedsCount = 100;
        int[][] seeds = new int[seedsCount][2];
        TerrainType[] seedTypes = new TerrainType[seedsCount];

        for (int i = 0; i < seedsCount; i++) {
            seeds[i][0] = random.nextInt(COLS);
            seeds[i][1] = random.nextInt(ROWS);
            seedTypes[i] = types[random.nextInt(types.length)];
        }

        ArrayList<Tile> tempTiles = new ArrayList<>();

        for (int col = 0; col < COLS; col++) {
            for (int row = 0; row < ROWS; row++) {
                double minD = Double.MAX_VALUE;
                TerrainType finalType = types[0];

                for (int i = 0; i < seedsCount; i++) {
                    double dist = Math.pow(seeds[i][0] - col, 2) + Math.pow(seeds[i][1] - row, 2);
                    dist += random.nextDouble() * 8.0;

                    if (dist < minD) {
                        minD = dist;
                        finalType = seedTypes[i];
                    }
                }
                tempTiles.add(new Tile(col, row, finalType));
            }
        }
        this.Tiles = tempTiles;

        units.add(new Unit(5, 5, UnitType.EXPLORER));
    }

    private void updateFog() {
        int visionRadius = 2;
        for (Tile tile : Tiles) {
            boolean visible = false;
//DEBUG:            visible = true;
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

    public ArrayList<Unit> getUnits() {
        return units;
    }

    public void addUnit(Unit unit){
        units.add(unit);
    }
}
