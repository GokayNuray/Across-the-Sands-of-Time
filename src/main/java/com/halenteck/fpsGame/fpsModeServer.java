package com.halenteck.fpsGame;

import com.halenteck.server.PacketData;
import com.halenteck.server.ServerListener;
import org.joml.Vector3f;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class fpsModeServer implements ServerListener {
    private Map<Byte, Player> players = new ConcurrentHashMap<>();

    @Override
    public void onLobbyJoin(PacketData packetData) {
        //TODO: Lobby joining implementation.
    }

    @Override
    public void onPlayerJoin(PacketData packetData) {
        //TODO: Player joining implementation.
    }

    @Override
    public void onPlayerMove(PacketData packetData) {
        Byte playerId = (Byte) packetData.getOnPlayerMoveData()[0];
        Vector3f newPosition = new Vector3f((Float)packetData.getOnPlayerMoveData()[1], (Float)packetData.getOnPlayerMoveData()[2], (Float)packetData.getOnPlayerMoveData()[3]);
        Player player = players.get(playerId);

        if (player != null)
        {
            player.setPosition(newPosition);
        }
    }

    @Override
    public void onPlayerRotate(PacketData packetData) {
        //TODO: Player rotation logic.
    }

    @Override
    public void onPlayerCrouchStateChange(PacketData packetData) {
        Byte playerId = (Byte) packetData.getOnPlayerCrouchStateChangeData()[0];
        Player player = players.get(playerId);

        if (player != null)
        {
            if (player.getCrouchState())
            {
                player.stand();
            } else
            {
                player.crouch();
            }
        }
    }

    @Override
    public void onPlayerWeaponChange(PacketData packetData) {
        Byte playerId = (Byte) packetData.getOnPlayerWeaponOnChangeData()[0];
        Player player = players.get(playerId);

        if (player != null)
        {
            player.switchWeapon();
        }
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

        if (player != null)
        {
            player.takeDamage(damage);
        }
    }

    @Override
    public void onPlayerDeath(PacketData packetData) {
        Byte playerId = (Byte) packetData.getOnPlayerDeathData()[0];
        Player player = players.get(playerId);

        if (player != null)
        {
            player.die();
        }
    }

    @Override
    public void onPlayerRespawn(PacketData packetData) {
        Byte playerId = (Byte) packetData.getOnPlayerRespawnData()[0];
        float[] details = (float[]) packetData.getOnPlayerRespawnData()[1];
        Player player = players.get(playerId);

        if (player != null)
        {
            Vector3f respawnPosition = new Vector3f(details[0], details[1], details[2]);
            player.setPosition(respawnPosition);
        }
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

        if (player != null)
        {
            player.shoot(direction);
        }
    }

    @Override
    public void onGameOver(PacketData packetData) {
        //TODO: Game over logic.
    }
}