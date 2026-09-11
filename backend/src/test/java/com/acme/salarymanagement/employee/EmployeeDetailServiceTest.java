package com.acme.salarymanagement.employee;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmployeeDetailServiceTest {
    @Mock private EmployeeRepository employeeRepository;
    @Mock private SalaryChangeRepository salaryChangeRepository;

    @Test
    void updates_compensation_and_records_an_immutable_audit_entry() {
        Employee employee = employee();
        when(employeeRepository.findByEmployeeNumber("ACME-00001")).thenReturn(Optional.of(employee));
        when(salaryChangeRepository.findByEmployeeEmployeeNumberOrderByChangedAtDesc("ACME-00001")).thenReturn(List.of());
        Clock clock = Clock.fixed(Instant.parse("2026-09-11T10:00:00Z"), ZoneOffset.UTC);

        var result = new EmployeeDetailService(employeeRepository, salaryChangeRepository, clock).updateCompensation("ACME-00001",
                new UpdateCompensationRequest(new BigDecimal("95000.00"), 15, "USD", LocalDate.of(2026, 10, 1), 0), "a.lee");

        assertThat(result.compensation().annualBaseSalary()).isEqualByComparingTo("95000.00");
        assertThat(result.compensation().bonusTargetPercent()).isEqualTo(15);
        verify(salaryChangeRepository).save(any(SalaryChange.class));
    }

    @Test
    void rejects_a_stale_compensation_version_without_writing_an_audit_entry() {
        Employee employee = employee();
        when(employeeRepository.findByEmployeeNumber("ACME-00001")).thenReturn(Optional.of(employee));

        assertThatThrownBy(() -> new EmployeeDetailService(employeeRepository, salaryChangeRepository)
                .updateCompensation("ACME-00001", new UpdateCompensationRequest(new BigDecimal("95000"), 10, "USD",
                        LocalDate.now(), 1), "a.lee"))
                .isInstanceOf(StaleCompensationException.class);
        verify(salaryChangeRepository, never()).save(any());
    }

    private Employee employee() {
        Employee employee = new Employee("ACME-00001", "Avery", "Shah", "avery@acme.example", "Engineering", "US",
                "Engineer", LocalDate.of(2020, 1, 1), EmploymentStatus.ACTIVE);
        employee.assignCompensation(new Compensation(employee, new BigDecimal("86400"), 10, "USD", LocalDate.of(2026, 1, 1)));
        return employee;
    }
}
