package com.app.attendance.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "attendance_records")
public class AttendanceRecord {

    @Id
    private String id;

    private String studentId;

    private String sessionId;

    private LocalDateTime attendanceTime;

    private String status;

    public AttendanceRecord() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
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