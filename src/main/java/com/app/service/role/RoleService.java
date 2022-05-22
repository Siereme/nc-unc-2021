package com.app.service.role;

import com.app.model.actor.Actor;
import com.app.model.role.Role;
import com.app.service.AbstractService;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService extends AbstractService<Role> {

    public Role findByName(String name) {
        Criteria regex = Criteria.where("name").regex(name);
        List<Role> roles = mongoTemplate.find(new Query().addCriteria(regex), Role.class);
        if (roles.size() != 0) {
            return roles.get(0);
        }
        return null;
    }

    @Override
    protected List<Role> findByContains(String query) {
        return null;
    }
}
