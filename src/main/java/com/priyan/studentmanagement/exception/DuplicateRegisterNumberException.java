package com.priyan.studentmanagement.exception;

public class DuplicateRegisterNumberException extends RuntimeException {

    public DuplicateRegisterNumberException() {
        super("Register number already exists");
    }

    public DuplicateRegisterNumberException(String message) {
        super(message);
    }
}
