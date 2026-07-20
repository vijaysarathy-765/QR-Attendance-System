package com.app.attendance.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class AttendanceSessionRequestDTO {

    @NotBlank(message = "Subject is required")
    private String subject;

    @NotBlank(message = "Faculty name is required")
    private String facultyName;

    @Min(value = 1, message = "Duration must be at least 1 minute")
    private int durationInMinutes;

    public AttendanceSessionRequestDTO() {
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getFacultyName() {
        return facultyName;
    }

    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }

    public int getDurationInMinutes() {
        return durationInMinutes;
    }

    public void setDurationInMinutes(int durationInMinutes) {
        this.durationInMinutes = durationInMinutes;
    }
}