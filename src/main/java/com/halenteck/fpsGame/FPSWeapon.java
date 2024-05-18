package com.halenteck.fpsGame;

public class FPSWeapon {
    private String name;

    private int id;
    private int damage;
    private int magazineSize;
    private int ammoInMagazine;

    private float fireRate;
    private float reloadTime;
    private float range;

    private boolean isReloading;

    Player player;

    public FPSWeapon(int id) {
        this.id = id;
        setStats(id);
        this.isReloading = false;
        ammoInMagazine = magazineSize;
        if (id < 5) {
            range = 100;
        } else {
            ammoInMagazine = 0;
            reloadTime = 0;
            range = 3;
        }
    }

    public void fire() {
        ammoInMagazine--;
    }

    public void reload() {
        if(id < 5) {
            if (!isReloading && magazineSize != ammoInMagazine)
            {
                startReloadThread();
            }
        }
    }

    public void startReloadThread() {
        isReloading = true;
        new Thread(() -> {
            try {
                Thread.sleep((long)getReloadTime() * 1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            ammoInMagazine = magazineSize;
            isReloading = false;
        }).start();
    }

    public void setStats(int id) {
        switch (id) {
            case 0:
                name = "AK-47";
                damage = 33;
                magazineSize = 30;
                fireRate = 14;
                reloadTime = 3;
                break;
            case 1:
                name = "M4";
                damage = 20;
                magazineSize = 31;
                fireRate = 18;
                reloadTime = 2;
                break;
            case 2:
                name = "MP5";
                damage = 15;
                magazineSize = 31;
                fireRate = 31;
                reloadTime = 2;
                break;
            case 3:
                name = "UF-2";
                damage = 3;
                magazineSize = 150;
                fireRate = 50;
                reloadTime = 1;
                break;
            case 4:
                name = "MK1";
                damage = 60;
                magazineSize = 10;
                fireRate = 3;
                reloadTime = 3;
                break;
            case 5:
                name = "Combat Knife";
                damage = 40;
                fireRate = 1;
                break;
            case 6:
                name = "Battle Axe";
                damage = 75;
                fireRate = 0.5f;
                break;
            case 7:
                name = "Skewer";
                damage = 10;
                fireRate = 5;
                break;
            case 8:
                name = "Cleave";
                damage = 1;
                fireRate = 20;
                break;
            case 9:
                name = "Dismantle";
                damage = 100;
                fireRate = 0.5f;
                break;
        }
    }

    public void setInfAmmo() {
        ammoInMagazine = 100000;
    }

    public void setRegularAmmo() {
        ammoInMagazine = magazineSize;
    }

    public boolean canFire() {
        return (ammoInMagazine > 0 && !isReloading) || magazineSize == 0;
    }

    public String getName() {
        return name;
    }

    public int getDamage() {
        return damage;
    }

    public int getMagazineSize() {
        return magazineSize;
    }

    public int getAmmoInMagazine() {
        return ammoInMagazine;
    }

    public int getId() {
        return id;
    }

    public float getRange() {
        return range;
    }

    public float getFireRate() {
        return fireRate;
    }

    public float getReloadTime() {
        return reloadTime;
    }

    public boolean isReloading() {
        return isReloading;
    }
}
