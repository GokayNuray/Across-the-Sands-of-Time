package com.halenteck.server;

public class PacketData {

    private final Object[] data;

    public PacketData(Object... data) {
        this.data = data;
    }

    /**
     * @return The data that was sent in the packet.
     * @apiNote [0] is lobby name, [1] is an array of players in the lobby.
     * Player data is in the form of {byte id, String playerName, float[5] playerX, playerY, playerZ, playerYaw, playerPitch, boolean playerCrouching, int playerWeapon, byte playerHealth}
     */
    public Object[] getOnLobbyJoinDataData() {
        return data;
    }

    /**
     * @return The data that was sent in the packet.
     * @apiNote [0] is player id, [1] is an array of length 1 containing player data.
     * Player data is in the same form as in the onLobbyJoinData method.
     */
    public Object[] getOnPlayerJoinData() {
        return data;
    }

    /**
     * @return The data that was sent in the packet.
     * @apiNote [0] is player id, [1] is an array of length 5 containing player position and rotation.
     * Player position and rotation data is in the form of {playerX, playerY, playerZ}
     */
    public Object[] getOnPlayerMoveData() {
        return data;
    }

    public Object[] getOnPlayerRotateData() {
        return data;
    }

    public Object[] getOnPlayerCrouchData() {
        return data;
    }

    public Object[] getOnPlayerWeaponOnChangeData() {
        return data;
    }

    public Object[] getOnPlayerLeaveData() {
        return data;
    }

    public Object[] getOnPlayerChatData() {
        return data;
    }

    public Object[] getOnPlayerAttackData() {
        return data;
    }

    public Object[] getOnPlayerDamageData() {
        return data;
    }

    public Object[] getOnPlayerDeathData() {
        return data;
    }

    public Object[] getOnPlayerRespawnData() {
        return data;
    }

    public Object[] getOnPlayerAbilityData() {
        return data;
    }

    public Object[] getOnPlayerShootData() {
        return data;
    }

    public Object[] getOnGameOverData() {
        return data;
    }

}
