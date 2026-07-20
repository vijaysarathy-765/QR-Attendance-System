# QR Attendance Management System

A secure QR Code based Attendance Management System built using **Spring Boot**, **Spring Security**, **MongoDB**, and **JWT Authentication**.

The system enables faculty to create attendance sessions with unique QR codes while allowing students to securely mark attendance using JWT authentication. Duplicate attendance is prevented and attendance is automatically linked to the authenticated student.

---

## Features

### Authentication
- Student Registration
- Student Login
- BCrypt Password Encryption
- JWT Authentication
- Protected REST APIs

### Attendance Sessions
- Create Attendance Session
- Generate QR Code
- End Attendance Session
- View All Sessions

### Attendance
- Mark Attendance using QR Code
- Duplicate Attendance Prevention
- Session Expiry Validation
- Attendance Count
- View Attendance by Session

### Security
- Spring Security
- JWT Authorization
- Password Hashing using BCrypt
- Authentication-based Attendance

---

## Tech Stack

### Backend

- Java 24
- Spring Boot
- Spring Security
- Spring Data MongoDB
- Maven
- JWT
- ZXing QR Generator

### Database

- MongoDB

### Frontend (Coming Soon)

- React
- React Router
- Axios

---

## Project Structure

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

## REST API

### Authentication

| Method | Endpoint |
|---------|----------|
| POST | /students/register |
| POST | /students/login |

---

### Attendance Sessions

| Method | Endpoint |
|---------|----------|
| POST | /attendance-sessions |
| GET | /attendance-sessions |
| GET | /attendance-sessions/{id}/qr |
| PUT | /attendance-sessions/{id}/end |

---

### Attendance

| Method | Endpoint |
|---------|----------|
| POST | /attendance/mark |
| GET | /attendance/session/{id} |
| GET | /attendance/session/{id}/count |

---

## Security Flow

```
Student Login
      │
      ▼
Receive JWT Token
      │
      ▼
JWT sent in Authorization Header
      │
      ▼
Spring Security
      │
      ▼
Authenticated Student
      │
      ▼
Attendance Marked Securely
```

---

## Future Enhancements

- React Frontend
- Faculty Dashboard
- Student Dashboard
- QR Scanner Integration
- Attendance Analytics
- Email Notifications
- Docker Deployment
- Swagger Documentation

---

## Author

**Vijay Sarathy**

