package com.halenteck.render;

import org.joml.Vector3f;

/**
 * A vector that is interpolatable
 */
public class CameraVector {

    Vector3f start;
    long startTime;

    Vector3f end;
    long endTime;

    CameraVector(float x, float y, float z) {
        start = new Vector3f(x, y, z);
        end = new Vector3f(start);

        startTime = System.currentTimeMillis();
        endTime = System.currentTimeMillis();
    }

    Vector3f get() {
        long currentTime = System.currentTimeMillis();
        if (currentTime >= endTime) {
            start = new Vector3f(end);
            startTime = System.currentTimeMillis();
            return end;
        }

        float progress = (float) (currentTime - startTime) / (endTime - startTime);
        Vector3f result = new Vector3f(start);
        return result.lerp(end, progress);
    }

    void set(Vector3f end, long duration) {

        this.start = get();
        this.startTime = System.currentTimeMillis();

        this.end = end;
        this.endTime = System.currentTimeMillis() + duration;

    }

    void add(Vector3f vec) {
        set(new Vector3f(end).add(vec), 100);
    }

    void sub(Vector3f vec) {
        set(new Vector3f(end).sub(vec), 100);
    }
}
