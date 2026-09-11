package com.acme.salarymanagement.employee;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

@Entity
@Table(name = "salary_changes")
public class SalaryChange {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @Column(name = "previous_base_salary", nullable = false, precision = 19, scale = 2)
    private BigDecimal previousBaseSalary;

    @Column(name = "new_base_salary", nullable = false, precision = 19, scale = 2)
    private BigDecimal newBaseSalary;

    @Column(nullable = false, length = 3)
    private String currency;

    @Column(name = "effective_date", nullable = false)
    private LocalDate effectiveDate;

    @Column(name = "changed_by", nullable = false, length = 120)
    private String changedBy;

    @Column(name = "changed_at", nullable = false, updatable = false)
    private Instant changedAt;

    protected SalaryChange() {
    }

    public SalaryChange(Employee employee, BigDecimal previousBaseSalary, BigDecimal newBaseSalary,
                        String currency, LocalDate effectiveDate, String changedBy, Instant changedAt) {
        this.employee = employee;
        this.previousBaseSalary = previousBaseSalary;
        this.newBaseSalary = newBaseSalary;
        this.currency = currency;
        this.effectiveDate = effectiveDate;
        this.changedBy = changedBy;
        this.changedAt = changedAt;
    }

    public BigDecimal getPreviousBaseSalary() { return previousBaseSalary; }
    public BigDecimal getNewBaseSalary() { return newBaseSalary; }
    public String getCurrency() { return currency; }
    public LocalDate getEffectiveDate() { return effectiveDate; }
    public String getChangedBy() { return changedBy; }
    public Instant getChangedAt() { return changedAt; }
}
