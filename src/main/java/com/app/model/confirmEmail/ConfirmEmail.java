package com.app.model.confirmEmail;

import com.app.model.IEntity;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.time.LocalDateTime;

@Document
public class ConfirmEmail implements IEntity {

    @Transient
    public static final String SEQUENCE_NAME = "confirm_email_sequence";

    @Id
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    private Integer userId;

    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    private LocalDateTime endDate;

    public ConfirmEmail(Integer userId, String token) {
        this.userId = userId;
        this.token = token;
        endDate = LocalDateTime.now().plusDays(1);
    }

    public ConfirmEmail() {

    }

}
