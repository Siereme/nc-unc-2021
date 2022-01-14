module app {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;
    requires log4j;


    opens app.viewFX to javafx.fxml;
    opens app.viewFX.login to javafx.fxml;
    opens app.viewFX.menu to javafx.fxml;
    opens app.model.genre to com.fasterxml.jackson.databind;
    opens app.model.user.Admin to com.fasterxml.jackson.databind;
    opens app.model.user.Visitor to com.fasterxml.jackson.databind;
    opens app.viewFX.menu.films to javafx.fxml, javafx.base;
    opens app.model.film to com.fasterxml.jackson.databind, javafx.base;
    opens app.model.actor to com.fasterxml.jackson.databind;
    opens app.model.director to com.fasterxml.jackson.databind;
    exports app.viewFX;
    exports app.model.genre to com.fasterxml.jackson.databind;
    exports app.model.user.Admin to com.fasterxml.jackson.databind;
    exports app.model.user.Visitor to com.fasterxml.jackson.databind;
    exports app.viewFX.login to com.fasterxml.jackson.databind;
    exports app.viewFX.menu;
    exports app.viewFX.menu.films to javafx.fxml;
    exports app.model.actor to com.fasterxml.jackson.databind;
    exports app.model.film to com.fasterxml.jackson.databind;
    exports app.model.director to com.fasterxml.jackson.databind;
    exports app.viewFX.menu.films.handle to javafx.fxml;
    opens app.viewFX.menu.films.handle to javafx.base, javafx.fxml;
    exports dto.request.imp to javafx.fxml;
    opens dto.request.imp to javafx.base, javafx.fxml;
    exports app.viewFX.menu.genres to javafx.fxml;
    opens app.viewFX.menu.genres to javafx.base, javafx.fxml;
    exports app.viewFX.menu.genres.handle to javafx.fxml;
    opens app.viewFX.menu.genres.handle to javafx.base, javafx.fxml;
    exports app.viewFX.menu.actors to javafx.fxml;
    opens app.viewFX.menu.actors to javafx.base, javafx.fxml;
    exports app.viewFX.menu.directors to javafx.fxml;
    opens app.viewFX.menu.directors to javafx.base, javafx.fxml;
    exports app.viewFX.menu.actors.handle to javafx.fxml;
    opens app.viewFX.menu.actors.handle to javafx.base, javafx.fxml;
    exports app.viewFX.menu.directors.handle to javafx.fxml;
    opens app.viewFX.menu.directors.handle to javafx.base, javafx.fxml;
}