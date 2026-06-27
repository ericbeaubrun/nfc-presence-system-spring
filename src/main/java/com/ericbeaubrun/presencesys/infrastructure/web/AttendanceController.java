package com.ericbeaubrun.presencesys.infrastructure.web;

import com.ericbeaubrun.presencesys.domain.service.AttendanceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/attendances")
@RequiredArgsConstructor
public class AttendanceController {

    private final AttendanceService attendanceService;

    @PostMapping
    public ResponseEntity<String> scanCard(@Valid @RequestBody AttendanceRequest request) {
        try {
            String message = attendanceService.registerAttendance(request.cardId(), request.readerId());
            return ResponseEntity.ok(message);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erreur serveur : " + e.getMessage());
        }
    }
}