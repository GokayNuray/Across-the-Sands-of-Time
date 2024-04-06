package Project;

import java.util.Scanner;



public class MenuFrame {
    static Scanner scan = new Scanner(System.in);
    Player player;
    Weapons weapon;
    Location location;

    public void logIn(){
        Scanner scan = new Scanner(System.in);
        System.out.println("Welcome to Across The Sands Of Game");
        System.out.println("Enter your name");
        String playerName = scan.nextLine();
        player = new Player(playerName);
    
    }

    public void start(){
        while(true){
            System.out.println("Please choose a location");
            System.out.println("1-) Combat");
            System.out.println("2-) ToolStore");
            System.out.println("Where you want to go?");
            int loc = scan.nextInt();
            while(loc < 1 || loc > 2){
                System.out.println("Please enter a valid location");
                loc = scan.nextInt();
            }
            if(loc == 1 && Player.characterId == 1){
                
            }
            if(loc == 2){
                System.out.println("You are in toolstore select which weapon do you want");
                int weaponId = scan.nextInt();
                weapon.selectWeapon(weaponId);
            }
        }
    }
    
}
