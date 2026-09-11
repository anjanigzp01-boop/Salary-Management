package com.acme.salarymanagement.analytics;

import com.acme.salarymanagement.employee.EmploymentStatus;

import java.util.Locale;

public record AnalyticsQuery(String country, String department, EmploymentStatus status) {
    public AnalyticsQuery {
        country = normalize(country);
        department = normalize(department);
    }

    private static String normalize(String value) {
        return value == null || value.isBlank() ? null : value.trim().toLowerCase(Locale.ROOT);
    }
}
