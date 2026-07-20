package com.app.attendance.repository;


import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

import com.app.attendance.model.Student;

public interface StudentRepository extends MongoRepository<Student, String> {
	
	Optional<Student> findByEmail(String email);

}