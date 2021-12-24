module app {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;


    opens app.viewFX to javafx.fxml;
    opens app.viewFX.login to javafx.fxml;
    opens app.viewFX.menu to javafx.fxml;
    opens app.viewFX.menu.add to javafx.fxml;
    opens app.model.genre to com.fasterxml.jackson.databind;
    opens app.model.user.Admin to com.fasterxml.jackson.databind;
    opens app.model.user.Visitor to com.fasterxml.jackson.databind;
    opens app.viewFX.menu.films to javafx.fxml, javafx.base;
    opens app.model.film to com.fasterxml.jackson.databind, javafx.base;
    opens app.model.actor to com.fasterxml.jackson.databind;
    opens app.model.director to com.fasterxml.jackson.databind;
    opens app.viewFX.menu.remove to javafx.fxml;
    opens app.viewFX.menu.remove.removeActor to javafx.fxml;
    opens app.viewFX.menu.remove.removeFilm to javafx.fxml;
    opens app.viewFX.menu.remove.removeDirector to javafx.fxml;
    opens app.viewFX.menu.remove.removeGenre to javafx.fxml;
    exports app.viewFX;
    exports app.model.genre to com.fasterxml.jackson.databind;
    exports app.model.user.Admin to com.fasterxml.jackson.databind;
    exports app.model.user.Visitor to com.fasterxml.jackson.databind;
    exports app.viewFX.login to com.fasterxml.jackson.databind;
    exports app.viewFX.menu;
    exports app.viewFX.menu.films to javafx.fxml;
    exports app.viewFX.menu.add to javafx.fxml;
    exports app.viewFX.menu.add.addFilm to javafx.fxml;
    exports app.model.actor to com.fasterxml.jackson.databind;
    exports app.model.film to com.fasterxml.jackson.databind;
    exports app.model.director to com.fasterxml.jackson.databind;
    exports app.viewFX.menu.remove to javafx.fxml;
    exports app.viewFX.menu.remove.removeActor to javafx.fxml;
    exports app.viewFX.menu.remove.removeFilm to javafx.fxml;
    exports app.viewFX.menu.remove.removeDirector to javafx.fxml;
    exports app.viewFX.menu.remove.removeGenre to javafx.fxml;
}