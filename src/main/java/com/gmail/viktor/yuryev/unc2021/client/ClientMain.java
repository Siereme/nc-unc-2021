package com.gmail.viktor.yuryev.unc2021.client;

import com.gmail.viktor.yuryev.unc2021.shared.dto.CreateEntityRequest;
import com.gmail.viktor.yuryev.unc2021.shared.dto.GetEntityRequest;
import com.gmail.viktor.yuryev.unc2021.shared.dto.GetEntityResponse;
import com.gmail.viktor.yuryev.unc2021.shared.dto.Response;
import com.gmail.viktor.yuryev.unc2021.shared.model.Film;

import java.io.IOException;

public class ClientMain {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        final CommunicationInterface clientInterface = new CommunicationInterface();
        Thread.sleep(1000);  // just to simulate some waiting...

        final Film film = new Film("New film", "123");

        final CreateEntityRequest<Film> filmCreateEntityRequest = new CreateEntityRequest<>("create", film);
        Response filmResp = clientInterface.exchange(filmCreateEntityRequest);

        System.out.println("filmResp " + filmResp);

        Thread.sleep(1000); // just to simulate some waiting...
        final GetEntityRequest<Film> getFilm = new GetEntityRequest<>("get", "123");
        GetEntityResponse<Film> commandResponse = (GetEntityResponse<Film>) clientInterface.exchange(getFilm);

    }

}
