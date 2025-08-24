package org.example.junit_spring_boot_prudwiceo.ServiceTest;

import org.assertj.core.api.Assertions;
import org.example.junit_spring_boot_prudwiceo.Exception.ResourceNotFoundException;
import org.example.junit_spring_boot_prudwiceo.Repo.EmpRepo;
import org.example.junit_spring_boot_prudwiceo.Service.Imple.EmployeeServiceImpl;
import org.example.junit_spring_boot_prudwiceo.model.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;


@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

    @Mock
    private EmpRepo empRepo;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private Employee e1;

    private int id;
    @BeforeEach
    public void setup()
    {
         id=1;
         e1= new Employee();
        e1.setId(id);
        e1.setName("PRUDWICEO");
        e1.setPositive_thing("EveryThing is happening for my highest good");

    }

    @Test
    public void givenEmpObj_whenSaveEmp_thenReturnEmpObj()
    {

        BDDMockito.given(empRepo.findById(id)).willReturn(Optional.empty());

        BDDMockito.given(empRepo.save(e1)).willReturn(e1);

        Employee savedemp=employeeService.saveEmp(e1);

        Assertions.assertThat(savedemp).isNotNull();

    }

    @Test
    public void givenAlreadyExistingEmp_whenSaveEmp_WillThrowException()
    {
        BDDMockito.given(empRepo.findById(e1.getId())).willReturn(Optional.of(e1));

        org.junit.jupiter.api.Assertions.assertThrows(ResourceNotFoundException.class,()->
        {
            employeeService.saveEmp(e1);
        });

        Mockito.verify(empRepo, never()).save(ArgumentMatchers.any(Employee.class));

    }


    @Test
    public void givenEmpList_whenGetAllEMp_thenWillReturnEmpList()
    {
        Employee e2= new Employee();
        e2.setId(2);
        e2.setName("CEO PRUDWI");
        e2.setPositive_thing("BE HAPPY IN LIFE and have confidence on your self");

        List<Employee> employeeList=new ArrayList<>();

        employeeList.add(e2);
        employeeList.add(e1);

        BDDMockito.given(empRepo.findAll()).willReturn(employeeList);

        List<Employee>employees=employeeService.getAllEmp();

        Assertions.assertThat(employees).isNotNull();

        Assertions.assertThat(employees.size()).isEqualTo(employeeList.size());

    }

    @Test
    public void givenEmptyEmpList_whenGetAllEmp_thenReturnEmptyEmpList()
    {
        BDDMockito.given(empRepo.findAll()).willReturn(Collections.emptyList());

        List<Employee>employeeList= employeeService.getAllEmp();

        Assertions.assertThat(employeeList).isEmpty();
        Assertions.assertThat(employeeList.size()).isEqualTo(0);

    }


    @Test
    public void givenEmpId_WhenGetEmpById_thenReturnEmpObj()
    {
        BDDMockito.given(empRepo.findById(id)).willReturn(Optional.of(e1));

        Employee employee= employeeService.getEmpById(id).get();

        Assertions.assertThat(employee).isNotNull();

    }


    @Test
    public void givenEmpObj_WhenUpdateEmp_thenReturnUpdatedEmpObj()
    {
       BDDMockito.given(empRepo.save(e1)).willReturn(e1);
       e1.setName("CEO PRUDWI");
       e1.setPositive_thing("A confident Guy is much better than talented guy who always questions his ability");


       Employee updatedEmp=employeeService.saveEmp(e1);


       Assertions.assertThat(updatedEmp).isNotNull();
       Assertions.assertThat(updatedEmp.getName()).isEqualTo("CEO PRUDWI");
       Assertions.assertThat(updatedEmp.getPositive_thing()).isEqualTo("A confident Guy is much better than talented guy who always questions his ability");

    }


    @Test
    public void givenEmpId_whenDeleteById_thenDoNothing()
    {
        BDDMockito.willDoNothing().given(empRepo).deleteById(id);

        employeeService.deleteEmpById(id);

        Mockito.verify(empRepo,times(1)).deleteById(id);
    }
}
