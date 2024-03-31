package com.halenteck.render;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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

        float[] triangleCoords = {
                0.0f, 0.622008459f, -2.0f,
                -0.5f, -0.311004243f, -2.0f,
                0.5f, -0.311004243f, -2.0f
        };

        float[] color = {
                1.0f, 0.0f, 0.0f, 1.0f,
                0.0f, 1.0f, 0.0f, 1.0f,
                0.0f, 0.0f, 1.0f, 1.0f
        };

        int[] indices0 = {
                0, 1, 2
        };

        Renderable renderable = new Renderable(triangleCoords, color, indices0);
        openGLComponent.addRenderable(renderable);

        float[] squareCoords = {
                -1, -0.5f, -1,
                1, -0.5f, -1,
                1, -0.5f, 1,
                -1, -0.5f, 1
        };

        float[] squareColor = {
                1, 1, 1, 1,
                1, 1, 1, 1,
                1, 1, 1, 1,
                1, 1, 1, 1
        };

        float[] textureCoords = {
                0, 0,
                0, 1,
                1, 1,
                1, 0
        };

        int[] indices = {
                0, 1, 2,
                0, 2, 3
        };

        Renderable renderable2 = new Renderable(squareCoords, squareColor, textureCoords, indices, "/test/clan.jpeg");
        openGLComponent.addRenderable(renderable2);

        float[] squareCoords2 = {
                -1, -1, 1,
                1, -1, 1,
                1, 1, 1,
                -1, 1, 1
        };

        float[] squareColor2 = {
                1, 1, 1, 1,
                1, 1, 1, 1,
                1, 1, 1, 1,
                1, 1, 1, 1
        };

        float[] textureCoords2 = {
                0, 0,
                0, 1,
                1, 1,
                1, 0
        };

        int[] indices2 = {
                0, 1, 2,
                0, 2, 3
        };
        Renderable renderable3 = new Renderable(squareCoords2, squareColor2, textureCoords2, indices2, "/test/adsiz.png");
        openGLComponent.addRenderable(renderable3);

        //ModelLoader.loadModel("src/main/resources/test/elgato/12221_Cat_v1_l3.obj").forEach(openGLComponent::addRenderable);
        ModelLoader.loadModel("src/main/resources/test/test2/test2.obj").forEach(openGLComponent::addRenderable);
    }
}
