package com.halenteck.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public final class Server {
    private static final String SERVER_IP = "34.105.208.236";
    private static final int SERVER_PORT = 37923;

    //Client to server commands
    private static final byte LOGIN = 0x00;
    private static final byte REGISTER = 0x01;
    private static final byte UPDATE_USER_DATA = 0x02;
    private static final byte GET_USER_DATA = 0x03;

    private static final byte GET_LEADERBOARD = 0x04;
    private static final byte GET_LOBBY_LIST = 0x10;
    private static final byte JOIN_LOBBY = 0x11;
    private static final byte QUICK_JOIN_LOBBY = 0x12;
    private static final byte CREATE_LOBBY = 0x13;
    private static final byte LEAVE_LOBBY = 0x14;
    private static final byte MOVE = 0x20;
    private static final byte ROTATE = 0x21;
    private static final byte WEAPON_CHANGE = 0x30;
    private static final byte SHOOT = 0x31;
    private static final byte ABILITY = 0x32;
    private static final byte CHAT = 0x40;
    private static final byte DAMAGED = 0x50;
    private static final byte DEATH = 0x51;
    private static final byte RESPAWN = 0x52;

    //Server to client commands
    private static final byte PLAYER_JOIN = 0x00;
    private static final byte PLAYER_LEAVE = 0x01;
    private static final byte PLAYER_MOVE = 0x10;
    private static final byte PLAYER_ROTATE = 0x11;
    private static final byte PLAYER_WEAPON_CHANGE = 0x20;
    private static final byte PLAYER_SHOOT = 0x21;
    private static final byte PLAYER_ABILITY = 0x22;
    private static final byte PLAYER_CHAT = 0x30;
    private static final byte PLAYER_DAMAGED = 0x40;
    private static final byte PLAYER_DEATH = 0x41;
    private static final byte PLAYER_RESPAWN = 0x42;
    private static final byte GAME_OVER = 0x50;


    private static Socket socket;
    private static DataOutputStream out;
    private static DataInputStream in;
    private static ServerListener listener;

    public static UserData getUserData() {
        return userData;
    }

    private static UserData userData;

    private Server() {
    }

    public static void connect() {
        try {
            socket = new Socket(SERVER_IP, SERVER_PORT);
            System.out.println("Connected to server");
            out = new DataOutputStream(socket.getOutputStream());
            in = new DataInputStream(socket.getInputStream());

            out.writeUTF("hello server");
            System.out.println("Response: " + in.readUTF());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void addServerListener(ServerListener listener) {
        Server.listener = listener;
    }

    private static void startServerPacketListenerThread() {
        new Thread(() -> {
            try {
                while (true) {
                    byte command = in.readByte();
                    switch (command) {
                        case PLAYER_JOIN: {
                            byte id = in.readByte();
                            boolean isRedTeam = in.readBoolean();
                            String name = in.readUTF();
                            float x = in.readFloat();
                            float y = in.readFloat();
                            float z = in.readFloat();
                            float yaw = in.readFloat();
                            float pitch = in.readFloat();
                            byte characterId = in.readByte();
                            int weapon = in.readInt();
                            int attackPower = in.readInt();
                            byte kill = in.readByte();
                            byte death = in.readByte();

                            PacketData data = new PacketData(id, isRedTeam, name, new float[]{x, y, z, yaw, pitch}, characterId, attackPower, weapon, kill, death);
                            listener.onPlayerJoin(data);
                            break;
                        }
                        case PLAYER_MOVE: {
                            byte id = in.readByte();
                            float x = in.readFloat();
                            float y = in.readFloat();
                            float z = in.readFloat();

                            PacketData data = new PacketData(id, new float[]{x, y, z});
                            listener.onPlayerMove(data);
                            break;
                        }
                        case PLAYER_ROTATE: {
                            byte id = in.readByte();
                            float yaw = in.readFloat();
                            float pitch = in.readFloat();

                            PacketData data = new PacketData(id, new float[]{yaw, pitch});
                            listener.onPlayerRotate(data);
                            break;
                        }
                        case PLAYER_WEAPON_CHANGE: {
                            byte id = in.readByte();
                            int weapon = in.readInt();
                            int attackPower = in.readInt();

                            PacketData data = new PacketData(id, weapon, attackPower);
                            listener.onPlayerWeaponChange(data);
                            break;
                        }
                        case PLAYER_LEAVE: {
                            byte id = in.readByte();

                            PacketData data = new PacketData(id);
                            listener.onPlayerLeave(data);
                            break;
                        }
                        case PLAYER_CHAT: {
                            byte id = in.readByte();
                            String message = in.readUTF();

                            PacketData data = new PacketData(id, message);
                            listener.onPlayerChat(data);
                            break;
                        }
                        case PLAYER_DAMAGED: {
                            byte id = in.readByte();

                            PacketData data = new PacketData(id);
                            listener.onPlayerDamaged(data);
                            break;
                        }
                        case PLAYER_DEATH: {
                            byte deadId = in.readByte();
                            byte killerId = in.readByte();

                            PacketData data = new PacketData(deadId, killerId);
                            listener.onPlayerDeath(data);
                            break;
                        }
                        case PLAYER_RESPAWN: {
                            byte id = in.readByte();
                            float x = in.readFloat();
                            float y = in.readFloat();
                            float z = in.readFloat();
                            float yaw = in.readFloat();
                            float pitch = in.readFloat();

                            PacketData data = new PacketData(id, new float[]{x, y, z, yaw, pitch});
                            listener.onPlayerRespawn(data);
                            break;
                        }
                        case PLAYER_ABILITY: {
                            byte id = in.readByte();
                            int ability = in.readInt();

                            PacketData data = new PacketData(id, ability);
                            listener.onPlayerAbility(data);
                            break;
                        }
                        case PLAYER_SHOOT: {
                            byte id = in.readByte();

                            PacketData data = new PacketData(id);
                            listener.onPlayerShoot(data);
                            break;
                        }
                        case GAME_OVER: {

                            PacketData data = new PacketData((Object) null);
                            listener.onGameOver(data);
                            break;
                        }

                        default:
                            System.out.println("Unknown command: " + command);
                            socket.close();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }


    public static boolean login(String name, String password) {
        try {
            out.writeByte(LOGIN);
            out.writeUTF(name);
            out.writeUTF(password);
            if (!in.readBoolean()) {
                return false;
            }
            userData = UserData.readUserData(in);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    public static boolean register(String name, String password) {
        try {
            out.writeByte(REGISTER);
            out.writeUTF(name);
            out.writeUTF(password);
            if (!in.readBoolean()) {
                return false;
            }
            return login(name, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void updateUserData() {
        try {
            out.writeByte(UPDATE_USER_DATA);
            UserData.writeUserData(out, userData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static UserData getUserData(String name) {
        try {
            out.writeByte(GET_USER_DATA);
            out.writeUTF(name);
            if (!in.readBoolean()) {
                return null;
            }
            return UserData.readUserData(in);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @return Array of player names and scores(sorted by score)
     * Format: [playerNames(String[]), playerScores(int[])]
     */
    public static Object[] getLeaderboard() {
        try {
            out.writeByte(GET_LEADERBOARD);
            int playerCount = in.readByte();
            String[] playerNames = new String[playerCount];
            byte[] playerLevels = new byte[playerCount];
            int[] playerScores = new int[playerCount];
            for (int i = 0; i < playerCount; i++) {
                playerNames[i] = in.readUTF();
                playerLevels[i] = in.readByte();
                playerScores[i] = in.readInt();
            }
            return new Object[]{playerNames, playerLevels, playerScores};
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new RuntimeException("Failed to get leaderboard");
    }

    /**
     * @return Array of lobbies
     * Lobby format: id(int),name(String),playerCount(int),creationTime(long){currentTimeMillis} all separated by commas
     */
    public static String[] getLobbyList() {
        try {
            out.writeByte(GET_LOBBY_LIST);
            int lobbyCount = in.readByte();
            String[] lobbyNames = new String[lobbyCount];
            for (int i = 0; i < lobbyCount; i++) {
                lobbyNames[i] = in.readUTF();
            }
            return lobbyNames;
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new RuntimeException("Failed to get lobby list");
    }

    public static boolean joinLobby(String playerName, int lobbyId) {
        try {
            out.writeByte(JOIN_LOBBY);
            out.writeInt(lobbyId);
            out.writeUTF(playerName);
            out.writeByte(userData.getLastSelectedCharacter());
            if (!in.readBoolean()) {
                return false;
            }
            String lobbyName = in.readUTF();
            int playerCount = in.readByte();
            System.out.println("Joined lobby: " + lobbyName + " Players: " + playerCount);
            Object[][] playerData = new Object[playerCount][];
            for (int i = 0; i < playerCount; i++) {
                byte id = in.readByte();
                boolean isRedTeam = in.readBoolean();
                String name = in.readUTF();
                float x = in.readFloat();
                float y = in.readFloat();
                float z = in.readFloat();
                float yaw = in.readFloat();
                float pitch = in.readFloat();
                boolean crouching = in.readBoolean();
                int weapon = in.readInt();
                int attackPower = in.readInt();
                byte kill = in.readByte();
                byte death = in.readByte();

                playerData[i] = new Object[]{id, isRedTeam, name, new float[]{x, y, z, yaw, pitch}, crouching, weapon, attackPower, kill, death};
                System.out.println("Player: " + name + " ID: " + id + " Position: (" + x + ", " + y + ", " + z + ") Rotation: (" + yaw + ", " + pitch + ")");
            }
            long creationTime = in.readLong();
            byte thisPlayerId = in.readByte();
            int[] score = new int[]{in.readInt(), in.readInt()};
            listener.onLobbyJoin(new PacketData(lobbyName, playerData, thisPlayerId, score, creationTime));

            startServerPacketListenerThread();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void quickJoinLobby(String playerName) {
        try {
            out.writeByte(QUICK_JOIN_LOBBY);
            int lobbyId = in.readInt();
            joinLobby(playerName, lobbyId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int createLobby(String lobbyName) {
        try {
            if (lobbyName.contains(",")) {
                System.out.println("Lobby name can't contain commas");
                return -1;
            }
            out.writeByte(CREATE_LOBBY);
            out.writeUTF(lobbyName);
            int lobbyId = in.readInt();
            return lobbyId;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static void leaveLobby() {
        try {
            out.writeByte(LEAVE_LOBBY);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void movePlayer(float x, float y, float z) {
        try {
            synchronized ("Server commands") {
                out.writeByte(MOVE);
                out.writeFloat(x);
                out.writeFloat(y);
                out.writeFloat(z);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void rotatePlayer(float yaw, float pitch) {
        try {
            synchronized ("Server commands") {
                out.writeByte(ROTATE);
                out.writeFloat(yaw);
                out.writeFloat(pitch);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void weaponChange(int weapon) {
        try {
            synchronized ("Server commands") {
                out.writeByte(WEAPON_CHANGE);
                out.writeInt(weapon);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void shoot() {
        try {
            synchronized ("Server commands") {
                out.writeByte(SHOOT);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void ability(int ability) {
        try {
            synchronized ("Server commands") {
                out.writeByte(ABILITY);
                out.writeInt(ability);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void chat(String message) {
        try {
            synchronized ("Server commands") {
                out.writeByte(CHAT);
                out.writeUTF(message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void damaged() {
        try {
            synchronized ("Server commands") {
                out.writeByte(DAMAGED);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void death(byte killerId) {
        try {
            synchronized ("Server commands") {
                out.writeByte(DEATH);
                out.writeByte(killerId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void respawn(float x, float y, float z, float yaw, float pitch) {
        try {
            synchronized ("Server commands") {
                out.writeByte(RESPAWN);
                out.writeFloat(x);
                out.writeFloat(y);
                out.writeFloat(z);
                out.writeFloat(yaw);
                out.writeFloat(pitch);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
