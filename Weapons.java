package Project;

import java.util.ArrayList;
import java.util.Scanner;


public class Weapons extends ToolStore {
    protected int price; 
    protected double damage;
    protected String name;
    protected int id;
    protected boolean bought;
    protected boolean isActive;
    protected Player player;
    protected ArrayList<Weapons> shortRangeWeapons = new ArrayList<Weapons>();
    
    
    public Weapons(int price, double damage, int id, Player player){
        super(player);
        this.price = price;
        this.damage = damage;
        this.id = id;
    }
    static Scanner scan = new Scanner(System.in);
    
    public void selectWeapon(int id){
       
        System.out.println("Enter a weapon id between 1 and 10 (10 for exit)");
        id = scan.nextInt();
        int damage = 0; 
        int price = 0;
        String weaponName = null;
        
        // Kategorilere göre silahları ayırmak için kategori seçimi
        System.out.println("Select a category: (1) Short Range (2) Long Range");
        int categoryChoice = scan.nextInt();
        
        if(categoryChoice == 1) {
            if(id >= 1 && id <= 5) {
                switch(id) {
                    case 1:
                        weaponName = "Spear";
                        damage = 2;
                        price = 0;
                        isActive = true;
                        break;
                    case 2:
                        weaponName = "Sword";
                        damage = 4;
                        price = 9;
                        break;
                    // Diğer silahlar için aynı şekilde devam eder
                    case 3:
                        weaponName = "Knife";
                        damage = 5;
                        price = 13;
                        break;
                    case 4:
                        weaponName = "Fireball";
                        damage = 6;
                        price = 17;
                        break;
                    case 5:
                        weaponName = "Lazer";
                        damage = 7;
                        price = 21;
                        break;
                    default:
                        // Eğer id geçerliyse ama silah bulunamazsa, varsayılan bir silah belirlenir
                        weaponName = "Basic Short Range Weapon";
                        damage = 2;
                        price = 0;
                        bought = true;
                        isActive = true;
                }
            }
            else {
                System.out.println("Invalid id number");
                id = scan.nextInt();
            }
        }
        else if(categoryChoice == 2) {
            if(id >= 6 && id <= 10) {
                switch(id) {
                    case 6:
                        weaponName = "Arrow";
                        damage = 3;
                        price = 8;
                        break;
                    case 7:
                        weaponName = "Ax";
                        damage = 5;
                        price = 12;
                        break;
                    // Diğer silahlar için aynı şekilde devam eder
                    case 8:
                        weaponName = "Gun";
                        damage = 6;
                        price = 16;
                        break;
                    case 9:
                        weaponName = "Iceball";
                        damage = 7;
                        price = 20;
                        break;
                    case 10:
                        weaponName = "Radioactive";
                        damage = 8;
                        price = 24;
                        break;    
                    default:
                        // Eğer id geçerliyse ama silah bulunamazsa, varsayılan bir silah belirlenir
                        weaponName = "";
                        damage = 2;
                        price = 0;
                        bought = true;
                        isActive = true;
                }
            }
            else {
                System.out.println("Invalid id number");
                id = scan.nextInt();
            }
        }
        else {
            System.out.println("Invalid category choice");
            return;
        }

        if(Player.money >= price){
            this.name = weaponName;
            this.damage = damage;
            Player.money = Player.money - price;
            
            
        }
        else{
            System.out.println("Insufficent balance");
        }
    }

    public double getDamage() {
        return this.damage;
    }
    
}

