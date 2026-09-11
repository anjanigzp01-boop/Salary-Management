package com.acme.salarymanagement.employee;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "compensations")
public class Compensation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "employee_id", nullable = false, unique = true)
    private Employee employee;

    @DecimalMin(value = "0.01")
    @Column(name = "annual_base_salary", nullable = false, precision = 19, scale = 2)
    private BigDecimal annualBaseSalary;

    @Min(0)
    @Max(100)
    @Column(name = "bonus_target_percent", nullable = false)
    private int bonusTargetPercent;

    @Column(nullable = false, length = 3)
    private String currency;

    @Column(name = "effective_date", nullable = false)
    private LocalDate effectiveDate;

    protected Compensation() {
    }

    public Compensation(Employee employee, BigDecimal annualBaseSalary, int bonusTargetPercent,
                        String currency, LocalDate effectiveDate) {
        this.employee = employee;
        this.annualBaseSalary = annualBaseSalary;
        this.bonusTargetPercent = bonusTargetPercent;
        this.currency = currency;
        this.effectiveDate = effectiveDate;
    }
}
