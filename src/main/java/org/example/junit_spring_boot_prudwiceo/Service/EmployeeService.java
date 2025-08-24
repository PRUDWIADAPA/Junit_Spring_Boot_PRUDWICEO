package org.example.junit_spring_boot_prudwiceo.Service;

import org.example.junit_spring_boot_prudwiceo.model.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {

    public Employee saveEmp(Employee employee);
    public List<Employee> getAllEmp();
    public Optional<Employee> getEmpById(int id);
    public Employee updateEmp(Employee employee);

    public void deleteEmpById(int id);
}
