package com.halenteck.fpsGame;

public class Vector3D {
    public float x, y, z;

    public Vector3D(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    // Add two vectors
    public void add(Vector3D vector) {
        this.x += vector.x;
        this.y += vector.y;
        this.z += vector.z;
    }

    // Scale vector by a scalar value
    public void scale(float value) {
        this.x *= value;
        this.y *= value;
        this.z *= value;
    }

    public static Vector3D scale(Vector3D vector, float scalar) {
        return new Vector3D(vector.x * scalar, vector.y * scalar, vector.z * scalar);
    }

    public void normalize() {
        float length = (float) Math.sqrt(x * x + y * y + z * z);
        if (length > 0) {
            x /= length;
            y /= length;
            z /= length;
        }
    }

    public float distanceTo(Vector3D vector) {
        float dx = this.x - vector.x;
        float dy = this.y - vector.y;
        float dz = this.z - vector.z;
        return (float) Math.sqrt(dx * dx + dy * dy + dz * dz);
    }
}
