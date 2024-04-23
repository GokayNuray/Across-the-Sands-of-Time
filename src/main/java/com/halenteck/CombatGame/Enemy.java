package com.halenteck.CombatGame;

import java.util.Random;

public class Enemy {


    protected double health;//initalized at the subclasses
    protected double lifeToRenew;
    //when there is more than one enemy
    protected double damage;//the amount of damage the opponent will originally inflict
    protected int x;
    protected int y;
    protected Player player;
    protected double damageFromEnemy;// the damage amount the enenmy will inflict after the distance calculations
    protected boolean isDead;
    protected boolean playedTurn;
    protected int number;//initalized at subclasses
    protected int maxNumber;
    protected String name;//error'u gidermek için koydum
    protected int award;

    public Enemy(Player aPlayer, String aName, int aDamage, int sAward, int aHealth, int maxNumber) {
        x = 480;
        y = 40;
        isDead = false;
        player = aPlayer;
        playedTurn = false;
        name = aName;
        health = aHealth;
        lifeToRenew = aHealth;
        damage = aDamage;
        this.maxNumber = maxNumber;
        award = sAward;

    }

    public int enemyCount() {
        Random rand = new Random();
        number = (rand.nextInt(maxNumber) + 1);
        return number;
    }

    public void checkLife() {
        if (health <= 0) {
            isDead = true;
        } else {
            isDead = false;
        }
    }

    public void reSetHealth() {//to make the life equal to its original level when there are more monsters

        health = lifeToRenew;
        isDead = false;
    }

    //karakter oynadıktan sonra enemy oynacak
    public void move() {

        playedTurn = false;//emin olmak için
        Random rand = new Random();

        while (playedTurn == false) {

            int enemyProcess = rand.nextInt(3);

            // we can do it like this, ben bundan yanayım
            if (player.playingChar.abilityExists && player.playingChar.isAbilityActive && player.characterID == 3) {

                enemyProcess = rand.nextInt(2);
            }

            if (player.playingChar.abilityExists && player.playingChar.isAbilityActive && player.characterID == 4) {

                enemyProcess = 2;
            }

            if (player.playingChar.abilityExists && player.playingChar.isAbilityActive && player.characterID == 5) {

                playedTurn = true;
            }

            if (enemyProcess == 0 && !playedTurn) {//to go forward, eğer çakışma olan bir şey gelirse henüz hareket edemedi varsayıp tekrar rastgele sayı seçiliyor

                if (this.x - 160 != player.x) { //düşmanın x koordinatını alıp onunla çakışmayacağına emin olmak lazım
                    this.x -= 80;
                    playedTurn = true;
                }
            }

            if (enemyProcess == 1 && !playedTurn) {// to go backward

                if (this.x + 80 <= 800) {
                    this.x += 80;
                    playedTurn = true;
                }
            }

            if (enemyProcess == 2 && !playedTurn) {//to attack

                double lossCoefficent;
                if (Math.abs(player.x - x) == 160) {
                    lossCoefficent = 4;
                }
                if (Math.abs(player.x - x) == 240) {
                    lossCoefficent = 3;
                }
                if (Math.abs(player.x - x) == 320) {
                    lossCoefficent = 2;
                }
                if (Math.abs(player.x - x) == 400) {
                    lossCoefficent = 1;
                } else {
                    lossCoefficent = 0;
                }

                this.damage += lossCoefficent;

                if (player.playingChar.abilityExists && player.playingChar.isAbilityActive && (player.characterID == 1 || player.characterID == 2)) {
                    this.damage = this.damage / 2;
                }

                if (player.armourExists) {
                    player.playingChar.health = player.playingChar.health - this.damage + player.armour.defence + player.playingChar.defencePower;
                } else {
                    player.playingChar.health = player.playingChar.health - this.damage + player.playingChar.defencePower;
                }

                playedTurn = true;
            }
        }
        playedTurn = false;//bir sonraki move için tekrar false yapıldı.
    }
}
