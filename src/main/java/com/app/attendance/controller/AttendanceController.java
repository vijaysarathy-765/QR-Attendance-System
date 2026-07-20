package com.app.attendance.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.app.attendance.dto.ApiResponse;
import com.app.attendance.dto.AttendanceCountResponseDTO;
import com.app.attendance.dto.AttendanceRecordResponseDTO;
import com.app.attendance.dto.AttendanceRequestDTO;
import com.app.attendance.dto.AttendanceResponseDTO;
import com.app.attendance.service.AttendanceService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/attendance")
@Validated
public class AttendanceController {

    private final AttendanceService attendanceService;

    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    @PostMapping("/mark")
    public ResponseEntity<ApiResponse<AttendanceResponseDTO>> markAttendance(
            @Valid @RequestBody AttendanceRequestDTO requestDTO) {

        return attendanceService.markAttendance(requestDTO);
    }
    
    @GetMapping("/session/{sessionId}")
    public ResponseEntity<ApiResponse<List<AttendanceRecordResponseDTO>>>
            getAttendanceBySession(
                    @PathVariable String sessionId) {

        return attendanceService.getAttendanceBySession(sessionId);
    }
    
    @GetMapping("/session/{sessionId}/count")
    public ResponseEntity<ApiResponse<AttendanceCountResponseDTO>>
            getAttendanceCount(
                    @PathVariable String sessionId) {

        return attendanceService.getAttendanceCount(sessionId);
    }
}