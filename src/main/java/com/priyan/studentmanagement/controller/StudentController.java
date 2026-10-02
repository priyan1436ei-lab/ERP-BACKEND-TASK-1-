package com.priyan.studentmanagement.controller;

import com.priyan.studentmanagement.dto.StatisticsResponseDTO;
import com.priyan.studentmanagement.dto.StudentRequestDTO;
import com.priyan.studentmanagement.dto.StudentResponseDTO;
import com.priyan.studentmanagement.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;

    // Constructor injection
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    /**
     * GET /api/students - Retrieve all students with optional combined filters
     * e.g., ?department=IT&year=3&semester=5
     */
    @GetMapping
    public ResponseEntity<List<StudentResponseDTO>> getAllStudents(
            @RequestParam(required = false) String department,
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) Integer semester) {
        List<StudentResponseDTO> students = studentService.getAllStudents(department, year, semester);
        return ResponseEntity.ok(students);
    }

    /**
     * GET /api/students/search - Partial, case-insensitive search by name or register number
     * Mapped before /{id} so 'search' is not parsed as an id
     */
    @GetMapping("/search")
    public ResponseEntity<List<StudentResponseDTO>> searchStudents(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String registerNo) {
        List<StudentResponseDTO> results = studentService.searchStudents(name, registerNo);
        return ResponseEntity.ok(results);
    }

    /**
     * GET /api/students/statistics - Aggregated count statistics
     * Mapped before /{id} so 'statistics' is not parsed as an id
     */
    @GetMapping("/statistics")
    public ResponseEntity<StatisticsResponseDTO> getStatistics() {
        StatisticsResponseDTO statistics = studentService.getStatistics();
        return ResponseEntity.ok(statistics);
    }

    /**
     * GET /api/students/{id} - Retrieve a single student by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<StudentResponseDTO> getStudentById(@PathVariable Long id) {
        StudentResponseDTO student = studentService.getStudentById(id);
        return ResponseEntity.ok(student);
    }

    /**
     * POST /api/students - Create a new student (returns 201 Created)
     */
    @PostMapping
    public ResponseEntity<StudentResponseDTO> createStudent(@Valid @RequestBody StudentRequestDTO request) {
        StudentResponseDTO created = studentService.createStudent(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * PUT /api/students/{id} - Update an existing student
     */
    @PutMapping("/{id}")
    public ResponseEntity<StudentResponseDTO> updateStudent(
            @PathVariable Long id,
            @Valid @RequestBody StudentRequestDTO request) {
        StudentResponseDTO updated = studentService.updateStudent(id, request);
        return ResponseEntity.ok(updated);
    }

    /**
     * DELETE /api/students/{id} - Delete a student
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.OK.value());
        response.put("message", "Student deleted successfully");
        return ResponseEntity.ok(response);
    }
}
