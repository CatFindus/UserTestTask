package ru.puchinets.userservice.model.dto.request;


import lombok.Data;

import java.io.Serializable;

@Data
public class RoleRequest implements Serializable {
    private String name;
    private String description;
}
