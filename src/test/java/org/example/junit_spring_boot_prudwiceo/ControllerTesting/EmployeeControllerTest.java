package org.example.junit_spring_boot_prudwiceo.ControllerTesting;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.junit_spring_boot_prudwiceo.Service.Imple.EmployeeServiceImpl;
import org.example.junit_spring_boot_prudwiceo.model.Employee;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@WebMvcTest
public class EmployeeControllerTest {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private EmployeeServiceImpl employeeServiceimp;

    private Employee e;

    private int id;
    @BeforeEach
    public void setup()
    {
        id=1;
       e=new Employee();
       e.setId(id);
       e.setName("PRUDWI CEO");
       e.setPositive_thing("You are What you Think");
    }

    @Test
    public void givenEmpObj_thenCreateEmp_returnEmpObj() throws Exception
    {
        BDDMockito.given(employeeServiceimp.saveEmp(ArgumentMatchers.any(Employee.class)))
                .willAnswer((in)->in.getArgument(0));

       ResultActions response= mockMvc.perform(MockMvcRequestBuilders.post("/api/employee").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(e)));

        response.andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(e.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.positive_thing", CoreMatchers.is(e.getPositive_thing())));

    }

    @Test
    public void givenEmpId_whenGetEmpById_thenReturnEmpObj() throws Exception
    {
        BDDMockito.given(employeeServiceimp.getEmpById(id)).willReturn(Optional.of(e));

       ResultActions response= mockMvc.perform(MockMvcRequestBuilders.get("/api/employee/{id}",id));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name",CoreMatchers.is(e.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.positive_thing",CoreMatchers.is(e.getPositive_thing())));

    }

    @Test
    public void givenWrongEmpId_WhenGetEmpById_thenReturnEmptyEmpObj() throws Exception
    {

        BDDMockito.given(employeeServiceimp.getEmpById(id)).willReturn(Optional.empty());


      ResultActions response=  mockMvc.perform(MockMvcRequestBuilders.get("/api/employee/{id}",id));

        response.andExpect(MockMvcResultMatchers.status().isNotFound())
                .andDo(MockMvcResultHandlers.print());
//                .andExpect(MockMvcResultMatchers.jsonPath("$.name",CoreMatchers.is(e.getName())))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.positive_thing",CoreMatchers.is(e.getPositive_thing())));
    }

    @Test
    public void givenListOfEmp_whenGetAllEmp_ThenReturnListOfEmp() throws Exception
    {
        Employee e2=new Employee();
        e2.setId(2);
        e2.setName("CEO PRUDWI");
        e2.setPositive_thing("Think Positive");

        List<Employee>employeeList=new ArrayList<>();
        employeeList.add(e);
        employeeList.add(e2);

        BDDMockito.given(employeeServiceimp.getAllEmp()).willReturn(employeeList);

       ResultActions response= mockMvc.perform(MockMvcRequestBuilders.get("/api/employee"));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()",CoreMatchers.is(employeeList.size())));

    }


    @Test
    public void givenEmpIdAndEmpObj_whenUpdateEmp_thenReturnUpdatedEmpObj() throws Exception {
        Employee updatedEMp = new Employee();
        updatedEMp.setId(id);
        updatedEMp.setName("CEO PRUDWI");
        updatedEMp.setPositive_thing("Be Confident");

        BDDMockito.given(employeeServiceimp.getEmpById(id)).willReturn(Optional.of(e));

        BDDMockito.given(employeeServiceimp.updateEmp(ArgumentMatchers.any(Employee.class)))
                .willAnswer((in)->in.getArgument(0));

        ResultActions response=mockMvc.perform(MockMvcRequestBuilders.put("/api/employee/{id}",id).contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedEMp)));


        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name",CoreMatchers.is(updatedEMp.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.positive_thing",CoreMatchers.is(updatedEMp.getPositive_thing())));


    }


    @Test
public void givenWrongEmpId_whenUpdateEMp_thenReturnEmptyEmpObj() throws Exception
{
    Employee updatedEMp = new Employee();
    updatedEMp.setId(id);
    updatedEMp.setName("CEO PRUDWI");
    updatedEMp.setPositive_thing("Be Confident");

   BDDMockito.given(employeeServiceimp.getEmpById(id)).willReturn(Optional.empty());

   BDDMockito.given(employeeServiceimp.updateEmp(ArgumentMatchers.any(Employee.class)))
           .willAnswer((in)->in.getArgument(0));

   ResultActions response=mockMvc.perform(MockMvcRequestBuilders.put("/api/employee/{id}",id)
           .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(updatedEMp)));


   response.andExpect(MockMvcResultMatchers.status().isNotFound())
           .andDo(MockMvcResultHandlers.print());

}

@Test
public void givenEmpId_whenDeleteEmpById_thenDeleteEmp() throws Exception
{
   BDDMockito.willDoNothing().given(employeeServiceimp).deleteEmpById(id);

  ResultActions response= mockMvc.perform(MockMvcRequestBuilders.delete("/api/employee/{id}",id));


  response.andExpect(MockMvcResultMatchers.status().isOk())
          .andDo(MockMvcResultHandlers.print());

}

}
