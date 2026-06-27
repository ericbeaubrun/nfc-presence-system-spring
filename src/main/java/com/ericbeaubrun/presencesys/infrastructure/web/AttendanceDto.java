package com.ericbeaubrun.presencesys.infrastructure.web;

public record AttendanceDto(
        String studentId,
        String studentFullName,
        String courseId,
        String courseDate,
        String courseStartTime,
        String courseEndTime,
        String roomId,
        String status,
        String arrivalTime,
        String justification
) {}
