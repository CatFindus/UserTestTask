package ru.puchinets.userservice.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.puchinets.userservice.model.dto.request.UserRequest;
import ru.puchinets.userservice.model.dto.response.UserResponse;

import java.util.Optional;

@Service
public interface UserService {


    Optional<UserResponse> getUserById(Long id);

    Page<UserResponse> getAll(Pageable pageable);

    boolean authenticateUser(String username, String passwordHash);

    UserResponse registerUser(UserRequest request);

    Optional<UserResponse> update(Long id, UserRequest request);

    boolean delete(Long id);

    Optional<UserResponse> addRoleToUser(Long userId, Integer roleId);

    Optional<UserResponse> replaceRoleFormUser(Long userId, Integer roleId);
}
