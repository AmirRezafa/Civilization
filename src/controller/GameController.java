package controller;

import model.*;
import view.GameControlPanel;
import view.Ground;
import view.UnitActionPanel;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;

public class GameController {
    private final static int BUILD_COST = 2;
    private final static int FOOD_REQUIREMENT = 1;
    private static final int BASE_PRODUCTION_RATE = 2;

    private static GameController instance;
    private AnimationController animationController;
    private Ground ground;
    private Camera camera;

    final static int ROWS = 100, COLS = 100;

    private ArrayList<Tile> Tiles = new ArrayList<>();
    private ArrayList<Unit> units = new ArrayList<>();
    private ArrayList<Building> buildings = new ArrayList<>();

    private final GlobalResourceManager economy;

    private Unit selectedUnit = null;
    private Tile tileUnderUnit = null;

    private int currentTurn = 1;

    private Tile Townhall;

    private int TownhallX = 10, TownhallY = 10;

    private int unitCapacity = 9;

    private boolean stoneTech = false;
    private boolean ironTech = false;
    private boolean settlementTech = false;
    private boolean proToolsTech = false;

    public GameController(Ground ground) {
        instance = this;
        camera = new Camera();
        this.ground = ground;
        ground.addMouseMotionListener(camera);
        ground.addMouseListener(camera);
        ground.addMouseWheelListener(camera);

        ground.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                handleMouseClick(e);
            }
        });

        Generator();

        updateFog();

        animationController = new AnimationController(this);
        economy = new GlobalResourceManager();

        Timer timer = new Timer(
                8,
                e -> frameGenerator()
        );
        timer.start();
    }

    public void Generator(){
        Random random = new Random();
        TerrainType[] types = TerrainType.values();

        int seedsCount = 150;
        int[][] seeds = new int[seedsCount + types.length + 1][2];
        TerrainType[] seedTypes = new TerrainType[seedsCount + types.length + 1];

        for (int i = 0; i < seedsCount; i++) {
            int c = random.nextInt(COLS);
            int r = random.nextInt(ROWS);
            while(Math.pow(c - TownhallX, 2) + Math.pow(r - TownhallY, 2) < 16){
                c = random.nextInt(COLS);
                r = random.nextInt(ROWS);
            }
            seeds[i][0] = c;
            seeds[i][1] = r;
            seedTypes[i] = types[random.nextInt(types.length)];
        }

        ArrayList<int[]> positions = new ArrayList<>(List.of(
                new int[]{TownhallX + 5, TownhallY},
                new int[]{TownhallX - 5, TownhallY},
                new int[]{TownhallX, TownhallY + 5},
                new int[]{TownhallX, TownhallY - 5},
                new int[]{TownhallX + 4, TownhallY + 4}
        ));

        Collections.shuffle(positions, random);

        for(int i = 0; i < types.length + 1; i++){
            seeds[i + seedsCount] = positions.get(i);
            seedTypes[i + seedsCount] = types[((i + 1) % types.length)];
        }

        ArrayList<Tile> tempTiles = new ArrayList<>();

        for (int col = 0; col < COLS; col++) {
            for (int row = 0; row < ROWS; row++) {
                double minD = Double.MAX_VALUE;
                TerrainType finalType = types[0];

                for (int i = 0; i < seedsCount + types.length + 1; i++) {
                    double dist = Math.pow(seeds[i][0] - col, 2) + Math.pow(seeds[i][1] - row, 2);
                    dist += random.nextDouble() * 8.0;

                    if (dist < minD) {
                        minD = dist;
                        finalType = seedTypes[i];
                    }
                }

                Map<ResourceType, Integer> tileResources = new HashMap<>();

                switch (finalType) {
                    case FOREST:
                        tileResources.put(ResourceType.WOOD, 500);
                        break;

                    case MOUNTAIN:
                        tileResources.put(ResourceType.STONE, 500);

                        if (random.nextDouble() < 0.20)
                            tileResources.put(ResourceType.IRON, 150);
                        break;

                    case PLAIN:
                        if (random.nextDouble() < 0.20)
                            tileResources.put(ResourceType.CATTLE, 300);
                        break;

                    case MEADOW:
                        if (random.nextDouble() < 0.30)
                            tileResources.put(ResourceType.WHEAT, 300);
                        break;
                }
                Tile tile = new Tile(col, row, finalType, tileResources);
                tempTiles.add(tile);
                if(col == TownhallX && row == TownhallY) Townhall = tile;
            }
        }
        this.Tiles = tempTiles;

        Townhall.setBuilding(new Building(BuildingType.TOWN_HALL));
        addUnit(new Unit(UnitType.BUILDER, TownhallX, TownhallY + 1));
        addUnit(new Unit(UnitType.BUILDER, TownhallX + 1, TownhallY));
        addUnit(new Unit(UnitType.WORKER, TownhallX - 1, TownhallY + 1));
        addUnit(new Unit(UnitType.WORKER, TownhallX, TownhallY - 1));
        addUnit(new Unit(UnitType.EXPLORER, TownhallX + 1, TownhallY + 1));
        tileUnderUnit = Townhall;
        expandTerritory();
        tileUnderUnit = null;

    }

    public void processTurnProduction(Tile tile, GlobalResourceManager economy) {
        Building building = tile.getBuilding();
        BuildingType type = building.getType();
        if (type == BuildingType.TOWN_HALL) {
            economy.addResource(ResourceType.WHEAT, 1);
            economy.addResource(ResourceType.WOOD, 1);
            return;
        }

        economy.spendResource(ResourceType.WOOD, type.getWoodCost() / 10);
        economy.spendResource(ResourceType.STONE, type.getStoneCost() / 10);
        economy.spendResource(ResourceType.IRON, type.getIronCost() / 10);

        if (!building.isOccupied() || type == BuildingType.SETTLEMENT) return;

        ResourceType targetResource = type.getOutputResource();

        if (targetResource != null && targetResource != ResourceType.NONE) {
            if (tile.hasResource(targetResource)) {
                economy.addResource(targetResource,
                        tile.extractResource(targetResource,
                                (int)((proToolsTech ? 1.5 : 1) * BASE_PRODUCTION_RATE) *
                                        building.getStationedWorkers().size()));
            }
        }
    }

    public void advanceTurn(){
        currentTurn++;
        for(Tile tile: Tiles){
            Building building = tile.getBuilding();
            if(building != null){
                processTurnProduction(tile, this.economy);
            }
        }

        for(Unit unit: units){
            boolean hasFed = economy.spendFood(FOOD_REQUIREMENT);
            if(!hasFed) System.out.println("Need Food!!");
            unit.resetActionPoints();
        }

        if(Townhall.getBuilding().isProducing()){
            Townhall.getBuilding().decrementProductionTurns();
            if (Townhall.getBuilding().getProductionTurnsLeft() <= 0) {
                Unit newUnit = new Unit(Townhall.getBuilding().getProducingUnit(), Townhall.getCol(), Townhall.getRow());
                addUnit(newUnit);

                Townhall.getBuilding().clearProduction();
            }
        }
    }

    public int getA() {
        return camera.getA();
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

    public static GameController getInstance() {
        return instance;
    }

    private void frameGenerator() {
        animationController.run();
        camera.run();
//        updateFog();
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

    public void deleteUnit(Unit unit){
        units.remove(unit);
    }

    public Unit getSelectedUnit() {
        return selectedUnit;
    }

    public Tile getTileUnderUnit() {
        return tileUnderUnit;
    }

    private Unit getUnitAt(int col, int row) {
        for (Unit u : units) {
            if (u.isAssigned()) continue;
            if (u.getCol() == col && u.getRow() == row) return u;
        }
        return null;
    }

    private Tile getTileAtPixel(int pixelX, int pixelY) {
        int worldX = pixelX + camera.getXOffset();
        int worldY = pixelY + camera.getYOffset();
        int a = getA();

        Tile closestTile = null;
        double minDistance = Double.MAX_VALUE;

        for (Tile tile : Tiles) {
            double x = ((tile.getCol() + 1) * 1.5) * a;
            double y = ((tile.getRow() + 1) * Math.sqrt(3) +
                    (tile.getCol() % 2 == 0 ? Math.sqrt(3)/2 : 0)) * a;

            double distance = Math.pow(worldX - x, 2) + Math.pow(worldY - y, 2);
            if (distance < minDistance) {
                minDistance = distance;
                closestTile = tile;
            }
        }

        if (minDistance <= a * a * 1.5) return closestTile;
        return null;
    }

    private boolean isNeighbor(int col1, int row1, int col2, int row2) {
        if (col1 == col2 && Math.abs(row1 - row2) == 1) return true;
        if (Math.abs(col1 - col2) == 1) {
            if (col1 % 2 == 0) {
                return (row2 == row1 || row2 == row1 + 1);
            } else {
                return (row2 == row1 || row2 == row1 - 1);
            }
        }
        return false;
    }

    private void handleMouseClick(MouseEvent e) {
        Tile clickedTile = getTileAtPixel(e.getX(), e.getY());
        if (clickedTile == null) return;

        if (SwingUtilities.isLeftMouseButton(e)) {
            Unit unitOnTile = getUnitAt(clickedTile.getCol(), clickedTile.getRow());
            if (unitOnTile != null) {
                selectedUnit = unitOnTile;
                tileUnderUnit = clickedTile;
            } else {
                selectedUnit = null;
                tileUnderUnit = clickedTile;
            }
            UnitActionPanel.getInstance().updateActions();
        } else if (SwingUtilities.isRightMouseButton(e)) {
            if (selectedUnit != null) {
                if (isNeighbor(selectedUnit.getCol(), selectedUnit.getRow(), clickedTile.getCol(), clickedTile.getRow())) {
                    if(selectedUnit.move(clickedTile.getCol(), clickedTile.getRow(),
                            clickedTile.getTerrain().getMovementCost())){
                        tileUnderUnit = clickedTile;
                        updateFog();
                        UnitActionPanel.getInstance().updateActions();
                    }
                }
            }
        }
    }

    public GlobalResourceManager getEconomy() {
        return economy;
    }

    public int getCurrentTurn() {
        return currentTurn;
    }

    public boolean constructBuilding(BuildingType bType) {
        if(selectedUnit.getCurrentAP() < bType.getApCost()) return false;
        selectedUnit.setCurrentAP(selectedUnit.getCurrentAP() - BUILD_COST);

        if(!(economy.hasEnough(ResourceType.WOOD, bType.getWoodCost()) &&
            economy.hasEnough(ResourceType.STONE, bType.getStoneCost()) &&
            economy.hasEnough(ResourceType.IRON, bType.getIronCost())))
            return false;

        economy.spendResource(ResourceType.WOOD, bType.getWoodCost());
        economy.spendResource(ResourceType.STONE, bType.getStoneCost());
        economy.spendResource(ResourceType.IRON, bType.getIronCost());

        if(bType == BuildingType.SETTLEMENT) unitCapacity += 3;

        selectedUnit.useCharge();
        if(selectedUnit.getCharge() == 0){
            deleteUnit(selectedUnit);
            selectedUnit = null;
        }

        Building building = new Building(bType);
        buildings.add(building);

        tileUnderUnit.setBuilding(building);
        GameControlPanel.getInstance().updateHUD();
        return true;
    }

    public void assignWorkerToBuilding() {
        tileUnderUnit.getBuilding().addWorker(selectedUnit);
        selectedUnit.setAssigned(true);

        selectedUnit = null;
        tileUnderUnit = null;
        GameControlPanel.getInstance().updateHUD();
    }

    public void removeWorker() {
        Building building = tileUnderUnit.getBuilding();
        Unit worker = building.getLastWorker();
        building.removeWorker(worker);
        worker.setAssigned(false);
    }

    public boolean hasEnoughFood(int foodCost) {
        return(economy.hasEnoughFood(foodCost));
    }
    public boolean hasEnoughWood(int woodCost) {
        return(economy.hasEnough(ResourceType.WOOD, woodCost));
    }
    public boolean hasEnoughStone(int stoneCost) {
        return(economy.hasEnough(ResourceType.STONE, stoneCost));
    }
    public boolean hasEnoughIron(int ironCost) {
        return(economy.hasEnough(ResourceType.IRON, ironCost));
    }

    public void startProducingUnitInTownHall(UnitType uType) {
        if(Townhall.getBuilding().isProducing()){
            System.out.println("Townhall is busy :((");
            return;
        }
        int cost = uType.getFoodCost();
        boolean isPaid = economy.spendFood(cost);
        GameControlPanel.getInstance().updateHUD();

        if(isPaid){
            Townhall.getBuilding().startProducing(uType);
            System.out.println("Producing Started :)))");
        }
    }

    public boolean checkUnitCap(){
        return (units.size() < unitCapacity);
    }


    public void expandTerritory() {
        tileUnderUnit.setOwned();
        for (Tile tile: Tiles) {
            if (isNeighbor(tile.getCol(), tile.getRow(), tileUnderUnit.getCol(), tileUnderUnit.getRow())) {
                tile.setOwned();
            }
        }

        deleteUnit(selectedUnit);
        selectedUnit = null;
    }

    public int getStorageLevel() {
        if(economy.getResourceCapacityAmount(ResourceType.WOOD) == 100) return 0;
        else if(economy.getResourceCapacityAmount(ResourceType.WOOD) == 250) return 1;
        else return 2;
    }

    public void upgradeStorage() {
        if(getStorageLevel() == 0){
            economy.spendResource(ResourceType.WOOD, 100);
            economy.updateStorage(150, 150, 250, 200, 180);
        }else if(getStorageLevel() == 1){
            economy.spendResource(ResourceType.WOOD, 200);
            economy.spendResource(ResourceType.STONE, 100);
            economy.updateStorage(400, 400, 600, 500, 400);
        }
        GameControlPanel.getInstance().updateHUD();
    }

    // "is" ha ro "has" kardam ke tamiz tar beshe yeho nagid ai e :((

    public boolean hasStoneTech() {
        return stoneTech;
    }

    public boolean hasIronTech() {
        return ironTech;
    }

    public boolean hasSettlementTech() {
        return settlementTech;
    }

    public boolean hasProToolsTech() {
        return proToolsTech;
    }

    public void researchStoneTech() {
        economy.spendResource(ResourceType.WOOD, 50);
        stoneTech = true;
        GameControlPanel.getInstance().updateHUD();
    }

    public void researchIronTech() {
        economy.spendResource(ResourceType.STONE, 100);
        stoneTech = true;
        GameControlPanel.getInstance().updateHUD();
    }

    public void researchSettlementTech() {
        economy.spendResource(ResourceType.WOOD, 150);
        stoneTech = true;
        GameControlPanel.getInstance().updateHUD();
    }

    public void researchProToolsTech() {
        economy.spendResource(ResourceType.IRON, 100);
        proToolsTech = true;
        GameControlPanel.getInstance().updateHUD();
    }


    public void updateNetChanges(){
        economy.resetNetChanges();

        economy.addNetChanges(ResourceType.WHEAT, 1);
        economy.addNetChanges(ResourceType.WOOD, 1);

        economy.addNetChanges(ResourceType.WHEAT, -1 * FOOD_REQUIREMENT * units.size());


        int wood = 0, stone = 0, iron = 0;
        for(Building building: buildings){
            wood -= building.getType().getWoodCost() / 10;
            stone -= building.getType().getStoneCost() / 10;
            iron -= building.getType().getIronCost() / 10;

            ResourceType source = building.getType().getOutputResource();
            if(source != null && source != ResourceType.NONE){
                economy.addNetChanges(source, (int)((proToolsTech ? 1.5 : 1) *
                        BASE_PRODUCTION_RATE) * building.getStationedWorkers().size());
            }
        }
        economy.addNetChanges(ResourceType.WOOD, wood);
        economy.addNetChanges(ResourceType.STONE, stone);
        economy.addNetChanges(ResourceType.IRON, iron);

    }

}
