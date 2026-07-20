package com.app.attendance.controller;

import org.springframework.http.HttpStatus;
import com.app.attendance.dto.StudentRequestDTO;
import com.app.attendance.dto.StudentResponseDTO;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.app.attendance.exception.DuplicateEmailException;
import com.app.attendance.dto.ApiResponse;
import com.app.attendance.dto.LoginRequestDTO;
import com.app.attendance.dto.LoginResponseDTO;
import com.app.attendance.security.JwtService;

import com.app.attendance.model.Student;
import com.app.attendance.service.StudentService;

@RestController
@RequestMapping("/students")
@CrossOrigin(origins = "http://localhost:5173")
public class StudentController {

    private final StudentService studentService;
    private final JwtService jwtService;
    

    public StudentController(StudentService studentService,
            JwtService jwtService) 
    {
    	this.studentService = studentService;
    	this.jwtService = jwtService;
    	}

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<StudentResponseDTO>> registerStudent(
            @Valid @RequestBody StudentRequestDTO studentRequestDTO) {

        // Convert Request DTO -> Entity
        Student student = new Student();

        student.setName(studentRequestDTO.getName());
        student.setEmail(studentRequestDTO.getEmail());
        student.setPassword(studentRequestDTO.getPassword());
        student.setRollNumber(studentRequestDTO.getRollNumber());
        student.setDepartment(studentRequestDTO.getDepartment());

        // Check for duplicate email
        if (studentService.emailExists(student.getEmail())) {
            throw new DuplicateEmailException("Email already registered");
        }

        // Save to MongoDB
        Student savedStudent = studentService.registerStudent(student);

        // Convert Entity -> Response DTO
        StudentResponseDTO responseDTO = new StudentResponseDTO();

        responseDTO.setId(savedStudent.getId());
        responseDTO.setName(savedStudent.getName());
        responseDTO.setEmail(savedStudent.getEmail());
        responseDTO.setRollNumber(savedStudent.getRollNumber());
        responseDTO.setDepartment(savedStudent.getDepartment());

        // Wrap inside ApiResponse
        ApiResponse<StudentResponseDTO> response =
                new ApiResponse<>(
                        true,
                        "Student registered successfully",
                        responseDTO
                );

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponseDTO>> loginStudent(
            @Valid @RequestBody LoginRequestDTO loginRequestDTO) {

    	Student student = studentService.loginStudent(
    	        loginRequestDTO.getEmail(),
    	        loginRequestDTO.getPassword());

    	String token = jwtService.generateToken(student.getEmail());

    	LoginResponseDTO responseDTO = new LoginResponseDTO();

    	responseDTO.setId(student.getId());
    	responseDTO.setName(student.getName());
    	responseDTO.setEmail(student.getEmail());
    	responseDTO.setToken(token);

        ApiResponse<LoginResponseDTO> response =
                new ApiResponse<>(
                        true,
                        "Login successful",
                        responseDTO);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<?> getAllStudents() {

        return ResponseEntity.ok(studentService.getAllStudents());

    }
    

}