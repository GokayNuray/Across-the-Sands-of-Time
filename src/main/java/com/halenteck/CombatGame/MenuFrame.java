package com.halenteck.CombatGame;

import java.util.Scanner;

public class MenuFrame {
    static Scanner scan = new Scanner(System.in);
    protected Player player;
    protected Weapons weapon;
    protected Location location;
    protected Location toolStoreDef;
    protected boolean play;

    public void logIn() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Welcome to Across The Sands Of Game");
        System.out.println("Enter your name");
        String playerName = scan.nextLine();
        player = new Player(playerName);
        toolStoreDef = new ToolStore(player);
        start();
    }

    public void start() {
        play = true;//give up button'ı için
        while (player.characterID <= 5 && play) {
            player.createCharacter();//burada bir sıkıntı var, objeyi yaratıyor ama nedense obje null görünüyor.
            boolean win = true;
            while (player.playingChar.locationID < 4 && win) {
                System.out.println("Please choose a location");
                System.out.println("1-) Combat");
                System.out.println("2-) ToolStore");
                System.out.println("Where you want to go?");
                int loc = scan.nextInt();
                while (loc < 1 || loc > 2) {
                    System.out.println("Please enter a valid location");
                    loc = scan.nextInt();
                }
                if (loc == 1) {
                    location = player.playingChar.maps[player.playingChar.locationID++];
                    if (location.getLocation()) {
                        win = true;
                    } else {
                        win = false;
                    }
                } else {
                    location = toolStoreDef;
                    ((ToolStore) location).buyWeapon();
                    ((ToolStore) location).buyArmour();
                    ((ToolStore) location).buyAbility();
                    ((ToolStore) location).upgradeAttack();
                    ((ToolStore) location).upgradeDefence();
                    location = player.playingChar.maps[player.playingChar.locationID++];
                    if (location.getLocation()) {
                        win = true;
                    } else {
                        win = false;
                    }
                }
            }
            player.checkItemList(); //eğer tüm item'lar toplandıysa karakter ID'yi artırmak için
        }
    }

    public void GiveUp() {// give up button'ı için ve hareket button'larını buraya almak gerekebilir!!!!(hareket methodlarını)
        //give up durumunda o karakteri baştan oynuyor

        player.checkXP();
        player.playingChar.ifLostGiveUp();//burada item'lar ve karakter progress sıfırlanıyor ama start() çağırdığımızda karakter  yeniden yaratılıp başlayacağı için gerek olmayabilir.
        play = false;
    }

    public void again() {
        System.out.print("Do you want to play again? Enter yes or no"); //buttonlı olacak
        String answer = scan.nextLine();
        if (answer.equals("yes")) {
            start();
        }
    }
}