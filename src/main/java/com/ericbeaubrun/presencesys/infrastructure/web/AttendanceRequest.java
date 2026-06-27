package com.ericbeaubrun.presencesys.infrastructure.web;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AttendanceRequest(
        @NotBlank(message = "L'ID du lecteur ne peut pas être vide")
        @Size(min = 5, max = 5, message = "L'ID du lecteur doit faire exactement 5 caractères")
        String readerId,

        @NotBlank(message = "L'ID de la carte ne peut pas être vide")
        @Size(min = 8, max = 8, message = "L'ID de la carte doit faire exactement 8 caractères")
        String cardId
) {}