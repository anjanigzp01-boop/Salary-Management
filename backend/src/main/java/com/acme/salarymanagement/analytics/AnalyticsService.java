package com.acme.salarymanagement.analytics;

import com.acme.salarymanagement.employee.CompensationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class AnalyticsService {
    private final CompensationRepository compensationRepository;

    public AnalyticsService(CompensationRepository compensationRepository) {
        this.compensationRepository = compensationRepository;
    }

    public CompensationAnalytics getAnalytics(AnalyticsQuery query) {
        Map<String, List<BigDecimal>> salariesByCurrency = compensationRepository
                .findSalaryValues(query.country(), query.department(), query.status()).stream()
                .collect(Collectors.groupingBy(CompensationRepository.SalaryValue::getCurrency,
                        Collectors.mapping(CompensationRepository.SalaryValue::getAnnualBaseSalary, Collectors.toList())));
        List<CompensationAnalytics.CurrencySummary> currencies = compensationRepository
                .summarizeByCurrency(query.country(), query.department(), query.status()).stream()
                .map(row -> new CompensationAnalytics.CurrencySummary(row.getCurrency(), row.getHeadcount(),
                        row.getTotalBaseSalary(), median(salariesByCurrency.get(row.getCurrency()))))
                .toList();
        return new CompensationAnalytics(currencies,
                compensationRepository.summarizeByCountry(query.country(), query.department(), query.status()).stream()
                        .map(row -> breakdown(row)).toList(),
                compensationRepository.summarizeByDepartment(query.country(), query.department(), query.status()).stream()
                        .map(row -> breakdown(row)).toList());
    }

    private CompensationAnalytics.Breakdown breakdown(CompensationRepository.BreakdownAggregate row) {
        return new CompensationAnalytics.Breakdown(row.getLabel(), row.getCurrency(), row.getHeadcount(), row.getTotalBaseSalary());
    }

    private BigDecimal median(List<BigDecimal> values) {
        List<BigDecimal> sorted = values.stream().sorted(Comparator.naturalOrder()).toList();
        int middle = sorted.size() / 2;
        return sorted.size() % 2 == 1 ? sorted.get(middle)
                : sorted.get(middle - 1).add(sorted.get(middle)).divide(BigDecimal.valueOf(2));
    }
}
