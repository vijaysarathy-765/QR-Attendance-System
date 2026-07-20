package com.app.attendance.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.app.attendance.dto.ApiResponse;
import com.app.attendance.dto.AttendanceSessionRequestDTO;
import com.app.attendance.dto.AttendanceSessionResponseDTO;
import com.app.attendance.service.AttendanceSessionService;
import org.springframework.http.MediaType;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/attendance-sessions")
@CrossOrigin(origins = "http://localhost:5173")
public class AttendanceSessionController {

    private final AttendanceSessionService attendanceSessionService;

    public AttendanceSessionController(
            AttendanceSessionService attendanceSessionService) {
        this.attendanceSessionService = attendanceSessionService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<AttendanceSessionResponseDTO>> createSession(
            @Valid @RequestBody AttendanceSessionRequestDTO requestDTO) {

        AttendanceSessionResponseDTO responseDTO =
                attendanceSessionService.createSession(requestDTO);

        ApiResponse<AttendanceSessionResponseDTO> response =
                new ApiResponse<>(
                        true,
                        "Attendance session created successfully",
                        responseDTO);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    
    @GetMapping(value = "/{sessionId}/qr", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getQRCode(
            @PathVariable String sessionId) throws Exception {

        byte[] qrImage = attendanceSessionService.generateQRCode(sessionId);

        return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(qrImage);
    }
    
    @PutMapping("/{sessionId}/end")
    public ResponseEntity<ApiResponse<AttendanceSessionResponseDTO>> endSession(
            @PathVariable String sessionId) {

        return attendanceSessionService.endSession(sessionId);
    }
    
    @GetMapping
    public ResponseEntity<ApiResponse<List<AttendanceSessionResponseDTO>>> getAllSessions() {

        List<AttendanceSessionResponseDTO> sessions =
                attendanceSessionService.getAllSessions();

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Attendance sessions retrieved successfully",
                        sessions));
    }
}