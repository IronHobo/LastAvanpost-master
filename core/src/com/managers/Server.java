package com.managers;

import com.badlogic.gdx.scenes.scene2d.ui.List;

import java.net.*;
import java.io.*;

public class Server {
    static DataInputStream in;
    static DataOutputStream out;
    static ServerSocket ss;
    List<Socket> socketList;

    public Server() {
        int port = 6666; // случайный порт (может быть любое число от 1025 до 65535)
        try {
            ss = new ServerSocket(port); // создаем сокет сервера и привязываем его к вышеуказанному порту
            System.out.println("Waiting for a client...Сейчас надо запустить connectionsListner");

//            Socket socket = ss.accept(); // заставляем сервер ждать подключений и выводим сообщение когда кто-то связался с сервером
//            System.out.println("Клиент подключился");
//
//            // Берем входной и выходной потоки сокета, теперь можем получать и отсылать данные клиенту.
//            InputStream sin = socket.getInputStream();
//            OutputStream sout = socket.getOutputStream();
//
//            // Конвертируем потоки в другой тип, чтоб легче обрабатывать текстовые сообщения.
//            in = new DataInputStream(sin);
//            out = new DataOutputStream(sout);

//            String line = null;
//            while (true) {
////                line = in.readUTF(); // ожидаем пока клиент пришлет строку текста.
////                System.out.println("The dumb client just sent me this line : " + line);
////                System.out.println("I'm sending it back...");
//                out.writeUTF(line); // отсылаем клиенту обратно ту самую строку текста.
//                out.flush(); // заставляем поток закончить передачу данных.
//                System.out.println("Waiting for the next line...");
//                System.out.println();
//            }
        } catch (Exception x) {
            x.printStackTrace();
        }
    }

    public static void connectionsListner() {
        try {
            Socket socket = ss.accept(); // заставляем сервер ждать подключений и выводим сообщение когда кто-то связался с сервером
            System.out.println("Клиент подключился");
            // Берем входной и выходной потоки сокета, теперь можем получать и отсылать данные клиенту.
            InputStream sin = socket.getInputStream();
            OutputStream sout = socket.getOutputStream();
            // Конвертируем потоки в другой тип, чтоб легче обрабатывать текстовые сообщения.
            in = new DataInputStream(sin);
            out = new DataOutputStream(sout);
//            out.writeUTF("Подключение успешно");
        } catch (Exception x) {
            x.printStackTrace();
        }
    }

    public static String serverListen() throws IOException {       // метод предназначен для считывания сервером сделанных клиентами ходов
        while (true) {
            String move = in.readUTF();
            System.out.println("Клиент прислал : " + move);
            return move;
        }
    }

    public static void serverSpeak(String coord) {
        try {
            out.writeUTF(coord);
            System.out.println("Отправил координаты "+coord);
        } catch (Exception x) {
            x.printStackTrace();
        }
    }

}