package view;

import controller.GameController;
import model.*;

import javax.swing.*;
import java.awt.*;

public class UnitActionPanel extends JPanel {
    private final GameController GC;
    private final JPanel buttonContainer;
    private static UnitActionPanel instance;

    public UnitActionPanel() {
        this.GC = GameController.getInstance();
        setVisible(false);

        this.setLayout(new BorderLayout());
        this.setBackground(new Color(40, 40, 40));
        this.setPreferredSize(new Dimension(0, 80));

        buttonContainer = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 25));
        buttonContainer.setOpaque(false);
        this.add(buttonContainer, BorderLayout.CENTER);
        instance = this;
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.9f));

        g2.setColor(new Color(40, 40, 40));
        g2.fillRect(0, 0, getWidth(), getHeight());

        g2.dispose();

        super.paintComponent(g);
    }

    public void showProducingMSG(Building building){
        String msg = "Producing: " + building.getProducingUnit().getDisplayName() +
                " (" + building.getProductionTurnsLeft() + " Turns Left)";
        JLabel producingLabel = new JLabel(msg);
        producingLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        producingLabel.setForeground(new Color(241, 196, 15));

        buttonContainer.add(producingLabel);
    }

    public void showProduceButtons(){
        for (UnitType uType : UnitType.values()) {
            String btnText = "Train " + uType.getDisplayName() +
                    " (" + uType.getFoodCost() + " Food, " + uType.getBuildTurns() + " Turns)";

            JButton trainBtn = new JButton(btnText);
            trainBtn.setFocusable(false);
            trainBtn.setFont(new Font("SansSerif", Font.BOLD, 12));

            boolean canAfford = GC.hasEnoughFood(uType.getFoodCost()) &&
                    GC.checkUnitCap();
            trainBtn.setEnabled(canAfford);

            trainBtn.addActionListener(e -> {
                GC.startProducingUnitInTownHall(uType);
                updateActions();
            });

            buttonContainer.add(trainBtn);
        }
    }

    private void showUnassignButton(int workerCount){
        JButton unassignBtn = new JButton("Unassign Worker (" + workerCount + ")");
        unassignBtn.setFocusable(false);
        unassignBtn.setFont(new Font("SansSerif", Font.BOLD, 12));
        unassignBtn.setBackground(new Color(231, 76, 60));
        unassignBtn.setForeground(Color.WHITE);

        unassignBtn.addActionListener(e -> {
            GC.removeWorker();
            updateActions();
        });

        buttonContainer.add(unassignBtn);
    }

    private void showBuildButtons(Tile currentTile){
        for (BuildingType bType : BuildingType.values()) {
            if (bType == BuildingType.TOWN_HALL) {
                continue;
            }

            JButton buildBtn = new JButton("Build " + bType.getDisplayName());
            buildBtn.setFocusable(false);
            buildBtn.setFont(new Font("SansSerif", Font.BOLD, 12));

            boolean isValidTerrain = (currentTile.getTerrain() == bType.getRequiredTerrain());
            boolean isTileEmpty = (currentTile.getBuilding() == null);
            boolean inTerritory = currentTile.isOwned();

            if(bType == BuildingType.SETTLEMENT) isValidTerrain = true;

            buildBtn.setEnabled(isValidTerrain && isTileEmpty && inTerritory);

            buildBtn.addActionListener(e -> {
                GC.constructBuilding(bType);
                updateActions();
            });

            buttonContainer.add(buildBtn);
        }
    }

    private void showWorkHereButton(){
        JButton workBtn = new JButton("Work Here");
        workBtn.setFocusable(false);
        workBtn.setFont(new Font("SansSerif", Font.BOLD, 12));
        workBtn.setBackground(new Color(46, 204, 113));
        workBtn.setForeground(Color.WHITE);

        workBtn.addActionListener(e -> {
            GC.assignWorkerToBuilding();
            updateActions();
        });

        buttonContainer.add(workBtn);
    }

    private void showExpandBorderHereButton(){
        JButton expandBtn = new JButton("Expand Borders Here");
        expandBtn.setFocusable(false);
        expandBtn.setBackground(new Color(155, 89, 182));
        expandBtn.setForeground(Color.WHITE);
        expandBtn.setFont(new Font("SansSerif", Font.BOLD, 12));

        expandBtn.addActionListener(e -> {
            GC.expandTerritory();
            updateActions();
        });

        buttonContainer.add(expandBtn);
    }

    public void updateActions() {
        buttonContainer.removeAll();

        Unit selectedUnit = GC.getSelectedUnit();
        Tile currentTile = GC.getTileUnderUnit();

        setVisible(false);
        if (selectedUnit == null) {
            if(currentTile == null) return;
            Building building = currentTile.getBuilding();
            if(building == null) return;
            int workerCount = building.getStationedWorkers().size();

            if(workerCount == 0 && building.getType() == BuildingType.TOWN_HALL){
                if (building.isProducing()) {
                    showProducingMSG(building);
                } else {
                    showProduceButtons();
                }
                setVisible(true);
            return;
            }

            if(workerCount != 0){
                showUnassignButton(workerCount);
                setVisible(true);
            }
            refreshUI();
            return;
        }

        if (selectedUnit.getType() == UnitType.BUILDER) {
            showBuildButtons(currentTile);
            setVisible(true);
        }else if(selectedUnit.getType() == UnitType.WORKER){
            Building build = GC.getTileUnderUnit().getBuilding();
            if(build == null) return;

            if(!build.needWorker()) return;

            showWorkHereButton();

            setVisible(true);
        } else if (selectedUnit.getType() == UnitType.BORDER_EXPANDER) {
            showExpandBorderHereButton();
            setVisible(true);
        }

        refreshUI();
    }

    private void refreshUI() {
        buttonContainer.revalidate();
        buttonContainer.repaint();
    }

    public static UnitActionPanel getInstance() {
        return instance;
    }
}