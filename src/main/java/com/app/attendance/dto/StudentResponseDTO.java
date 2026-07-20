package com.app.attendance.dto;

public class StudentResponseDTO {

    private String id;
    private String name;
    private String email;
    private String rollNumber;
    private String department;
    private String token;

    public StudentResponseDTO() {
    }

    public StudentResponseDTO(String id, String name, String email,
                              String rollNumber, String department) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.rollNumber = rollNumber;
        this.department = department;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRollNumber() {
        return rollNumber;
    }

    public void setRollNumber(String rollNumber) {
        this.rollNumber = rollNumber;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}