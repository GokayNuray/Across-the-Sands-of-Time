package com.halenteck.render;

import java.util.List;
import java.util.Map;

public class Entity {

    private Animation animation;

    private List<Renderable> renderables;
    private float x = 0;
    private float y = 0;
    private float z = 0;
    private float yaw = 0;
    private float pitch = 0;

    public Entity(int modelId, float x, float y, float z, float yaw, float pitch, float scale) {
        this.renderables = Models.getModel(modelId);
        rotate(yaw, pitch);
        scale(scale, scale, scale);
        translate(x, y, z);

        Map<String, Animation> animations = Models.getAnimations(modelId);
        if (animations != null) {
            this.animation = new Animation(animations.get("wave"), this);
            this.animation.start();
        }

    }

    public void translate(float x, float y, float z) {
        this.x += x;
        this.y += y;
        this.z += z;
        renderables.forEach(r -> r.translate(x, y, z));
    }

    public void move(float x, float y, float z) {
        float dX = x - this.x;
        float dY = y - this.y;
        float dZ = z - this.z;
        translate(dX, dY, dZ);
    }

    public void rotate(float yaw, float pitch) {
        this.yaw += yaw;
        this.pitch += pitch;
        float yawRad = (float) Math.toRadians(yaw);
        float pitchRad = (float) Math.toRadians(pitch);
        renderables.forEach(r -> r.increaseYaw(yawRad));
    }

    public void setRotation(float yaw, float pitch) {
        float dYaw = yaw - this.yaw;
        float dPitch = pitch - this.pitch;
        rotate(dYaw, dPitch);
    }

    public void scale(float x, float y, float z) {
        renderables.forEach(r -> r.scale(x, y, z));
    }

    public void getRenderables(List<Renderable> renderables) {
        renderables.addAll(this.renderables);
    }
}
