package com.company.junitmockito.employeecontrollertest;


import com.company.junitmockito.controller.EmployeeController;
import com.company.junitmockito.entity.Employee;
import com.company.junitmockito.service.EmployeeService;
import com.company.junitmockito.util.ErrorResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// for result matcher...used in andExcept


//@ExtendWith(MockitoExtension.class)
@AutoConfigureJsonTesters
//it's included as a meta annotation in the Spring Boot test annotations like @DataJpaTest, @WebMvcTest
@WebMvcTest({EmployeeController.class, EmployeeService.class})
public class EmployeeControllerTest {

    @Autowired
    MockMvc mockMvc;


//    @MockBean
//    EmployeeRepo employeeRepository;

    @MockBean
    EmployeeService employeeService;

    @Autowired
    private JacksonTester<Employee> jasonEmp;

    @Autowired
    ObjectMapper om;


    // verifying controller listening
    @Test
    public void canRetrieveByIdWhenExists() throws Exception {
        // given
        given(employeeService.getEmployeeById(anyInt()))
                .willReturn(new Employee(1, "Narendra", "Waykos", "abc@gmail.com"));

        // when
        MockHttpServletResponse response = mockMvc.perform(
                get("/employee/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(
                jasonEmp.write(new Employee(1, "Narendra", "Waykos", "abc@gmail.com")).getJson()
        );
    }

    // verify http serialization
    @Test
    public void testSerialization() throws Exception {
        Employee emp = new Employee(1, "Narendra", "Waykos", "abc@gmail.com");
        String jsonEmp = om.writeValueAsString(emp);
        //given
        given(employeeService.addEmployee(emp)).willReturn(emp);

        // when
         MvcResult mvcResult = mockMvc.perform(post("/employee/save")
                .contentType("application/json")
                .content(jsonEmp))
        .andExpect(status().isOk()).andReturn();

         MockHttpServletResponse response = mvcResult.getResponse();

        // then
        assertThat(response.getContentAsString()).isEqualTo(jsonEmp);
    }


    @Test
    public void testValidation() throws Exception {
        Employee emp = new Employee(null, "", "Waykos", "abc@gmail.com");
        String jsonEmp = om.writeValueAsString(emp);
        //given
        given(employeeService.addEmployee(emp)).willReturn(emp);

        // when
        mockMvc.perform(post("/employee/save")
                .contentType("application/json")
                .content(jsonEmp))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void whenValidInput_thenBusinessLogc() throws Exception {
        Employee emp = new Employee(null, "Riya", "Waykos", "abc@gmail.com");
        String jsonEmp = om.writeValueAsString(emp);
        // when
        MvcResult mvcResult = mockMvc.perform(post("/employee/save")
                .contentType("application/json")
                .content(jsonEmp))
                .andExpect(status().isOk()).andReturn();

        // to capture arg passed to this method
        ArgumentCaptor<Employee> empCaptor = ArgumentCaptor.forClass(Employee.class);

        // capture which arg has passed in this method
        verify(employeeService, times(1)).addEmployee(empCaptor.capture());
        assertThat(empCaptor.getValue().getFirstName()).isEqualTo("Riya");
        assertThat(empCaptor.getValue().getEmail()).isEqualTo("abc@gmail.com");
    }

    @Test
    void whenNullValue_thenReturns400AndErrorResult() throws Exception {

        Employee emp = new Employee(null,  "Riya", "Waykos", "abc@gmail.com");

        MvcResult mvcResult = mockMvc.perform(post("/employee/save")
                .contentType("application/json")
                .content(om
                        .writeValueAsString(emp)))
                .andExpect(status().isBadRequest())
                .andReturn();

        ErrorResult expectedErrorResponse = new ErrorResult("id", "id can't be null");
        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        String expectedResponseBody = om.writeValueAsString(expectedErrorResponse);
        assertThat(actualResponseBody).isEqualToIgnoringWhitespace(expectedResponseBody);
    }

}
