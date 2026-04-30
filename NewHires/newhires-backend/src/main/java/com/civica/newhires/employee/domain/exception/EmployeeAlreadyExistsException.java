package com.civica.newhires.employee.domain.exception;

public class EmployeeAlreadyExistsException extends RuntimeException {
    public EmployeeAlreadyExistsException(String username) {
        super("El empleado ya existe: " + username);
    }
}

