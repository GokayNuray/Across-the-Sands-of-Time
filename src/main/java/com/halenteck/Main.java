package com.halenteck;

import com.halenteck.commonUI.LogInFrame;
import com.halenteck.server.Server;

public class Main {
    public static void main(String[] args) {
        Server.connect();
        LogInFrame loginFrame = new LogInFrame();
    }
}
