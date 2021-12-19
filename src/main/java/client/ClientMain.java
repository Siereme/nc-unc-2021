package client;

import client.CommunicationInterface;
import dto.CreateAuthorizationRequest;
import dto.CreateEntityRequest;
import dto.GetAuthorizationResponse;
import dto.GetEntityRequest;
import dto.GetEntityResponse;
import dto.Request;
import dto.Response;
import model.film.Film;
import model.user.IUser;

import java.io.IOException;
import java.util.Scanner;

public class ClientMain {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        final CommunicationInterface clientInterface = new CommunicationInterface();
        Thread.sleep(1000);  // just to simulate some waiting...
        IUser user;
        while (true) {
            // can be read from the console
            // must be taken from the window
            final Scanner input = new Scanner(System.in);
            System.out.println("Enter user name");
            String userName = input.nextLine();
            System.out.println("Enter password");
            String password = input.nextLine();
            final Request authorizationRequest = new CreateAuthorizationRequest("authorization", userName, password);

            Response response = clientInterface.exchange(authorizationRequest);
            if (response instanceof GetAuthorizationResponse) {
                if (((GetAuthorizationResponse) response).isSuccessfully()) {
                    user = ((GetAuthorizationResponse) response).getUser();
                    break;
                }
            }
        }

        // find by...
        while (true){

            break;
        }

        System.out.println("User: " + user.getName());

        Thread.sleep(1000); // just to simulate some waiting...

    }

}
