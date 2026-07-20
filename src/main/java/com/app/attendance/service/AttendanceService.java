package com.app.attendance.service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.app.attendance.dto.ApiResponse;
import com.app.attendance.model.Student;
import com.app.attendance.repository.StudentRepository;
import com.app.attendance.dto.AttendanceCountResponseDTO;
import com.app.attendance.dto.AttendanceRequestDTO;
import com.app.attendance.dto.AttendanceResponseDTO;
import com.app.attendance.model.AttendanceRecord;
import com.app.attendance.model.AttendanceSession;
import com.app.attendance.repository.AttendanceRecordRepository;
import com.app.attendance.repository.AttendanceSessionRepository;
import com.app.attendance.dto.AttendanceRecordResponseDTO;

@Service
public class AttendanceService {

    private final AttendanceRecordRepository attendanceRecordRepository;
    private final AttendanceSessionRepository attendanceSessionRepository;
    private final StudentRepository studentRepository;

    public AttendanceService(
            AttendanceRecordRepository attendanceRecordRepository,
            AttendanceSessionRepository attendanceSessionRepository,StudentRepository studentRepository) {

        this.attendanceRecordRepository = attendanceRecordRepository;
        this.attendanceSessionRepository = attendanceSessionRepository;
        this.studentRepository=studentRepository;
    }
    
    public ResponseEntity<ApiResponse<AttendanceResponseDTO>> markAttendance(
            AttendanceRequestDTO requestDTO) {

        Optional<AttendanceSession> optionalSession =
                attendanceSessionRepository.findById(requestDTO.getSessionId());

        if (optionalSession.isEmpty()) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(
                            false,
                            "Attendance session not found",
                            null));
        }

        AttendanceSession session = optionalSession.get();
        
        if (!session.isActive()) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(
                            false,
                            "Attendance session is no longer active",
                            null));
        }
        
        if (LocalDateTime.now().isAfter(session.getEndTime())) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(
                            false,
                            "Attendance session has expired",
                            null));
        }
        
        if (!session.getQrToken().equals(requestDTO.getQrToken())) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(
                            false,
                            "Invalid QR Code",
                            null));
        }
        
        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();
        UserDetails userDetails =
                (UserDetails) authentication.getPrincipal();

        String email = userDetails.getUsername();
        Student student = studentRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("Student not found"));

        String studentId = student.getId();
        
        Optional<AttendanceRecord> existingAttendance =
                attendanceRecordRepository.findByStudentIdAndSessionId(
                        studentId,
                        requestDTO.getSessionId());

        if (existingAttendance.isPresent()) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(
                            false,
                            "Attendance already marked",
                            null));
        }
        
        AttendanceRecord attendanceRecord = new AttendanceRecord();

        attendanceRecord.setStudentId(studentId);
        attendanceRecord.setSessionId(requestDTO.getSessionId());
        attendanceRecord.setAttendanceTime(LocalDateTime.now());
        attendanceRecord.setStatus("PRESENT");

        attendanceRecordRepository.save(attendanceRecord);
        
        AttendanceResponseDTO responseDTO = new AttendanceResponseDTO();

        responseDTO.setStudentId(attendanceRecord.getStudentId());
        responseDTO.setSessionId(attendanceRecord.getSessionId());
        responseDTO.setAttendanceTime(attendanceRecord.getAttendanceTime());
        responseDTO.setStatus(attendanceRecord.getStatus());
        
        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Attendance marked successfully",
                        responseDTO));

        
    }
    
    public ResponseEntity<ApiResponse<List<AttendanceRecordResponseDTO>>> getAttendanceBySession(String sessionId) {

        // Check if the attendance session exists
        Optional<AttendanceSession> optionalSession =
                attendanceSessionRepository.findById(sessionId);

        if (optionalSession.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(
                            false,
                            "Attendance session not found",
                            null));
        }

        // Fetch attendance records for the session
        List<AttendanceRecord> attendanceRecords =
                attendanceRecordRepository.findBySessionId(sessionId);

        // Convert entities to DTOs
        List<AttendanceRecordResponseDTO> responseList =
                new ArrayList<>();

        for (AttendanceRecord attendanceRecord : attendanceRecords) {

            AttendanceRecordResponseDTO dto =
                    new AttendanceRecordResponseDTO();

            dto.setStudentId(attendanceRecord.getStudentId());
            dto.setAttendanceTime(attendanceRecord.getAttendanceTime());
            dto.setStatus(attendanceRecord.getStatus());

            responseList.add(dto);
        }

        // Return the attendance records (empty list if none exist)
        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Attendance records fetched successfully",
                        responseList));
    }
    
    public ResponseEntity<ApiResponse<AttendanceCountResponseDTO>>
    getAttendanceCount(String sessionId) {

Optional<AttendanceSession> optionalSession =
        attendanceSessionRepository.findById(sessionId);

if (optionalSession.isEmpty()) {

    return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(new ApiResponse<>(
                    false,
                    "Attendance session not found",
                    null));
}

long count =
        attendanceRecordRepository.countBySessionId(sessionId);

AttendanceCountResponseDTO response =
        new AttendanceCountResponseDTO();

response.setSessionId(sessionId);
response.setCount(count);

return ResponseEntity.ok(
        new ApiResponse<>(
                true,
                "Attendance count fetched successfully",
                response));
}    	
    	
    }

