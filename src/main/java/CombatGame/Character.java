package CombatGame;

public abstract class Character {

    protected double health;//the health of the character, initialized at subclasses
    protected boolean abilityExists;// to see if he has an ability
    protected int locationID;//all characters have four locations so ID differs from 1 to 4.
    protected Player player;
    //protected double enemyDamage;//the damage enemy will cause to the character after the armour interference and if exists ability interference
    protected boolean itemsDone;//to check if all the items have been collected
    protected int defeatedEnemies;//the number of enemies that will appear on each location will differ from 1 to 3.
    //to see how many of them have been defeated
    protected int characterProgress;
    protected Ability ability;
    protected boolean isAbilityActive;
    protected int attackPower;
    protected int defencePower;
    protected int upgradelimit;
    protected boolean[] items = new boolean[4];
    protected Weapons[] weapons = new Weapons[2];//index 0'da short range, index 1'de long range var
    protected Location[] maps = new Location[4];
    protected double shortRangeDamage;
    protected double longRangeDamage;


    public Character(Player aPlayer) {

        upgradelimit = 3;
        attackPower = 0;
        defencePower = 0;
        defeatedEnemies = 0;
        characterProgress = 0;
        isAbilityActive = false;
        locationID = 0;//her yeni karakter oluşturulduğunda bu 0 olmalı
        itemsDone = false;//may not be needed
        this.player = aPlayer;
        abilityExists = false;
        createWeapons();
    }

    public void createWeapons() {
        weapons[0] = new Weapons(player);
        weapons[1] = new Weapons(player);
        shortRangeDamage = weapons[0].performance;
        longRangeDamage = weapons[1].performance;
    }

    public void ifLostGiveUp() {//if the character loses all life points then that characters's all item booleans will be made false
        //have to play that character's parts again
        for (int a = 0; a < 4; a++) {
            items[a] = false;
        }
        characterProgress = 0;
    }

    public void collectItem() {//when all the enemies at a location are killed, the item's collected
        items[locationID - 1] = true;
        increaseProgress();
    }

    public void increaseProgress() {
        if (items[3]) {
            characterProgress += 40;
        } else if (items[2]) {
            characterProgress += 25;
        } else if (items[1]) {
            characterProgress += 20;
        } else if (items[0]) {
            characterProgress += 15;
        }
    }
}
