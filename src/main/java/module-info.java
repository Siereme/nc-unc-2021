module app {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;


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
}