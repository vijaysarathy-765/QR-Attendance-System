package com.app.attendance.service;

import java.util.List;


import org.springframework.stereotype.Service;

import com.app.attendance.exception.InvalidCredentialsException;
import com.app.attendance.model.Student;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.app.attendance.repository.StudentRepository;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;

    public StudentService(StudentRepository studentRepository,
            PasswordEncoder passwordEncoder) {

this.studentRepository = studentRepository;
this.passwordEncoder = passwordEncoder;
}

    public Student registerStudent(Student student) {

        student.setPassword(
                passwordEncoder.encode(student.getPassword())
        );

        return studentRepository.save(student);
    }

    public List<Student> getAllStudents() {

        return studentRepository.findAll();

    }
    public boolean emailExists(String email) {
        return studentRepository.findByEmail(email).isPresent();
    }
    public Student loginStudent(String email, String password) {

        Student student = studentRepository.findByEmail(email)
                .orElseThrow(() ->
                        new InvalidCredentialsException("Invalid email or password"));

        if (!passwordEncoder.matches(password, student.getPassword())) {
            throw new InvalidCredentialsException("Invalid email or password");
        }

        return student;
    }

}