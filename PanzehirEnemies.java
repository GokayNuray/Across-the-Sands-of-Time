import java.util.Scanner;

public class PanzehirEnemies extends Enemy {
    public PanzehirEnemies(String name, double damage, int award, double health, int maxNumber) {
        super(name, damage, award, health, maxNumber);
        //TODO Auto-generated constructor stub
    }

    public static Scanner scan = new Scanner(System.in);
   
    public void selectCombatLocation(){
        System.out.println("Select the combat location(1-) Tent   2-) Tunnel  3-) Lab )");
         int locationId = scan.nextInt();
        switch(locationId){
            case 1: 
                locationName = "Tent";
                name = "Zombie";
                damage = 3;
                health = 15;
                number = (int)(Math.random() * (maxNumber + 1));
                break;
            
            case 2:
                locationName = "Tunnel";
                name = "Wolfman";
                damage = 3;
                health = 13;
                number = (int)(Math.random() * (maxNumber + 1));
                break;

            case 3:
                locationName = "Lab";
                name = "Security";
                damage = 3;
                health = 13;
                number = (int)(Math.random() * (maxNumber + 1));
                break;

            default:
                locationName = "Tent";
                name = "Zombie";
                damage = 3;
                health = 15;
                number = (int)(Math.random() * (maxNumber + 1));
                break;
        }
    }
}
