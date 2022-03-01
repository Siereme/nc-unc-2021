package com.app.config.converter.imp;

import com.app.config.converter.IdToEntityConverter;
import com.app.model.user.User.User;
import com.app.repository.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class IdToUserConverter extends IdToEntityConverter<Object, User> {

    public IdToUserConverter(UserRepository repository) {
        super(repository);
    }
}
