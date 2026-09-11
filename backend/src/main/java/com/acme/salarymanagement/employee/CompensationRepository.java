package com.acme.salarymanagement.employee;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface CompensationRepository extends JpaRepository<Compensation, Long> {
    @Query("""
            select c.currency as currency, count(c) as headcount, sum(c.annualBaseSalary) as totalBaseSalary
            from Compensation c join c.employee e
            where (:country is null or lower(e.country) = :country)
              and (:department is null or lower(e.department) = :department)
              and (:status is null or e.employmentStatus = :status)
            group by c.currency
            order by c.currency
            """)
    List<CurrencyAggregate> summarizeByCurrency(@Param("country") String country, @Param("department") String department,
                                                @Param("status") EmploymentStatus status);

    @Query("""
            select e.country as label, c.currency as currency, count(c) as headcount, sum(c.annualBaseSalary) as totalBaseSalary
            from Compensation c join c.employee e
            where (:country is null or lower(e.country) = :country)
              and (:department is null or lower(e.department) = :department)
              and (:status is null or e.employmentStatus = :status)
            group by e.country, c.currency
            order by e.country, c.currency
            """)
    List<BreakdownAggregate> summarizeByCountry(@Param("country") String country, @Param("department") String department,
                                                @Param("status") EmploymentStatus status);

    @Query("""
            select e.department as label, c.currency as currency, count(c) as headcount, sum(c.annualBaseSalary) as totalBaseSalary
            from Compensation c join c.employee e
            where (:country is null or lower(e.country) = :country)
              and (:department is null or lower(e.department) = :department)
              and (:status is null or e.employmentStatus = :status)
            group by e.department, c.currency
            order by e.department, c.currency
            """)
    List<BreakdownAggregate> summarizeByDepartment(@Param("country") String country, @Param("department") String department,
                                                   @Param("status") EmploymentStatus status);

    @Query("""
            select c.currency as currency, c.annualBaseSalary as annualBaseSalary
            from Compensation c join c.employee e
            where (:country is null or lower(e.country) = :country)
              and (:department is null or lower(e.department) = :department)
              and (:status is null or e.employmentStatus = :status)
            """)
    List<SalaryValue> findSalaryValues(@Param("country") String country, @Param("department") String department,
                                       @Param("status") EmploymentStatus status);

    interface CurrencyAggregate {
        String getCurrency();
        long getHeadcount();
        BigDecimal getTotalBaseSalary();
    }

    interface BreakdownAggregate extends CurrencyAggregate {
        String getLabel();
    }

    interface SalaryValue {
        String getCurrency();
        BigDecimal getAnnualBaseSalary();
    }
}
