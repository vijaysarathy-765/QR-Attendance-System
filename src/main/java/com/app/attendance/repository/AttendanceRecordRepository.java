package com.app.attendance.repository;

import java.util.Optional;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.app.attendance.model.AttendanceRecord;

public interface AttendanceRecordRepository
        extends MongoRepository<AttendanceRecord, String> {

    Optional<AttendanceRecord> findByStudentIdAndSessionId(
            String studentId,
            String sessionId);
    
    
    List<AttendanceRecord> findBySessionId(String sessionId);
    
    long countBySessionId(String sessionId);

}