package com.halenteck.CombatGame;

public class BossEnemy extends Enemy {

    public BossEnemy(Player player) {
        super(player, "Boss", player.characterID + 3, 7, 18, 3);

    }

}
