package ru.puchinets.userservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.puchinets.userservice.mapper.RoleMapper;
import ru.puchinets.userservice.model.dto.request.RoleRequest;
import ru.puchinets.userservice.model.dto.response.RoleResponse;
import ru.puchinets.userservice.model.entity.Role;
import ru.puchinets.userservice.repository.RoleRepository;
import ru.puchinets.userservice.service.RoleService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RoleServiceImpl implements RoleService {

    private final RoleRepository repository;
    private final RoleMapper mapper;

    @Override
    public Optional<RoleResponse> getRoleById(Integer id) {
        return repository.findById(id)
                .map(mapper::entityToDto);
    }

    @Override
    public Page<RoleResponse> getAll(Pageable pageable) {
        return repository.findAll(pageable)
                .map(mapper::entityToDto);
    }

    @Transactional
    @Override
    public Optional<RoleResponse> update(Integer id, RoleRequest request) {
        var mayBeRole = repository.findById(id);
        return mayBeRole.map(role -> mapper.update(role, request))
                .map(repository::saveAndFlush)
                .map(mapper::entityToDto);
    }

    @Transactional
    @Override
    public boolean delete(Integer id) {
        var mayBeRole = repository.findById(id);
        mayBeRole.ifPresent(repository::delete);
        return mayBeRole.isPresent();
    }

    @Transactional
    @Override
    public RoleResponse create(RoleRequest request) {
        Role newRole = mapper.dtoToEntity(request);
        newRole = repository.saveAndFlush(newRole);
        return mapper.entityToDto(newRole);
    }
}
