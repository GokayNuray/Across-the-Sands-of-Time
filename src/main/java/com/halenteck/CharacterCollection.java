package com.halenteck;

import javax.swing.*;
import java.awt.*;

public class CharacterCollection extends JFrame{

    private static final int FRAME_WIDTH = 800;
    private static final int FRAME_HEIGHT = 500;
    public CharacterCollection() {

        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setTitle("User Card");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        setVisible(true);
    }

}
