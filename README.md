ERP Backend Task demo

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

# ERP Backend Task 1

backend project for the ERP Backend Task 1 assessment.

I created a Student Management REST API using Java, Spring Boot and MySQL. The main purpose of this project is to manage student details through REST APIs and store the data in a MySQL database.

## Features

* Add student details
* Get all students
* Get student by ID
* Update student details
* Delete student
* Search students by name or register number
* Filter students by department, year and semester
* Check duplicate register numbers
* Validate input data
* Handle common API errors
* Department management
* Student statistics

## Technologies Used

* Java
* Spring Boot
* Spring Web
* Spring Data JPA
* MySQL
* Maven
* Postman

## Project Structure

The project is divided into different layers such as controllers, services, repositories, entities, DTOs and exception handling.

```text
src/
├── controller/
├── service/
├── repository/
├── entity/
├── dto/
├── exception/
└── config/
```

The repository also contains the database script and Postman collection.

## Student API

```text
GET    /api/students
GET    /api/students/{id}
POST   /api/students
PUT    /api/students/{id}
DELETE /api/students/{id}
GET    /api/students/search?name=Arun
GET    /api/students?department=IT
GET    /api/students?year=3
GET    /api/students?semester=5
GET    /api/students/statistics
```

## Department API

```text
GET    /api/departments
GET    /api/departments/{id}
POST   /api/departments
PUT    /api/departments/{id}
DELETE /api/departments/{id}
GET    /api/departments/{id}/students
```

## Database

MySQL is used for storing student and department information.

The SQL script is available inside the `database` folder.

## Validation

The application checks student details before saving them.

* Register number is required and should be unique
* Name is required
* Email should be valid
* Phone number should be valid
* Department is required
* Year and semester should contain valid values

## Testing

I used Postman to test the REST APIs.

The Postman collection is included inside the `postman` folder.

## How to Run

1. Install Java and MySQL.
2. Create the required database.
3. Update the MySQL username and password in `application.properties`.
4. Run the Spring Boot application using Maven.
5. Use Postman to test the APIs.

## About

I worked on this project as part of my backend assessment. While doing this project, I got practical experience with Spring Boot, REST APIs, MySQL, JPA, validation and exception handling.

