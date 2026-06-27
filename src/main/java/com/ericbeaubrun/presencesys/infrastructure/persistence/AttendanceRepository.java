package com.ericbeaubrun.presencesys.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendanceRepository extends JpaRepository<AttendanceEntity, AttendanceId> {
    boolean existsByStudentIdAndCourseId(String studentId, String courseId);
}