package server;

import app.controller.FilmController;
import app.controller.IEntityController;
import app.model.IEntity;
import app.model.film.Film;
import dto.request.*;
import dto.response.GetAddFilmResponse;
import dto.response.GetAuthorizationResponse;
import dto.response.GetEntitiesByNamesResponse;
import dto.response.GetEntityResponse;
import dto.response.GetFilmEditResponse;
import dto.response.GetFindByFilterResponse;
import dto.response.GetRemoveEntityResponse;
import dto.response.Response;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
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
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        private static final Map<Class<? extends IEntity>, Class<? extends IEntityController>> REQUEST_RESOLVER_HASH_MAP_CHECK = new HashMap<>();
        static {
            REQUEST_RESOLVER_HASH_MAP_CHECK.put (Film.class, FilmController.class);
        }

        private Response respond(Request request) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
            Class<? extends IEntity> entityClass = request.getEntityType();
            Class<? extends IEntityController> controllerClass = REQUEST_RESOLVER_HASH_MAP_CHECK.get(entityClass);
            IEntityController controller = controllerClass.getConstructor().newInstance();

            if (request instanceof AuthorizationRequest) {
                return new GetAuthorizationResponse("ok", (AuthorizationRequest) request);
            } else if (request instanceof FindByFilterRequest) {
                return new GetFindByFilterResponse("ok", (FindByFilterRequest) request);
            } else if (request instanceof AddFilmRequest) {
                return new GetAddFilmResponse("ok", (AddFilmRequest) request);
            } else if (request instanceof GetEntitiesByNamesRequest) {
                return new GetEntitiesByNamesResponse("ok", (GetEntitiesByNamesRequest) request);
            } else if (request instanceof GetEntityRequest) {
                return new GetEntityResponse("ok", (GetEntityRequest) request);
            } else if (request instanceof EditFilmRequest) {
                return new GetFilmEditResponse("ok", (EditFilmRequest) request);
            } else if (request instanceof RemoveEntityRequest) {
//                controller.removeEntity(request);
                return new GetRemoveEntityResponse("ok", (RemoveEntityRequest) request);
            } else if (request instanceof AddEntityRequest request1){
//                controller.addEntity(request1.getEntity());
                    return null;
            }
            else {
                return new Response("error");
            }
        }
    }
}
