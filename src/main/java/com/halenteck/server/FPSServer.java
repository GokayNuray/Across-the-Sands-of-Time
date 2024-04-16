package com.halenteck.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public final class FPSServer {
    private static final String SERVER_IP = "34.147.226.117";
    private static final int SERVER_PORT = 37923;

    private static final byte JOIN_LOBBY = 1;

    private static Socket socket;
    private static DataOutputStream out;
    private static DataInputStream in;

    private FPSServer() {
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
            for (int i = 0; i < playerCount; i++) {
                byte id = in.readByte();
                float x = in.readFloat();
                float y = in.readFloat();
                float z = in.readFloat();
                float yaw = in.readFloat();
                float pitch = in.readFloat();
                String name = in.readUTF();
                System.out.println("Player: " + name + " ID: " + id + " Position: (" + x + ", " + y + ", " + z + ") Rotation: (" + yaw + ", " + pitch + ")");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
