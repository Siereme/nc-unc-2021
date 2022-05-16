package com.app.repository;

import com.app.model.film.Film;
import com.app.model.user.User;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Transactional
@Repository
public interface UserRepository extends IRepository<User> {
}
