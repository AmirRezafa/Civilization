package view;

import controller.GameController;
import model.BuildingType;
import model.Tile;
import model.Unit;
// import model.UnitType; // If you have a UnitType enum

import javax.swing.*;
import java.awt.*;

public class UnitActionPanel extends JPanel {
    private final GameController GC;
    private final JPanel buttonContainer;
    private static UnitActionPanel instance;

    public UnitActionPanel() {
        this.GC = GameController.getInstance();

        this.setLayout(new BorderLayout());
        this.setBackground(new Color(40, 40, 40));
        this.setPreferredSize(new Dimension(0, 80));

        JLabel title = new JLabel("  Unit Actions: ");
        title.setForeground(new Color(241, 196, 15)); // Golden text
        title.setFont(new Font("SansSerif", Font.BOLD, 15));
        this.add(title, BorderLayout.WEST);

        buttonContainer = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 25));
        buttonContainer.setOpaque(false);
        this.add(buttonContainer, BorderLayout.CENTER);
        instance = this;
    }

    public void updateActions() {
        buttonContainer.removeAll();

        Unit selectedUnit = GC.getSelectedUnit();

        if (selectedUnit == null) {
            refreshUI();
            return;
        }

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
                boolean success = GC.constructBuilding(selectedUnit, bType);

                if (success) {
                    System.out.println("Successfully constructed: " + bType.getDisplayName());

                    updateActions();
                }
            });

            buttonContainer.add(buildBtn);
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