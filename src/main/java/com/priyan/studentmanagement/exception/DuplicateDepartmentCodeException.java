package com.priyan.studentmanagement.exception;

public class DuplicateDepartmentCodeException extends RuntimeException {

    public DuplicateDepartmentCodeException() {
        super("Department code already exists");
    }

    public DuplicateDepartmentCodeException(String message) {
        super(message);
    }
}
