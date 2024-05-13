package com.halenteck.fpsGame;

import org.joml.Vector3f;

public class Bullet {
    private Vector3f position;
    private Vector3f velocity;
    private int damage;
    private static final float SPEED = 50f;
    private Player player;

    public Bullet(Vector3f startPosition, Vector3f direction, int damage) {
        this.position = new Vector3f(startPosition.x, startPosition.y, startPosition.z);
        direction.normalize();
        this.velocity = new Vector3f(direction.x * SPEED, direction.y * SPEED, direction.z * SPEED);
        this.damage = damage;
    }

    public boolean doesBulletHitTarget(Player player) {
        for (float i = 0; i < 100; i+= 0.1f) {
            update(i);
            if (isBulletHittingTarget(this, player)) return true;
        }
        return false;
    }

    private boolean isBulletHittingTarget(Bullet bullet, Player target) {
        float targetX = target.getX();
        float targetY = target.getY();
        float targetZ = target.getZ();

        float bulletX = bullet.getPosition().x;
        float bulletY = bullet.getPosition().y;
        float bulletZ = bullet.getPosition().z;

        return (bulletX >= targetX - 0.2f && bulletX <= targetX + 0.2f) &&
                (bulletY >= targetY && bulletY <= targetY + 1.7f ) &&
                (bulletZ >= targetZ - 0.2f && bulletZ <= targetZ + 0.2f);
    }

    public void update(float time) {
        position.add(new Vector3f(velocity.mul(time)));
    }

    public Vector3f getPosition() {
        return position;
    }

    public int getDamage() {
        return damage;
    }

    public Player getPlayer() {
        return player;
    }
}
