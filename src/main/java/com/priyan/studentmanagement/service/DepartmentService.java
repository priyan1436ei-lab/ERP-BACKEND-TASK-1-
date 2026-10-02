package com.priyan.studentmanagement.service;

import com.priyan.studentmanagement.dto.DepartmentRequestDTO;
import com.priyan.studentmanagement.dto.DepartmentResponseDTO;
import com.priyan.studentmanagement.entity.Department;
import com.priyan.studentmanagement.exception.DepartmentNotFoundException;
import com.priyan.studentmanagement.exception.DuplicateDepartmentCodeException;
import com.priyan.studentmanagement.repository.DepartmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public DepartmentResponseDTO createDepartment(DepartmentRequestDTO request) {
        if (departmentRepository.existsByDepartmentCodeIgnoreCase(request.getDepartmentCode().trim())) {
            throw new DuplicateDepartmentCodeException("Department code already exists");
        }

        Department department = new Department(
                request.getDepartmentCode().trim(),
                request.getDepartmentName().trim()
        );
        Department saved = departmentRepository.save(department);
        return mapToDTO(saved);
    }

    @Transactional(readOnly = true)
    public DepartmentResponseDTO getDepartmentById(Long id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new DepartmentNotFoundException("Department not found"));
        return mapToDTO(department);
    }

    @Transactional(readOnly = true)
    public List<DepartmentResponseDTO> getAllDepartments() {
        return departmentRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public DepartmentResponseDTO updateDepartment(Long id, DepartmentRequestDTO request) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new DepartmentNotFoundException("Department not found"));

        if (departmentRepository.existsByDepartmentCodeIgnoreCaseAndIdNot(request.getDepartmentCode().trim(), id)) {
            throw new DuplicateDepartmentCodeException("Department code already exists");
        }

        department.setDepartmentCode(request.getDepartmentCode().trim());
        department.setDepartmentName(request.getDepartmentName().trim());

        Department updated = departmentRepository.save(department);
        return mapToDTO(updated);
    }

    public void deleteDepartment(Long id) {
        if (!departmentRepository.existsById(id)) {
            throw new DepartmentNotFoundException("Department not found");
        }
        departmentRepository.deleteById(id);
    }

    private DepartmentResponseDTO mapToDTO(Department department) {
        return new DepartmentResponseDTO(
                department.getId(),
                department.getDepartmentCode(),
                department.getDepartmentName()
        );
    }
}
