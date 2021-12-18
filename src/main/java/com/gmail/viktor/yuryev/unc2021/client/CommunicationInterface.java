package com.gmail.viktor.yuryev.unc2021.client;

import com.gmail.viktor.yuryev.unc2021.shared.dto.Request;
import com.gmail.viktor.yuryev.unc2021.shared.dto.Response;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class CommunicationInterface {
    public static final int PORT = 7777;
    public static final String LOCALHOST = "localhost";
    private ObjectInputStream dis;
    private ObjectOutputStream dos;

    public CommunicationInterface() throws IOException {
        Socket socket = new Socket(LOCALHOST, PORT);
        System.out.println("connection established");
        dis = new ObjectInputStream(socket.getInputStream());
        dos = new ObjectOutputStream(socket.getOutputStream());
    }

    public Response exchange(Request request) throws IOException, ClassNotFoundException {
        System.out.println("request sent : "+ request);
        dos.writeObject(request);
        final Response commandResponse = (Response) dis.readObject();
        System.out.println("response received "+ commandResponse);
        return commandResponse;
    }

}
