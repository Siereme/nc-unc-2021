package com.app.model.confirmEmail;

import com.app.model.user.User;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

@Document
public class ConfirmEmail {

    @Id
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    @Column(name = "date_end")
    private LocalDateTime endDate;

    public ConfirmEmail(Integer userId, String token) {
        this.userId = userId;
        this.token = token;
        endDate = LocalDateTime.now().plusDays(1);
    }

    public ConfirmEmail() {

    }

}
