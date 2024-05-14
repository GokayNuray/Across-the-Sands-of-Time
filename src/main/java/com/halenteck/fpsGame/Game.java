package com.halenteck.fpsGame;

import com.halenteck.fpsUI.FpsInGame;
import com.halenteck.render.Models;
import com.halenteck.render.OpenGLComponent;
import com.halenteck.render.World;
import com.halenteck.server.PacketData;
import com.halenteck.server.Server;
import com.halenteck.server.ServerListener;
import org.joml.Vector3f;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

public class Game implements ServerListener {
    private boolean isRunning;

    private Team redTeam;
    private Team blueTeam;
    private Map<Byte, Player> players;
    private Player thisPlayer;

    private World world;
    private OpenGLComponent renderer;
    private JLabel debugger;

    public Game(int lobbyId, FpsInGame fpsInGame) {
        Server.addServerListener(this);
        this.players = new HashMap<>();
        this.redTeam = new Team();
        this.blueTeam = new Team();
        this.isRunning = true;
        this.world = new World(Models.WORLD_MAP1, "test/testworld2/BlockData.txt");
        this.renderer = fpsInGame.getRenderer();
        this.debugger = fpsInGame.getDebugLabel();

        Server.addServerListener(this);
        if (!Server.joinLobby(Server.getUserData().getPlayerName(), lobbyId)) {
            throw new IllegalArgumentException("Lobby is Full.");
        }

        thisPlayer.attachRenderer(renderer);
        thisPlayer.setDebugConsole(debugger);//TODO dont forget to remove this
        thisPlayer.createWeapons(thisPlayer.getWeaponId());
        renderer.addEntity(world.getModel());
        thisPlayer.startMovementThread();
    }

    public void loop() {
        while (isRunning) {
            //TODO: Create a game loop.
        }
    }

    public void addPlayer(Player player) {
        if (players.size() < 10) {

            if (redTeam.getTeamSize() == blueTeam.getTeamSize() && redTeam.getTeamSize() < 5) {
                redTeam.addPlayer(player);
                player.setTeam(redTeam);
            }
            if (redTeam.getTeamSize() < blueTeam.getTeamSize()) {
                redTeam.addPlayer(player);
                player.setTeam(redTeam);
            }
            if (blueTeam.getTeamSize() < redTeam.getTeamSize()) {
                blueTeam.addPlayer(player);
                player.setTeam(blueTeam);
            }

            if (player.getTeam() == blueTeam) {
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
        } else {
            redTeam.incrementScore();
        }
    }

    @Override
    public void onLobbyJoin(PacketData packetData) {
        Object[] data = packetData.getOnLobbyJoinDataData();
        String lobbyName = (String) data[0];
        Object[][] players = (Object[][]) data[1];
        byte thisPlayerId = (byte) data[2];
        int[] currentScore = (int[]) data[3];
        long gameStartTime = (Long) data[4];

        joinPlayer(players);

        thisPlayer = this.players.get(thisPlayerId);
    }

    @Override
    public void onPlayerJoin(PacketData packetData) {
        Object[] data = packetData.getOnPlayerJoinData();
        joinPlayer(new Object[][]{data});
    }

    public void joinPlayer(Object[][] data) {
        for (Object[] playerData : data) {
            Byte playerId = (Byte) playerData[0];
            boolean isRedTeam = (Boolean) playerData[1];
            String name = (String) playerData[2];

            float[] posAndRot = (float[]) playerData[3];
            Vector3f startPosition = new Vector3f(posAndRot[0], posAndRot[1], posAndRot[2]);
            float yaw = posAndRot[3];
            float pitch = posAndRot[4];
            boolean crouching = (Boolean) playerData[4];
            int weaponId = (Integer) playerData[5];
            int attackPower = (Integer) playerData[6];
            byte kill = (Byte) playerData[7];
            byte death = (Byte) playerData[8];

            //TODO: characterId is temporarily set to 0
            Player newPlayer = new Player(playerId, isRedTeam, name, startPosition, yaw, pitch, crouching, weaponId, attackPower, kill, death, (byte) 0, world);
            players.put(playerId, newPlayer);

            renderer.addEntity(newPlayer.getEntity());
        }
    }

    @Override
    public void onPlayerMove(PacketData packetData) {
        Byte playerId = (Byte) packetData.getOnPlayerMoveData()[0];
        float[] posArray = (float[]) packetData.getOnPlayerMoveData()[1];
        Vector3f dPosition = new Vector3f(posArray[0], posArray[1], posArray[2]);
        Player player = players.get(playerId);

        if (player == null) {
            throw new IllegalArgumentException("Incorrect player ID in onPlayerMove packet");
        }

        player.move(dPosition);
    }

    @Override
    public void onPlayerRotate(PacketData packetData) {
        Byte playerId = (Byte) packetData.getOnPlayerRotateData()[0];
        Player player = players.get(playerId);

        if (player == null) {
            throw new IllegalArgumentException("Incorrect player ID in onPlayerWeaponChange packet");
        }

        float[] rotateData = (float[]) packetData.getOnPlayerRotateData()[1];
        player.rotate(rotateData[0], rotateData[1]);
    }

    @Override
    public void onPlayerCrouchStateChange(PacketData packetData) {
        Byte playerId = (Byte) packetData.getOnPlayerCrouchStateChangeData()[0];
        Player player = players.get(playerId);

        if (player == null) {
            throw new IllegalArgumentException("Incorrect player ID in onPlayerCrouchStateChange packet");
        }

        if (player.getCrouchState()) {
            player.stand();
        } else {
            player.crouch();
        }
    }

    @Override
    public void onPlayerWeaponChange(PacketData packetData) {
        Byte playerId = (Byte) packetData.getOnPlayerWeaponOnChangeData()[0];
        Player player = players.get(playerId);

        if (player == null) {
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

        if (player == null) {
            throw new IllegalArgumentException("Incorrect player ID in onPlayerDamaged packet");
        }

        player.takeDamage(damage);
    }

    @Override
    public void onPlayerDeath(PacketData packetData) {
        Byte playerId = (Byte) packetData.getOnPlayerDeathData()[0];
        Player player = players.get(playerId);

        if (player == null) {
            throw new IllegalArgumentException("Incorrect player ID in onPlayerDeath packet");
        }

        player.die();
    }

    @Override
    public void onPlayerRespawn(PacketData packetData) {
        Byte playerId = (Byte) packetData.getOnPlayerRespawnData()[0];
        float[] details = (float[]) packetData.getOnPlayerRespawnData()[1];
        Player player = players.get(playerId);

        if (player == null) {
            throw new IllegalArgumentException("Incorrect player ID in onPlayerRespawn packet");
        }

        Vector3f respawnPosition = new Vector3f(details[0], details[1], details[2]);
        player.setPosition(respawnPosition);
    }

    //TODO: Ability is commented.
    @Override
    public void onPlayerAbility(PacketData packetData) {
        Byte playerId = (Byte) packetData.getOnPlayerAbilityData()[0];
        Player player = players.get(playerId);
    }

    @Override
    public void onPlayerShoot(PacketData packetData) {
        Byte playerId = (Byte) packetData.getOnPlayerShootData()[0];
        Player player = players.get(playerId);

        if (player == null) {
            throw new IllegalArgumentException("Incorrect player ID in onPlayerShoot packet");
        }

        player.shoot();
        Bullet bullet = player.spawnBullet();
        thisPlayer.handleBullet(bullet);
    }

    @Override
    public void onGameOver(PacketData packetData) {
        //TODO: Game over implementation.
    }
}
