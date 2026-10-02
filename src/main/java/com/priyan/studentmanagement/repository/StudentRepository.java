package com.priyan.studentmanagement.repository;

import com.priyan.studentmanagement.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long>, JpaSpecificationExecutor<Student> {

    Optional<Student> findByRegisterNo(String registerNo);

    boolean existsByRegisterNo(String registerNo);

    boolean existsByRegisterNoAndIdNot(String registerNo, Long id);

    // Derived search queries for partial, case-insensitive match
    List<Student> findByNameContainingIgnoreCase(String name);

    List<Student> findByRegisterNoContainingIgnoreCase(String registerNo);

    List<Student> findByNameContainingIgnoreCaseOrRegisterNoContainingIgnoreCase(String name, String registerNo);

    // Bonus 1: Students belonging to a department
    List<Student> findByDepartmentEntityId(Long departmentId);

    // Bonus 2: JPQL GROUP BY queries for statistics
    @Query("SELECT s.department, COUNT(s) FROM Student s GROUP BY s.department")
    List<Object[]> countStudentsByDepartment();

    @Query("SELECT s.year, COUNT(s) FROM Student s GROUP BY s.year")
    List<Object[]> countStudentsByYear();
}
