package com.halenteck.render;

import javax.swing.*;

public class RenderTester {

    public static void main(String[] args) {
        JFrame frame = new JFrame("OpenGLComponent");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(640, 480);

        JLayeredPane layeredPane = new JLayeredPane();

        OpenGLComponent openGLComponent = new OpenGLComponent();
        openGLComponent.setBounds(0, 0, 640, 480);
        layeredPane.add(openGLComponent, JLayeredPane.DEFAULT_LAYER);

        JButton button = new JButton("Click me");
        button.addActionListener(e -> openGLComponent.goForward());
        button.setBounds(10, 10, 100, 50);
        layeredPane.add(button, JLayeredPane.POPUP_LAYER);

        frame.add(layeredPane);
        frame.setVisible(true);

        openGLComponent.startRender();
    }
}
