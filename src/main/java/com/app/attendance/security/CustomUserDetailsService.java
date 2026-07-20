package com.app.attendance.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.app.attendance.model.Student;
import com.app.attendance.repository.StudentRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final StudentRepository studentRepository;

    public CustomUserDetailsService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        Student student = studentRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found"));

        return User.builder()
                .username(student.getEmail())
                .password(student.getPassword())
                .authorities("ROLE_STUDENT")
                .build();
    }
}