package com.ericbeaubrun.presencesys.infrastructure.persistence;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "cours")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CourseEntity {

    @Id
    @Column(name = "id_cours", length = 9)
    private String id;

    @Column(name = "date_cours")
    private LocalDate date;

    @Column(name = "heure_debut")
    private LocalTime startTime;

    @Column(name = "heure_fin")
    private LocalTime endTime;

    @Column(name = "fk_salle", length = 9)
    private String roomId;
}