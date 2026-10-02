package com.priyan.studentmanagement.exception;

public class StudentNotFoundException extends RuntimeException {

    public StudentNotFoundException() {
        super("Student not found");
    }

    public StudentNotFoundException(String message) {
        super(message);
    }

    public StudentNotFoundException(Long id) {
        super("Student not found with id: " + id);
    }
}
