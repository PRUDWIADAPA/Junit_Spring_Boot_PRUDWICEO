package org.example.junit_spring_boot_prudwiceo.Repo;

import org.example.junit_spring_boot_prudwiceo.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmpRepo extends JpaRepository<Employee,Integer> {

    Optional<Employee> findByName(String name);

    @Query("select e from Employee e where e.name=?1 and e.positive_thing=?2")
    Employee customJpql_with_index(String name,String positive_thing);

    @Query("select e from Employee e where e.name=:name and e.positive_thing=:positive_thing")
    Employee customJpql_with_Named_Params(@Param("name") String name,@Param("positive_thing") String positive_thing);

    @Query(value = "select * from Employee e where e.name=:name and e.positive_thing=:positive_thing",nativeQuery = true)
    Employee customJpql_with_NativeSql_WithParam(@Param("name") String name,@Param("positive_thing") String positive_thing);

    @Query(value = "select * from Employee e where e.name=?1 and e.positive_thing=?2",nativeQuery = true)
    Employee customJpql_with_NativeSql_WithIndex( String name, String positive_thing);
}
