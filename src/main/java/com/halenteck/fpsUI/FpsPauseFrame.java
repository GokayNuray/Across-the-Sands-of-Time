package com.halenteck.fpsUI;

import com.halenteck.commonUI.GameSelectionMenu;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class FpsPauseFrame extends JFrame {

    public JFrame fpsInGame;

        public FpsPauseFrame(JFrame fpsInGame) {
            this.fpsInGame = fpsInGame;
            setTitle("Game Paused");
            setLayout(new BorderLayout());
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setSize(600, 400);

            JLabel pauseLabel = new JLabel("Game Paused", SwingConstants.CENTER);
            pauseLabel.setFont(pauseLabel.getFont().deriveFont(40.0f));
            add(pauseLabel, BorderLayout.NORTH);

            JPanel pausePanel = new JPanel(new GridLayout(2, 1));
            JLabel pauseInfo = new JLabel("Are you sure you want to leave the game?", SwingConstants.CENTER);
            pauseInfo.setFont(pauseInfo.getFont().deriveFont(20.0f));
            pausePanel.add(pauseInfo);
            JLabel pauseInfo2 = new JLabel("Your progress will be lost.", SwingConstants.CENTER);
            pauseInfo2.setFont(pauseInfo2.getFont().deriveFont(15.0f));
            pausePanel.add(pauseInfo2);
            add(pausePanel, BorderLayout.CENTER);

            JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
            JButton resumeButton = new JButton("Resume");
            resumeButton.setFont(resumeButton.getFont().deriveFont(20.0f));
            resumeButton.addActionListener(e -> {
                dispose();
            });
            buttonPanel.add(resumeButton);

            JButton exitButton = new JButton("Exit");
            exitButton.setFont(exitButton.getFont().deriveFont(20.0f));
            exitButton.addActionListener(e -> {
                new GameSelectionMenu();
                fpsInGame.dispose();
                dispose();
            });
            buttonPanel.add(exitButton);

            add(buttonPanel, BorderLayout.SOUTH);

            }
}
