package com.fallt.spring_data_projections.controller;

import com.fallt.spring_data_projections.repository.EmployeeRepository;
import com.fallt.spring_data_projections.repository.projections.EmployeeProjection;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static com.fallt.spring_data_projections.TestConstant.COMMON_EMPLOYEE_PATH;
import static com.fallt.spring_data_projections.TestConstant.EMPLOYEE_ID_PATH;
import static com.fallt.spring_data_projections.TestConstant.EMPLOYEE_REQUEST;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Sql(scripts = "/sql/before-test.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/after-test.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    @DisplayName("Get employee by id")
    void whenGetById_thenReturnEmployeeProjection() throws Exception {
        Long id = 100L;

        mockMvc.perform(get(EMPLOYEE_ID_PATH, id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullName").value("John Doe"))
                .andExpect(jsonPath("$.position").value("engineer"))
                .andExpect(jsonPath("$.departmentName").value("First"))
                .andExpect(jsonPath("salary").doesNotExist());
    }

    @Test
    @DisplayName("Get employee by incorrect id")
    void whenGetByIncorrectId_thenThrowNotFound() throws Exception {
        Long id = 500L;

        mockMvc.perform(get(EMPLOYEE_ID_PATH, id))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Get all employees")
    void whenGetAll_thenReturnListEmployeeProjections() throws Exception {
        mockMvc.perform(get(COMMON_EMPLOYEE_PATH))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].fullName").value("John Doe"))
                .andExpect(jsonPath("$[1].fullName").value("Tom Smith"))
                .andExpect(jsonPath("$[2].fullName").value("Jack Sullivan"))
                .andExpect(jsonPath("$[3].fullName").value("Samantha Collins"))
                .andExpect(jsonPath("$[4].fullName").value("Daniel Crosby"));
    }

    @Test
    @DisplayName("Create employee")
    void whenCreate_thenReturnSuccessResponse() throws Exception {
        mockMvc.perform(post(COMMON_EMPLOYEE_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(EMPLOYEE_REQUEST)))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Update employee")
    void whenUpdate_thenReturnSuccessResponse() throws Exception {
        Long id = 101L;

        mockMvc.perform(put(EMPLOYEE_ID_PATH, id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(EMPLOYEE_REQUEST)))
                .andDo(print())
                .andExpect(status().isOk());
        EmployeeProjection employeeProjection = employeeRepository.getById(id);
        assertThat(employeeProjection.getFullName()).isEqualTo("Bob Kent");
    }

    @Test
    @DisplayName("Update by incorrect id")
    void whenUpdateByIncorrectId_thenThrowNotFoundException() throws Exception {
        Long id = 1000L;

        mockMvc.perform(put(EMPLOYEE_ID_PATH, id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(EMPLOYEE_REQUEST)))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Employee with ID: " + id + " not found"));

    }

    @Test
    @DisplayName("Delete employee")
    void whenDeleteById_thenRemoveFromDatabase() throws Exception {
        Long id = 100L;

        mockMvc.perform(delete(EMPLOYEE_ID_PATH, id))
                .andDo(print())
                .andExpect(status().isNoContent());
        EmployeeProjection projection = employeeRepository.getById(id);
        assertThat(projection).isNull();
    }
}
