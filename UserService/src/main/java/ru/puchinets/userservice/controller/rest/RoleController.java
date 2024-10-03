package ru.puchinets.userservice.controller.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.puchinets.userservice.model.dto.request.RoleRequest;
import ru.puchinets.userservice.model.dto.response.RoleResponse;
import ru.puchinets.userservice.model.dto.response.UserResponse;
import ru.puchinets.userservice.model.entity.Role;
import ru.puchinets.userservice.service.RoleService;

import static org.springframework.data.domain.Sort.Direction.ASC;
import static ru.puchinets.userservice.Constants.PAGINATION_EXAMPLE;

@RestController
@RequiredArgsConstructor
@RequestMapping("/roles")
@Tag(name = "Role", description = "Operations related to roles in User Service")
public class RoleController {
    private final RoleService roleService;

    @Operation(summary = "Get role by ID", description = "Retrieve role by ID")
    @ApiResponse(responseCode = "200", description = "Role found",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Role.class)))
    @ApiResponse(responseCode = "404", description = "Role not found")
    @GetMapping("/{id}")
    public ResponseEntity<RoleResponse> getById(@PathVariable("id") Integer id) {
        var mayBeRole = roleService.getRoleById(id);
        return mayBeRole.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Retrieve a paginated list of all roles")
    @GetMapping
    public ResponseEntity<Page<RoleResponse>> getAll(@Parameter(description = "Pagination and sorting parameters", example = PAGINATION_EXAMPLE)
                                                     @PageableDefault(sort = "id", direction = ASC) Pageable pageable) {
        var rolesPage = roleService.getAll(pageable);
        return ResponseEntity.ok(rolesPage);
    }

    @Operation(summary = "Create new role", description = "Create a new role and return the role data")
    @ApiResponse(responseCode = "201", description = "Role created",
            content = @Content(schema = @Schema(implementation = RoleResponse.class)))
    @PostMapping
    public ResponseEntity<RoleResponse> create(@Parameter(description = "Fields of new Role") @RequestBody RoleRequest request) {
        var newRole = roleService.create(request);
        return new ResponseEntity<>(newRole, HttpStatus.CREATED);
    }

    @Operation(summary = "Update role by ID", description = "Update role data by ID and return updated data of role")
    @ApiResponse(responseCode = "200", description = "Role updated successfully",
            content = @Content(schema = @Schema(implementation = UserResponse.class)))
    @ApiResponse(responseCode = "404", description = "Role not found")
    @PutMapping("/{id}")
    public ResponseEntity<RoleResponse> update(@Parameter(description = "ID of role for update") @PathVariable("id") Integer id,
                                               @Parameter(description = "Fields of role for update") @RequestBody RoleRequest request) {
        var mayBeUpdatedRole = roleService.update(id, request);
        return mayBeUpdatedRole
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete role by ID", description = "Delete role by ID and return response code 204")
    @ApiResponse(responseCode = "204", description = "Role deleted successfully")
    @ApiResponse(responseCode = "404", description = "Role not found")
    public ResponseEntity<Void> delete(@Parameter(description = "ID of role for delete") @PathVariable("id") Integer id) {
        var isDeleted = roleService.delete(id);
        if (isDeleted) return ResponseEntity.noContent().build();
        else return ResponseEntity.notFound().build();
    }

}
