package com.acme.salarymanagement.analytics;

import java.math.BigDecimal;
import java.util.List;

public record CompensationAnalytics(List<CurrencySummary> currencies, List<Breakdown> byCountry,
                                    List<Breakdown> byDepartment) {
    public record CurrencySummary(String currency, long headcount, BigDecimal totalAnnualBaseSalary,
                                  BigDecimal medianAnnualBaseSalary) { }
    public record Breakdown(String label, String currency, long headcount, BigDecimal totalAnnualBaseSalary) { }
}
