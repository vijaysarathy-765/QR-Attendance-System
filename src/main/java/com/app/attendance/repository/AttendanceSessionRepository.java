package com.app.attendance.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.app.attendance.model.AttendanceSession;

public interface AttendanceSessionRepository extends MongoRepository<AttendanceSession, String> {

    Optional<AttendanceSession> findByQrToken(String qrToken);

}