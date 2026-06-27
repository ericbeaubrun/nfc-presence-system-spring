package com.ericbeaubrun.presencesys.domain.service;

import com.ericbeaubrun.presencesys.domain.AttendanceStatus;
import com.ericbeaubrun.presencesys.infrastructure.persistence.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class AttendanceService {

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final AttendanceRepository attendanceRepository;

    @Transactional
    public String registerAttendance(String cardId, String readerId) {
        // 1. Trouver l'étudiant par son badge NFC
        StudentEntity student = studentRepository.findByCardId(cardId)
                .orElseThrow(() -> new IllegalArgumentException("Aucun étudiant trouvé avec la carte NFC : " + cardId));

        // 2. Déterminer la salle (Dans une v2, on pourrait chercher la salle par le readerId,
        String roomId = readerId.replace("L", "S");

        LocalDate today = LocalDate.now();
        LocalTime now = LocalTime.now();

        // 3. Trouver le cours actif en ce moment dans cette salle
        CourseEntity currentCourse = courseRepository.findCurrentCourse(roomId, today, now)
                .orElseThrow(() -> new IllegalArgumentException("Aucun cours n'est programmé dans cette salle en ce moment."));

        // 4. Vérifier si l'étudiant a déjà émis un pointage pour ce cours
        boolean alreadyCheckedIn = attendanceRepository.existsByStudentIdAndCourseId(student.getId(), currentCourse.getId());
        if (alreadyCheckedIn) {
            throw new IllegalArgumentException("L'étudiant a déjà été enregistré (présent) pour ce cours.");
        }

        // 5. Enregistrer la présence
        AttendanceEntity attendance = new AttendanceEntity();
        attendance.setStudentId(student.getId());
        attendance.setCourseId(currentCourse.getId());
        attendance.setStatus(AttendanceStatus.PRESENT.getValue());
        attendance.setArrivalTime(now);
        attendance.setJustification(null);

        attendanceRepository.save(attendance);

        return "Présence enregistrée avec succès pour l'étudiant " + student.getId() + " au cours " + currentCourse.getId();
    }
}