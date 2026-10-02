package com.priyan.studentmanagement.exception;

public class DepartmentNotFoundException extends RuntimeException {

    public DepartmentNotFoundException() {
        super("Department not found");
    }

    public DepartmentNotFoundException(String message) {
        super(message);
    }

    public DepartmentNotFoundException(Long id) {
        super("Department not found with id: " + id);
    }
}
