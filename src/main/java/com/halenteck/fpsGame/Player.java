package com.halenteck.fpsGame;

import java.util.ArrayList;
import java.util.Iterator;

public class Player {
    private final float SPEED = 5f;
    private final float GRAVITY = -10;
    private final float JUMP_FORCE = 20f;
    private final float CROUCH_MULTIPLIER = 0.5f; // Units are subject to change.

    private String name;
    private int health;
    private int armor;
    private FPSWeapon weapon1;
    private FPSWeapon weapon2;
    private Vector3D position;
    private Vector3D velocity;
    private float speed;
    private FPSWeapon currentWeapon;
    private boolean isCrouching;
    private boolean isGrounded;
    ArrayList<Bullet> bullets = new ArrayList<>();

    public Player(String name, int health, FPSWeapon weapon1, FPSWeapon weapon2, Vector3D startPosition) {
        this.name = name;
        this.health = health;
        this.weapon1 = weapon1;
        this.weapon2 = weapon2;
        this.position = startPosition;
        this.velocity = new Vector3D(0, 0, 0);
        isCrouching = false;
        isGrounded = true;

        currentWeapon = weapon1;
        speed = SPEED;
    }

    public void update(float time) {
        // Apply gravity if the player is not grounded
        if (!isGrounded) {
            velocity.add(new Vector3D(0, GRAVITY * time, 0));
        }

        // Update position of the player
        Vector3D positionChange = new Vector3D(velocity.x, velocity.y, velocity.z);
        positionChange.scale(time);
        position.add(positionChange);

        if (position.y <= 0) {
            position.y = 0;
            isGrounded = true;
            velocity.y = 0;
        }
    }

    public void jump() {
        if (isGrounded) {
            isGrounded = false;
            velocity.y = JUMP_FORCE;
        }
    }

    public void moveForward(float deltaTime) {
        if (isCrouching)
        {
            position.z += SPEED * CROUCH_MULTIPLIER * deltaTime;
        }
        else
        {
            position.z += SPEED * deltaTime;
        }
    }

    public void moveBackward(float deltaTime) {
        if (isCrouching)
        {
            position.z -= SPEED * CROUCH_MULTIPLIER * deltaTime;
        }
        else
        {
            position.z -= SPEED * deltaTime;
        }
    }

    public void moveLeft(float deltaTime) {
        if (isCrouching)
        {
            position.x -= SPEED * CROUCH_MULTIPLIER * deltaTime;
        }
        else
        {
            position.x -= SPEED * deltaTime;
        }
    }

    public void moveRight(float deltaTime) {
        if (isCrouching)
        {
            position.x += SPEED * CROUCH_MULTIPLIER * deltaTime;
        }
        else
        {
            position.x += SPEED * deltaTime;
        }
    }

    public void shoot(Vector3D direction) {
        if (currentWeapon.canFire())
        {
            currentWeapon.fire();
            Bullet newBullet = new Bullet(this.position, direction, currentWeapon.getDamage());
            bullets.add(newBullet);
        }
        else if (currentWeapon.isReloading())
        {
            if (currentWeapon.isReloading())
            {
                return;
            }
        }
        else
        {
            currentWeapon.reload();
        }
    }

    public void updateBullets(float time, ArrayList<Player> targets) {
        Iterator<Bullet> bulletIterator = bullets.iterator();
        while (bulletIterator.hasNext())
        {
            Bullet bullet = bulletIterator.next();
            bullet.update(time);

            for (Player target : targets)
            {
                if (isBulletHittingTarget(bullet, target))
                {
                    bulletIterator.remove();
                    target.takeDamage(bullet.getDamage());
                    break;
                }
            }
        }
    }

    private boolean isBulletHittingTarget(Bullet bullet, Player target) {
        float hitRadius = 1.0f; // Subject to change.
        return bullet.getPosition().distanceTo(target.position) <= hitRadius;
    }

    public void reload() {
        if (currentWeapon.getAmmoInMagazine() < currentWeapon.magazineSize) {
            currentWeapon.reload();
        }
        else
        {
            return;
        }
    }

    public void switchWeapon() {
        if (currentWeapon == weapon1)
        {
            currentWeapon = weapon2;
        }
        if (currentWeapon == weapon2)
        {
            currentWeapon = weapon1;
        }
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

    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public FPSWeapon getWeapon1() {
        return weapon1;
    }

    public FPSWeapon getWeapon2() {
        return weapon2;
    }

    public void setWeapon1(FPSWeapon weapon1) {
        this.weapon1 = weapon1;
    }

    public void setWeapon2(FPSWeapon weapon2) {
        this.weapon2 = weapon2;
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

    public boolean getCrouchState() {
        return isCrouching;
    }

    public Vector3D getPosition() {
        return position;
    }

    public Vector3D getVelocity() {
        return velocity;
    }
}
