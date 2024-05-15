package com.halenteck.server;

public class PacketData {

    private final Object[] data;

    public PacketData(Object... data) {
        this.data = data;
    }

    /**
     * @apiNote [0] is lobby name(String), [1] is an array of players in the lobby(Object[]), [2] is thisPlayerId(byte), [3] is the current score(int[]) {red, blue}, [4] is the game start time in milliseconds(long).
     * Player data is in the form of {byte id, boolean isRedTeam, String name, float[5] x, y, z, yaw, pitch, boolean crouching, int weapon, int attackPower, byte kill, byte death}.
     */
    public Object[] getOnLobbyJoinDataData() {
        return data;
    }

    /**
     * @apiNote [0] is player data.
     * Player data is in the same form as in the onLobbyJoinData method.
     */
    public Object[] getOnPlayerJoinData() {
        return data;
    }

    /**
     * @apiNote [0] is player id(byte), [1] is an array containing player position and rotation(float[]) {x, y, z}.
     */
    public Object[] getOnPlayerMoveData() {
        return data;
    }

    /**
     * @apiNote [0] is player id(byte), [1] is an array containing player yaw and pitch(float[]) {yaw, pitch}.
     */
    public Object[] getOnPlayerRotateData() {
        return data;
    }

    /**
     * @apiNote [0] is player id(byte), [1] is weapon id(int), [2] is attack power(int).
     */
    public Object[] getOnPlayerWeaponOnChangeData() {
        return data;
    }

    /**
     * @apiNote [0] is player id(byte).
     */
    public Object[] getOnPlayerLeaveData() {
        return data;
    }

    /**
     * @apiNote [0] is player id(byte), [1] is player message(String).
     */
    public Object[] getOnPlayerChatData() {
        return data;
    }

    /**
     * @apiNote [0] is player id(byte).
     */
    public Object[] getOnPlayerDamagedData() {
        return data;
    }

    /**
     * @apiNote [0] is id of dead player(byte), [1] is id of killer(byte).
     */
    public Object[] getOnPlayerDeathData() {
        return data;
    }

    /**
     * @apiNote [0] is player id(byte), [1] is an array containing position and rotation(float[]) {x, y, z, yaw, pitch}.
     */
    public Object[] getOnPlayerRespawnData() {
        return data;
    }

    /**
     * @apiNote [0] is player id(byte), [1] is ability id(int).
     */
    public Object[] getOnPlayerAbilityData() {
        return data;
    }

    /**
     * @apiNote [0] is player id(byte).
     */
    public Object[] getOnPlayerShootData() {
        return data;
    }

    /**
     * @apiNote [0] is the change in rankPoints(int)
     */
    public Object[] getOnGameOverData() {
        return data;
    }

}
