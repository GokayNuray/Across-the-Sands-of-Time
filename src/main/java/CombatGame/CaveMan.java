package CombatGame;

public class CaveMan extends Character {

    public CaveMan(Player player) {
        super(player);
        items[0] = false;
        items[1] = false;
        items[2] = false;
        items[3] = false;
        maps[0] = new Forest(player);
        maps[1] = new Cave(player);
        maps[2] = new River(player);
        maps[3] = new HistoryMuseum(player);
    }
}
