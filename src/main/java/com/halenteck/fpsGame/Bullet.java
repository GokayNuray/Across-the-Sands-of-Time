package com.halenteck.fpsGame;

public class Bullet {
    private Vector3D position;
    private Vector3D velocity;
    private int damage;
    private final float SPEED = 50f;

    public Bullet(Vector3D startPosition, Vector3D direction, int damage) {
        this.position = new Vector3D(startPosition.x, startPosition.y, startPosition.z);
        direction.normalize();
        this.velocity = new Vector3D(direction.x * SPEED, direction.y * SPEED, direction.z * SPEED);
        this.damage = damage;
    }

    public void update(float time) {
        position.add(Vector3D.scale(velocity, time));
    }

    public Vector3D getPosition() {
        return position;
    }

    public Vector3D getVelocity() {
        return velocity;
    }

    public int getDamage() {
        return damage;
    }
}
