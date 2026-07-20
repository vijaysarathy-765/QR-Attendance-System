# QR Attendance Management System

> 🚧 **Project Status:** Backend Complete | Frontend Under Development

A secure **QR Code-based Attendance Management System** built using **Spring Boot**, **Spring Security**, **MongoDB**, and **JWT Authentication**.

The system enables faculty members to create attendance sessions with unique QR codes while allowing students to securely mark attendance using JWT authentication. Duplicate attendance is prevented, and attendance records are automatically associated with the authenticated student.

---

# Features

## Authentication

- Student Registration
- Student Login
- BCrypt Password Encryption
- JWT Authentication
- Protected REST APIs
- Secure User Authentication with Spring Security

---

## Attendance Session Management

- Create Attendance Sessions
- Generate QR Codes
- End Attendance Sessions
- View All Attendance Sessions

---

## Attendance Management

- Mark Attendance Using QR Code
- Duplicate Attendance Prevention
- Session Expiry Validation
- Attendance Count per Session
- View Attendance Records by Session

---

## Security

- Spring Security
- JWT Authorization
- BCrypt Password Hashing
- Protected REST Endpoints
- Authentication-based Attendance
- Student Identity Derived from JWT
- No Client-side Student ID Manipulation

---

# Tech Stack

## Backend

- Java 24
- Spring Boot
- Spring Security
- Spring Data MongoDB
- Maven
- JWT (JSON Web Token)
- ZXing QR Code Generator

## Database

- MongoDB

## Frontend (In Progress)

- React
- React Router
- Axios

---

# Project Architecture

```
                React Frontend
                      │
                      ▼
                REST API (HTTP)
                      │
                      ▼
               Spring Boot Backend
                      │
          ┌───────────┴───────────┐
          ▼                       ▼
 Spring Security             QR Generator
          │
          ▼
      MongoDB Database
```

---

# Project Structure

```
src
├── config
├── controller
├── dto
├── exception
├── model
├── repository
├── security
├── service
└── util
```

---

# REST API

## Authentication

| Method | Endpoint |
|----------|-----------------------|
| POST | `/students/register` |
| POST | `/students/login` |

---

## Attendance Sessions

| Method | Endpoint |
|----------|-------------------------------------|
| POST | `/attendance-sessions` |
| GET | `/attendance-sessions` |
| GET | `/attendance-sessions/{id}/qr` |
| PUT | `/attendance-sessions/{id}/end` |

---

## Attendance

| Method | Endpoint |
|----------|-----------------------------------------|
| POST | `/attendance/mark` |
| GET | `/attendance/session/{id}` |
| GET | `/attendance/session/{id}/count` |

---

# Authentication Flow

```
Student Login
      │
      ▼
Receive JWT Token
      │
      ▼
JWT Stored by Client
      │
      ▼
Authorization: Bearer <JWT>
      │
      ▼
Spring Security Filter
      │
      ▼
JWT Validation
      │
      ▼
Authenticated Student
      │
      ▼
Attendance Recorded
```

---

# Getting Started

## Prerequisites

- Java 24
- Maven
- MongoDB

---

## Clone the Repository

```bash
git clone https://github.com/vijaysarathy-765/QR-Attendance-System.git
```

---

## Navigate to the Project

```bash
cd QR-Attendance-System
```

---

## Configure MongoDB

Update the following file:

```
src/main/resources/application.properties
```

Configure:

- MongoDB URI
- Database Name
- JWT Secret

---

## Run the Application

Using Maven Wrapper:

```bash
./mvnw spring-boot:run
```

Or with Maven:

```bash
mvn spring-boot:run
```

The backend starts at:

```
http://localhost:8080
```

---

# Security Features

- JWT Authentication
- Spring Security
- BCrypt Password Encryption
- Stateless Authentication
- Protected REST APIs
- Authentication-based Attendance
- Duplicate Attendance Prevention
- Session Validation

---

# Future Enhancements

## Frontend

- React Frontend
- Faculty Dashboard
- Student Dashboard
- QR Code Scanner
- Attendance History
- Responsive UI

## Backend

- Swagger API Documentation
- Docker Support
- Unit & Integration Testing
- Role-Based Authorization (Faculty/Admin)
- Attendance Analytics
- Email Notifications

---

# Screenshots

Frontend implementation is currently in progress.

The following screenshots will be added after completion:

- Login Page
- Student Registration
- Student Dashboard
- Faculty Dashboard
- QR Code Generation
- QR Code Scanner
- Attendance History
- Attendance Reports

---

# Current Project Progress

| Module | Status |
|----------|---------|
| Student Registration | ✅ Completed |
| Student Login | ✅ Completed |
| JWT Authentication | ✅ Completed |
| Spring Security | ✅ Completed |
| Password Encryption | ✅ Completed |
| Attendance Sessions | ✅ Completed |
| QR Code Generation | ✅ Completed |
| Attendance Marking | ✅ Completed |
| Attendance Count | ✅ Completed |
| Attendance Records | ✅ Completed |
| MongoDB Integration | ✅ Completed |
| React Frontend | 🚧 In Progress |

---

# Author

**Vijay Sarathy**

Computer Science Engineering Student

GitHub: https://github.com/vijaysarathy-765

---

## License

This project is intended for educational and portfolio purposes.
