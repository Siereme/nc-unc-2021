package app.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client {



    public static void main(String[] args) throws IOException {
        try (Socket socket = new Socket("127.0.0.1", 8000);
                DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                DataInputStream dis = new DataInputStream(socket.getInputStream());) {
            // авторизация может быть и здесь?
            // контроллеры будут на стороне сервера
            // окно с выбором действия
            // поиск по...
            System.out.println("Client started!");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
