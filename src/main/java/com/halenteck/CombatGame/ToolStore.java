import java.util.Scanner;
public class ToolStore extends Location {
    String name;
    public ToolStore(Player player) {
        super(player);
       
    }
    @Override
    public boolean getLocation() {
        return true;
    }

    public void buyArmour(){
        Scanner scan = new Scanner(System.in);
        int type = scan.nextInt();
        if(type >= 1 && type <= 3 && player.armours[type - 1] != null && player.armours[type - 1].price <= player.money){
            player.armours[type - 1].makePayment();
            player.armour = player.armours[type - 1];
        }
    }

    public void buyWeapon(){
        
        if(player.playingChar.weapons[1].isActive == false && player.money >= player.playingChar.weapons[1].price){//player'ı buarada böyle belirtemeyiz. Objesi lazım.
            player.playingChar.weapons[1].isActive = true;
            player.money -= player.playingChar.weapons[1].price;
            player.boughtWeapons.add(player.playingChar.weapons[1]);
        }
    }

    public void buyAbility(){
        if(!player.playingChar.abilityExists){
            player.playingChar.ability = new Ability(player);
            if (player.money >= player.playingChar.ability.price){
                player.playingChar.ability.makePayment();
                player.playingChar.abilityExists = true;
            }
        }
    }   

    public void upgradeAttack(){//bu bir button'a bağlı olacak
        if(player.playingChar.attackPower <= player.playingChar.upgradelimit && player.money >= 4 * (player.playingChar.attackPower + 1)){
            player.playingChar.attackPower++;
            player.money -= 4 * player.playingChar.attackPower;
        }
    }

    public void upgradeDefence(){//bu bir button'a baplı olacak
        if(player.playingChar.defencePower <= player.playingChar.upgradelimit && player.money >= 4 * (player.playingChar.defencePower + 1)){
            player.playingChar.defencePower++;
            player.money -= 4 * player.playingChar.defencePower;
        }
    }
}