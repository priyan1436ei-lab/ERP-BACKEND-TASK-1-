package com.priyan.studentmanagement.service;

import com.priyan.studentmanagement.dto.StudentRequestDTO;
import com.priyan.studentmanagement.dto.StudentResponseDTO;
import com.priyan.studentmanagement.dto.StatisticsResponseDTO;
import com.priyan.studentmanagement.entity.Department;
import com.priyan.studentmanagement.entity.Student;
import com.priyan.studentmanagement.exception.DepartmentNotFoundException;
import com.priyan.studentmanagement.exception.DuplicateRegisterNumberException;
import com.priyan.studentmanagement.exception.StudentNotFoundException;
import com.priyan.studentmanagement.repository.DepartmentRepository;
import com.priyan.studentmanagement.repository.StudentRepository;
import com.priyan.studentmanagement.repository.StudentSpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class StudentService {

    private final StudentRepository studentRepository;
    private final DepartmentRepository departmentRepository;

    // Constructor injection
    public StudentService(StudentRepository studentRepository, DepartmentRepository departmentRepository) {
        this.studentRepository = studentRepository;
        this.departmentRepository = departmentRepository;
    }

    public StudentResponseDTO createStudent(StudentRequestDTO request) {
        // Check for duplicate register number
        if (studentRepository.existsByRegisterNo(request.getRegisterNo().trim())) {
            throw new DuplicateRegisterNumberException("Register number already exists");
        }

        Student student = new Student();
        student.setRegisterNo(request.getRegisterNo().trim());
        student.setName(request.getName().trim());
        student.setEmail(request.getEmail().trim());
        student.setPhone(request.getPhone().trim());
        student.setDepartment(request.getDepartment().trim());
        student.setYear(request.getYear());
        student.setSemester(request.getSemester());

        // Associate with department if matching code exists
        Optional<Department> deptOpt = departmentRepository.findByDepartmentCodeIgnoreCase(request.getDepartment().trim());
        deptOpt.ifPresent(student::setDepartmentEntity);

        Student saved = studentRepository.save(student);
        return mapToDTO(saved);
    }

    @Transactional(readOnly = true)
    public StudentResponseDTO getStudentById(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException("Student not found"));
        return mapToDTO(student);
    }

    @Transactional(readOnly = true)
    public List<StudentResponseDTO> getAllStudents(String department, Integer year, Integer semester) {
        Specification<Student> spec = StudentSpecification.withFilters(department, year, semester);
        List<Student> students = studentRepository.findAll(spec);
        return students.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<StudentResponseDTO> searchStudents(String name, String registerNo) {
        List<Student> students;
        boolean hasName = name != null && !name.trim().isEmpty();
        boolean hasRegisterNo = registerNo != null && !registerNo.trim().isEmpty();

        if (hasName && hasRegisterNo) {
            students = studentRepository.findByNameContainingIgnoreCaseOrRegisterNoContainingIgnoreCase(
                    name.trim(), registerNo.trim());
        } else if (hasName) {
            students = studentRepository.findByNameContainingIgnoreCase(name.trim());
        } else if (hasRegisterNo) {
            students = studentRepository.findByRegisterNoContainingIgnoreCase(registerNo.trim());
        } else {
            students = studentRepository.findAll();
        }

        return students.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public StudentResponseDTO updateStudent(Long id, StudentRequestDTO request) {
        Student existingStudent = studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException("Student not found"));

        // Check if register number is taken by another student
        if (studentRepository.existsByRegisterNoAndIdNot(request.getRegisterNo().trim(), id)) {
            throw new DuplicateRegisterNumberException("Register number already exists");
        }

        existingStudent.setRegisterNo(request.getRegisterNo().trim());
        existingStudent.setName(request.getName().trim());
        existingStudent.setEmail(request.getEmail().trim());
        existingStudent.setPhone(request.getPhone().trim());
        existingStudent.setDepartment(request.getDepartment().trim());
        existingStudent.setYear(request.getYear());
        existingStudent.setSemester(request.getSemester());

        // Associate with department if matching code exists
        Optional<Department> deptOpt = departmentRepository.findByDepartmentCodeIgnoreCase(request.getDepartment().trim());
        existingStudent.setDepartmentEntity(deptOpt.orElse(null));

        Student updated = studentRepository.save(existingStudent);
        return mapToDTO(updated);
    }

    public void deleteStudent(Long id) {
        if (!studentRepository.existsById(id)) {
            throw new StudentNotFoundException("Student not found");
        }
        studentRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public StatisticsResponseDTO getStatistics() {
        long totalStudents = studentRepository.count();

        // Department-wise count using JPQL GROUP BY
        Map<String, Long> departmentWise = new LinkedHashMap<>();
        List<Object[]> deptResults = studentRepository.countStudentsByDepartment();
        for (Object[] row : deptResults) {
            if (row[0] != null) {
                departmentWise.put(row[0].toString(), ((Number) row[1]).longValue());
            }
        }

        // Year-wise count using JPQL GROUP BY
        Map<String, Long> yearWise = new LinkedHashMap<>();
        List<Object[]> yearResults = studentRepository.countStudentsByYear();
        for (Object[] row : yearResults) {
            if (row[0] != null) {
                yearWise.put(row[0].toString(), ((Number) row[1]).longValue());
            }
        }

        return new StatisticsResponseDTO(totalStudents, departmentWise, yearWise);
    }

    @Transactional(readOnly = true)
    public List<StudentResponseDTO> getStudentsByDepartmentId(Long departmentId) {
        if (!departmentRepository.existsById(departmentId)) {
            throw new DepartmentNotFoundException("Department not found");
        }
        List<Student> students = studentRepository.findByDepartmentEntityId(departmentId);
        return students.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    private StudentResponseDTO mapToDTO(Student student) {
        Long deptId = student.getDepartmentEntity() != null ? student.getDepartmentEntity().getId() : null;
        return new StudentResponseDTO(
                student.getId(),
                student.getRegisterNo(),
                student.getName(),
                student.getEmail(),
                student.getPhone(),
                student.getDepartment(),
                deptId,
                student.getYear(),
                student.getSemester(),
                student.getCreatedAt(),
                student.getUpdatedAt()
        );
    }
}
