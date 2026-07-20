package com.app.attendance.service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.app.attendance.dto.ApiResponse;
import com.app.attendance.dto.AttendanceSessionRequestDTO;
import com.app.attendance.dto.AttendanceSessionResponseDTO;
import com.app.attendance.model.AttendanceSession;
import com.app.attendance.repository.AttendanceSessionRepository;
import com.app.attendance.util.QRCodeGenerator;

@Service
public class AttendanceSessionService {

    private final AttendanceSessionRepository attendanceSessionRepository;
    private final QRCodeGenerator qrCodeGenerator;

    public AttendanceSessionService(
            AttendanceSessionRepository attendanceSessionRepository,
            QRCodeGenerator qrCodeGenerator) {

        this.attendanceSessionRepository = attendanceSessionRepository;
        this.qrCodeGenerator = qrCodeGenerator;
    }

    public AttendanceSessionResponseDTO createSession(
            AttendanceSessionRequestDTO requestDTO) {

        AttendanceSession session = new AttendanceSession();

        // Copy data from DTO
        session.setSubject(requestDTO.getSubject());
        session.setFacultyName(requestDTO.getFacultyName());

        // Current time
        LocalDateTime startTime = LocalDateTime.now();

        session.setStartTime(startTime);

        // Calculate end time
        session.setEndTime(
                startTime.plusMinutes(
                        requestDTO.getDurationInMinutes()));

        // Generate secure QR token
        session.setQrToken(UUID.randomUUID().toString());

        // Session is active
        session.setActive(true);

        // Save into MongoDB
        AttendanceSession savedSession =
                attendanceSessionRepository.save(session);

        // Convert Entity -> Response DTO
        return convertToDTO(savedSession);
    }
    
    
    public byte[] generateQRCode(String sessionId) throws Exception {

        AttendanceSession session =
                attendanceSessionRepository
                        .findById(sessionId)
                        .orElseThrow(() ->
                                new RuntimeException("Attendance session not found"));

        return qrCodeGenerator.generateQRCode(
                session.getQrToken(),
                300,
                300);
    }
    public ResponseEntity<ApiResponse<AttendanceSessionResponseDTO>> endSession(
            String sessionId) {

        Optional<AttendanceSession> optionalSession =
                attendanceSessionRepository.findById(sessionId);

        if (optionalSession.isEmpty()) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(
                            false,
                            "Attendance session not found",
                            null));
        }

        AttendanceSession session = optionalSession.get();
        
        if (!session.isActive()) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(
                            false,
                            "Attendance session is already ended",
                            null));
        }

        session.setActive(false);

        attendanceSessionRepository.save(session);

        AttendanceSessionResponseDTO responseDTO =
                convertToDTO(session);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Attendance session ended successfully",
                        responseDTO));
    }
    
    public List<AttendanceSessionResponseDTO> getAllSessions() {

        List<AttendanceSession> sessions =
                attendanceSessionRepository.findAll();

        List<AttendanceSessionResponseDTO> responseList =
                new ArrayList<>();

        for (AttendanceSession session : sessions) {

        	responseList.add(convertToDTO(session));
        }

        return responseList;
    }
    
    private AttendanceSessionResponseDTO convertToDTO(AttendanceSession session) {

        AttendanceSessionResponseDTO dto =
                new AttendanceSessionResponseDTO();

        dto.setId(session.getId());
        dto.setSubject(session.getSubject());
        dto.setFacultyName(session.getFacultyName());
        dto.setStartTime(session.getStartTime());
        dto.setEndTime(session.getEndTime());
        dto.setQrToken(session.getQrToken());
        dto.setActive(session.isActive());

        return dto;
    }
    
    
    

}