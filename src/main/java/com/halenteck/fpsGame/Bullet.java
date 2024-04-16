package com.halenteck.fpsGame;

import org.joml.Vector3f;

public class Bullet {
    private Vector3f position;
    private Vector3f velocity;
    private int damage;
    private final float SPEED = 50f;

    public Bullet(Vector3f startPosition, Vector3f direction, int damage) {
        this.position = new Vector3f(startPosition.x, startPosition.y, startPosition.z);
        direction.normalize();
        this.velocity = new Vector3f(direction.x * SPEED, direction.y * SPEED, direction.z * SPEED);
        this.damage = damage;
    }

    public void update(float time) {
        position.add(new Vector3f(velocity.mul(time)));
    }

    public Vector3f getPosition() {
        return position;
    }

    public Vector3f getVelocity() {
        return velocity;
    }

    public int getDamage() {
        return damage;
    }
}
