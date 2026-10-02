-- ============================================================
-- DATABASE SCHEMA SCRIPT: student_db
-- ============================================================

CREATE DATABASE IF NOT EXISTS student_db;
USE student_db;

-- ------------------------------------------------------------
-- Table: departments (Bonus 1)
-- ------------------------------------------------------------
CREATE TABLE IF NOT EXISTS departments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    department_code VARCHAR(20) NOT NULL UNIQUE,
    department_name VARCHAR(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ------------------------------------------------------------
-- Table: students
-- ------------------------------------------------------------
CREATE TABLE IF NOT EXISTS students (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    register_no VARCHAR(20) NOT NULL UNIQUE,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    phone VARCHAR(10) NOT NULL,
    department VARCHAR(50) NOT NULL,
    department_id BIGINT NULL,
    year INT NOT NULL,
    semester INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_students_department FOREIGN KEY (department_id) REFERENCES departments(id) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ------------------------------------------------------------
-- Optional Seed Data for Quick Testing
-- ------------------------------------------------------------
INSERT IGNORE INTO departments (id, department_code, department_name) VALUES
(1, 'IT', 'Information Technology'),
(2, 'CSE', 'Computer Science and Engineering'),
(3, 'ECE', 'Electronics and Communication Engineering');

INSERT IGNORE INTO students (id, register_no, name, email, phone, department, department_id, year, semester, created_at, updated_at) VALUES
(1, '23IT001', 'Arun Kumar', 'arun@example.com', '9876543210', 'IT', 1, 3, 5, NOW(), NOW()),
(2, '23IT002', 'Priya Dharshini', 'priya@example.com', '9876543211', 'IT', 1, 3, 5, NOW(), NOW()),
(3, '23CSE001', 'Vijay Anand', 'vijay@example.com', '9876543212', 'CSE', 2, 2, 3, NOW(), NOW());
