package dto.response;

import dto.controller.ClientServerFilmController;
import dto.request.CreateAddFilmRequest;

public class GetAddFilmResponse extends Response {
    public GetAddFilmResponse(String name, CreateAddFilmRequest addFilmRequest) {
        super(name);
        ClientServerFilmController clientServerFilmController = new ClientServerFilmController();
        isSuccessfully = clientServerFilmController.addFilm(addFilmRequest);
    }

    private final boolean isSuccessfully;

    public String toString() {
        String result;
        if (isSuccessfully) {
            result = "successfully";
        } else {
            result = "not successfully";
        }
        return "GetAddFilmResponse " + result;
    }

}
