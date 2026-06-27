package com.ericbeaubrun.presencesys.infrastructure.persistence;

import java.io.Serializable;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class AttendanceId implements Serializable {
    private String studentId;
    private String courseId;
}