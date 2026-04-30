package com.civica.newhires.employee.domain.exception;

public class EmployeeNotFoundException extends RuntimeException {
    public EmployeeNotFoundException(String id) {
        super("Empleado no encontrado: " + id);
    }
}