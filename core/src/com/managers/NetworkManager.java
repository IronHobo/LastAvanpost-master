package com.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.net.ServerSocket;
import com.badlogic.gdx.net.ServerSocketHints;
import com.badlogic.gdx.net.Socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class NetworkManager {
   public void create () {
       // setup a server thread where we wait for incoming connections
       // to the server
       new Thread(new Runnable() {
           @Override
           public void run() {
               ServerSocketHints hints = new ServerSocketHints();
               hints.acceptTimeout=0;
               ServerSocket server = Gdx.net.newServerSocket(Net.Protocol.TCP, "localhost", 9999, hints);
               // Ждем подключения клиента
               Socket client = server.accept(null);
               // read message and send it back
               try {
                   String message = new BufferedReader(new InputStreamReader(client.getInputStream())).readLine();
                   Gdx.app.log("PingPongSocketExample", "got client message: " + message);
                   client.getOutputStream().write("PONG\n".getBytes());
               } catch (IOException e) {
                   Gdx.app.log("PingPongSocketExample", "an error occured", e);
               }
           }
       }).start();

   }
}
