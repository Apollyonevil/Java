package com.civica.newhires.employee.application.mapper;

import com.civica.newhires.employee.application.dto.EmployeeDTO;
import com.civica.newhires.employee.domain.model.Employee;


public class EmployeeMapper {
    public static EmployeeDTO toDTO(Employee employee) {
        return new EmployeeDTO(
            employee.getId(),
            employee.getUsername(),
            employee.isEnabled(),
            employee.getRole().name()
        );
    }
}