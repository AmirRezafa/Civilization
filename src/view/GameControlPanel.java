package view;

import controller.GameController;
import model.ResourceType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameControlPanel extends JPanel {
    private final GameController GC;

    private JLabel turnLabel;
    private JLabel foodLabel;
    private JLabel woodLabel;
    private JLabel stoneLabel;
    private JLabel ironLabel;
    private JButton nextTurnButton;


    public GameControlPanel() {
        this.GC = GameController.getInstance();

        this.setLayout(new FlowLayout(FlowLayout.CENTER, 25, 12));
        this.setBackground(new Color(45, 45, 45));

        initializeComponents();
        updateHUD();
    }

    private JLabel createLabel(Font hudFont, Color textColor){
        JLabel label = new JLabel();
        label.setFont(hudFont);
        label.setForeground(textColor);
        this.add(label);
        return label;
    }

    private void initializeComponents() {
        Font hudFont = new Font("SansSerif", Font.BOLD, 14);
        Color textColor = Color.WHITE;

        turnLabel = new JLabel("Turn: 1");
        turnLabel.setFont(hudFont);
        turnLabel.setForeground(new Color(241, 196, 15));
        this.add(turnLabel);

        foodLabel = createLabel(hudFont, textColor);
        woodLabel = createLabel(hudFont, textColor);
        stoneLabel = createLabel(hudFont, textColor);
        ironLabel = createLabel(hudFont, textColor);

        nextTurnButton = new JButton("Next Turn");
        nextTurnButton.setFont(hudFont);
        nextTurnButton.setFocusable(false);
        nextTurnButton.setBackground(new Color(39, 174, 96));
        nextTurnButton.setForeground(Color.WHITE);

        nextTurnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleNextTurnAction();
            }
        });

        this.add(nextTurnButton);
    }

    private void handleNextTurnAction() {
        GC.advanceTurn();

        turnLabel.setText("Turn: " + GC.getCurrentTurn());

        updateHUD();

        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        if (topFrame != null) {
            topFrame.repaint();
        }
    }

    public void updateHUD() {
        var economy = GC.getEconomy();

        foodLabel.setText("Food: " + (economy.getResourceAmount(ResourceType.CATTLE) +
                economy.getResourceAmount(ResourceType.WHEAT)));
        woodLabel.setText("Wood: " + economy.getResourceAmount(ResourceType.WOOD));
        stoneLabel.setText("Stone: " + economy.getResourceAmount(ResourceType.STONE));
        ironLabel.setText("Iron: " + economy.getResourceAmount(ResourceType.IRON));
    }
}