package ru.hackaton.vtb.util;

public class ServiceNotFoundException extends RuntimeException {
    public ServiceNotFoundException() {
        super("The author did not found!");
    }
}
