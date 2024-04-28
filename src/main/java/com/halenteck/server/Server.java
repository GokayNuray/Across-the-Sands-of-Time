package com.halenteck.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public final class Server {
    private static final String SERVER_IP = "34.105.208.236";
    private static final int SERVER_PORT = 37923;

    private static final byte LOGIN = 0x00;
    private static final byte REGISTER = -0x01;
    private static final byte UPDATE_USER_DATA = -0x02;
    private static final byte JOIN_LOBBY = 0x01;
    private static final byte ADD_PLAYER = 0x02;
    private static final byte MOVE_PLAYER = 0x10;

    private static Socket socket;
    private static DataOutputStream out;
    private static DataInputStream in;
    private static ServerListener listener;
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

    public static void joinLobby(String playerName) {
        try {
            out.writeByte(JOIN_LOBBY);
            out.writeUTF(playerName);
            int lobbyName = in.readInt();
            int playerCount = in.readByte();
            System.out.println("Joined lobby: " + lobbyName + " Players: " + playerCount);
            Object[][] playerData = new Object[playerCount][];
            for (int i = 0; i < playerCount; i++) {
                String name = in.readUTF();
                byte id = in.readByte();
                float x = in.readFloat();
                float y = in.readFloat();
                float z = in.readFloat();
                float yaw = in.readFloat();
                float pitch = in.readFloat();
                boolean crouching = in.readBoolean();
                int weapon = in.readInt();
                byte health = in.readByte();

                playerData[i] = new Object[]{id, name, new float[]{x, y, z, yaw, pitch}, crouching, weapon, health};
                System.out.println("Player: " + name + " ID: " + id + " Position: (" + x + ", " + y + ", " + z + ") Rotation: (" + yaw + ", " + pitch + ")");
            }
            listener.onLobbyJoin(new PacketData(lobbyName, playerData));

            startServerPacketListenerThread();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void movePlayer(float x, float y, float z) {
        try {
            out.writeByte(MOVE_PLAYER);
            out.writeFloat(x);
            out.writeFloat(y);
            out.writeFloat(z);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
            return in.readBoolean();
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

    public static String[] getLobbyList() {
        throw new UnsupportedOperationException("Not supported yet.");
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
                        case MOVE_PLAYER: {
                            byte id = in.readByte();
                            float x = in.readFloat();
                            float y = in.readFloat();
                            float z = in.readFloat();

                            PacketData data = new PacketData(id, new float[]{x, y, z});
                            listener.onPlayerMove(data);
                            break;
                        }
                        case ADD_PLAYER: {
                            String name = in.readUTF();
                            byte id = in.readByte();
                            float x = in.readFloat();
                            float y = in.readFloat();
                            float z = in.readFloat();
                            float yaw = in.readFloat();
                            float pitch = in.readFloat();
                            boolean crouching = in.readBoolean();
                            int weapon = in.readInt();
                            byte health = in.readByte();

                            PacketData data = new PacketData(id, name, new float[]{x, y, z, yaw, pitch}, crouching, weapon, health);
                            listener.onPlayerJoin(data);
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
}
