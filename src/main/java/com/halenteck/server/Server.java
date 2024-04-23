package com.halenteck.server;

import com.halenteck.render.Entity;
import com.halenteck.render.Models;
import com.halenteck.render.OpenGLComponent;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

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

    private static final Map<Byte, Entity> players = new HashMap<>(10);

    private static OpenGLComponent renderer;

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
            for (int i = 0; i < playerCount; i++) {
                byte id = in.readByte();
                float x = in.readFloat();
                float y = in.readFloat();
                float z = in.readFloat();
                float yaw = in.readFloat();
                float pitch = in.readFloat();
                String name = in.readUTF();

                Entity player = new Entity(Models.TEST2, x, y, z, yaw, pitch, 0.5f);
                players.put(id, player);
                renderer.addEntity(player);
                System.out.println("Player: " + name + " ID: " + id + " Position: (" + x + ", " + y + ", " + z + ") Rotation: (" + yaw + ", " + pitch + ")");
            }

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
                                float yaw = in.readFloat();
                                float pitch = in.readFloat();
                                Entity player = players.get(id);
                                player.move(x, y, z);
                                player.setRotation(yaw, pitch);
                                //System.out.println("Player " + player.getName() + " moved to (" + x + ", " + y + ", " + z + ") Rotation: (" + yaw + ", " + pitch + ")");
                                break;
                            }
                            case ADD_PLAYER: {
                                byte id = in.readByte();
                                float x = in.readFloat();
                                float y = in.readFloat();
                                float z = in.readFloat();
                                float yaw = in.readFloat();
                                float pitch = in.readFloat();
                                String name = in.readUTF();
                                Entity player = new Entity(Models.TEST2, x, y, z, yaw, pitch, 0.5f);
                                players.put(id, player);
                                renderer.addEntity(player);
                                System.out.println("Player Added: " + name + " ID: " + id + " Position: (" + x + ", " + y + ", " + z + ") Rotation: (" + yaw + ", " + pitch + ")");
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void movePlayer(float x, float y, float z, float yaw, float pitch) {
        try {
            out.writeByte(MOVE_PLAYER);
            out.writeFloat(x);
            out.writeFloat(y);
            out.writeFloat(z);
            out.writeFloat(yaw);
            out.writeFloat(pitch);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void attachRenderer(OpenGLComponent renderer) {
        Server.renderer = renderer;
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


}
