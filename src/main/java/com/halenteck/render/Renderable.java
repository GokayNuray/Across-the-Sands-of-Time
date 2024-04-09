package com.halenteck.render;

import org.joml.Matrix3f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class Renderable {

    private boolean built = false;
    private boolean updated = false;

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

        colors = null;
        texCoords = null;
        indices = null;
        texturePath = null;

        built = true;
    }

    //Recreate the vertex buffer with the new vertices
    void update() {
        FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(vertices.length);
        vertexBuffer.put(vertices).flip();

        buffers[0] = vertexBuffer;

        updated = false;
    }

    void translate(float x, float y, float z) {
        for (int i = 0; i < vertices.length; i += 3) {
            vertices[i] += x;
            vertices[i + 1] += y;
            vertices[i + 2] += z;
        }

        updated = true;
    }

    void rotate(float angle, float x, float y, float z) {
        for (int i = 0; i < vertices.length; i += 3) {
            Vector3f vertex = new Vector3f(vertices[i], vertices[i + 1], vertices[i + 2]);
            Matrix3f rotationMatrix = new Matrix3f().rotate(angle, x, y, z);
            rotationMatrix.transform(vertex);
            vertices[i] = vertex.x;
            vertices[i + 1] = vertex.y;
            vertices[i + 2] = vertex.z;
        }

        updated = true;
    }

    void scale(float x, float y, float z) {
        for (int i = 0; i < vertices.length; i += 3) {
            vertices[i] *= x;
            vertices[i + 1] *= y;
            vertices[i + 2] *= z;
        }

        updated = true;
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

    public boolean isUpdated() {
        return updated;
    }
}
