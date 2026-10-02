MERGE INTO departments (id, department_code, department_name) KEY(id) VALUES
(1, 'IT', 'Information Technology'),
(2, 'CSE', 'Computer Science and Engineering'),
(3, 'ECE', 'Electronics and Communication Engineering');

MERGE INTO students (id, register_no, name, email, phone, department, department_id, "year", semester, created_at, updated_at) KEY(id) VALUES
(1, '23IT001', 'Arun Kumar', 'arun@example.com', '9876543210', 'IT', 1, 3, 5, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, '23IT002', 'Priya Dharshini', 'priya@example.com', '9876543211', 'IT', 1, 3, 5, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, '23CSE001', 'Vijay Anand', 'vijay@example.com', '9876543212', 'CSE', 2, 2, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
