package org.example.junit_spring_boot_prudwiceo.Service.Imple;

import org.example.junit_spring_boot_prudwiceo.Exception.ResourceNotFoundException;
import org.example.junit_spring_boot_prudwiceo.Repo.EmpRepo;
import org.example.junit_spring_boot_prudwiceo.Service.EmployeeService;
import org.example.junit_spring_boot_prudwiceo.model.Employee;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private EmpRepo empRepo;

    public EmployeeServiceImpl(EmpRepo empRepo)
    {
        this.empRepo=empRepo;
    }


    @Override
    public Employee saveEmp(Employee employee) {

        Optional<Employee>employee1=empRepo.findById(employee.getId());
        if(employee1.isPresent()) throw new ResourceNotFoundException("Employee is already existing");

        return empRepo.save(employee);
    }


    public List<Employee>getAllEmp()
    {
        return empRepo.findAll();
    }

    public Optional<Employee>getEmpById(int id)
    {
        return empRepo.findById(id);
    }

    @Override
    public Employee updateEmp(Employee employee) {
        return empRepo.save(employee);
    }

    @Override
    public void deleteEmpById(int id) {
        empRepo.deleteById(id);
    }


}
