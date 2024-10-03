package ru.puchinets.userservice.model.dto.request;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
public class UserRequest implements Serializable {
    private String username;
    private String email;
    private String passwordHash;
    private String firstname;
    private String lastName;
    private LocalDate birthDate;
    private String phone;
}
