package com.acme.salarymanagement.employee;

import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

final class EmployeeSpecifications {
    private EmployeeSpecifications() {
    }

    static Specification<Employee> matching(EmployeeDirectoryQuery query) {
        return (root, ignoredQuery, criteriaBuilder) -> {
            List<jakarta.persistence.criteria.Predicate> predicates = new ArrayList<>();
            if (query.search() != null) {
                String pattern = "%" + query.search() + "%";
                predicates.add(criteriaBuilder.or(
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("firstName")), pattern),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("lastName")), pattern),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("employeeNumber")), pattern)));
            }
            if (query.country() != null) {
                predicates.add(criteriaBuilder.equal(criteriaBuilder.lower(root.get("country")), query.country()));
            }
            if (query.department() != null) {
                predicates.add(criteriaBuilder.equal(criteriaBuilder.lower(root.get("department")), query.department()));
            }
            if (query.status() != null) {
                predicates.add(criteriaBuilder.equal(root.get("employmentStatus"), query.status()));
            }
            return criteriaBuilder.and(predicates.toArray(new jakarta.persistence.criteria.Predicate[0]));
        };
    }
}
