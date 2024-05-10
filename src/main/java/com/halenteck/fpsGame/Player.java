package com.halenteck.fpsGame;

import com.halenteck.render.Entity;
import com.halenteck.render.Models;
import com.halenteck.render.World;
import org.joml.Vector3f;

import java.awt.event.KeyEvent;

public class Player {
    private final float SPEED = 0.05f;
    private final float GRAVITY = -10;
    private final float JUMP_FORCE = 20f;
    private final float CROUCH_MULTIPLIER = 0.5f;

    private String name;
    private Team team;
    private int health;
    private int armor;
    private int kills;
    private int deaths;
    private FPSWeapon weapon;
    private int attackPower = weapon.getDamage();
    private Vector3f position;
    private Vector3f velocity;
    private Vector3f directionVector;
    private float yaw = -180;
    private float pitch = 0;
    private float speed;
    private boolean isCrouching;
    private boolean isGrounded;

    private boolean moveForward;
    private boolean moveBackward;
    private boolean moveLeft;
    private boolean moveRight;

    public Player(String name, int health, FPSWeapon weapon, Vector3f startPosition) {
        this.name = name;
        this.health = health;
        this.weapon = weapon;
        this.position = startPosition;
        this.velocity = new Vector3f(0, 0, 0);
        kills = 0;
        deaths = 0;
        isCrouching = false;
        isGrounded = true;
        speed = SPEED;
    }

    public Player(Vector3f startPosition) {
        this.position = startPosition;
        this.velocity = new Vector3f(0, 0, 0);
        isCrouching = false;
        isGrounded = true;
        speed = SPEED;
    }

    public void update(float time) {
        new Thread(() -> {
            while (true) {
                if (moveForward) {
                    moveForward();
                }
                if (moveBackward) {
                    moveBackward();
                }
                if (moveLeft) {
                    moveLeft();
                }
                if (moveRight) {
                    moveRight();
                }

                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                velocity.mul(0.8f,0.8f,0.8f);
                if (!isGrounded) {
                    velocity.add(0,-0.4f, 0);
                }
                if (velocity.x < 0.005) velocity.x = 0;
                if (velocity.y < 0.005) velocity.y = 0;
                if (velocity.z < 0.005) velocity.z = 0;
            }
        }).run();
    }

    public void jump() {
        if (isGrounded) {
            isGrounded = false;
            velocity.y = JUMP_FORCE;
        }
    }

    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                moveForward = true;
                break;
            case KeyEvent.VK_S:
                moveBackward = true;
                break;
            case KeyEvent.VK_A:
                moveLeft = true;
                break;
            case KeyEvent.VK_D:
                moveRight = true;
                break;
        }
    }

    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                moveForward = false;
                break;
            case KeyEvent.VK_S:
                moveBackward = false;
                break;
            case KeyEvent.VK_A:
                moveLeft = false;
                break;
            case KeyEvent.VK_D:
                moveRight = false;
                break;
        }
    }

    public void moveForward() {
        velocity.x = speed;
    }

    public void moveBackward() {
        velocity.x = -speed;
    }

    public void moveRight() {
        velocity.z = speed;
    }

    public void moveLeft() {
        velocity.z = -speed;
    }

    public void rotate(float dYaw, float dPitch) {
        yaw += dYaw;
        pitch += dPitch;
        if (pitch > 85) pitch = 85;
        if (pitch < -90) pitch = -90;
        float directionX = (float) (Math.sin(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch)));
        float directionY = (float) Math.sin(Math.toRadians(pitch));
        float directionZ = (float) (Math.cos(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch)));
        directionVector = new Vector3f(directionX, directionY, directionZ);
    }

    public void shoot(Vector3f direction) {
        if (weapon.canFire())
        {
            weapon.fire();
            Bullet newBullet = new Bullet(this.position, direction, weapon.getDamage());
            newBullet.getBullets().add(newBullet);
        }
        else if (weapon.isReloading())
        {
            if (weapon.isReloading())
            {
                return;
            }
        }
        else
        {
            weapon.reload();
        }
    }

    public void reload() {
        if (weapon.getAmmoInMagazine() < weapon.magazineSize) {
            weapon.reload();
        }
        else
        {
            return;
        }
    }

    public void switchWeapon() {
        //TODO: Switch function.
    }

    public void takeDamage(int damage) {
        if (armor > 0)
        {
            if (armor < damage)
            {
                damage = damage - armor;
                health = health - damage;
            }
            else
            {
                armor = armor - damage;
            }
        }

        if (health <= 0) {
            this.die();
        }
    }

    public void die() {
        // TODO: Implement death logic.
        System.out.println("die() method will be implemented soon.");
        this.incrementDeaths();
        health = -1;
    }

    public void crouch() {
        if (!isCrouching)
        {
            isCrouching = true;
            speed = SPEED * CROUCH_MULTIPLIER;
        }
    }

    public void stand() {
        if (isCrouching)
        {
            isCrouching = false;
            speed = SPEED / CROUCH_MULTIPLIER;
        }
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Team getTeam() {
        return team;
    }

    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public FPSWeapon getWeapon() {
        return weapon;
    }

    public void setWeapon(FPSWeapon weapon) {
        this.weapon = weapon;
    }

    public float getX() {
        return this.position.x;
    }

    public float getY() {
        return this.position.y;
    }

    public float getZ() {
        return this.position.z;
    }

    public int getKills() {
        return kills;
    }

    public void incrementKills() {
        kills++;
    }

    public int getDeaths() {
        return deaths;
    }

    public void incrementDeaths() {
        deaths++;
    }

    public boolean getCrouchState() {
        return isCrouching;
    }

    public Vector3f getPosition() {
        return position;
    }

    public Vector3f getVelocity() {
        return velocity;
    }

    public Vector3f getDirectionVector() {
        return directionVector;
    }

    public void setPosition(Vector3f newPosition) {
        position = newPosition;
    }
}
