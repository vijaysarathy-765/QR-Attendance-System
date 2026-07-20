package com.app.attendance.dto;

import java.time.LocalDateTime;

public class AttendanceRecordResponseDTO {

    private String studentId;
    private LocalDateTime attendanceTime;
    private String status;

    public AttendanceRecordResponseDTO() {
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public LocalDateTime getAttendanceTime() {
        return attendanceTime;
    }

    public void setAttendanceTime(LocalDateTime attendanceTime) {
        this.attendanceTime = attendanceTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}