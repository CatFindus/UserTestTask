package ru.puchinets.userservice.model.dto.response;


import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class RoleResponse implements Serializable {
    private Integer id;
    private String name;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
