package ru.puchinets.userservice.mapper;

import org.mapstruct.Mapper;
import ru.puchinets.userservice.model.dto.request.RoleRequest;
import ru.puchinets.userservice.model.dto.response.RoleResponse;
import ru.puchinets.userservice.model.entity.Role;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    Role dtoToEntity(RoleRequest request);

    RoleResponse entityToDto(Role entity);

    default Role update(Role role, RoleRequest request) {
        if (request==null) return role;
        if (request.getName()!=null && !request.getName().isBlank())
            role.setName(request.getName());
        if (request.getDescription()!=null)
            role.setDescription(request.getDescription());
        return role;
    }
}
