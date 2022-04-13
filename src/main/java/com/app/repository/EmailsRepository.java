package com.app.repository;

import com.app.model.emailInfo.NewFilmEmail;
import org.springframework.stereotype.Repository;

import java.util.concurrent.ArrayBlockingQueue;

@Repository
public class EmailsRepository {

    private ArrayBlockingQueue<NewFilmEmail> newFilmEmails;




}
