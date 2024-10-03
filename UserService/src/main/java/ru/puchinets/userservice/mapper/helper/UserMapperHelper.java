package ru.puchinets.userservice.mapper.helper;

import org.springframework.stereotype.Component;
import ru.puchinets.userservice.model.entity.Role;

import java.util.List;

@Component
public class UserMapperHelper {

    public List<String> roleToString(List<Role> roles) {
        return roles.stream().map(Role::getName).toList();
    }
}
