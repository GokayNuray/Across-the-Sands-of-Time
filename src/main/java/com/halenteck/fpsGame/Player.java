package com.halenteck.fpsGame;

import com.halenteck.render.Entity;
import com.halenteck.render.Models;
import com.halenteck.render.OpenGLComponent;
import com.halenteck.render.World;
import com.halenteck.server.Server;
import org.joml.Vector3f;

import javax.swing.*;
import java.awt.event.*;

public class Player implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener {
    private static final float TICKS_PER_SECOND = 20;
    private static final float SPEED = 0.02f;
    private static final float JUMP_FORCE = 0.5f;
    private static final float CROUCH_MULTIPLIER = 0.5f;

    private Vector3f position;
    private Vector3f velocity;
    private Vector3f accelerationOfTheVelocityWhichWillEffectThePositionOfTheCurrentPlayer;
    private Vector3f directionVector;

    private FPSWeapon weapon;
    private Entity entity;
    private OpenGLComponent renderer;
    private Team team;

    private String name;

    private byte id;
    private byte kills;
    private byte deaths;
    private byte characterId;

    private int health = 100;
    private int armor;
    private int weaponId;
    private int attackPower;

    private int lastMouseX;
    private int lastMouseY;

    private float yaw = -180;
    private float pitch = 0;
    private float speed;

    private boolean isRedTeam;
    private boolean isCrouching;
    private boolean isGrounded;
    private boolean abilityActive;
    private boolean abilityThreadActive;
    private boolean ableToUseAbility;
    private boolean shooting;

    //Abilities for 0, 1(done), 2(done), 3(done), 4 respectively, used to keep track
    private boolean isInvisible0;
    private boolean isImmortal1;
    private boolean isFlying2;
    private boolean redBullets3;
    private boolean infAmmo4;

    private boolean moveForward;
    private boolean moveBackward;
    private boolean moveLeft;
    private boolean moveRight;
    private boolean jump;

    World world;

    private JLabel debugLabel;

    public Player(Byte id, boolean isRedTeam, String name, Vector3f startPosition,
                  float yaw, float pitch, boolean isCrouching, int weaponId,
                  int attackPower, byte kill, byte death, byte characterId, World world) {
        this.id = id;
        this.isRedTeam = isRedTeam;
        this.name = name;
        this.position = startPosition;
        this.yaw = yaw;
        this.pitch = pitch;
        this.directionVector = new Vector3f(0, 0, -1);
        this.isCrouching = isCrouching;
        this.weaponId = weaponId;
        this.attackPower = attackPower;
        this.kills = kill;
        this.deaths = death;
        this.characterId = characterId;
        this.accelerationOfTheVelocityWhichWillEffectThePositionOfTheCurrentPlayer = new Vector3f(0, 0, 0);
        this.velocity = new Vector3f(0, 0, 0);

        speed = SPEED;

        int modelId;
        switch (characterId) {
            case 0x00 -> modelId = Models.CHARACTER1;
            case 0x01 -> modelId = Models.CHARACTER2;
            case 0x02 -> modelId = Models.CHARACTER3;
            case 0x03 -> modelId = Models.CHARACTER4;
            case 0x04 -> modelId = Models.CHARACTER5;
            default -> throw new IllegalArgumentException("invalid character id: " + characterId);
        }
        this.entity = new Entity(modelId, startPosition.x, startPosition.y, startPosition.z, 0, 0, 0.01f);
        this.world = world;
    }

    public Player(Vector3f startPosition) {
        this.position = startPosition;
        this.velocity = new Vector3f(0, 0, 0);
        isCrouching = false;
        isGrounded = true;
        speed = SPEED;
    }

    public void startMovementThread() {
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
                if (shooting) {
                    shoot();
                }
                if (jump) {
                    jump();
                }

                velocity.add(accelerationOfTheVelocityWhichWillEffectThePositionOfTheCurrentPlayer);
                StringBuilder debugText = new StringBuilder();
                debugText.append("Position: ").append(position).append(" ");
                debugText.append("IPosition: ").append(Math.floor(position.x)).append(" ").append(Math.floor(position.y)).append(" ").append(Math.floor(position.z)).append(" ");
                debugText.append("Velocity: ").append(velocity).append(" ");
                debugText.append("Acceleration: ").append(accelerationOfTheVelocityWhichWillEffectThePositionOfTheCurrentPlayer).append("\n");
                debugLabel.setText(debugText.toString());
                move(velocity);
                entity.move(position.x, position.y, position.z);
                if (!isGrounded && !(isAbilityActive() && characterId == 2)) {
                    velocity.add(0, -0.019f, 0);
                }
                velocity.mul(0.8f, 0.8f, 0.8f);
                if (Math.abs(velocity.x) < 0.005) velocity.x = 0;
                if (Math.abs(velocity.y) < 0.005) velocity.y = 0;
                if (Math.abs(velocity.z) < 0.005) velocity.z = 0;

                accelerationOfTheVelocityWhichWillEffectThePositionOfTheCurrentPlayer.set(0, 0, 0);

                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }

    public void startAbilityThread() {
        if (!abilityThreadActive) {
            abilityThreadActive = true;
            new Thread(() -> {
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                abilityActive = false;
                abilityThreadActive = false;
                startCooldownThread();
            }).start();
        }
    }

    public void startCooldownThread() {
        ableToUseAbility = false;
        new Thread(() -> {
            try {
                Thread.sleep(15000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            ableToUseAbility = true;
        }).start();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
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
            case KeyEvent.VK_Q:
                if (ableToUseAbility) {
                    abilityActive = true;
                    startAbilityThread();
                }
            case KeyEvent.VK_R:
                reload();
            case KeyEvent.VK_SPACE:
                jump = true;
                break;
            case KeyEvent.VK_SHIFT:
                crouch();
                break;
        }
    }

    @Override
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
            case KeyEvent.VK_SPACE:
                jump = false;
                break;
            case KeyEvent.VK_SHIFT:
                stand();
                break;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        switch (e.getButton()) {
            case MouseEvent.BUTTON1:
                shooting = true;
                break;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        switch (e.getButton()) {
            case MouseEvent.BUTTON1:
                shooting = false;
                break;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        int dx = e.getX() - lastMouseX;
        int dy = e.getY() - lastMouseY;
        lastMouseX = e.getX();
        lastMouseY = e.getY();
        rotate(dx, dy);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        lastMouseX = e.getX();
        lastMouseY = e.getY();
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        int notches = e.getWheelRotation();
        if (notches != 0) {
            switchWeapon();
        }
    }

    public void move(Vector3f velocity) {
        float dX = position.x;
        float dY = position.y;
        float dZ = position.z;
        moveX(velocity.x);
        moveY(velocity.y);
        moveZ(velocity.z);
        entity.move(position.x, position.y, position.z);
        dX = position.x - dX;
        dY = position.y - dY;
        dZ = position.z - dZ;
        if (renderer != null) {
            renderer.moveCamera(new Vector3f(position.x, position.y + 1.7f, position.z));
            if (Math.abs(dX) > 0.001 || Math.abs(dY) > 0.001 || Math.abs(dZ) > 0.001) {
                Server.movePlayer(dX, dY, dZ);
            }
        }
    }

    private void moveX(float x) {
        if (x == 0) return;
        float newX = position.x + x;
        int direction = (int) Math.signum(x);
        if (world.isFull(newX - 0.2F, position.y, position.z) ||
                world.isFull(newX + 0.2F, position.y, position.z)) {
            position.x = (float) (Math.floor(position.x) + 0.5f + direction * 0.3f);
            velocity.x = 0;
            return;
        }
        position.x = newX;
    }

    private void moveY(float y) {
        float newY = position.y + y;
        int direction = (int) Math.signum(y);

        if (world.isFull(position.x, newY, position.z) ||
                world.isFull(position.x, newY + 1.7f, position.z)) {
            position.y = (float) Math.floor(position.y);
            velocity.y = 0;
            isGrounded = true;
            return;
        }

        isGrounded = false;
        if (world.isFull(position.x, (newY - 0.001f), position.z)) {
            isGrounded = true;
            velocity.y = 0;
        }

        position.y = newY;
    }

    private void moveZ(float z) {
        if (z == 0) return;
        float newZ = position.z + z;
        int direction = (int) Math.signum(z);
        if (world.isFull(position.x, position.y, newZ - 0.2f) ||
                world.isFull(position.x, position.y, newZ + 0.2f)) {
            position.z = (float) (Math.floor(position.z) + 0.5f + direction * 0.3f);
            velocity.z = 0;
            return;
        }
        position.z = newZ;
    }

    public void moveForward() {
        Vector3f acceleration = new Vector3f(directionVector);
        acceleration.set(acceleration.x, 0, acceleration.z).normalize().mul(speed);
        accelerationOfTheVelocityWhichWillEffectThePositionOfTheCurrentPlayer.add(acceleration);
    }

    public void moveBackward() {
        Vector3f acceleration = new Vector3f(directionVector);
        acceleration.set(acceleration.x, 0, acceleration.z).normalize().mul(speed);
        accelerationOfTheVelocityWhichWillEffectThePositionOfTheCurrentPlayer.sub(acceleration);
    }

    public void moveRight() {
        Vector3f acceleration = new Vector3f(directionVector);
        acceleration.set(acceleration.x, 0, acceleration.z).normalize();
        Vector3f right = acceleration.cross(new Vector3f(0, 1, 0));
        accelerationOfTheVelocityWhichWillEffectThePositionOfTheCurrentPlayer.add(right.mul(speed));
    }

    public void moveLeft() {
        Vector3f acceleration = new Vector3f(directionVector);
        acceleration.set(acceleration.x, 0, acceleration.z).normalize();
        Vector3f right = acceleration.cross(new Vector3f(0, 1, 0));
        accelerationOfTheVelocityWhichWillEffectThePositionOfTheCurrentPlayer.sub(right.mul(speed));
    }

    public void jump() {
        if (isAbilityActive() && characterId == 2) {
            velocity.y = JUMP_FORCE;
        } else if (isGrounded) {
            isGrounded = false;
            velocity.y = JUMP_FORCE;
        }
    }

    public void crouch() {
        if (!isCrouching) {
            isCrouching = true;
            speed = SPEED * CROUCH_MULTIPLIER;
        }
    }

    public void stand() {
        if (isCrouching) {
            isCrouching = false;
            speed = SPEED;
        }
    }

    public void rotate(float dYaw, float dPitch) {
        yaw -= dYaw;
        pitch -= dPitch;
        if (pitch > 89.99f) pitch = 89.99f;
        if (pitch < -89.99f) pitch = -89.99f;
        float directionX = (float) (Math.sin(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch)));
        float directionY = (float) Math.sin(Math.toRadians(pitch));
        float directionZ = (float) (Math.cos(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch)));
        directionVector = new Vector3f(directionX, directionY, directionZ);

        entity.setRotation(yaw, pitch);
        if (renderer != null) {
            renderer.setCameraRotation(yaw, pitch);
            Server.rotatePlayer(yaw, pitch);
        }
    }

    public Bullet shoot() {
        if (weapon.canFire()) {
            weapon.fire();
            return new Bullet(this.position, directionVector, weapon.getDamage());
        } else if (weapon.isReloading()) {
            if (weapon.isReloading()) {
                return null;
            }
        } else {
            weapon.reload();
        }
        return null;
    }

    public void reload() {
        if (weapon.getAmmoInMagazine() < weapon.getMagazineSize()) {
            weapon.reload();
        } else {
            return;
        }
    }

    public void switchWeapon() {
        //TODO: Switch function.
    }

    public void handleBullet(Bullet bullet) {
        if (bullet.doesBulletHitTarget(this)) {
            if (!(isAbilityActive() && characterId == 1)) {
                takeDamage(bullet.getDamage());
            }
        }
    }

    public void takeDamage(int damage) {
        if (armor > 0) {
            if (armor < damage) {
                damage = damage - armor;
                health = health - damage;
            } else {
                armor = armor - damage;
            }
        }

        if (health <= 0) {
            this.die();
        }
    }

    public void die() {
        this.incrementDeaths();
        health = -1;
    }

    public void attachRenderer(OpenGLComponent renderer) {
        renderer.addKeyListener(this);
        renderer.addMouseListener(this);
        renderer.addMouseMotionListener(this);
        renderer.addMouseWheelListener(this);
        renderer.moveCamera(new Vector3f(position.x, position.y + 1.7f, position.z));
        renderer.removeEntity(entity);
        this.renderer = renderer;
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

    public byte getKills() {
        return kills;
    }

    public void incrementKills() {
        kills++;
    }

    public byte getDeaths() {
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

    public byte getCharacterId() {
        return characterId;
    }

    public boolean isAbilityActive() {
        return abilityActive;
    }

    public Entity getEntity() {
        return entity;
    }

    public void setDebugConsole(JLabel chatArea) {
        this.debugLabel = chatArea;
    }
}
