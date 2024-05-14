package com.halenteck.render;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class Renderable implements Cloneable {

    private String name;

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

    private Vector3f position = new Vector3f();
    private float yaw = 0;
    private float pitch = 0;
    private Vector3f scale = new Vector3f(1, 1, 1);
    private Matrix4f nodeTransformation;
    private Matrix4f animationTransformation;

    public Renderable(float[] vertices, float[] colors, int[] indices) {
        this(vertices, colors, new float[colors.length / 2], indices, "/whiteSquare.png");
    }

    public Renderable(float[] vertices, float[] colors, float[] texCoords, int[] indices, String texturePath) {
        this("default", vertices, colors, texCoords, indices, texturePath);
    }

    public Renderable(String name, float[] vertices, float[] colors, float[] texCoords, int[] indices, String texturePath) {
        this.name = name;
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

        if (animationTransformation != null) {
            vertexBuffer = BufferUtils.createFloatBuffer(vertices.length);
            for (int i = 0; i < vertices.length; i += 3) {
                Vector3f vertex = new Vector3f(vertices[i], vertices[i + 1], vertices[i + 2]);
                Matrix4f nodeTransformation = new Matrix4f(this.nodeTransformation);
                nodeTransformation.invert().transformPosition(vertex);
                animationTransformation.transformPosition(vertex);
                //nodeTransformation.invert().transformPosition(vertex);
                vertexBuffer.put(vertex.x).put(vertex.y).put(vertex.z);
            }
            vertexBuffer.flip();
        }

        FloatBuffer vertexBuffer2 = BufferUtils.createFloatBuffer(vertices.length);
        for (int i = 0; i < vertices.length; i += 3) {
            Vector3f vertex = new Vector3f(vertexBuffer.get(), vertexBuffer.get(), vertexBuffer.get());
            Matrix4f transformation = new Matrix4f().scale(scale).rotate(yaw, 0, 1, 0).rotate(pitch, 1, 0, 0);
            transformation.transformPosition(vertex);
            vertex.add(position);
            vertexBuffer2.put(vertex.x).put(vertex.y).put(vertex.z);
        }
        vertexBuffer2.flip();

        buffers[0] = vertexBuffer2;

        updated = false;
    }

    void translate(float x, float y, float z) {
        position.add(x, y, z);

        updated = true;
    }

    void increaseYaw(float yaw) {
        this.yaw += yaw;
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

    public String getName() {
        return name;
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

    @Override
    public Renderable clone() {
        try {
            Renderable clone = (Renderable) super.clone();
            clone.vertices = vertices.clone();
            clone.colors = colors.clone();
            clone.texCoords = texCoords.clone();
            clone.indices = indices.clone();
            clone.texturePath = texturePath;
            clone.position = new Vector3f(position);
            return clone;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void setNodeTransformation(Matrix4f nodeTransformation) {
        this.nodeTransformation = nodeTransformation;
    }

    public void setAnimationTransformation(Matrix4f animationTransformation) {
        this.animationTransformation = animationTransformation;
        updated = true;
    }
}
