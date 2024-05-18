package com.halenteck.render;

import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Entity {

    private Map<String, Animation> animations = new HashMap<>();

    private List<Renderable> renderables;
    private List<Renderable> headRenderables;
    private List<Entity> children = new ArrayList<>();
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
            if (renderable.getName().equals("Head_1") || renderable.getName().equals("Right Arm") || renderable.getName().equals("Left Arm") ||
                    renderable.getName().equals("Hat Layer") || renderable.getName().equals("Right Arm Layer") || renderable.getName().equals("Left Arm Layer")) {
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

    public List<Renderable> getRenderables() {
        return renderables;
    }

    public void startAnimation(String name) {
        Animation animation = animations.get(name);
        if (animation != null) {
            animation.start();
        }
    }

    public void addChild(int entityModel, float offsetX, float offsetY, float offsetZ) {
        Entity entity = new Entity(entityModel, this.x + offsetX, this.y + offsetY, this.z + offsetZ, 0, 0, 1);
        entity.setPivotPoint(new Vector3f(offsetX, offsetY, offsetZ));
        renderables.addAll(entity.renderables);
        headRenderables.addAll(entity.renderables);
        children.add(entity);
    }

    public void setPivotPoint(Vector3f pivot) {
        renderables.forEach(r -> r.setPivotPoint(pivot));
    }

    public List<Renderable> getHeadRenderables() {
        return headRenderables;
    }

    public List<Entity> getChildren() {
        return children;
    }
}
