package com.halenteck.fpsGame;

import com.halenteck.render.Entity;
import org.joml.Vector3f;

import java.util.ArrayList;

public class Bullet {
    private Entity entity;
    private Vector3f position;
    private Vector3f velocity;
    private int damage;
    private final float SPEED = 50f;

    ArrayList<Bullet> bullets = new ArrayList<>();

    public Bullet(Vector3f startPosition, Vector3f direction, int damage) {
        this.position = new Vector3f(startPosition.x, startPosition.y, startPosition.z);
        direction.normalize();
        this.velocity = new Vector3f(direction.x * SPEED, direction.y * SPEED, direction.z * SPEED);
        this.damage = damage;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    public void updateBullets(float time, ArrayList<Player> targets) {
        for (Bullet bullet : bullets) {
            bullet.update(time);

            for (Player target : targets)
            {
                if (isBulletHittingTarget(bullet, target))
                {
                    bullets.remove(bullet);
                    target.takeDamage(bullet.getDamage());
                    break;
                }
            }
        }
    }

    private boolean isBulletHittingTarget(Bullet bullet, Player target) {
        float hitRadius = 1.0f; // Subject to change.
        return bullet.getPosition().distance(target.getPosition()) <= hitRadius;
    }

    public void update(float time) {
        position.add(new Vector3f(velocity.mul(time)));
        entity.move(position.x, position.y, position.z);
    }

    public ArrayList<Bullet> getBullets() {
        return bullets;
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
