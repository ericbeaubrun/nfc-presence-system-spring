package com.ericbeaubrun.presencesys.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

public interface CourseRepository extends JpaRepository<CourseEntity, String> {

    @Query("SELECT c FROM CourseEntity c WHERE c.roomId = :roomId AND c.date = :date AND :time BETWEEN c.startTime AND c.endTime")
    Optional<CourseEntity> findCurrentCourse(
            @Param("roomId") String roomId,
            @Param("date") LocalDate date,
            @Param("time") LocalTime time
    );
}