package com.acme.salarymanagement.analytics;

import com.acme.salarymanagement.employee.CompensationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AnalyticsServiceTest {
    @Mock private CompensationRepository compensationRepository;

    @Test
    void calculates_currency_median_while_using_database_aggregates_for_totals() {
        when(compensationRepository.findSalaryValues(any(), any(), any())).thenReturn(List.of(value("USD", "80000"), value("USD", "100000")));
        when(compensationRepository.summarizeByCurrency(any(), any(), any())).thenReturn(List.of(currency("USD", 2, "180000")));
        when(compensationRepository.summarizeByCountry(any(), any(), any())).thenReturn(List.of());
        when(compensationRepository.summarizeByDepartment(any(), any(), any())).thenReturn(List.of());

        var result = new AnalyticsService(compensationRepository).getAnalytics(new AnalyticsQuery(null, null, null));

        assertThat(result.currencies()).singleElement().satisfies(summary -> {
            assertThat(summary.headcount()).isEqualTo(2);
            assertThat(summary.totalAnnualBaseSalary()).isEqualByComparingTo("180000");
            assertThat(summary.medianAnnualBaseSalary()).isEqualByComparingTo("90000");
        });
    }

    private CompensationRepository.SalaryValue value(String currency, String salary) {
        return new CompensationRepository.SalaryValue() {
            public String getCurrency() { return currency; }
            public BigDecimal getAnnualBaseSalary() { return new BigDecimal(salary); }
        };
    }

    private CompensationRepository.CurrencyAggregate currency(String currency, long headcount, String total) {
        return new CompensationRepository.CurrencyAggregate() {
            public String getCurrency() { return currency; }
            public long getHeadcount() { return headcount; }
            public BigDecimal getTotalBaseSalary() { return new BigDecimal(total); }
        };
    }
}
