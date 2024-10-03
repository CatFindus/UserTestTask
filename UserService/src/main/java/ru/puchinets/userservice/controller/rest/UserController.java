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
import ru.puchinets.userservice.model.dto.request.UserRequest;
import ru.puchinets.userservice.model.dto.response.UserResponse;
import ru.puchinets.userservice.model.entity.User;
import ru.puchinets.userservice.service.UserService;

import java.util.Optional;

import static org.springframework.data.domain.Sort.Direction.ASC;
import static ru.puchinets.userservice.Constants.PAGINATION_EXAMPLE;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/users")
@Tag(name = "User", description = "Operations related to users in User Service")
public class UserController {
    private final UserService userService;

    @GetMapping("/{id}")
    @Operation(summary = "Get user by ID", description = "Retrieve user by their ID")
    @ApiResponse(responseCode = "200", description = "User found",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = User.class)))
    @ApiResponse(responseCode = "404", description = "User not found")
    public ResponseEntity<UserResponse> getUserById(@Parameter(description = "ID of the user to retrieve") @PathVariable("id") Long id) {
        Optional<UserResponse> mayBeUser = userService.getUserById(id);
        return mayBeUser
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    @Operation(summary = "Retrieve a paginated list of all users")
    public ResponseEntity<Page<UserResponse>> getAllUsers(@Parameter(name = "pagination parameter",
            description = "Pagination and sorting parameters", example = PAGINATION_EXAMPLE)
                                                          @PageableDefault(sort = "id", direction = ASC) Pageable pageable) {
        return ResponseEntity.ok(userService.getAll(pageable));
    }

    @PostMapping("/login")
    @Operation(summary = "Login user", description = "Authenticate a user's credentials and return a success message")
    @ApiResponse(responseCode = "401", description = "Invalid username or password")
    @ApiResponse(responseCode = "200", description = "Authenticated")
    public ResponseEntity<String> login(@Parameter(description = "username and passwordHash of user") @RequestBody UserRequest request) {
        boolean isAuthenticated = userService.authenticateUser(request.getUsername(), request.getPasswordHash());
        if (isAuthenticated) return ResponseEntity.ok("Authenticated");
        else return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
    }

    @PostMapping
    @Operation(summary = "Register new user", description = "Register a new user and return the user data")
    @ApiResponse(responseCode = "201", description = "User created",
            content = @Content(schema = @Schema(implementation = UserResponse.class)))
    public ResponseEntity<UserResponse> register(@RequestBody UserRequest request) {
        UserResponse savedUser = userService.registerUser(request);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update user by ID", description = "Update user data by ID and return updated user data")
    @ApiResponse(responseCode = "200", description = "User updated successfully",
            content = @Content(schema = @Schema(implementation = UserResponse.class)))
    @ApiResponse(responseCode = "404", description = "User not found")
    public ResponseEntity<UserResponse> updateUser(@Parameter(description = "ID of the user to update") @PathVariable("id") Long id,
                                                   @RequestBody UserRequest request) {
        var updatedUser = userService.update(id, request);
        return updatedUser.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete user by ID", description = "Delete user by ID and return response code 204")
    @ApiResponse(responseCode = "204", description = "User deleted successfully")
    @ApiResponse(responseCode = "404", description = "User not found")
    public ResponseEntity<Void> deleteUser(@Parameter(description = "ID of the user to delete") @PathVariable("id") Long id) {
        boolean deleted = userService.delete(id);
        if (deleted) return ResponseEntity.noContent().build();
        else return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Add role to user", description = "Add role to user by userId and roleId")
    @ApiResponse(responseCode = "200", description = "Role added to user if user not contains role",
            content = @Content(schema = @Schema(implementation = UserResponse.class)))
    @ApiResponse(responseCode = "404", description = "Role or user not found")
    @PostMapping("{userId}/role/{roleId}")
    public ResponseEntity<UserResponse> addRoleToUser(@Parameter(description = "ID of the user")
                                                      @PathVariable("userId") Long userId,
                                                      @Parameter(description = "ID of the role")
                                                      @PathVariable("roleId") Integer roleId) {
        Optional<UserResponse> response = userService.addRoleToUser(userId, roleId);
        return response.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Replace role to user", description = "Replace role to user by userId and roleId")
    @ApiResponse(responseCode = "200", description = "Role replaced from user if user contains role",
            content = @Content(schema = @Schema(implementation = UserResponse.class)))
    @ApiResponse(responseCode = "404", description = "Role or user not found")
    @DeleteMapping("{userId}/role/{roleId}")
    public ResponseEntity<UserResponse> replaceRoleFromUser(@Parameter(description = "ID of the user")
                                                            @PathVariable("userId") Long userId,
                                                            @Parameter(description = "ID of the role")
                                                            @PathVariable("roleId") Integer roleId) {
        Optional<UserResponse> response = userService.replaceRoleFormUser(userId, roleId);
        return response.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
}
