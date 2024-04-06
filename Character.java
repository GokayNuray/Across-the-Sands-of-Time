import java.util.ArrayList;

public abstract class Character{

    protected double health;//the health of the character, initialized at subclasses
    protected int x;//the coordinates of the character
    protected int y;
    protected Enemies currentEnemy;//th enemy the player is fighting against
    protected boolean armourExists;// to se if he has an armour
    protected boolean abilityExists;// to see if he has an ability
    protected static int noOfCharacter = 1;//to determine character IDs
    protected int ID;
    protected ArrayList<Item> collect; //collected items are here
    protected Location location;
    protected PlayingPerson frame;//to get acces to menuframe
    protected Armour armour;//armour if bought
    protected double finalDamage;//the damage the weapons will cause
    protected double enemyDamage;//the damage enemy will give after the armour interference
    protected boolean itemsDone;
    protected ArrayList<Enemies> allEnemies;//the enemies the character will face on every location
    protected Item item1;//4 items of the characters, initialized at subclasses
    protected Item item2;
    protected Item item3;
    protected Item item4;
    protected ArrayList<Item> listToBeCollected;//items will be here and put into the collect ArrayList according to killings and location ID
    protected int noOfEnemies;
    protected int defeatedEnemies;//the number of enemies that will appear on each location will differ from 1 to 3.
    protected ArrayList<Enemies> hunters;//all the villains the character will face on 4 locations

    public Character(ArrayList<Enemies> aHunters, PlayingPerson aFrame){

        noOfEnemies = (int)(Math.random()*3)+1; //the number of enemies in location (might need a change of place but not determined where yet)
        defeatedEnemies = 0;
        listToBeCollected = new ArrayList<Item>();
        hunters = aHunters;
        enemyDamage = 0;
        listToBeCollected.add(item1);
        listToBeCollected.add(item2);
        listToBeCollected.add(item3);
        listToBeCollected.add(item4);
        determineEnemy(hunters);
        itemsDone = false;
        finalDamage = 0;
        frame = aFrame;
        ID = noOfCharacter;
        x = 40;//the x coordinate the character will appear on
        y = 80;//the y coordinate the character will appear on
        collect = new ArrayList<Item>(); 
        armourExists = false; 
        abilityExists = false; 
    }

    public void ifLost(){//if the character loses all life points then that characters collect arraylist will be erased and he will 
        //have to play that character's part again

        collect = new ArrayList<Item>();
    }

    public void determineEnemy(ArrayList<Enemies> hunters){//to get the locations enemy

        currentEnemy = hunters.get(location.locationID - 1);
        noOfEnemies = (int)(Math.random()*3)+1; // for the next location
    }

    public void checkItemList(){//to get to the next character
        if(collect.size() == 4){ //called in the 4th location
            itemsDone = true;
            noOfCharacter++;
            ID = noOfCharacter;
            location.locationID = 1;
        }
        else{
            itemsDone = false;
        }
    }

    public void setShortRangeDamage(Weapons shortW){//determines the short range attack the weapon will cause

        finalDamage = 0;

        if (Math.abs(currentEnemy.x - this.x) == 560){
            finalDamage = (shortW.performance - 0.50);
        }
        if (Math.abs(currentEnemy.x - this.x) == 480){
            finalDamage = (shortW.performance - 0.25);
        }
        if (Math.abs(currentEnemy.x - this.x) == 400){
            finalDamage = (shortW.performance);
        }
        if (Math.abs(currentEnemy.x - this.x) == 320){
            finalDamage = (shortW.performance + 0.25);
        }
        if (Math.abs(currentEnemy.x - this.x) == 240){
            finalDamage = (shortW.performance + 0.50);
        }
        else if (Math.abs(currentEnemy.x - this.x) == 160){
            finalDamage = (shortW.performance + 0.75);
        }
    }

    public void setLongRangeDamage(Weapons longW){//determies the long range attack the weapon will cause

        finalDamage = 0;
        
        if (Math.abs(currentEnemy.x - this.x) == 560){
            finalDamage = (longW.performance + 0.75);
        }
        if (Math.abs(currentEnemy.x - this.x) == 480){
            finalDamage = (longW.performance + 0.5);
        }
        if (Math.abs(currentEnemy.x - this.x) == 400){
            finalDamage = (longW.performance + 0.25);
        }
        if (Math.abs(currentEnemy.x - this.x) == 320){
            finalDamage = (longW.performance);
        }
        if (Math.abs(currentEnemy.x - this.x) == 240){
            finalDamage = (longW.performance - 0.25);
        }
        else if (Math.abs(currentEnemy.x - this.x) == 160){
            finalDamage = (longW.performance - 0.25);
        }
    }

    public void checkForMoney(){//when an enemy dies, the player gains money and XP point
        //whenever an enemy is defeated money will be collected (the amount is equal to item's value) but 
        //item will be collected after all the enemies in the location are dead.
        //all the items of a character are worth the same
        if(currentEnemy.isDead){
            frame.money += this.item1.itemValue;
            frame.XP += this.item1.itemValue;
            defeatedEnemies++;
            if (defeatedEnemies == noOfEnemies){//to check if all the enemies in the location are dead
                collectItem();
                checkItemList();
                defeatedEnemies = 0;//needs to be zero again for the next location's enemies
                if(!itemsDone){
                    location.locationID++;
                    determineEnemy(hunters);
                } 
            }
            else{
                currentEnemy.reSetHealth();
            }  
        }
    }

    public void collectItem(){//if all the monsters at the location are defeated then the item will be collected
        collect.add(listToBeCollected.get(location.locationID - 1));
    }
    public void modifyDamageFromEnemy(double damageFromOpponent){//armour's protection decreased the damage

        if(armourExists){//enemy damage is finalized
            if (armour.type == 1){
                enemyDamage = damageFromOpponent - 0.2;
            }
            else if (armour.type == 2){
                enemyDamage = damageFromOpponent - 0.4;
            }
            else if (armour.type == 3){
                enemyDamage = damageFromOpponent - 0.6;
            }
        }
    }

    public void extractLoss(){//the loss is extracted from the health
        if (health <= enemyDamage){
            health = 0; //as there will be healthbar for the character, I didn't want it to be negative
            ifLost();
        }
        else{
            health -= enemyDamage;
        }
        enemyDamage = 0;
    }
}
