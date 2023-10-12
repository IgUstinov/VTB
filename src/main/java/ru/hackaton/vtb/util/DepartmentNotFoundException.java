package ru.hackaton.vtb.util;

public class DepartmentNotFoundException extends RuntimeException {
    public DepartmentNotFoundException() {
        super("The Department did not found!");
    }
}
