package com.ericbeaubrun.presencesys.infrastructure.persistence;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "utilisateurs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

    @Id
    @Column(name = "id_user", length = 9)
    private String id;

    @Column(name = "identifiant", unique = true, nullable = false)
    private String username;

    @Column(name = "mot_de_passe", nullable = false)
    private String password;

    @Column(name = "role", nullable = false)
    private String role;
}
