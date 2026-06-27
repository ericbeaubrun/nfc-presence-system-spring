package com.ericbeaubrun.presencesys.infrastructure.persistence;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "etudiants")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentEntity {

    @Id
    @Column(name = "id_etudiant", length = 9, columnDefinition = "bpchar(9)")
    private String id;

    @Column(name = "fk_carte", length = 8, unique = true)
    private String cardId;

    @Column(name = "fk_classe", length = 9)
    private String classId;
}