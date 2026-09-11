package com.acme.salarymanagement.employee;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.math.BigDecimal;
import java.time.LocalDate;

public record UpdateCompensationRequest(
        @NotNull @DecimalMin(value = "0.01") BigDecimal annualBaseSalary,
        @Min(0) @Max(100) int bonusTargetPercent,
        @NotBlank @Pattern(regexp = "[A-Z]{3}") String currency,
        @NotNull LocalDate effectiveDate,
        @Min(0) long expectedVersion) {
}
