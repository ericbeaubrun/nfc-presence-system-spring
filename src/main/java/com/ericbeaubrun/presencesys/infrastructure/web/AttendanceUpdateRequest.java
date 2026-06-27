package com.ericbeaubrun.presencesys.infrastructure.web;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

/**
 * Corps de la requête pour créer ou modifier un pointage depuis l'interface admin.
 */
public record AttendanceUpdateRequest(
        @NotBlank(message = "L'ID étudiant est obligatoire")
        String studentId,

        @NotBlank(message = "L'ID cours est obligatoire")
        String courseId,

        @NotBlank(message = "Le statut est obligatoire")
        @Pattern(regexp = "present|absent justifie", message = "Statut invalide")
        String status,

        String arrivalTime,    // format HH:mm:ss, nullable
        String justification   // nullable
) {}
