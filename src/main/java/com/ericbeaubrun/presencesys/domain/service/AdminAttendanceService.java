package com.ericbeaubrun.presencesys.domain.service;

import com.ericbeaubrun.presencesys.infrastructure.persistence.*;
import com.ericbeaubrun.presencesys.infrastructure.web.AttendanceDto;
import com.ericbeaubrun.presencesys.infrastructure.web.AttendanceUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.List;

/**
 * Service métier pour l'administration des pointages.
 * Fournit les opérations CRUD nécessaires à l'interface admin.
 */
@Service
@RequiredArgsConstructor
public class AdminAttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final StudentRepository    studentRepository;
    private final CourseRepository     courseRepository;
    private final PersonRepository     personRepository;

    /**
     * Récupère tous les pointages enrichis avec les noms des étudiants et les détails des cours.
     */
    @Transactional(readOnly = true)
    public List<AttendanceDto> getAllAttendances() {
        return attendanceRepository.findAll().stream()
                .map(this::toDto)
                .toList();
    }

    /**
     * Crée un nouveau pointage. Refuse si un pointage existe déjà pour le même étudiant/cours.
     */
    @Transactional
    public AttendanceDto createAttendance(AttendanceUpdateRequest req) {
        var id = new AttendanceId();
        id.setStudentId(req.studentId());
        id.setCourseId(req.courseId());

        if (attendanceRepository.existsById(id)) {
            throw new IllegalArgumentException(
                "Un pointage existe déjà pour l'étudiant " + req.studentId() + " au cours " + req.courseId()
            );
        }

        // Validation de l'existence de l'étudiant et du cours
        studentRepository.findById(req.studentId())
                .orElseThrow(() -> new IllegalArgumentException("Étudiant introuvable : " + req.studentId()));
        courseRepository.findById(req.courseId())
                .orElseThrow(() -> new IllegalArgumentException("Cours introuvable : " + req.courseId()));

        AttendanceEntity entity = new AttendanceEntity();
        entity.setStudentId(req.studentId());
        entity.setCourseId(req.courseId());
        entity.setStatus(req.status());
        entity.setArrivalTime(parseTime(req.arrivalTime()));
        entity.setJustification(req.justification());

        return toDto(attendanceRepository.save(entity));
    }

    /**
     * Met à jour un pointage existant (statut, heure d'arrivée, justificatif).
     */
    @Transactional
    public AttendanceDto updateAttendance(AttendanceUpdateRequest req) {
        var id = new AttendanceId();
        id.setStudentId(req.studentId());
        id.setCourseId(req.courseId());

        AttendanceEntity entity = attendanceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                    "Pointage introuvable pour l'étudiant " + req.studentId() + " au cours " + req.courseId()
                ));

        entity.setStatus(req.status());
        entity.setArrivalTime(parseTime(req.arrivalTime()));
        entity.setJustification(req.justification());

        return toDto(attendanceRepository.save(entity));
    }

    /**
     * Supprime un pointage identifié par (studentId, courseId).
     */
    @Transactional
    public void deleteAttendance(String studentId, String courseId) {
        var id = new AttendanceId();
        id.setStudentId(studentId);
        id.setCourseId(courseId);

        if (!attendanceRepository.existsById(id)) {
            throw new IllegalArgumentException(
                "Pointage introuvable pour l'étudiant " + studentId + " au cours " + courseId
            );
        }
        attendanceRepository.deleteById(id);
    }

    // -------------------------------------------------------------------------
    // Méthodes privées utilitaires
    // -------------------------------------------------------------------------

    private AttendanceDto toDto(AttendanceEntity entity) {
        // Nom de l'étudiant (via personnes)
        String fullName = personRepository.findById(entity.getStudentId())
                .map(p -> p.getFirstName() + " " + p.getLastName())
                .orElse(entity.getStudentId());

        // Détails du cours
        String courseDate      = "";
        String courseStartTime = "";
        String courseEndTime   = "";
        String roomId          = "";

        var courseOpt = courseRepository.findById(entity.getCourseId());
        if (courseOpt.isPresent()) {
            var course = courseOpt.get();
            courseDate      = course.getDate()      != null ? course.getDate().toString()      : "";
            courseStartTime = course.getStartTime() != null ? course.getStartTime().toString() : "";
            courseEndTime   = course.getEndTime()   != null ? course.getEndTime().toString()   : "";
            roomId          = course.getRoomId()    != null ? course.getRoomId()               : "";
        }

        return new AttendanceDto(
                entity.getStudentId(),
                fullName,
                entity.getCourseId(),
                courseDate,
                courseStartTime,
                courseEndTime,
                roomId,
                entity.getStatus(),
                entity.getArrivalTime() != null ? entity.getArrivalTime().toString() : null,
                entity.getJustification()
        );
    }

    private LocalTime parseTime(String time) {
        if (time == null || time.isBlank()) return null;
        return LocalTime.parse(time);
    }
}
