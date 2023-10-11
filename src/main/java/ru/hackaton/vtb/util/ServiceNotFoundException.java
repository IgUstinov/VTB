package ru.hackaton.vtb.util;

public class ServiceNotFoundException extends RuntimeException {
    public ServiceNotFoundException() {
        super("The Service did not found!");
    }
}
