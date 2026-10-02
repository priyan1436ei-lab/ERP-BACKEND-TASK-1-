package com.priyan.studentmanagement.controller;

import com.priyan.studentmanagement.dto.DepartmentRequestDTO;
import com.priyan.studentmanagement.dto.DepartmentResponseDTO;
import com.priyan.studentmanagement.dto.StudentResponseDTO;
import com.priyan.studentmanagement.service.DepartmentService;
import com.priyan.studentmanagement.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {

    private final DepartmentService departmentService;
    private final StudentService studentService;

    // Constructor injection
    public DepartmentController(DepartmentService departmentService, StudentService studentService) {
        this.departmentService = departmentService;
        this.studentService = studentService;
    }

    /**
     * GET /api/departments - Retrieve all departments
     */
    @GetMapping
    public ResponseEntity<List<DepartmentResponseDTO>> getAllDepartments() {
        List<DepartmentResponseDTO> departments = departmentService.getAllDepartments();
        return ResponseEntity.ok(departments);
    }

    /**
     * GET /api/departments/{id} - Retrieve a department by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<DepartmentResponseDTO> getDepartmentById(@PathVariable Long id) {
        DepartmentResponseDTO department = departmentService.getDepartmentById(id);
        return ResponseEntity.ok(department);
    }

    /**
     * POST /api/departments - Create a department (returns 201 Created)
     */
    @PostMapping
    public ResponseEntity<DepartmentResponseDTO> createDepartment(@Valid @RequestBody DepartmentRequestDTO request) {
        DepartmentResponseDTO created = departmentService.createDepartment(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * PUT /api/departments/{id} - Update department
     */
    @PutMapping("/{id}")
    public ResponseEntity<DepartmentResponseDTO> updateDepartment(
            @PathVariable Long id,
            @Valid @RequestBody DepartmentRequestDTO request) {
        DepartmentResponseDTO updated = departmentService.updateDepartment(id, request);
        return ResponseEntity.ok(updated);
    }

    /**
     * DELETE /api/departments/{id} - Delete department
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteDepartment(@PathVariable Long id) {
        departmentService.deleteDepartment(id);
        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.OK.value());
        response.put("message", "Department deleted successfully");
        return ResponseEntity.ok(response);
    }

    /**
     * GET /api/departments/{id}/students - Get all students belonging to this department
     */
    @GetMapping("/{id}/students")
    public ResponseEntity<List<StudentResponseDTO>> getStudentsByDepartment(@PathVariable Long id) {
        List<StudentResponseDTO> students = studentService.getStudentsByDepartmentId(id);
        return ResponseEntity.ok(students);
    }
}
