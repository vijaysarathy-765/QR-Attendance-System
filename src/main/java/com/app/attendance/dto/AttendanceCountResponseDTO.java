package com.app.attendance.dto;

public class AttendanceCountResponseDTO {

    private String sessionId;
    private long count;

    public AttendanceCountResponseDTO() {
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }
}