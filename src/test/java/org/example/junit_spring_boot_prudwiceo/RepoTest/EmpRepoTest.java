package org.example.junit_spring_boot_prudwiceo.RepoTest;

import org.assertj.core.api.Assertions;
import org.example.junit_spring_boot_prudwiceo.Repo.EmpRepo;
import org.example.junit_spring_boot_prudwiceo.model.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class EmpRepoTest {

    @Autowired
    private EmpRepo empRepo;

    private Employee e;
    private int id;
    @BeforeEach
    public void setup()
    {
        empRepo.deleteAll();
      e=new Employee();
      e.setName("PRUDWI CEO");
      e.setPositive_thing("All is well");
    }

    @Test
    public void givenEmpObj_whenSaveEmp_thenReturnEmpObj()
    {

        Employee employee= empRepo.save(e);
        Assertions.assertThat(employee).isNotNull();
        Assertions.assertThat(e.getId()).isGreaterThan(0);
    }

    @Test
    public void givenListOfEmp_whenFindAllEmp_thenReturnListOfEmp()
    {
        Employee e2= new Employee();
        e2.setName("CEO PRUDWI");
        e2.setPositive_thing("Everything is happening for my highest good");

        empRepo.save(e);
        empRepo.save(e2);

        List<Employee>employeeList=empRepo.findAll();

        Assertions.assertThat(employeeList).isNotNull();
        Assertions.assertThat(employeeList.size()).isEqualTo(2);
    }

    @Test
    public void givenEmpId_whenFindById_thenReturnEmpObj()
    {
       empRepo.save(e);

       Employee savedEmp=empRepo.findById(e.getId()).get();

       Assertions.assertThat(savedEmp).isNotNull();


    }

    @Test
    public void givenEmpName_whenFindByName_thenReturnEmpObj()
    {
        empRepo.save(e);

        Employee emp=empRepo.findByName(e.getName()).get();

        Assertions.assertThat(emp).isNotNull();
    }

    @Test
    public void givenUpdatedEmp_whenSaveEmp_thenReturnUpdatedEmp()
    {
        empRepo.save(e);
        Employee savedEmp=empRepo.findById(e.getId()).get();

        String name="CEO PRUDWI";
        String positive_thing="Allways be happy and think positive";

        savedEmp.setName(name);
        savedEmp.setPositive_thing(positive_thing);

        Employee updatedEmp=empRepo.save(savedEmp);

        Assertions.assertThat(updatedEmp).isNotNull();
        Assertions.assertThat(updatedEmp.getName()).isEqualTo(name);
        Assertions.assertThat(updatedEmp.getPositive_thing()).isEqualTo(positive_thing);
    }

    @Test
    public void givenEmpObj_whenDeleteEmp_theRemoveEmp()
    {
        empRepo.save(e);

        empRepo.deleteById(e.getId());

        Optional<Employee>employee=empRepo.findById(e.getId());

        Assertions.assertThat(employee).isEmpty();
    }

    @Test
    public void givenEmpNameAndPositiveThing_whencustomJpql_thenReturnEmpObj()
    {
        empRepo.save(e);

        String name="PRUDWI CEO";
        String positive_thing="All is well";

        Employee emp=empRepo.customJpql_with_index(name,positive_thing);

        Assertions.assertThat(emp).isNotNull();
    }

    @Test
public void givenEmpNameAndPositiveThing_whenCustomJpqlWithParam_thenReturnEmp()
{
    empRepo.save(e);

    String name="PRUDWI CEO";
    String positive_thing="All is well";

    Employee emp=empRepo.customJpql_with_Named_Params(name,positive_thing);

    Assertions.assertThat(emp).isNotNull();
}

@Test
public void givenEmpNameAndpostive_Thing_whenCustomQueryWithinex_thenReturnEmpObj()
{
    empRepo.save(e);

    String name="PRUDWI CEO";
    String positive_thing="All is well";
    Employee emp=empRepo.customJpql_with_index(name,positive_thing);

    Assertions.assertThat(emp).isNotNull();
}



    @Test
    public void givenEmpNameAndpostive_Thing_whenCustomQueryWithParam_thenReturnEmpObj()
    {
        empRepo.save(e);

        String name="PRUDWI CEO";
        String positive_thing="All is well";
        Employee emp=empRepo.customJpql_with_Named_Params(name,positive_thing);

        Assertions.assertThat(emp).isNotNull();
    }


}
