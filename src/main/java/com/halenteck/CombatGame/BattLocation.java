package com.halenteck.CombatGame;

import java.util.Scanner;

public class BattLocation extends Location {

    protected Enemy enemies;
    protected String award;
    //protected Location location;

    public BattLocation(Player player, String name, Enemy enemies, String award) {
        super(player);
        this.name = name;
        this.enemies = enemies;
        this.award = award;
        //getBLocation();
    }

    public boolean getLocation() {
        int enemyCount = enemies.enemyCount();
        System.out.println("You are in here now: " + this.name);
        System.out.println("Pay attention! you can encounter with " + enemyCount + " " + enemies.name + " here");
        //combat(enemyCount); //buna bu şekilde gerek olmayabilir.
        if (combat(enemyCount)) {
            // System.out.println("you beated the enemies");
            return true;
        } else {
            System.out.println("you died enemy defeated you game over");
            return false;
        }

    }
    
    /*public void getBLocation(){//bu methodun amacı ne?, niye direkten combat'a gitmeyelim?
        int enemyCount = enemies.number;
        combat(enemyCount);
    }*/

    public boolean combat(int enemyCount) {
        Scanner scan = new Scanner(System.in);

        double defEnemyHealth = enemies.health;

        //checkForMoney() methodu enemy sayısını kontrol eden ve ona göre enemy canını yenileyen bir methdou çağırdığı için enemy sayısına
        //bakmaya gerek kalmadı. Doğru sayıya ulaşınca can yenilenmeyecek ve burada enemies.health > 0 koşulu sağlanmadığı için loop bitecek.
        while (player.playingChar.health > 0 && enemies.health > 0) {
            System.out.println("1-) Forward   2-) Backward   3-) Attack Short Range  4-) Attack Long Range  5-)Use Ability");
            int process = scan.nextInt();
            if (process == 1 && (player.x + 80 < 240)) { //Karoların uzunluğuyla istediğin gibi oynayabilirsin, biz 80 almıştık.
                player.x += 80;
                enemies.move();
                System.out.println("You are in x = " + player.x);

            } else if (process == 2 && (player.x - 80 > 0)) {
                player.x -= 80;
                enemies.move();
                System.out.println("You are in x = " + player.x);

            } else if (process == 3) {
                double addDamageForShortRange;
                if (Math.abs(player.x - enemies.x) == 400) {
                    addDamageForShortRange = 0;
                }
                if (Math.abs(player.x - enemies.x) == 320) {
                    addDamageForShortRange = 1;
                }
                if (Math.abs(player.x - enemies.x) == 240) {
                    addDamageForShortRange = 2;
                }
                if (Math.abs(player.x - enemies.x) == 160) {
                    addDamageForShortRange = 3;
                }
                if (Math.abs(player.x - enemies.x) == 480) {
                    addDamageForShortRange = -1;
                }
                if (Math.abs(player.x - enemies.x) == 560) {
                    addDamageForShortRange = -2;
                } else {
                    addDamageForShortRange = -3;
                }
                enemies.health -= player.playingChar.shortRangeDamage - addDamageForShortRange;
                enemies.health -= player.playingChar.attackPower;

                enemies.checkLife();
                checkForMoney();
                enemies.checkLife();
                player.checkLevel();
                if (!enemies.isDead) {
                    enemies.move();
                }
                System.out.println("Your health: " + player.playingChar.health + " Enemy's health: " + enemies.health);

            } else if (process == 4 && player.playingChar.weapons[1].isActive) {
                double addDamageForLongRange;
                if (Math.abs(player.x - enemies.x) == 640) {
                    addDamageForLongRange = 3;
                }
                if (Math.abs(player.x - enemies.x) == 560) {
                    addDamageForLongRange = 2;
                }
                if (Math.abs(player.x - enemies.x) == 480) {
                    addDamageForLongRange = 1;
                }
                if (Math.abs(player.x - enemies.x) == 400) {
                    addDamageForLongRange = 0;
                }
                if (Math.abs(player.x - enemies.x) == 320) {
                    addDamageForLongRange = -1;
                }
                if (Math.abs(player.x - enemies.x) == 240) {
                    addDamageForLongRange = -2;
                } else {
                    addDamageForLongRange = -3;
                }
                enemies.health -= player.playingChar.longRangeDamage + addDamageForLongRange;
                enemies.health -= player.playingChar.attackPower;

                enemies.checkLife();
                checkForMoney();
                enemies.checkLife();
                player.checkLevel();
                if (!enemies.isDead) {
                    enemies.move();
                }
                System.out.println("Your health: " + player.playingChar.health + " Enemy's health: " + enemies.health);
            } else if (player.playingChar.abilityExists && process == 5) {//player.playingChar.ability.useage > 0 &&   bu belki gerekir, emin değilim ya da sadece bu olabilir
                player.playingChar.isAbilityActive = true;
                if (player.characterID == 1) {//uçma
                    player.y = player.y * 2;//sol üsütn y'si bu. height falan aynı
                    //burada karakter çizildiğinde buna göre çizilmeli
                } else if (player.characterID == 2) {//eğilme
                    player.y = player.y / 2;//sol üsütn y'si bu. height yarıya inecek
                    player.height = player.height / 2;
                    //burada karakter çizildiğinde buna göre çizilmeli
                }
                //bu son 3 if bizim için gerekli olmayabilir ama Dilara'nın çizimleri yaparken ihtiyacı olabilir
                else if (player.characterID == 3) {//görünmezlik
                    //burada karakter çizilmeyecek
                } else if (player.characterID == 4) {//yeri sarsma
                    //çizm
                } else if (player.characterID == 5) {//afallatmak
                    //çizim
                }
                enemies.move(); //etkisi enemy nin hareket etmesiyle görünecek
                player.playingChar.ability.used();
                player.playingChar.isAbilityActive = false;
                player.y = 80;
                player.height = 80;// her durumda yenilemek lazım
            }
        }
        if (enemies.health <= 0 && player.playingChar.health > 0) {
            System.out.println("you defeated all enemies :)");
            player.money += enemies.award;
            System.out.println("Your current money : " + player.money);
            enemies.health = defEnemyHealth;
        } else {
            return false;
        }

        return true;
    }

    public void goForward() {

        if ((enemies.x > player.x + 160) && (player.x + 80 < 320)) { //düşmanın x koordinatını alıp onunla çakışmayacağına emin olmak lazım
            player.x += 80;
            enemies.move();//in this case enemy will move after the character and will be able to move if the player made a valid move
            //the same goes for the enemy
        }
    }

    public void goBackward() {

        if (player.x - 80 > 0) {//to check limits
            player.x -= 80;
            enemies.move();
        }
    }

    //!!!!!Bir karakter bir lokasyondaki item'ını topladığında, bir sonraki lokasyon için onje nerede yaratılacak?
    //Ya da yeni karakter nerde yaratılacak?

    public void shortRange() {

        double addDamageForShortRange;

        if (Math.abs(player.x - enemies.x) == 400) {
            addDamageForShortRange = 0;
        }
        if (Math.abs(player.x - enemies.x) == 320) {
            addDamageForShortRange = 1;
        }
        if (Math.abs(player.x - enemies.x) == 240) {
            addDamageForShortRange = 2;
        }
        if (Math.abs(player.x - enemies.x) == 160) {
            addDamageForShortRange = 3;
        }
        if (Math.abs(player.x - enemies.x) == 480) {
            addDamageForShortRange = -1;
        }
        if (Math.abs(player.x - enemies.x) == 560) {
            addDamageForShortRange = -2;
        } else {
            addDamageForShortRange = -3;
        }
        enemies.health -= player.playingChar.shortRangeDamage + addDamageForShortRange;
        enemies.health -= player.playingChar.attackPower;

        enemies.checkLife();
        checkForMoney();
        enemies.checkLife();
        player.checkLevel();
        if (!enemies.isDead) {
            enemies.move();
        }
    }

    public void longRange() {

        if (player.playingChar.weapons[1].isActive) {

            double addDamageForLongRange;
            if (Math.abs(player.x - enemies.x) == 640) {
                addDamageForLongRange = 3;
            }
            if (Math.abs(player.x - enemies.x) == 560) {
                addDamageForLongRange = 2;
            }
            if (Math.abs(player.x - enemies.x) == 480) {
                addDamageForLongRange = 1;
            }
            if (Math.abs(player.x - enemies.x) == 400) {
                addDamageForLongRange = 0;
            }
            if (Math.abs(player.x - enemies.x) == 320) {
                addDamageForLongRange = -1;
            }
            if (Math.abs(player.x - enemies.x) == 240) {
                addDamageForLongRange = -2;
            } else {
                addDamageForLongRange = -3;
            }

            enemies.health -= player.playingChar.longRangeDamage + addDamageForLongRange;//1'deki long range, bu button'ın aktifliği Dilara'daydı
            enemies.health -= player.playingChar.attackPower;

            enemies.checkLife();
            checkForMoney();
            enemies.checkLife();
            player.checkLevel();
            if (!enemies.isDead) {
                enemies.move();
            }
        }
    }

    public void useAbility() {
        if (player.playingChar.abilityExists) {// player.playingChar.ability.useage > 0 &&  bu belki gerekir, emin değilim ya da sadece bu olabilir
            player.playingChar.isAbilityActive = true;
            if (player.characterID == 1) {//uçma
                player.y = player.y * 2;//sol üsütn y'si bu. height falan aynı
                //burada karakter çizildiğinde buna göre çizilmeli
            } else if (player.characterID == 2) {//eğilme
                player.y = player.y / 2;//sol üsütn y'si bu. height yarıya inecek
                player.height = player.height / 2;
                //burada karakter çizildiğinde buna göre çizilmeli
            }
            //bu son 3 if bizim için gerekli olmayabilir ama Dilara'nın çizimleri yaparken ihtiyacı olabilir
            else if (player.characterID == 3) {//görünmezlik
                //burada karakter çizilmeyecek
            } else if (player.characterID == 4) {//yeri sarsma
                //çizm
            } else if (player.characterID == 5) {//afallatmak
                //çizim
            }
            enemies.move(); //etkisi enemy nin hareket etmesiyle görünecek
            player.playingChar.ability.used();
            player.playingChar.isAbilityActive = false;
            player.y = 80;
            player.height = 80;
        }
    }

    public void checkForMoney() {//when an enemy dies, the player gains money and XP point
        //whenever an enemy is defeated XP point will be collected (the amount is equal to item's value) but 
        //item and money will be collected after all the enemies in the location are dead.
        //all the items of a character are worth the same
        if (enemies.isDead) {
            player.XP += enemies.award;
            player.checkLevel();
            player.playingChar.defeatedEnemies++;
            if (player.playingChar.defeatedEnemies == enemies.number) {//to check if all the enemies in the location are dead
                player.playingChar.collectItem();
                player.playingChar.defeatedEnemies = 0;//needs to be zero again for the next location's enemies
            } else {
                enemies.reSetHealth();//needs to be reset if there are more monsters in the same location
            }
        }
    }
}