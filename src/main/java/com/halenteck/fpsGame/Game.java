package com.halenteck.fpsGame;

import com.halenteck.server.PacketData;
import com.halenteck.server.ServerListener;
import org.joml.Vector3f;

import java.util.HashMap;
import java.util.Map;

public class Game implements ServerListener {
    private Team redTeam;
    private Team blueTeam;
    private boolean isRunning;
    private Map<Byte, Player> players;

    public Game() {
        this.players = new HashMap<>();
        this.redTeam = new Team();
        this.blueTeam = new Team();
        this.isRunning = true;
    }

    public void loop() {
        while (isRunning)
        {
            //TODO: Create a game loop.
        }
    }

    public void addPlayer(Player player) {
        if (players.size() < 10) {

            if(redTeam.getTeamSize() == blueTeam.getTeamSize() && redTeam.getTeamSize() < 5)
            {
                redTeam.addPlayer(player);
                player.setTeam(redTeam);
            }
            if (redTeam.getTeamSize() < blueTeam.getTeamSize())
            {
                redTeam.addPlayer(player);
                player.setTeam(redTeam);
            }
            if(blueTeam.getTeamSize() < redTeam.getTeamSize())
            {
                blueTeam.addPlayer(player);
                player.setTeam(blueTeam);
            }

            if(player.getTeam() == blueTeam) {
                blueTeam.addPlayer(player);
            } else {
                redTeam.addPlayer(player);
            }

            System.out.println(player.getName() + " has joined the game.");
        }
    }

    public void announceDeath(Player killed, Player killer) {
        System.out.println(killed.getName() + " has been killed by " + killer.getName());
        killer.incrementKills();
        killed.incrementDeaths();

        if (killer.getTeam() == blueTeam) {
            blueTeam.incrementScore();
        }
        else
        {
            redTeam.incrementScore();
        }
    }

    @Override
    public void onLobbyJoin(PacketData packetData) {
        //TODO: Lobby joining implementation.
    }

    @Override
    public void onPlayerJoin(PacketData packetData) {
        Object[] data = packetData.getOnPlayerJoinData();
        Byte playerId = (Byte) data[0];
        boolean isRedTeam = (Boolean) data[1];
        String name = (String) data[2];

        float[] posAndRot = (float[]) data[3];
        Vector3f startPosition = new Vector3f(posAndRot[0], posAndRot[1], posAndRot[2]);
        boolean crouching = (Boolean) data[4];
        int weaponId = (Integer) data[5];
        byte kill = (Byte) data[6];
        byte death = (Byte) data[7];


        Player newPlayer = new Player(name, 100, null, null, startPosition);
        players.put(playerId, newPlayer);
    }

    @Override
    public void onPlayerMove(PacketData packetData) {
        Byte playerId = (Byte) packetData.getOnPlayerMoveData()[0];
        Vector3f newPosition = new Vector3f((Float)packetData.getOnPlayerMoveData()[1], (Float)packetData.getOnPlayerMoveData()[2], (Float)packetData.getOnPlayerMoveData()[3]);
        Player player = players.get(playerId);

        if (player == null)
        {
            throw new IllegalArgumentException("Incorrect player ID in onPlayerMove packet");
        }

        player.setPosition(newPosition);
    }

    @Override
    public void onPlayerRotate(PacketData packetData) {

    }

    @Override
    public void onPlayerCrouchStateChange(PacketData packetData) {
        Byte playerId = (Byte) packetData.getOnPlayerCrouchStateChangeData()[0];
        Player player = players.get(playerId);

        if (player == null)
        {
            throw new IllegalArgumentException("Incorrect player ID in onPlayerCrouchStateChange packet");
        }

        if (player.getCrouchState())
        {
            player.stand();
        }
        else
        {
            player.crouch();
        }
    }

    @Override
    public void onPlayerWeaponChange(PacketData packetData) {
        Byte playerId = (Byte) packetData.getOnPlayerWeaponOnChangeData()[0];
        Player player = players.get(playerId);

        if (player == null)
        {
            throw new IllegalArgumentException("Incorrect player ID in onPlayerWeaponChange packet");
        }

        player.switchWeapon();
    }

    @Override
    public void onPlayerLeave(PacketData packetData) {
        Byte playerId = (Byte) packetData.getOnPlayerLeaveData()[0];
        players.remove(playerId);
    }

    @Override
    public void onPlayerChat(PacketData packetData) {
        Byte playerId = (Byte) packetData.getOnPlayerChatData()[0];
        String message = (String) packetData.getOnPlayerChatData()[1];
        System.out.println(playerId + ": " + message);
    }

    @Override
    public void onPlayerDamaged(PacketData packetData) {
        Byte playerId = (Byte) packetData.getOnPlayerDamagedData()[0];
        int damage = (Integer) packetData.getOnPlayerDamagedData()[1];
        Player player = players.get(playerId);

        if (player == null)
        {
            throw new IllegalArgumentException("Incorrect player ID in onPlayerDamaged packet");
        }

        player.takeDamage(damage);
    }

    @Override
    public void onPlayerDeath(PacketData packetData) {
        Byte playerId = (Byte) packetData.getOnPlayerDeathData()[0];
        Player player = players.get(playerId);

        if (player == null)
        {
            throw new IllegalArgumentException("Incorrect player ID in onPlayerDeath packet");
        }

        player.die();
    }

    @Override
    public void onPlayerRespawn(PacketData packetData) {
        Byte playerId = (Byte) packetData.getOnPlayerRespawnData()[0];
        float[] details = (float[]) packetData.getOnPlayerRespawnData()[1];
        Player player = players.get(playerId);

        if (player == null)
        {
            throw new IllegalArgumentException("Incorrect player ID in onPlayerRespawn packet");
        }

        Vector3f respawnPosition = new Vector3f(details[0], details[1], details[2]);
        player.setPosition(respawnPosition);
    }

    @Override
    public void onPlayerAbility(PacketData packetData) {
        //TODO: Player ability usage implementation.
    }

    @Override
    public void onPlayerShoot(PacketData packetData) {
        Byte playerId = (Byte) packetData.getOnPlayerShootData()[0];
        Vector3f direction = (Vector3f) packetData.getOnPlayerShootData()[1];
        Player player = players.get(playerId);

        if (player == null)
        {
            throw new IllegalArgumentException("Incorrect player ID in onPlayerShoot packet");
        }

        player.shoot(direction);
    }

    @Override
    public void onGameOver(PacketData packetData) {

    }
}
