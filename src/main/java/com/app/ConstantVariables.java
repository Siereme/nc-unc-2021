package com.app;

public enum ConstantVariables {
    FILMS("films"),
    GENRES("genres"),
    ACTORS("actors"),
    DIRECTORS("directors"),
    JSON("json"),
    FILM_LIST("filmList"),
    GENRE_LIST("genreList"),
    ACTOR_LIST("actorList"),
    DIRECTOR_LIST("directorList"),
    MODAL_TITLE("modalTitle"),
    EVENT_TYPE("eventType"),
    RESULT("result"),
    ERRORS("errors"),
    SUCCESS("success"),
    USER_FORM("userForm"),
    USERNAME_ERROR("usernameError"),
    PASSWORD_ERROR("passwordError"),
    EMAIL_QUEUE_CAPACITY("10"),
    JOB_BATCH_SIZE("10");

    private String value;
    ConstantVariables(String value) {
        this.value = value;
    }

    public String value(){
        return value;
    }
}
