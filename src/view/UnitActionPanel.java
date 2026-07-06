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

            if(workerCount == 0) return;

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
            setVisible(true);
            System.out.println(4);

            refreshUI();
            return;
        }

        if (selectedUnit.getType() == UnitType.BUILDER) {
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