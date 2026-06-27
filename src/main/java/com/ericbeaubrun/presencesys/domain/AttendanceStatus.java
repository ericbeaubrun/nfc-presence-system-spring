package com.ericbeaubrun.presencesys.domain;

import lombok.Getter;

@Getter
public enum AttendanceStatus {
    PRESENT("present"),
    ABSENT_JUSTIFIE("absent justifie");

    private final String value;

    AttendanceStatus(String value) {
        this.value = value;
    }

}