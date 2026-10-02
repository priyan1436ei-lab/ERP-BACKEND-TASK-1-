package com.priyan.studentmanagement.repository;

import com.priyan.studentmanagement.entity.Student;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class StudentSpecification {

    private StudentSpecification() {
    }

    public static Specification<Student> withFilters(String department, Integer year, Integer semester) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (department != null && !department.trim().isEmpty()) {
                predicates.add(cb.equal(cb.lower(root.get("department")), department.trim().toLowerCase()));
            }

            if (year != null) {
                predicates.add(cb.equal(root.get("year"), year));
            }

            if (semester != null) {
                predicates.add(cb.equal(root.get("semester"), semester));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
