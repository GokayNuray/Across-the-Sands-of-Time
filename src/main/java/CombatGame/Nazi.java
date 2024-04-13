package CombatGame;

public class Nazi extends Character {

    public Nazi(Player player) {
        super(player);
        items[0] = false;
        items[1] = false;
        items[2] = false;
        items[3] = false;
        maps[0] = new Camp(player);
        maps[1] = new Berlin(player);
        maps[2] = new Market(player);
        maps[3] = new HistoryMuseum(player);

    }
}
