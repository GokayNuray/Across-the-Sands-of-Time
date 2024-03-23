package com.halenteck.render;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class RenderTester {

    public static void main(String[] args) {
        JFrame frame = new JFrame("OpenGLComponent");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(640, 480);

        JLayeredPane layeredPane = new JLayeredPane();

        OpenGLComponent openGLComponent = new OpenGLComponent();
        openGLComponent.setBounds(0, 0, 640, 480);
        openGLComponent.addMouseMotionListener(new MouseMotionListener() {
            private int lastX = -1;
            private int lastY = -1;

            @Override
            public void mouseDragged(MouseEvent e) {
                int currentX = e.getX();
                int currentY = e.getY();

                if (lastX != -1 && lastY != -1) {
                    int dx = currentX - lastX;
                    int dy = currentY - lastY;

                    // Use dx and dy as the amount of movement
                    // For example, you can rotate the camera based on the mouse movement
                    openGLComponent.rotate(dx, dy);
                }

                lastX = currentX;
                lastY = currentY;
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                lastX = -1;
                lastY = -1;
            }
        });
        openGLComponent.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_W) {
                    openGLComponent.move(0, 0, -0.2f);
                }
                if (e.getKeyCode() == KeyEvent.VK_S) {
                    openGLComponent.move(0, 0, 0.2f);
                }
                if (e.getKeyCode() == KeyEvent.VK_A) {
                    openGLComponent.move(-0.2f, 0, 0);
                }
                if (e.getKeyCode() == KeyEvent.VK_D) {
                    openGLComponent.move(0.2f, 0, 0);
                }
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    openGLComponent.move(0, 0.2f, 0);
                }
                if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
                    openGLComponent.move(0, -0.2f, 0);
                }
            }
        });
        layeredPane.add(openGLComponent, JLayeredPane.DEFAULT_LAYER);

        JButton button = new JButton("Click me");
        button.setBounds(10, 10, 100, 50);
        layeredPane.add(button, JLayeredPane.POPUP_LAYER);

        frame.add(layeredPane);
        frame.setVisible(true);

        openGLComponent.startRender();
    }
}
