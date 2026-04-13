package com.civica.newhires.employee.domain.ports.output;

import com.civica.newhires.employee.domain.model.Employee;
import java.util.List;
import java.util.Optional;

public interface EmployeePort {
    List<Employee> findAll();
    Optional<Employee> findById(String id);
    Optional<Employee> findByUsername(String username);
    Employee save(Employee employee);
    void deleteById(String id);
}