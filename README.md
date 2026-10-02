# Student Management REST API

A production-ready, RESTful Student Management API built with **Java 17**, **Spring Boot 3.x**, **Spring Data JPA**, and **MySQL**, adhering to clean architectural layering and best REST design practices.

---

## 🚀 Tech Stack

- **Language:** Java 17
- **Framework:** Spring Boot 3.3.4
- **Modules:**
  - Spring Web (`spring-boot-starter-web`)
  - Spring Data JPA (`spring-boot-starter-data-jpa`)
  - Spring Validation (`spring-boot-starter-validation`)
- **Database:** MySQL (`mysql-connector-j`) / H2 (test runtime)
- **Build Tool:** Maven & Maven Wrapper (`mvnw` / `mvnw.cmd`)
- **Architecture Pattern:** Controller → Service → Repository (Layered Architecture with Constructor Injection)

---

## 📁 Project Structure

```text
student-management-backend-priyan/
├── .mvn/
│   └── wrapper/
│       ├── maven-wrapper.jar
│       └── maven-wrapper.properties
├── database/
│   └── schema.sql                          # MySQL database creation & seed data
├── postman/
│   └── Student_Management_API.postman_collection.json # Ready-to-import Postman collection
├── src/
│   ├── main/
│   │   ├── java/com/priyan/studentmanagement/
│   │   │   ├── config/
│   │   │   │   └── JpaAuditingConfig.java  # Enables JPA timestamp auditing
│   │   │   ├── controller/
│   │   │   │   ├── StudentController.java  # Student REST endpoints (/api/students)
│   │   │   │   └── DepartmentController.java # Department REST endpoints (/api/departments)
│   │   │   ├── dto/
│   │   │   │   ├── DepartmentRequestDTO.java
│   │   │   │   ├── DepartmentResponseDTO.java
│   │   │   │   ├── ErrorResponse.java
│   │   │   │   ├── StatisticsResponseDTO.java
│   │   │   │   ├── StudentRequestDTO.java
│   │   │   │   └── StudentResponseDTO.java
│   │   │   ├── entity/
│   │   │   │   ├── Department.java         # Department entity (1-to-many with Student)
│   │   │   │   └── Student.java            # Student entity with auditing
│   │   │   ├── exception/
│   │   │   │   ├── DepartmentNotFoundException.java
│   │   │   │   ├── DuplicateDepartmentCodeException.java
│   │   │   │   ├── DuplicateRegisterNumberException.java
│   │   │   │   ├── GlobalExceptionHandler.java # Centralized @RestControllerAdvice
│   │   │   │   └── StudentNotFoundException.java
│   │   │   ├── repository/
│   │   │   │   ├── DepartmentRepository.java
│   │   │   │   ├── StudentRepository.java  # JPA queries & JPQL GROUP BY stats
│   │   │   │   └── StudentSpecification.java # Dynamic filter predicates
│   │   │   └── StudentManagementApplication.java # Spring Boot Main
│   │   └── resources/
│   │       └── application.properties       # Database & Hibernate configuration
│   └── test/
│       ├── java/com/priyan/studentmanagement/
│       │   └── StudentManagementApplicationTests.java # Full integration test suite
│       └── resources/
│           └── application.properties       # H2 in-memory test configuration
├── .gitignore
├── mvnw
├── mvnw.cmd
├── pom.xml
└── README.md
```

---

## 🗄️ Database Schema (`student_db`)

The database script is located at `database/schema.sql`.

### Table: `departments` (Bonus 1)
| Column | Type | Constraints |
| :--- | :--- | :--- |
| `id` | BIGINT | PRIMARY KEY, AUTO_INCREMENT |
| `department_code` | VARCHAR(20) | NOT NULL, UNIQUE |
| `department_name` | VARCHAR(100) | NOT NULL |

### Table: `students`
| Column | Type | Constraints |
| :--- | :--- | :--- |
| `id` | BIGINT | PRIMARY KEY, AUTO_INCREMENT |
| `register_no` | VARCHAR(20) | NOT NULL, UNIQUE |
| `name` | VARCHAR(100) | NOT NULL |
| `email` | VARCHAR(100) | NOT NULL |
| `phone` | VARCHAR(10) | NOT NULL |
| `department` | VARCHAR(50) | NOT NULL |
| `department_id` | BIGINT | FOREIGN KEY (`department_id`) REFERENCES `departments(id)` |
| `year` | INT | NOT NULL (1 to 4) |
| `semester` | INT | NOT NULL (1 to 8) |
| `created_at` | TIMESTAMP | DEFAULT CURRENT_TIMESTAMP |
| `updated_at` | TIMESTAMP | DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP |

---

## ⚙️ Configuration (`application.properties`)

The application uses placeholders for database credentials. You can set them via environment variables or replace them in `src/main/resources/application.properties`:

```properties
server.port=8080

spring.datasource.url=jdbc:mysql://${DB_HOST:localhost}:${DB_PORT:3306}/student_db?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
spring.datasource.username=${DB_USERNAME:root}
spring.datasource.password=${DB_PASSWORD:your_password_here}
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.properties.hibernate.auto_quote_keyword=true
spring.jpa.open-in-view=false
```

---

## 🏃 Setup & Running the Application

### Prerequisites
- **Java 17+** installed (`java -version`)
- **MySQL 8.x** running locally or in Docker

### Step 1: Create Database
Run MySQL CLI or MySQL Workbench:
```sql
CREATE DATABASE student_db;
```
*(Optional: run `database/schema.sql` to preload sample data.)*

### Step 2: Configure Credentials
Provide your MySQL password using either:
- **Environment variables**:
  - `DB_USERNAME=root`
  - `DB_PASSWORD=your_mysql_password`
- Or directly update `spring.datasource.password` in `src/main/resources/application.properties`.

### Step 3: Run the Application
Using the Maven Wrapper:
```bash
# Windows
.\mvnw.cmd spring-boot:run

# macOS / Linux
./mvnw spring-boot:run
```
Or using standard Maven:
```bash
mvn spring-boot:run
```

The application starts on **`http://localhost:8080`**.

### Step 4: Run Tests
Automated integration tests run with an embedded in-memory database:
```bash
.\mvnw.cmd test
```

---

## 📡 API Endpoints

### 1. Student Endpoints (`/api/students`)

| Method | Endpoint | Description | Status Code |
| :--- | :--- | :--- | :--- |
| `GET` | `/api/students` | Get all students, with optional filters (`?department=&year=&semester=`) | `200 OK` |
| `GET` | `/api/students/search` | Partial, case-insensitive search (`?name=` or `?registerNo=`) | `200 OK` |
| `GET` | `/api/students/statistics` | Aggregated student count by department and year (Bonus 2) | `200 OK` |
| `GET` | `/api/students/{id}` | Get student by ID | `200 OK` / `404 Not Found` |
| `POST` | `/api/students` | Create new student | `201 Created` / `400 Bad Request` / `409 Conflict` |
| `PUT` | `/api/students/{id}` | Update student by ID | `200 OK` / `400 Bad Request` / `404 Not Found` / `409 Conflict` |
| `DELETE`| `/api/students/{id}` | Delete student by ID | `200 OK` / `404 Not Found` |

### 2. Department Endpoints (`/api/departments` - Bonus 1)

| Method | Endpoint | Description | Status Code |
| :--- | :--- | :--- | :--- |
| `GET` | `/api/departments` | Get all departments | `200 OK` |
| `GET` | `/api/departments/{id}` | Get department by ID | `200 OK` / `404 Not Found` |
| `POST` | `/api/departments` | Create new department | `201 Created` / `400 Bad Request` / `409 Conflict` |
| `PUT` | `/api/departments/{id}` | Update department by ID | `200 OK` / `400 Bad Request` / `404 Not Found` / `409 Conflict` |
| `DELETE`| `/api/departments/{id}` | Delete department by ID | `200 OK` / `404 Not Found` |
| `GET` | `/api/departments/{id}/students` | Get all students belonging to the department | `200 OK` / `404 Not Found` |

---

## 📝 Sample Requests & Responses

### 1. Create Student
**POST** `/api/students`
```json
{
  "registerNo": "23IT001",
  "name": "Arun Kumar",
  "email": "arun@example.com",
  "phone": "9876543210",
  "department": "IT",
  "year": 3,
  "semester": 5
}
```
**Response (201 Created):**
```json
{
  "id": 1,
  "registerNo": "23IT001",
  "name": "Arun Kumar",
  "email": "arun@example.com",
  "phone": "9876543210",
  "department": "IT",
  "departmentId": 1,
  "year": 3,
  "semester": 5,
  "createdAt": "2026-10-01 16:30:00",
  "updatedAt": "2026-10-01 16:30:00"
}
```

### 2. Combined Filters
**GET** `/api/students?department=IT&year=3`
```json
[
  {
    "id": 1,
    "registerNo": "23IT001",
    "name": "Arun Kumar",
    "email": "arun@example.com",
    "phone": "9876543210",
    "department": "IT",
    "departmentId": 1,
    "year": 3,
    "semester": 5,
    "createdAt": "2026-10-01 16:30:00",
    "updatedAt": "2026-10-01 16:30:00"
  }
]
```

### 3. Partial Case-Insensitive Search
**GET** `/api/students/search?name=arun`
*(Matches "Arun Kumar", "Varun", etc.)*

**GET** `/api/students/search?registerNo=23it`
*(Matches "23IT001", "23IT002", etc.)*

### 4. Statistics Endpoint (Bonus 2)
**GET** `/api/students/statistics`
**Response (200 OK):**
```json
{
  "totalStudents": 120,
  "departmentWise": {
    "IT": 40,
    "CSE": 35,
    "ECE": 45
  },
  "yearWise": {
    "1": 30,
    "2": 32,
    "3": 28,
    "4": 30
  }
}
```

---

## 🛡️ Validation Rules & Error Handling

All error responses strictly follow the JSON contract:
```json
{
  "status": <int>,
  "message": "<text>"
}
```

### Validation Annotations (Request DTO)
- `registerNo`: `@NotBlank(message = "Register number cannot be empty")`
- `name`: `@NotBlank(message = "Name cannot be empty")`
- `email`: `@NotBlank` + `@Email(message = "Invalid email address")`
- `phone`: `@Pattern(regexp = "^[6-9][0-9]{9}$", message = "Invalid phone number")`
- `department`: `@NotBlank(message = "Department cannot be empty")`
- `year`: `@Min(1) @Max(4)` (`"Year must be between 1 and 4"`)
- `semester`: `@Min(1) @Max(8)` (`"Semester must be between 1 and 8"`)

### Error Examples

#### 1. Validation Failure (`400 Bad Request`)
```json
{
  "status": 400,
  "message": "phone: Invalid phone number; email: Invalid email address",
  "errors": {
    "phone": "Invalid phone number",
    "email": "Invalid email address"
  }
}
```

#### 2. Duplicate Register Number (`409 Conflict`)
```json
{
  "status": 409,
  "message": "Register number already exists"
}
```

#### 3. Student Not Found (`404 Not Found`)
```json
{
  "status": 404,
  "message": "Student not found"
}
```

#### 4. Malformed JSON / Bad Type (`400 Bad Request`)
```json
{
  "status": 400,
  "message": "Parameter 'id' should be of type 'Long'"
}
```

#### 5. Unhandled Exceptions (`500 Internal Server Error`)
```json
{
  "status": 500,
  "message": "Internal server error"
}
```

---

## 🧪 Testing with Postman

A complete Postman collection is included in this repository at:
[`postman/Student_Management_API.postman_collection.json`](postman/Student_Management_API.postman_collection.json)

### How to Import & Test:
1. Open **Postman**.
2. Click **Import** (top left).
3. Drag & drop `postman/Student_Management_API.postman_collection.json`.
4. The collection will appear with three folders:
   - **Departments (Bonus 1)**: Department CRUD and `/departments/{id}/students`
   - **Students**: Student CRUD, combined filters, search, and statistics
   - **Error Cases**: Validation errors (400), Duplicate register numbers (409), Not found errors (404), Bad type (400), and Malformed JSON (400)
5. Start the Spring Boot application and execute requests!
# ERP-BACKEND-TASK-1-
