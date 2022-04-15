package com.app.repository;

import com.app.model.emailInfo.NewEmail;
import org.springframework.stereotype.Repository;

import java.util.concurrent.ArrayBlockingQueue;

@Repository
public class EmailsRepository {

    int capacity = 2;

/*
    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
*/

    public ArrayBlockingQueue<NewEmail> getNewFilmEmails() {
        return newFilmEmails;
    }

    public void setNewFilmEmails(ArrayBlockingQueue<NewEmail> newFilmEmails) {
        this.newFilmEmails = newFilmEmails;
    }

    private ArrayBlockingQueue<NewEmail> newFilmEmails;

    public EmailsRepository() {
        newFilmEmails = new ArrayBlockingQueue<>(capacity);
    }

    public boolean isEmpty(){
        return newFilmEmails.isEmpty();
    }


}
