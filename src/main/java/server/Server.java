package server;

import app.controller.imp.ActorController;
import app.controller.imp.DirectorController;
import app.controller.imp.FilmController;
import app.controller.imp.GenreController;
import app.controller.IEntityController;
import app.controller.imp.UserController;
import app.model.IEntity;
import app.model.actor.Actor;
import app.model.director.Director;
import app.model.film.Film;
import app.model.genre.Genre;
import app.model.user.IUser;
import dto.request.*;
import dto.response.GetAuthorizationResponse;
import dto.response.GetEntitiesByNamesResponse;
import dto.response.GetEntityResponse;
import dto.response.GetFindByFilterResponse;
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
import java.util.LinkedList;
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
            } catch (IOException | ClassNotFoundException | InvocationTargetException | NoSuchMethodException | InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        private static final Map<Class<? extends IEntity>, Class<? extends IEntityController>>
                REQUEST_RESOLVER_HASH_MAP_CHECK = new HashMap<>();

        static {
            REQUEST_RESOLVER_HASH_MAP_CHECK.put(Film.class, FilmController.class);
            REQUEST_RESOLVER_HASH_MAP_CHECK.put(IUser.class, UserController.class);
            REQUEST_RESOLVER_HASH_MAP_CHECK.put(Genre.class, GenreController.class);
            REQUEST_RESOLVER_HASH_MAP_CHECK.put(Actor.class, ActorController.class);
            REQUEST_RESOLVER_HASH_MAP_CHECK.put(Director.class, DirectorController.class);
        }

        private Response respond(Request request)
                throws NoSuchMethodException, InvocationTargetException, InstantiationException,
                IllegalAccessException {
            Class<? extends IEntity> entityClass = request.getEntityType();
            Class<? extends IEntityController> controllerClass = REQUEST_RESOLVER_HASH_MAP_CHECK.get(entityClass);
            IEntityController controller = controllerClass.getConstructor().newInstance();

            if (request instanceof AuthorizationRequest) {
                UserController userController = (UserController) controller;
                IUser user = userController.getEntityByLogin(((AuthorizationRequest) request).getUserName(),
                        ((AuthorizationRequest) request).getPassword());
                return new GetAuthorizationResponse("ok", user);
            } else if (request instanceof FindByFilterRequest) {
                FilmController filmController = (FilmController) controller;

                LinkedList<String> actorNames = ((FindByFilterRequest) request).getActors();
                ActorController actorController = new ActorController();
                LinkedList<String> actorsIds = actorController.getIdsByNames(actorNames);

                LinkedList<String> directorNames = ((FindByFilterRequest) request).getDirectors();
                DirectorController directorController = new DirectorController();
                LinkedList<String> directorIds = directorController.getIdsByNames(directorNames);

                LinkedList<String> genreNames = ((FindByFilterRequest) request).getGenres();
                GenreController genreController = new GenreController();
                LinkedList<String> genreIds = genreController.getIdsByNames(genreNames);

                LinkedList<Film> films = filmController.filmsBy(actorsIds, genreIds, directorIds);
                return new GetFindByFilterResponse("ok", films);
            } else if (request instanceof AddEntityRequest) {
                IEntity entity = ((AddEntityRequest) request).getEntity();
                controller.addEntity(entity);
                return new Response("the entity was successfully added");
            } else if (request instanceof RemoveEntityRequest) {
                IEntity entity = ((RemoveEntityRequest) request).getEntity();
                boolean isSuccessfully = controller.remove(entity);
                if (isSuccessfully) {
                    return new Response("the entity was successfully removed");
                } else {
                    return new Response("failed to remove entity");
                }
            } else if (request instanceof GetEntityRequest) {
                String entityId = ((GetEntityRequest) request).getEntityId();
                IEntity entity = (IEntity) controller.getEntityById(entityId);
                return new GetEntityResponse("ok", entity);
            } else if(request instanceof GetEntitiesByNamesRequest){
                LinkedList<String> names = ((GetEntitiesByNamesRequest) request).getNames();
                LinkedList<? extends IEntity> entities = controller.getEntitiesByNames(names);
                return new GetEntitiesByNamesResponse("ok", entities);
            }
            else {
                return new Response("error");
            }
        }
    }
}
