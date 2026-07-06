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
    }

    public void updateActions() {
        buttonContainer.removeAll();

        Unit selectedUnit = GC.getSelectedUnit();

        setVisible(false);
        if (selectedUnit == null) {
            refreshUI();
            return;
        }

        if (selectedUnit.getType() == UnitType.BUILDER) {

            Tile currentTile = GC.getTileUnderUnit();

            for (BuildingType bType : BuildingType.values()) {
                if (bType == BuildingType.TOWN_HALL || bType == BuildingType.SETTLEMENT) {
                    continue;
                }

                JButton buildBtn = new JButton("Build " + bType.getDisplayName());
                buildBtn.setFocusable(false);
                buildBtn.setFont(new Font("SansSerif", Font.BOLD, 12));

                boolean isValidTerrain = (currentTile.getTerrain() == bType.getRequiredTerrain());
                boolean isTileEmpty = (currentTile.getBuilding() == null);

                buildBtn.setEnabled(isValidTerrain && isTileEmpty);

                buildBtn.addActionListener(e -> {
                    GC.constructBuilding(bType);
                    updateActions();
                });

                buttonContainer.add(buildBtn);
                setVisible(true);
            }
        }else if(selectedUnit.getType() == UnitType.WORKER){
            Building build = GC.getTileUnderUnit().getBuilding();
            if(build == null) return;

            if(!build.needWorker()) return;

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