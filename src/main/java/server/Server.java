package server;

import app.model.IEntity;
import dto.request.CreateAddFilmRequest;
import dto.request.CreateAuthorizationRequest;
import dto.request.CreateEditFilmRequest;
import dto.request.CreateFindByFilterRequest;
import dto.request.CreateGetEntitiesByNamesRequest;
import dto.request.CreateGetEntityRequest;
import dto.response.GetAddFilmResponse;
import dto.response.GetAuthorizationResponse;
import dto.response.GetEntitiesByNamesResponse;
import dto.response.GetEntityResponse;
import dto.response.GetFilmEditResponse;
import dto.response.GetFindByFilterResponse;
import dto.request.Request;
import dto.response.Response;

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

    public static void main(String[] args) throws IOException {
        int PORT = 7777;
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
            SingleServer.client = client;
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

        private Response respond(Request request) {
            if (request instanceof CreateAuthorizationRequest) {
                return new GetAuthorizationResponse("ok", (CreateAuthorizationRequest) request);
            } else if (request instanceof CreateFindByFilterRequest) {
                return new GetFindByFilterResponse("ok", (CreateFindByFilterRequest) request);
            } else if (request instanceof CreateAddFilmRequest) {
                return new GetAddFilmResponse("ok", (CreateAddFilmRequest) request);
            } else if (request instanceof CreateGetEntitiesByNamesRequest) {
                return new GetEntitiesByNamesResponse("ok", (CreateGetEntitiesByNamesRequest) request);
            } else if (request instanceof CreateGetEntityRequest) {
                return new GetEntityResponse("ok", (CreateGetEntityRequest) request);
            } else if (request instanceof CreateEditFilmRequest) {
                return new GetFilmEditResponse("ok", (CreateEditFilmRequest) request);
            } else {
                return new Response("error");
            }
        }
    }
}
