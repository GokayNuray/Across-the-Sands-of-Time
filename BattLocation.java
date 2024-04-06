import java.util.Scanner;

public class BattLocation extends Location {
    protected Enemy enemies;
    protected String award;

    public BattLocation(Player player, String name, Enemy enemies, String award) {
        super(player);
        this.name = name;
        this.enemies = enemies;
        this.award = award;
    }
    
    public boolean getBLocation(){
        int enemyCount = enemies.enemyCount();
        System.out.println("You are in here now: " + this.name);
        System.out.println("Pay attention! you can encounter with " + enemyCount + " " + enemies.name + " here");
        combat(enemyCount);

    }

    public boolean combat(int enemyCount){
        Scanner scan = new Scanner(System.in);
        for(int i = 0; i < enemyCount; i++){
            double defEnemyHealth = enemies.health;
            System.out.println("1-) Forward   2-) Backward   3-) Attack Short Range  4-) Attack Long Range");
            int process = scan.nextInt();
            if(process == 1){
                player.x += 10;
            }
            else if(process == 2){
                player.x -= 10;
            }
            else if(process == 3){
                
            }
        }
    }
}
