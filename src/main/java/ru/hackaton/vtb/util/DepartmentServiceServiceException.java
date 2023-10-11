package ru.hackaton.vtb.util;

public class DepartmentServiceServiceException extends RuntimeException {
    public DepartmentServiceServiceException() {
        super("The DepartmentService did not found!");
    }
}
