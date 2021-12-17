package server;

import controller.ActorController;
import controller.DirectorController;
import controller.FilmController;
import controller.GenreController;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread {
    private Socket socket;
    DataOutputStream out;

    private String showEntities(String entityName) {
        FilmController filmController = new FilmController();
        switch (entityName) {
            case "director": {
                DirectorController directorController = new DirectorController();
                return directorController.getAllEntitiesAsString();
            }
            case "actor": {
                ActorController actorController = new ActorController();
                return actorController.getAllEntitiesAsString();
            }
            case "genre": {
                GenreController genreController = new GenreController();
                return genreController.getAllEntitiesAsString();
            }
            default:
                return "unknown command";
        }
    }



    // как на клиенте сформировать окно из этой строки, чтобы пользователь еще выбрал что-то?
    private void sendFindMenu() {
        String menu = "Select option:\nFind film by directors\nFind film by genres\nFind film by actors";
    }

    private String findFilmByDirector() {
        return null;
    }

    public void run() {
        try (ServerSocket server = new ServerSocket(3345)) {
            Socket client = server.accept();
            DataOutputStream out = new DataOutputStream(client.getOutputStream());
            DataInputStream in = new DataInputStream(client.getInputStream());
            this.out = out;
            this.socket = client;

            while (!client.isClosed()) {
                // авторизация
                out.writeUTF("enter name\n");
                String name = in.readUTF();
                out.writeUTF("enter password\n");
                String passwd = in.readUTF();
                // определяем админ или визитор
                // даем ему меню... и ждем отклика

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

    }

}
