package com.priyan.studentmanagement;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.priyan.studentmanagement.dto.DepartmentRequestDTO;
import com.priyan.studentmanagement.dto.StudentRequestDTO;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class StudentManagementApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Order(1)
    void contextLoads() {
    }

    @Test
    @Order(2)
    void shouldCreateDepartment() throws Exception {
        DepartmentRequestDTO dept = new DepartmentRequestDTO("IT", "Information Technology");

        mockMvc.perform(post("/api/departments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dept)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.departmentCode", is("IT")))
                .andExpect(jsonPath("$.departmentName", is("Information Technology")));
    }

    @Test
    @Order(3)
    void shouldReturn409OnDuplicateDepartmentCode() throws Exception {
        DepartmentRequestDTO dept = new DepartmentRequestDTO("IT", "Information Technology Duplicate");

        mockMvc.perform(post("/api/departments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dept)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.status", is(409)))
                .andExpect(jsonPath("$.message", containsString("Department code already exists")));
    }

    @Test
    @Order(4)
    void shouldCreateStudentSuccessfully() throws Exception {
        StudentRequestDTO student = new StudentRequestDTO(
                "23IT001",
                "Arun Kumar",
                "arun@example.com",
                "9876543210",
                "IT",
                3,
                5
        );

        mockMvc.perform(post("/api/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(student)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.registerNo", is("23IT001")))
                .andExpect(jsonPath("$.name", is("Arun Kumar")))
                .andExpect(jsonPath("$.department", is("IT")))
                .andExpect(jsonPath("$.year", is(3)))
                .andExpect(jsonPath("$.semester", is(5)))
                .andExpect(jsonPath("$.createdAt").exists())
                .andExpect(jsonPath("$.updatedAt").exists());
    }

    @Test
    @Order(5)
    void shouldReturn409OnDuplicateRegisterNumber() throws Exception {
        StudentRequestDTO student = new StudentRequestDTO(
                "23IT001",
                "Another Student",
                "another@example.com",
                "9876543219",
                "IT",
                3,
                5
        );

        mockMvc.perform(post("/api/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(student)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.status", is(409)))
                .andExpect(jsonPath("$.message", is("Register number already exists")));
    }

    @Test
    @Order(6)
    void shouldReturn400OnValidationFailure() throws Exception {
        // Invalid email, invalid phone, out of range year and semester
        StudentRequestDTO invalidStudent = new StudentRequestDTO(
                "",
                "",
                "invalid-email",
                "12345",
                "",
                5,
                9
        );

        mockMvc.perform(post("/api/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidStudent)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status", is(400)))
                .andExpect(jsonPath("$.errors").exists());
    }

    @Test
    @Order(7)
    void shouldFilterStudentsByDepartmentAndYear() throws Exception {
        mockMvc.perform(get("/api/students")
                        .param("department", "IT")
                        .param("year", "3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))))
                .andExpect(jsonPath("$[0].registerNo", is("23IT001")));
    }

    @Test
    @Order(8)
    void shouldSearchStudentsByNameAndRegisterNo() throws Exception {
        mockMvc.perform(get("/api/students/search")
                        .param("name", "Arun"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))))
                .andExpect(jsonPath("$[0].name", is("Arun Kumar")));

        mockMvc.perform(get("/api/students/search")
                        .param("registerNo", "23it001"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))))
                .andExpect(jsonPath("$[0].registerNo", is("23IT001")));
    }

    @Test
    @Order(9)
    void shouldReturnStatistics() throws Exception {
        mockMvc.perform(get("/api/students/statistics"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalStudents", greaterThanOrEqualTo(1)))
                .andExpect(jsonPath("$.departmentWise.IT", greaterThanOrEqualTo(1)))
                .andExpect(jsonPath("$.yearWise.3", greaterThanOrEqualTo(1)));
    }

    @Test
    @Order(10)
    void shouldReturn404WhenStudentNotFound() throws Exception {
        mockMvc.perform(get("/api/students/999999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status", is(404)))
                .andExpect(jsonPath("$.message", containsString("Student not found")));
    }
}
