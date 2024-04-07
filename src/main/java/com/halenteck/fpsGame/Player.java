package com.halenteck.fpsGame;

public class Player {
    private String name;
    private int health;
    private int armor;
    private FPSWeapon weapon1;
    private FPSWeapon weapon2;
    private float posX, posY, posZ;
    private FPSWeapon currentWeapon;
    private boolean isCrouching;
    private float speed;
    private final float CROUCH_MULTIPLIER = 0.5f;

    public Player(String name, int health, FPSWeapon weapon1, FPSWeapon weapon2, float posX, float posY, float posZ, float speed) {
        this.name = name;
        this.health = health;
        this.weapon1 = weapon1;
        this.weapon2 = weapon2;
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        this.speed = speed;
        isCrouching = false;

        currentWeapon = weapon1;
    }

    public void move(Direction direction) {

        if (this.getCrouchState())
        {
            switch (direction) {
                case FORWARD:  posZ += 1 * CROUCH_MULTIPLIER; break; //(W)
                case BACKWARD: posZ -= 1 * CROUCH_MULTIPLIER; break; //(S)
                case LEFT:     posX -= 1 * CROUCH_MULTIPLIER; break; //(A)
                case RIGHT:    posX += 1 * CROUCH_MULTIPLIER; break; //(D)
            }
        }

        switch (direction) {
            case FORWARD:  posZ += 1; break; //(W)
            case BACKWARD: posZ -= 1; break; //(S)
            case LEFT:     posX -= 1; break; //(A)
            case RIGHT:    posX += 1; break; //(D)
        }
    }

    public enum Direction {
        FORWARD, BACKWARD, LEFT, RIGHT
    }

    public void shoot() {
        currentWeapon.fire();
    }

    public void reload() {
        if (currentWeapon.getAmmoInMagazine() < currentWeapon.magazineSize) {
            currentWeapon.reload();
        } else {
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
            speed = speed * CROUCH_MULTIPLIER;
        }
    }

    public void stand() {
        if (isCrouching)
        {
            isCrouching = false;
            speed = speed / CROUCH_MULTIPLIER;
        }
    }

    public void jump() {
        // TODO: Implement jump method.
        System.out.println("jump() method will be implemented soon.");
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
        return posX;
    }

    public float getY() {
        return posY;
    }

    public float getZ() {
        return posZ;
    }

    public boolean getCrouchState() {
        return isCrouching;
    }
}
