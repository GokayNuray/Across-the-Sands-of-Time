package com.halenteck.server;

import com.halenteck.render.Entity;
import com.halenteck.render.Models;
import com.halenteck.render.OpenGLComponent;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class ServerTester {

    public static void main(String[] args) {
        Server.connect();

        JFrame frame = new JFrame("FPS Server");
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
                    openGLComponent.rotate(-dx, -dy);
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

        openGLComponent.addKeyListener(new KeyListener() {
            boolean isWPressed = false;
            boolean isSPressed = false;
            boolean isAPressed = false;
            boolean isDPressed = false;
            boolean isSpacePressed = false;
            boolean isShiftPressed = false;

            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_W) {
                    isWPressed = true;
                } else if (e.getKeyCode() == KeyEvent.VK_A) {
                    isAPressed = true;
                } else if (e.getKeyCode() == KeyEvent.VK_S) {
                    isSPressed = true;
                } else if (e.getKeyCode() == KeyEvent.VK_D) {
                    isDPressed = true;
                } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    isSpacePressed = true;
                } else if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
                    isShiftPressed = true;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_W) {
                    isWPressed = false;
                } else if (e.getKeyCode() == KeyEvent.VK_A) {
                    isAPressed = false;
                } else if (e.getKeyCode() == KeyEvent.VK_S) {
                    isSPressed = false;
                } else if (e.getKeyCode() == KeyEvent.VK_D) {
                    isDPressed = false;
                } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    isSpacePressed = false;
                } else if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
                    isShiftPressed = false;
                }
            }

            {
                Thread movementThread = new Thread(() -> {
                    while (true) {
                        if (isWPressed) {
                            openGLComponent.moveForward(0.03f);
                        }
                        if (isSPressed) {
                            openGLComponent.moveBackward(0.03f);
                        }
                        if (isAPressed) {
                            openGLComponent.moveLeft(0.03f);
                        }
                        if (isDPressed) {
                            openGLComponent.moveRight(0.03f);
                        }
                        if (isSpacePressed) {
                            openGLComponent.moveUp(0.03f);
                        }
                        if (isShiftPressed) {
                            openGLComponent.moveDown(0.03f);
                        }
                        if (isWPressed || isSPressed || isAPressed || isDPressed || isSpacePressed || isShiftPressed) {
                            Server.movePlayer(openGLComponent.getCameraPosition().x, openGLComponent.getCameraPosition().y - 1, openGLComponent.getCameraPosition().z);
                        }
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });
                movementThread.start();
            }
        });


        layeredPane.add(openGLComponent, JLayeredPane.DEFAULT_LAYER);

        JButton button = new JButton("Click me");
        button.setFocusable(false);
        button.setBounds(10, 10, 100, 50);
        layeredPane.add(button, JLayeredPane.PALETTE_LAYER);

        JLabel fpsText = new JLabel("FPS: 0");
        fpsText.setBounds(120, 10, 100, 20);
        openGLComponent.setFpsText(fpsText);
        layeredPane.add(fpsText, JLayeredPane.PALETTE_LAYER);

        frame.add(layeredPane);
        frame.setVisible(true);

        openGLComponent.startRender();

        button.addActionListener(e -> {
            String name = JOptionPane.showInputDialog("Enter player name:");
            Server.joinLobby(name);
        });

        Entity test2 = new Entity(Models.SQUARE_PRISM, 0, 0, 0, 0, 0, 1);
        openGLComponent.addEntity(test2);

        frame.setVisible(true);
    }
}
