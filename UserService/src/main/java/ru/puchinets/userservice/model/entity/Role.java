package ru.puchinets.userservice.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(of = "name", callSuper = false)
@Table(name = "roles", schema = "user_management")
public class Role extends BaseEntity {
    @Id
    @SequenceGenerator(name = "role_seq", allocationSize = 1, sequenceName = "roles_id_seq", schema = "user_management")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_seq")
    private Integer id;
    @Column(name = "name", unique = true, nullable = false)
    private String name;
    @Column(name = "description")
    private String description;
    @ManyToMany(mappedBy = "roles")
    @ToString.Exclude
    private List<User> users = new ArrayList<>();
}
