package com.halenteck.render;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Entity {

    private Map<String, Animation> animations = new HashMap<>();

    private List<Renderable> renderables;
    private List<Renderable> headRenderables;
    private float x = 0;
    private float y = 0;
    private float z = 0;
    private float yaw = 0;
    private float pitch = 0;

    public Entity(int modelId, float x, float y, float z, float yaw, float pitch, float scale) {
        this.renderables = Models.getModel(modelId);
        headRenderables = new ArrayList<>();
        rotate(yaw, pitch);
        scale(scale, scale, scale);
        translate(x, y, z);

        Map<String, Animation> animations = Models.getAnimations(modelId);
        if (animations != null) {
            for (Map.Entry<String, Animation> entry : animations.entrySet()) {
                Animation animation = new Animation(entry.getValue(), this);
                this.animations.put(entry.getKey(), animation);
            }
        }

        for (Renderable renderable : renderables) {
            if (renderable.getName().equals("Head_1") || renderable.getName().equals("Right Arm") || renderable.getName().equals("Left Arm")) {
                headRenderables.add(renderable);
            }
        }

    }

    public void translate(float x, float y, float z) {
        this.x += x;
        this.y += y;
        this.z += z;
        renderables.forEach(r -> r.translate(x, y, z));
        Animation walk = animations.get("walkWithGun");
        if (walk != null) {
            walk.start();
        }
    }

    public void moveTo(float x, float y, float z) {
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
        headRenderables.forEach(r -> r.increasePitch(pitchRad));
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

    public void startAnimation(String name) {
        Animation animation = animations.get(name);
        if (animation != null) {
            animation.start();
        }
    }
}
