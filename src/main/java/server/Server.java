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
import dto.request.imp.*;
import dto.response.imp.*;
import dto.response.Response;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

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
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private static final Logger logger = Logger.getLogger(Server.class.getName());
    private static final ExecutorService pool = Executors.newFixedThreadPool(10);

    public static void main(String[] args) throws IOException {
        BasicConfigurator.configure();
        int PORT = 7777;
        ServerSocket ss = new ServerSocket(PORT);
        logger.info("ServerSocket awaiting connections...");

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
            logger.info("Connection from " + client);
            try {
                final OutputStream outputStream = client.getOutputStream();
                ObjectOutputStream dos = new ObjectOutputStream(outputStream);
                final InputStream inputStream = client.getInputStream();
                ObjectInputStream dis = new ObjectInputStream(inputStream);

                while (!client.isClosed()) {
                    final Request request = (Request) dis.readObject();
                    logger.info("handling request " + request);
                    //do some logic
                    final Response response = respond(request);
                    dos.writeObject(response);
                    logger.info(" response sent");
                }
            } catch (IOException | ClassNotFoundException | InvocationTargetException | NoSuchMethodException | InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        private static final Map<Class<? extends IEntity>, Class<? extends IEntityController>>
                REQUEST_RESOLVER_HASH_MAP_CHECK = new HashMap<>();

        static {
            REQUEST_RESOLVER_HASH_MAP_CHECK.put(Film.class, FilmController.class);
            REQUEST_RESOLVER_HASH_MAP_CHECK.put(RequestFilm.class, FilmController.class);
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
                Map<String, List<String>> entityIds = new HashMap<>();

                LinkedList<String> actorNames = ((FindByFilterRequest) request).getActors();
                ActorController actorController = new ActorController();
                LinkedList<String> actorsIds = actorController.getIdsByNames(actorNames);
                entityIds.put("actor", actorsIds);


                LinkedList<String> directorNames = ((FindByFilterRequest) request).getDirectors();
                DirectorController directorController = new DirectorController();
                LinkedList<String> directorIds = directorController.getIdsByNames(directorNames);
                entityIds.put("director", directorIds);

                LinkedList<String> genreNames = ((FindByFilterRequest) request).getGenres();
                GenreController genreController = new GenreController();
                LinkedList<String> genreIds = genreController.getIdsByNames(genreNames);
                entityIds.put("genre", genreIds);


                List entities = controller.findBy(entityIds);
                return new GetFindByFilterResponse("ok", entities);
            } else if (request instanceof AddEntityRequest) {
                IEntity entity = ((AddEntityRequest) request).getEntity();
                controller.addEntity(entity);
                return new Response("the entity was successfully added");
            } else if (request instanceof EditEntityRequest) {
                IEntity entity = ((EditEntityRequest) request).getEntity();
                controller.edit(entity);
                return new Response("the entity was successfully edit");
            } else if (request instanceof RemoveEntityRequest) {
                String entityId = ((RemoveEntityRequest) request).getEntityId();
                IEntity entity = (IEntity) controller.getEntityById(entityId);
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
            } else if (request instanceof GetEntitiesByNamesRequest) {
                List<String> names = ((GetEntitiesByNamesRequest) request).getNames();
                List<? extends IEntity> entities = controller.getEntitiesByNames(names);
                return new GetEntitiesResponse("ok", entities);
            } else if (request instanceof GetAllEntitiesRequest) {
                List entities = controller.findAll();
                return new GetEntitiesResponse("ok", entities);
            } else if (request instanceof SearchEntityRequest) {
                String entityName = ((SearchEntityRequest) request).getEntityName();
                List entities = controller.search(entityName);
                return new GetSearchEntityResponse("ok", entities);
            } else {
                return new Response("error");
            }
        }
    }
}
