package dto.response;

import dto.controller.ClientServerFilmController;
import dto.request.EditFilmRequest;

public class GetFilmEditResponse extends Response {

    public GetFilmEditResponse(String name, EditFilmRequest editFilmRequest) {
        super(name);
        ClientServerFilmController editFilmController = new ClientServerFilmController();
        isSuccessfully = editFilmController.editFilm(editFilmRequest);
    }

    public boolean isSuccessfully() {
        return isSuccessfully;
    }

    private final boolean isSuccessfully;

    public String toString() {
        String result;
        if (isSuccessfully) {
            result = "successfully";
        } else {
            result = "not successfully";
        } return "GetFilmEditResponse" + result;
    }

}
