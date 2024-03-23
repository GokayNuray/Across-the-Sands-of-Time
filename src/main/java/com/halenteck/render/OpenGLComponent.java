package com.halenteck.render;

import org.lwjgl.opengl.awt.AWTGLCanvas;
import org.lwjgl.opengl.awt.GLData;

import static org.lwjgl.opengl.GL.createCapabilities;
import static org.lwjgl.opengl.GL.setCapabilities;
import static org.lwjgl.opengl.GL20.*;

public class OpenGLComponent extends AWTGLCanvas {
    double turnSpeed = 0.001;

    public OpenGLComponent() {
        super(new GLData());
    }

    @Override
    public void initGL() {
        createCapabilities();
        glClearColor(0.3f, 0.4f, 0.5f, 1.0f);
    }

    @Override
    public void paintGL() {
        int w = getWidth();
        int h = getHeight();
        float aspect = (float) w / h;
        double now = System.currentTimeMillis() * turnSpeed;
        float width = (float) Math.abs(Math.sin(now));
        glClear(GL_COLOR_BUFFER_BIT);
        glViewport(0, 0, w, h);
        glBegin(GL_QUADS);
        glColor3f(0.4f, 0.6f, 0.8f);
        glVertex2f(-0.75f * width / aspect, 0.0f);
        glVertex2f(0, -0.75f);
        glVertex2f(0.75f * width / aspect, 0);
        glVertex2f(0, +0.75f);
        glEnd();
        swapBuffers();
    }


    public void startRender() {
        while (true) {
            if (!isValid()) {
                setCapabilities(null);
                return;
            }
            render();
        }
    }

    public void increaseTurnSpeed() {
        System.out.println("Increasing turn speed");
        turnSpeed += 0.0004;
    }


}
