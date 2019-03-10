package com.managers;

import java.net.*;
import java.io.*;

public class Client {
   static DataInputStream in;
   static DataOutputStream out;
    public Client() {
        int serverPort = 6666; // здесь обязательно нужно указать порт к которому привязывается сервер.
        String address = "127.0.0.1"; // это IP-адрес компьютера, где исполняется наша серверная программа.
        // Здесь указан адрес того самого компьютера где будет исполняться и клиент.

        try {
            InetAddress ipAddress = InetAddress.getByName(address); // создаем объект который отображает вышеописанный IP-адрес.
            Socket socket = new Socket(ipAddress, serverPort); // создаем сокет используя IP-адрес и порт сервера.
            System.out.println("Сокет создан, клиент подключился");

            // Берем входной и выходной потоки сокета, теперь можем получать и отсылать данные клиентом.
            InputStream sin = socket.getInputStream();
            OutputStream sout = socket.getOutputStream();
            // Конвертируем потоки в другой тип, чтоб легче обрабатывать текстовые сообщения.
            in = new DataInputStream(sin);
            out = new DataOutputStream(sout);
            // Создаем поток для чтения с клавиатуры.
            //BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
            //String line = null;
            //System.out.println("Type in something and press enter. Will send it to the server and tell ya what it thinks.");
            //System.out.println();

//            while (true) {
//                line = keyboard.readLine(); // ждем пока пользователь введет что-то и нажмет кнопку Enter.
//                System.out.println("Sending this line to the server...");
//                out.writeUTF(line); // отсылаем введенную строку текста серверу.
//                out.flush(); // заставляем поток закончить передачу данных.
//                line = in.readUTF(); // ждем пока сервер отошлет строку текста.
//                System.out.println("The server was very polite. It sent me this : " + line);
//                System.out.println("Looks like the server is pleased with us. Go ahead and enter more lines.");
//                System.out.println();
//            }
        } catch (Exception x) {
            x.printStackTrace();
        }
    }
    public static String clientListen() throws IOException {       // метод предназначен для считывания с сервера сделанных ходов
        String move =in.readUTF();
        System.out.println("Получил координаты " + move);

        return move;
    }
    public static void clientSpeak(String coord) throws IOException {   // метод предназначен для передачи сделанного хода клиентом на сервер
        out.writeUTF(coord); // отсылаем введенную строку текста серверу.
        System.out.println("Отправил координаты + coord");
        out.flush(); // заставляем поток закончить передачу данных.
//        if (in.readInt()==0){
//            System.out.println("Ход успешно передан и произошел на стороне сервера");
//        }
//        else System.out.println("На сервере возникла ошибка, очень жаль");
    }
}