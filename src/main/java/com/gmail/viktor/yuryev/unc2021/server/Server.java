package com.gmail.viktor.yuryev.unc2021.server;

import com.gmail.viktor.yuryev.unc2021.shared.dto.CreateEntityRequest;
import com.gmail.viktor.yuryev.unc2021.shared.dto.GetEntityRequest;
import com.gmail.viktor.yuryev.unc2021.shared.dto.GetEntityResponse;
import com.gmail.viktor.yuryev.unc2021.shared.dto.Request;
import com.gmail.viktor.yuryev.unc2021.shared.dto.Response;
import com.gmail.viktor.yuryev.unc2021.shared.model.Film;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private static final ExecutorService pool = Executors.newFixedThreadPool(10);
    private static int PORT = 7777;

    public static void main(String[] args) throws IOException {
        ServerSocket ss = new ServerSocket(PORT);
        System.out.println("ServerSocket awaiting connections...");
        while (!ss.isClosed()) {
            Socket client = ss.accept();
            pool.execute(new SingleServer(client));
        }
        pool.shutdown();
    }

    /**
     * The {@code SingleServer} class creates single server socket for one client
     */
    private static class SingleServer implements Runnable {
        /**
         * client socket
         */
        private static Socket client;

        public SingleServer(Socket client) {
            this.client = client;
        }

        @Override
        public void run() {
            System.out.println("Connection from " + client);
            try {
                final OutputStream outputStream = client.getOutputStream();
                ObjectOutputStream dos = new ObjectOutputStream(outputStream);
                final InputStream inputStream = client.getInputStream();
                ObjectInputStream dis = new ObjectInputStream(inputStream);
                while (!client.isClosed()) {
                    final Request request = (Request) dis.readObject();
                    System.out.println("handling request " + request);
                    //do some logic
                    final Response response = respond(request);
                    dos.writeObject(response);
                    System.out.println(" response sent");

                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        private Response respond(Request request) throws IOException {
            if (request instanceof CreateEntityRequest) {
                //create entity
                return new Response("ok");
            } else if (request instanceof GetEntityRequest) {
                return new GetEntityResponse<>("ok", new Film("this is film!", "123"));
            } else {
                return new Response("error");
            }
        }
    }
}
