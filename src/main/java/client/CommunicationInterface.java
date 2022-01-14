package client;

import dto.request.Request;
import dto.response.Response;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import server.Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class CommunicationInterface {
    public static final int PORT = 7777;
    public static final String LOCALHOST = "localhost";
    private ObjectInputStream dis;
    private ObjectOutputStream dos;

    private static CommunicationInterface communicationInterface;

    private static final Logger logger = Logger.getLogger(Server.class.getName());

    public static CommunicationInterface getInstance() {
        if (communicationInterface == null) {
            BasicConfigurator.configure();
            communicationInterface = new CommunicationInterface();
        }
        return communicationInterface;
    }

    public CommunicationInterface() {

        try {
            Socket socket = new Socket(LOCALHOST, PORT);
            logger.info("connection established");
            dis = new ObjectInputStream(socket.getInputStream());
            dos = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Response exchange(Request request) throws IOException, ClassNotFoundException {
        logger.info("request sent : " + request);
        dos.writeObject(request);
        final Response commandResponse = (Response) dis.readObject();
        logger.info("response received " + commandResponse);
        return commandResponse;
    }

}
