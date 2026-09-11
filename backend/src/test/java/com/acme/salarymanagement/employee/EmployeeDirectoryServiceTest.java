package com.acme.salarymanagement.employee;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmployeeDirectoryServiceTest {
    @Mock private EmployeeRepository employeeRepository;
    @Captor private ArgumentCaptor<Pageable> pageableCaptor;

    @Test
    void maps_a_database_page_to_a_compact_directory_response() {
        Employee employee = new Employee("ACME-00001", "Avery", "Shah", "avery@acme.example", "Engineering", "US",
                "Engineering Specialist", LocalDate.of(2020, 1, 1), EmploymentStatus.ACTIVE);
        employee.assignCompensation(new Compensation(employee, new BigDecimal("86400.00"), 10, "USD", LocalDate.of(2026, 1, 1)));
        when(employeeRepository.findAll(any(Specification.class), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(employee)));

        var result = new EmployeeDirectoryService(employeeRepository)
                .findEmployees(new EmployeeDirectoryQuery("avery", "US", null, EmploymentStatus.ACTIVE, 0, 25));

        assertThat(result.content()).singleElement().satisfies(item -> {
            assertThat(item.fullName()).isEqualTo("Avery Shah");
            assertThat(item.annualBaseSalary()).isEqualByComparingTo("86400.00");
            assertThat(item.currency()).isEqualTo("USD");
        });
        verify(employeeRepository).findAll(any(Specification.class), pageableCaptor.capture());
        assertThat(pageableCaptor.getValue().getPageSize()).isEqualTo(25);
    }
}
