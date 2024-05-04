package com.halenteck.server;

public interface ServerListener {

    void onLobbyJoin(PacketData packetData);

    void onPlayerJoin(PacketData packetData);

    void onPlayerMove(PacketData packetData);

    void onPlayerRotate(PacketData packetData);

    void onPlayerCrouchStateChange(PacketData packetData);

    void onPlayerWeaponChange(PacketData packetData);

    void onPlayerLeave(PacketData packetData);

    void onPlayerChat(PacketData packetData);

    void onPlayerDamaged(PacketData packetData);

    void onPlayerDeath(PacketData packetData);

    void onPlayerRespawn(PacketData packetData);

    void onPlayerAbility(PacketData packetData);

    void onPlayerShoot(PacketData packetData);

    void onGameOver(PacketData packetData);
}