package com.acme.salarymanagement.seed;

import com.acme.salarymanagement.employee.EmploymentStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

public record SeedEmployee(
        String employeeNumber,
        String firstName,
        String lastName,
        String email,
        String department,
        String country,
        String jobTitle,
        LocalDate hireDate,
        EmploymentStatus employmentStatus,
        BigDecimal annualBaseSalary,
        int bonusTargetPercent,
        String currency,
        LocalDate salaryEffectiveDate) {
}
