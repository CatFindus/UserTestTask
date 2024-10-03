package ru.puchinets.userservice.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static ru.puchinets.userservice.Constants.PASSWORD_LENGTH_MSG;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(of = "username", callSuper = false)
@Table(name = "users", schema = "user_management")
public class User extends BaseEntity {
    @Id
    @SequenceGenerator(name = "user_seq", allocationSize = 1, sequenceName = "users_id_seq", schema = "user_management")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    private Long id;
    @NotBlank
    @Column(name = "username", unique = true, nullable = false)
    private String username;
    @Email
    @Column(name = "email", unique = true, nullable = false)
    private String email;
    @Column(name = "password_hash", nullable = false)
    @NotEmpty
    @Size(min = 8, max = 128, message = PASSWORD_LENGTH_MSG)
    private String passwordHash;
    @Column(name = "firstname")
    private String firstname;
    @Column(name = "lastname")
    private String lastName;
    @Column(name = "birth_date")
    @Past
    private LocalDate birthDate;
    private String phone;
    @Column(name = "last_login")
    private LocalDateTime lastLogin;
    @ToString.Exclude
    @ManyToMany
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles = new ArrayList<>();

    public void addRole(Role role) {
        if (!roles.contains(role)) {
            roles.add(role);
            role.getUsers().add(this);
        }
    }

    public void replaceRole(Role role) {
        if (roles.contains(role)) {
            roles.remove(role);
            role.getUsers().remove(this);
        }
    }
}
