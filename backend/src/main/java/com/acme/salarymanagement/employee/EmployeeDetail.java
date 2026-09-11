package com.acme.salarymanagement.employee;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

public record EmployeeDetail(
        String employeeNumber, String firstName, String lastName, String email, String department, String jobTitle,
        String country, LocalDate hireDate, EmploymentStatus employmentStatus, CompensationDetail compensation,
        List<SalaryChangeItem> salaryHistory) {

    static EmployeeDetail from(Employee employee, List<SalaryChange> changes) {
        Compensation compensation = employee.getCompensation();
        return new EmployeeDetail(employee.getEmployeeNumber(), employee.getFirstName(), employee.getLastName(), employee.getEmail(),
                employee.getDepartment(), employee.getJobTitle(), employee.getCountry(), employee.getHireDate(), employee.getEmploymentStatus(),
                new CompensationDetail(compensation.getAnnualBaseSalary(), compensation.getBonusTargetPercent(), compensation.getCurrency(),
                        compensation.getEffectiveDate(), compensation.getVersion()),
                changes.stream().map(SalaryChangeItem::from).toList());
    }

    public record CompensationDetail(BigDecimal annualBaseSalary, int bonusTargetPercent, String currency,
                                     LocalDate effectiveDate, long version) { }

    public record SalaryChangeItem(BigDecimal previousBaseSalary, BigDecimal newBaseSalary, String currency,
                                   LocalDate effectiveDate, String changedBy, Instant changedAt) {
        static SalaryChangeItem from(SalaryChange change) {
            return new SalaryChangeItem(change.getPreviousBaseSalary(), change.getNewBaseSalary(), change.getCurrency(),
                    change.getEffectiveDate(), change.getChangedBy(), change.getChangedAt());
        }
    }
}
