package org.example.junit_spring_boot_prudwiceo.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.junit_spring_boot_prudwiceo.Service.Imple.EmployeeServiceImpl;

import org.example.junit_spring_boot_prudwiceo.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {

    private EmployeeServiceImpl employeeServiceimp;

    public EmployeeController(EmployeeServiceImpl employeeServiceimp)
    {
        this.employeeServiceimp=employeeServiceimp;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee createEmp(@RequestBody Employee employee)
    {
        return employeeServiceimp.saveEmp(employee);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> findEmpById(@PathVariable int id)
    {
        return employeeServiceimp.getEmpById(id)
                .map(ResponseEntity::ok)
                .orElseGet(()->ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmp()
    {
        List<Employee>employeeList=employeeServiceimp.getAllEmp();

        return new ResponseEntity<>(employeeList,HttpStatus.OK);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Employee>updateEmp(@PathVariable int id,@RequestBody Employee employee)
    {
        return employeeServiceimp.getEmpById(id)
                .map(savedemp->
                {
                    savedemp.setId(id);
                    savedemp.setName(employee.getName());
                    savedemp.setPositive_thing(employee.getPositive_thing());

                    Employee updatedEmp=employeeServiceimp.updateEmp(savedemp);

                    return new ResponseEntity<>(updatedEmp,HttpStatus.OK);
                }
                ).orElseGet(()->ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String>deleteEMp(@PathVariable int id)
    {
        employeeServiceimp.deleteEmpById(id);

        return new ResponseEntity<>("Employee has been Deleted",HttpStatus.OK);
    }
}
