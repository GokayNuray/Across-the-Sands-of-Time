package com.halenteck.render;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class RenderTester {

    public static void main(String[] args) {
        Models.loadModels();
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
                    openGLComponent.rotateCamera(-dx, -dy);
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
            boolean isCtrlPressed = false;

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
                } else if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
                    isCtrlPressed = true;
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
                } else if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
                    isCtrlPressed = false;
                }
            }

            {
                Thread movementThread = new Thread(() -> {
                    while (true) {
                        float moveDistance = 0.3f;
                        if (isCtrlPressed) {
                            moveDistance *= 0.1f;
                        }
                        if (isWPressed) {
                            openGLComponent.moveCameraForward(moveDistance);
                        }
                        if (isSPressed) {
                            openGLComponent.moveCameraBackward(moveDistance);
                        }
                        if (isAPressed) {
                            openGLComponent.moveCameraLeft(moveDistance);
                        }
                        if (isDPressed) {
                            openGLComponent.moveCameraRight(moveDistance);
                        }
                        if (isSpacePressed) {
                            openGLComponent.moveCameraUp(moveDistance);
                        }
                        if (isShiftPressed) {
                            openGLComponent.moveCameraDown(moveDistance);
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
        openGLComponent.setFpsCounter(fpsText);
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

        Entity entity = new Entity(Models.TEST2, 0, 0, 2, 180, 0, 0.05f);
        openGLComponent.addEntity(entity);
        new Thread(() -> {
            while (true) {
                entity.rotate(0.5f, 0);
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        Renderable cube = ModelLoader.createRectangularPrism(new float[]{-4, -1, -1}, new float[]{-2, 1, 1});
        openGLComponent.addRenderable(cube);

        Entity worldMap = new Entity(Models.WORLD_MAP1, 0, 0.5f, 0, 0, 0, 1);
        worldMap.translate(0, 0, 0);
        openGLComponent.addEntity(worldMap);

        Entity animated = new Entity(Models.CHARACTER1, 0, 1, -10, 0, 0, 1f);
        openGLComponent.addEntity(animated);

        Entity animated2 = new Entity(Models.CHARACTER1, 0, 1, 10, 0, 0, 1f);
        animated2.addChild(Models.WEAPON2, -0.35f, 1.6f, 0.9f);
        openGLComponent.addEntity(animated2);

        new Thread(() -> {
            while (true) {
                animated.rotate(0.5f, 0);
                //animated.translate((float) 0, 0.1f, 0);
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(() -> {
            while (true) {
                animated2.translate((float) 0, 0.00f, 0);
                animated2.rotate(0.5f, 0);
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
