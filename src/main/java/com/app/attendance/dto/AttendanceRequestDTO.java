package com.app.attendance.dto;

import jakarta.validation.constraints.NotBlank;

public class AttendanceRequestDTO {

  ;

    @NotBlank(message = "Session ID is required")
    private String sessionId;

    @NotBlank(message = "QR Token is required")
    private String qrToken;

    public AttendanceRequestDTO() {
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getQrToken() {
        return qrToken;
    }

    public void setQrToken(String qrToken) {
        this.qrToken = qrToken;
    }
}