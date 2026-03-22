#  Attendance Management System (Spring Boot + JWT + WiFi Security)

##  Overview

A **secure and scalable Attendance Management System** built using **Spring Boot**.
This system allows employees to mark attendance and admins to monitor attendance with advanced features like **JWT authentication, role-based access, PDF reports, dashboard analytics, and WiFi/IP validation**.

---

##  Features

###  Authentication & Security

* JWT-based login system
* Role-based access (ADMIN / EMPLOYEE)
* Secure APIs using token authentication

###  Employee Features

* Login system
* Check-In / Check-Out
* Late detection (after 9:30 AM → LATE)

###  Admin Features

* View all attendance records
* Dashboard analytics:

  * Total employees
  * Present
  * Late
  * Absent
* Download attendance reports as PDF
* Date-wise attendance filtering

###  Advanced Features

* WiFi/IP-based attendance restriction (office-only access)
* Structured API responses (DTO-based)
* Global exception handling

---

##  Tech Stack

* **Backend:** Spring Boot
* **Database:** PostgreSQL
* **Security:** JWT (JSON Web Token)
* **ORM:** Spring Data JPA (Hibernate)
* **Build Tool:** Maven
* **PDF Generation:** iText
* **Version Control:** Git & GitHub

---

##  Project Structure

```
com.company.attendance
│
├── controller
├── service
│   ├── impl
├── repository
├── entity
├── dto
├── security
├── exception
```

---

##  Setup Instructions

### 1️ Clone Repository

```bash
git clone https://github.com/your-username/attendance-system.git
cd attendance-system
```

### 2️ Configure Database

Update `application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/attendance_db
spring.datasource.username=your_username
spring.datasource.password=your_password
```

---

### 3️ Run Application

```bash
mvn spring-boot:run
```

---

##  API Endpoints

###  Authentication

* `POST /api/auth/login`

###  Employee

* `POST /api/attendance/check-in`
* `POST /api/attendance/check-out`

###  Admin

* `GET /api/attendance/all`
* `GET /api/attendance/dashboard?date=YYYY-MM-DD`
* `GET /api/attendance/pdf/{date}`

---

##  Sample Dashboard Response

```json
{
  "total": 10,
  "present": 6,
  "late": 3,
  "absent": 1
}
```

---

##  Security Flow

```
Login → JWT Token → API Request → Token Validation → Access Granted/Denied
```

---

##  Future Enhancements

* Face Recognition Attendance
* Mobile App Integration
* Cloud Deployment (AWS)
* Email Notifications
* Monthly Reports

---

##  Author

**Sreekar Varala**

* Java Backend Developer
* Skills: Java, Spring Boot, SQL, DevOps

---

##  If you like this project

Give it a ⭐ on GitHub and share your feedback!
