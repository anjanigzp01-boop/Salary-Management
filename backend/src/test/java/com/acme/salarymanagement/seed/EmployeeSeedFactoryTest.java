package com.acme.salarymanagement.seed;

import com.acme.salarymanagement.employee.EmploymentStatus;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class EmployeeSeedFactoryTest {
    private final EmployeeSeedFactory factory = new EmployeeSeedFactory();

    @Test
    void produces_reproducible_synthetic_employee_records() {
        SeedEmployee employee = factory.create(1);

        assertThat(employee.employeeNumber()).isEqualTo("ACME-00001");
        assertThat(employee.email()).isEqualTo("employee00001@acme.example");
        assertThat(employee.currency()).isEqualTo("USD");
        assertThat(employee.annualBaseSalary()).isEqualByComparingTo(BigDecimal.valueOf(86400));
        assertThat(employee.employmentStatus()).isEqualTo(EmploymentStatus.ACTIVE);
        assertThat(factory.create(1)).isEqualTo(employee);
    }

    @Test
    void distributes_records_across_supported_countries_and_currencies() {
        assertThat(factory.create(1).country()).isEqualTo("US");
        assertThat(factory.create(2).country()).isEqualTo("IN");
        assertThat(factory.create(3).currency()).isEqualTo("GBP");
        assertThat(factory.create(6).currency()).isEqualTo("AUD");
    }

    @Test
    void marks_a_predictable_subset_as_on_leave() {
        assertThat(factory.create(97).employmentStatus()).isEqualTo(EmploymentStatus.ON_LEAVE);
        assertThat(factory.create(98).employmentStatus()).isEqualTo(EmploymentStatus.ACTIVE);
    }

    @Test
    void rejects_invalid_sequence_numbers() {
        assertThatThrownBy(() -> factory.create(0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("sequence must be positive");
    }
}
