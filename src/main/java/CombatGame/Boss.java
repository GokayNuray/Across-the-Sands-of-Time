package CombatGame;

public class Boss extends Character {

    public Boss(Player player) {

        super(player);
        items[0] = false;
        items[1] = false;
        items[2] = false;
        items[3] = false;
        maps[0] = new Office(player);
        maps[1] = new RepairShop(player);
        maps[2] = new Library(player);
        maps[3] = new HistoryMuseum(player);

    }
}
