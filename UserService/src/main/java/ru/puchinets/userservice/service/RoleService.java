package ru.puchinets.userservice.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.puchinets.userservice.model.dto.request.RoleRequest;
import ru.puchinets.userservice.model.dto.response.RoleResponse;

import java.util.Optional;

public interface RoleService {

    Optional<RoleResponse> getRoleById(Integer id);
    Page<RoleResponse> getAll(Pageable pageable);
    Optional<RoleResponse> update(Integer id, RoleRequest request);
    boolean delete(Integer id);
    RoleResponse create(RoleRequest request);

}
