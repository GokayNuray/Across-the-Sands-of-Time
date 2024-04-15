package com.halenteck.server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public final class FPSServer {
    private static final String SERVER_IP = "34.142.56.99";
    private static final int SERVER_PORT = 37923;

    private static Socket socket;
    private static PrintWriter out;
    private static BufferedReader in;

    private FPSServer() {
    }

    public static void connect() {
        try {
            socket = new Socket(SERVER_IP, SERVER_PORT);
            System.out.println("Connected to server");
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            out.println("hello server");
            System.out.println("Response: " + in.readLine());
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
