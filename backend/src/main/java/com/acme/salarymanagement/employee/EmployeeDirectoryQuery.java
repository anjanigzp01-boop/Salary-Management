package com.acme.salarymanagement.employee;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Locale;

public record EmployeeDirectoryQuery(String search, String country, String department, EmploymentStatus status,
                                     int page, int size) {
    private static final int MAX_PAGE_SIZE = 100;

    public EmployeeDirectoryQuery {
        if (page < 0) {
            throw new IllegalArgumentException("page must not be negative");
        }
        if (size < 1 || size > MAX_PAGE_SIZE) {
            throw new IllegalArgumentException("size must be between 1 and " + MAX_PAGE_SIZE);
        }
        search = normalize(search);
        country = normalize(country);
        department = normalize(department);
    }

    public Pageable pageable() {
        return PageRequest.of(page, size, Sort.by("employeeNumber").ascending());
    }

    private static String normalize(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return value.trim().toLowerCase(Locale.ROOT);
    }
}
