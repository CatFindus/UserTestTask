package ru.puchinets.userservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ru.puchinets.userservice.mapper.helper.UserMapperHelper;
import ru.puchinets.userservice.model.dto.request.UserRequest;
import ru.puchinets.userservice.model.dto.response.UserResponse;
import ru.puchinets.userservice.model.entity.User;

@Mapper(componentModel = "spring", uses = {UserMapperHelper.class}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    User dtoToEntity(UserRequest request);

    UserResponse entityToDto(User entity);

    default User update(User user, UserRequest request) {
        if (request==null) return user;
        if (request.getPasswordHash()!=null && !request.getPasswordHash().isBlank())
            user.setPasswordHash(request.getPasswordHash());
        if (request.getEmail()!=null && !request.getEmail().isBlank())
            user.setEmail(request.getEmail());
        if (request.getFirstname()!=null && !request.getFirstname().isBlank())
            user.setFirstname(request.getFirstname());
        if (request.getLastName()!=null && !request.getLastName().isBlank())
            user.setLastName(request.getLastName());
        if (request.getPhone()!=null && !request.getPhone().isBlank())
            user.setPhone(request.getPhone());
        if (request.getBirthDate()!=null)
            user.setBirthDate(request.getBirthDate());
        return user;
    }
}
