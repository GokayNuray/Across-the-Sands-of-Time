package com.halenteck.fpsGame;

public abstract class FPSWeapon {
    protected String name;
    protected int damage;
    protected int magazineSize;
    protected int ammoInMagazine;
    protected float fireRate;
    protected float reloadTime;
    protected boolean isReloading;

    public FPSWeapon(String name, int damage, int magazineSize, float fireRate, float reloadTime) {
        this.name = name;
        this.damage = damage;
        this.magazineSize = magazineSize;
        this.ammoInMagazine = magazineSize;
        this.fireRate = fireRate;
        this.reloadTime = reloadTime;
        this.isReloading = false;
    }

    public void fire() {
        ammoInMagazine--;
    }

    public void reload() {
        if (!isReloading)
        {
            isReloading = true;
            ammoInMagazine = magazineSize;
            isReloading = false;
        }
    }

    protected boolean canFire() {
        return ammoInMagazine > 0 && !isReloading;
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
