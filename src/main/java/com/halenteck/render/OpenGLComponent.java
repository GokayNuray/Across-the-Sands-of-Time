package com.halenteck.render;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.awt.AWTGLCanvas;
import org.lwjgl.opengl.awt.GLData;

import javax.swing.*;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL.createCapabilities;
import static org.lwjgl.opengl.GL.setCapabilities;
import static org.lwjgl.opengl.GL30.*;

public class OpenGLComponent extends AWTGLCanvas {
    private int width;
    private int height;
    private float aspect;

    private int fps = 0;

    public Vector3f getCameraPosition() {
        return cameraPosition;
    }

    private Vector3f cameraPosition = new Vector3f(0, 0, 0);
    private Vector3f directionVector = new Vector3f(0, 0, -1);

    private float yaw = -180;
    private float pitch = 0;

    private int programHandle;
    private int vPMatrixHandle;
    private int positionHandle;
    private int colorHandle;
    private int textureCoordinateHandle;

    private final Matrix4f projectionMatrix = new Matrix4f();

    private final List<Renderable> renderables = new ArrayList<>();

    public OpenGLComponent() {
        super(new GLData());
    }

    @Override
    public void initGL() {
        createCapabilities();
        glClearColor(0.3f, 0.4f, 0.5f, 1.0f);
        width = getWidth();
        height = getHeight();
        aspect = (float) width / height;
        glViewport(0, 0, width, height);

        glEnable(GL_DEPTH_TEST);
        glDepthFunc(GL_LEQUAL);

        String vertexShaderCode = "#version 130\n" +
                "uniform mat4 u_MVPMatrix;" +
                "in vec4 a_Position;" +
                "in vec4 a_Color;" +
                "in vec2 a_TexCoordinate;" +
                "out vec4 v_Color;" +
                "out vec2 v_TexCoordinate;" +
                "void main()" +
                "{" +
                "v_Color = a_Color;" +
                "v_TexCoordinate = a_TexCoordinate;" +
                "gl_Position = u_MVPMatrix * a_Position;" +
                "}";
        int vertexShader = OpenGLUtils.loadShader(GL_VERTEX_SHADER, vertexShaderCode);
        String fragmentShaderCode = "#version 130\n" +
                "precision mediump float;" +
                "uniform sampler2D u_Texture;" +
                "in vec4 v_Color;" +
                "in vec2 v_TexCoordinate;" +
                "void main() {" +
                "vec4 val = v_Color * texture(u_Texture, v_TexCoordinate);" +
                "if(val.a < 0.25){ discard; }" +
                "gl_FragColor = val;" +
                "}";
        int fragShader = OpenGLUtils.loadShader(GL_FRAGMENT_SHADER, fragmentShaderCode);
        programHandle = OpenGLUtils.createAndLinkProgram(vertexShader, fragShader);

        glUseProgram(programHandle);

        vPMatrixHandle = glGetUniformLocation(programHandle, "u_MVPMatrix");
        positionHandle = glGetAttribLocation(programHandle, "a_Position");
        colorHandle = glGetAttribLocation(programHandle, "a_Color");
        textureCoordinateHandle = glGetAttribLocation(programHandle, "a_TexCoordinate");

        projectionMatrix.setPerspective((float) Math.toRadians(45), aspect, 0.1f, 10.0f);


    }

    @Override
    public void paintGL() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        Matrix4f viewMatrix = new Matrix4f().setLookAt(cameraPosition,
                new Vector3f(directionVector).add(cameraPosition), new Vector3f(0, 1, 0));
        Matrix4f viewProjectionMatrix = new Matrix4f(projectionMatrix).mul(viewMatrix);
        FloatBuffer vp = BufferUtils.createFloatBuffer(16);
        viewProjectionMatrix.get(vp);
        glUniformMatrix4fv(vPMatrixHandle, false, vp);

        synchronized ("renderables") {
            for (Renderable renderable : renderables) {
                if (!renderable.isBuilt()) renderable.build();
                if (renderable.isUpdated()) renderable.update();
                renderRenderable(renderable);
            }
        }

        swapBuffers();

        fps++;
    }

    private void renderRenderable(Renderable renderable) {
        FloatBuffer[] buffers = renderable.getBuffers();
        IntBuffer indicesBuffer = renderable.getIndicesBuffer();
        int textureHandle = renderable.getTextureHandle();

        glEnableVertexAttribArray(positionHandle);
        glVertexAttribPointer(positionHandle, 3, GL_FLOAT, false, 0, buffers[0]);

        glEnableVertexAttribArray(colorHandle);
        glVertexAttribPointer(colorHandle, 4, GL_FLOAT, false, 0, buffers[1]);


        glBindTexture(GL_TEXTURE_2D, textureHandle);
        glEnableVertexAttribArray(textureCoordinateHandle);
        glVertexAttribPointer(textureCoordinateHandle, 2, GL_FLOAT, false, 0, buffers[2]);

        glDrawElements(GL_TRIANGLES, indicesBuffer);

    }

    public void addRenderable(Renderable renderable) {
        synchronized ("renderables") {
            renderables.add(renderable);
        }
    }

    public void addEntity(Entity entity) {
        synchronized ("renderables") {
            entity.getRenderables(renderables);
        }
    }

    public void startRender() {
        new Thread(() -> {
            while (true) {
                if (!isValid()) {
                    setCapabilities(null);
                    return;
                }
                render();
            }
        }).start();
    }

    public void moveForward(float distance) {
        cameraPosition.add(new Vector3f(directionVector).mul(distance));
    }

    public void moveBackward(float distance) {
        cameraPosition.sub(new Vector3f(directionVector).mul(distance));
    }

    public void moveRight(float distance) {
        Vector3f right = new Vector3f(directionVector).cross(new Vector3f(0, 1, 0));
        cameraPosition.add(right.mul(distance));
    }

    public void moveLeft(float distance) {
        Vector3f right = new Vector3f(directionVector).cross(new Vector3f(0, 1, 0));
        cameraPosition.sub(right.mul(distance));
    }

    public void moveUp(float distance) {
        cameraPosition.add(new Vector3f(0, 1, 0).mul(distance));
    }

    public void moveDown(float distance) {
        cameraPosition.sub(new Vector3f(0, 1, 0).mul(distance));
    }


    public void rotate(float dYaw, float dPitch) {
        yaw += dYaw;
        pitch += dPitch;
        if (pitch > 90) pitch = 90;
        if (pitch < -90) pitch = -90;
        float directionX = (float) (Math.sin(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch)));
        float directionY = (float) Math.sin(Math.toRadians(pitch));
        float directionZ = (float) (Math.cos(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch)));
        directionVector = new Vector3f(directionX, directionY, directionZ);
    }

    public void setFpsText(JLabel fpsText) {
        new Timer(1000, e -> {
            fpsText.setText("FPS: " + fps);
            fps = 0;
        }).start();
    }

}
