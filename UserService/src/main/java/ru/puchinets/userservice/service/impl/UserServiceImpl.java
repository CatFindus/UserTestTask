package ru.puchinets.userservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.puchinets.userservice.Constants;
import ru.puchinets.userservice.mapper.UserMapper;
import ru.puchinets.userservice.model.dto.request.UserRequest;
import ru.puchinets.userservice.model.dto.response.UserResponse;
import ru.puchinets.userservice.model.entity.Role;
import ru.puchinets.userservice.model.entity.User;
import ru.puchinets.userservice.repository.RoleRepository;
import ru.puchinets.userservice.repository.UserRepository;
import ru.puchinets.userservice.service.UserService;

import java.time.LocalDateTime;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper mapper;

    @Override
    public Optional<UserResponse> getUserById(Long id) {
        return userRepository.findById(id)
                .map(mapper::entityToDto);
    }

    @Override
    public Page<UserResponse> getAll(Pageable pageable) {
        return userRepository.findAll(pageable).map(mapper::entityToDto);
    }

    @Override
    @Transactional
    public boolean authenticateUser(String username, String passwordHash) {
        return userRepository.findByUsername(username)
                .map(user -> {
                    boolean authenticated = user.getPasswordHash().equals(passwordHash);
                    if (authenticated) {
                        user.setLastLogin(LocalDateTime.now());
                        userRepository.saveAndFlush(user);
                    }
                    return authenticated;
                })
                .orElse(false);
    }

    @Transactional
    @Override
    public UserResponse registerUser(UserRequest request) {
        User user = mapper.dtoToEntity(request);
        Optional<Role> mayBeRole = roleRepository.findByName(Constants.DEFAULT_ROLE);
        mayBeRole.ifPresent(user::addRole);
        user = userRepository.saveAndFlush(user);
        return mapper.entityToDto(user);
    }

    @Transactional
    @Override
    public Optional<UserResponse> update(Long id, UserRequest request) {
        var mayBeUser = userRepository.findById(id);
        return mayBeUser
                .map(user -> {
                    User updated = mapper.update(user, request);
                    userRepository.saveAndFlush(updated);
                    return updated;
                })
                .map(mapper::entityToDto);
    }

    @Transactional
    @Override
    public boolean delete(Long id) {
        var mayBeUser = userRepository.findById(id);
        mayBeUser.ifPresent(userRepository::delete);

        return mayBeUser.isPresent();
    }

    @Transactional
    @Override
    public Optional<UserResponse> addRoleToUser(Long userId, Integer roleId) {
        Optional<User> mayBeUser = userRepository.findById(userId);
        Optional<Role> mayBeRole = roleRepository.findById(roleId);
        if (mayBeUser.isEmpty() || mayBeRole.isEmpty()) return Optional.empty();
        User user = mayBeUser.get();
        user.addRole(mayBeRole.get());
        userRepository.saveAndFlush(user);
        return Optional.of(mapper.entityToDto(user));
    }

    @Transactional
    @Override
    public Optional<UserResponse> replaceRoleFormUser(Long userId, Integer roleId) {
        Optional<User> mayBeUser = userRepository.findById(userId);
        Optional<Role> mayBeRole = roleRepository.findById(roleId);
        if (mayBeUser.isEmpty() || mayBeRole.isEmpty()) return Optional.empty();
        User user = mayBeUser.get();
        user.replaceRole(mayBeRole.get());
        userRepository.saveAndFlush(user);
        return Optional.of(mapper.entityToDto(user));
    }
}
