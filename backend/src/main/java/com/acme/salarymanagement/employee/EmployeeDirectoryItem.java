package com.acme.salarymanagement.employee;

import java.math.BigDecimal;
import java.time.LocalDate;

public record EmployeeDirectoryItem(
        String employeeNumber,
        String fullName,
        String email,
        String department,
        String jobTitle,
        String country,
        EmploymentStatus employmentStatus,
        BigDecimal annualBaseSalary,
        String currency,
        LocalDate salaryEffectiveDate) {

    static EmployeeDirectoryItem from(Employee employee) {
        Compensation compensation = employee.getCompensation();
        return new EmployeeDirectoryItem(employee.getEmployeeNumber(), employee.getFirstName() + " " + employee.getLastName(),
                employee.getEmail(), employee.getDepartment(), employee.getJobTitle(), employee.getCountry(),
                employee.getEmploymentStatus(), compensation.getAnnualBaseSalary(), compensation.getCurrency(),
                compensation.getEffectiveDate());
    }
}
