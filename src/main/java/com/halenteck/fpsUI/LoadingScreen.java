package com.halenteck.fpsUI;

import javax.swing.*;
import java.awt.*;

public class LoadingScreen extends JFrame {
    private static final int FRAME_WIDTH = 800;
    private static final int FRAME_HEIGHT = 500;

    public LoadingScreen() {

        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setTitle("Loading...");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.BLACK);

        ImageIcon imageIcon =
                new ImageIcon(this.getClass().getResource("/loadinggif.gif"));

        JLabel imageLabel = new JLabel(imageIcon);
        imageLabel.setIcon(new ImageIcon(imageIcon.getImage().getScaledInstance(300, 400, Image.SCALE_DEFAULT)));
        add(imageLabel, BorderLayout.CENTER);

        JLabel loadingLabel = new JLabel("Across the Sands of Time", SwingConstants.CENTER);
        loadingLabel.setForeground(Color.WHITE);
        loadingLabel.setFont(new Font("Sans Serif", Font.ITALIC, 24));

        add(loadingLabel, BorderLayout.SOUTH);



        setVisible(true);


    }
}
