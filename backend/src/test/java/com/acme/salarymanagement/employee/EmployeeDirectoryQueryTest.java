package com.acme.salarymanagement.employee;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class EmployeeDirectoryQueryTest {

    @Test
    void normalizes_optional_text_filters_and_applies_stable_default_sorting() {
        EmployeeDirectoryQuery query = new EmployeeDirectoryQuery("  Avery ", " us ", "  Engineering ",
                EmploymentStatus.ACTIVE, 2, 25);

        assertThat(query.search()).isEqualTo("avery");
        assertThat(query.country()).isEqualTo("us");
        assertThat(query.department()).isEqualTo("engineering");
        assertThat(query.pageable().getSort().getOrderFor("employeeNumber").isAscending()).isTrue();
    }

    @Test
    void rejects_unbounded_or_negative_paging_requests() {
        assertThatThrownBy(() -> new EmployeeDirectoryQuery(null, null, null, null, -1, 25))
                .hasMessage("page must not be negative");
        assertThatThrownBy(() -> new EmployeeDirectoryQuery(null, null, null, null, 0, 101))
                .hasMessage("size must be between 1 and 100");
    }
}
