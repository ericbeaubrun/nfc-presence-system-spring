package com.ericbeaubrun.presencesys.infrastructure.web;

import com.ericbeaubrun.presencesys.domain.service.AdminAttendanceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Contrôleur REST réservé aux administrateurs.
 * Toutes les routes sont sous /api/admin/** et protégées par Spring Security (rôle ADMIN).
 */
@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminAttendanceService adminAttendanceService;

    /**
     * Vérifie que les credentials sont valides et retourne les infos de l'utilisateur connecté.
     * Utilisé par le front JS pour valider la session au login.
     */
    @GetMapping("/me")
    public ResponseEntity<Map<String, String>> me(Authentication auth) {
        return ResponseEntity.ok(Map.of(
                "username", auth.getName(),
                "role", auth.getAuthorities().iterator().next().getAuthority()
        ));
    }

    /**
     * Retourne la liste complète de tous les pointages avec les détails enrichis.
     */
    @GetMapping("/attendances")
    public ResponseEntity<List<AttendanceDto>> getAllAttendances() {
        return ResponseEntity.ok(adminAttendanceService.getAllAttendances());
    }

    /**
     * Crée un nouveau pointage manuel.
     */
    @PostMapping("/attendances")
    public ResponseEntity<AttendanceDto> createAttendance(
            @Valid @RequestBody AttendanceUpdateRequest request) {
        try {
            AttendanceDto created = adminAttendanceService.createAttendance(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Met à jour le statut / heure / justificatif d'un pointage existant.
     */
    @PutMapping("/attendances")
    public ResponseEntity<AttendanceDto> updateAttendance(
            @Valid @RequestBody AttendanceUpdateRequest request) {
        try {
            AttendanceDto updated = adminAttendanceService.updateAttendance(request);
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Supprime un pointage identifié par studentId et courseId (passés en query params).
     */
    @DeleteMapping("/attendances")
    public ResponseEntity<Map<String, String>> deleteAttendance(
            @RequestParam String studentId,
            @RequestParam String courseId) {
        try {
            adminAttendanceService.deleteAttendance(studentId, courseId);
            return ResponseEntity.ok(Map.of("message", "Pointage supprimé avec succès."));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", e.getMessage()));
        }
    }
}
