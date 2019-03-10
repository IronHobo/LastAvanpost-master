package com.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.net.ServerSocket;
import com.badlogic.gdx.net.ServerSocketHints;
import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.net.SocketHints;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class NetworkManager {

    public static void create(int typeOfPlayer) {
        if (typeOfPlayer == 1) {


        }
        if (typeOfPlayer == 2) {  //клиент
            SocketHints hints = new SocketHints();
            Socket client = Gdx.net.newClientSocket(Net.Protocol.TCP, "localhost", 7777, hints);
            try {
//                client.getOutputStream().write((GameManager.temp.toString()).getBytes());
                String response = new BufferedReader(new InputStreamReader(client.getInputStream())).readLine();
                Gdx.app.log("PingPongSocketExample", "got server message: " + response);
            } catch (IOException e) {
                Gdx.app.log("PingPongSocketExample", "an error occured", e);
            }
        }
    }

//    public static void doing(int typeOfPlayer) {
//        try {
//            String message = new BufferedReader(new InputStreamReader(client.getInputStream())).readLine();
//            GameManager.temp.fromString(message);
//            Gdx.app.log("PingPongSocketExample", "got client message: " + message);
//            client.getOutputStream().write("PONG\n".getBytes());
//        } catch (IOException e) {
//            Gdx.app.log("PingPongSocketExample", "an error occured", e);
//        }
//    }
//}).start();
//    }
}

