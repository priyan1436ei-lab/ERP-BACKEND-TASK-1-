package com.priyan.studentmanagement.repository;

import com.priyan.studentmanagement.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

    Optional<Department> findByDepartmentCode(String departmentCode);

    Optional<Department> findByDepartmentCodeIgnoreCase(String departmentCode);

    boolean existsByDepartmentCodeIgnoreCase(String departmentCode);

    boolean existsByDepartmentCodeIgnoreCaseAndIdNot(String departmentCode, Long id);
}
