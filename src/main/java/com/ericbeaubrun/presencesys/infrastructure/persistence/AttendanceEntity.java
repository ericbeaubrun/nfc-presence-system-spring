package com.ericbeaubrun.presencesys.infrastructure.persistence;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalTime;

@Entity
@Table(name = "assister")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@IdClass(AttendanceId.class)
public class AttendanceEntity {

    @Id
    @Column(name = "fk_etudiant", length = 9)
    private String studentId;

    @Id
    @Column(name = "fk_cours", length = 9)
    private String courseId;

    @Column(name = "status_etudiant", columnDefinition = "status_assister")
    private String status; // 'present' ou 'absent justifie'

    @Column(name = "justificatif")
    private String justification;

    @Column(name = "heure_arrive")
    private LocalTime arrivalTime;
}