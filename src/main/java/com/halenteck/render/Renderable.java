package com.halenteck.render;

import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class Renderable {

    private boolean built = false;

    private float[] vertices;
    private float[] colors;
    private float[] texCoords;
    private int[] indices;
    private String texturePath;
    private FloatBuffer[] buffers;
    private IntBuffer indicesBuffer;
    private int textureHandle;

    public Renderable(float[] vertices, float[] colors, int[] indices) {
        this(vertices, colors, new float[colors.length / 2], indices, "/whiteSquare.png");
    }

    public Renderable(float[] vertices, float[] colors, float[] texCoords, int[] indices, String texturePath) {
        this.vertices = vertices;
        this.colors = colors;
        this.texCoords = texCoords;
        this.indices = indices;
        this.texturePath = texturePath;
    }

    //We can't build the buffers in the constructor because the OpenGL context is not available yet
    void build() {
        FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(vertices.length);
        vertexBuffer.put(vertices).flip();

        FloatBuffer colorBuffer = BufferUtils.createFloatBuffer(colors.length);
        colorBuffer.put(colors).flip();

        FloatBuffer texCoordBuffer = BufferUtils.createFloatBuffer(texCoords.length);
        texCoordBuffer.put(texCoords).flip();

        buffers = new FloatBuffer[]{vertexBuffer, colorBuffer, texCoordBuffer};

        indicesBuffer = BufferUtils.createIntBuffer(indices.length);
        indicesBuffer.put(indices).flip();

        textureHandle = OpenGLUtils.loadTexture(texturePath);

        vertices = null;
        colors = null;
        texCoords = null;
        indices = null;
        texturePath = null;

        built = true;
    }

    public FloatBuffer[] getBuffers() {
        return buffers;
    }

    public IntBuffer getIndicesBuffer() {
        return indicesBuffer;
    }

    public int getTextureHandle() {
        return textureHandle;
    }

    public boolean isBuilt() {
        return built;
    }
}
