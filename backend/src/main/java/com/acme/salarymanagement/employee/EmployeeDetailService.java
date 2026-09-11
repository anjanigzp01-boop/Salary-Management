package com.acme.salarymanagement.employee;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.Instant;
import java.util.NoSuchElementException;

@Service
public class EmployeeDetailService {
    private final EmployeeRepository employeeRepository;
    private final SalaryChangeRepository salaryChangeRepository;
    private final Clock clock;

    @Autowired
    public EmployeeDetailService(EmployeeRepository employeeRepository, SalaryChangeRepository salaryChangeRepository) {
        this(employeeRepository, salaryChangeRepository, Clock.systemUTC());
    }

    EmployeeDetailService(EmployeeRepository employeeRepository, SalaryChangeRepository salaryChangeRepository, Clock clock) {
        this.employeeRepository = employeeRepository;
        this.salaryChangeRepository = salaryChangeRepository;
        this.clock = clock;
    }

    @Transactional(readOnly = true)
    public EmployeeDetail findByEmployeeNumber(String employeeNumber) {
        Employee employee = employeeRepository.findByEmployeeNumber(employeeNumber).orElseThrow(() -> notFound(employeeNumber));
        return EmployeeDetail.from(employee, salaryChangeRepository.findByEmployeeEmployeeNumberOrderByChangedAtDesc(employeeNumber));
    }

    @Transactional
    public EmployeeDetail updateCompensation(String employeeNumber, UpdateCompensationRequest request, String actor) {
        Employee employee = employeeRepository.findByEmployeeNumber(employeeNumber).orElseThrow(() -> notFound(employeeNumber));
        Compensation compensation = employee.getCompensation();
        if (compensation.getVersion() != request.expectedVersion()) {
            throw new StaleCompensationException();
        }
        salaryChangeRepository.save(new SalaryChange(employee, compensation.getAnnualBaseSalary(), request.annualBaseSalary(),
                request.currency(), request.effectiveDate(), actor, Instant.now(clock)));
        compensation.update(request.annualBaseSalary(), request.bonusTargetPercent(), request.currency(), request.effectiveDate());
        return EmployeeDetail.from(employee, salaryChangeRepository.findByEmployeeEmployeeNumberOrderByChangedAtDesc(employeeNumber));
    }

    private NoSuchElementException notFound(String employeeNumber) {
        return new NoSuchElementException("Employee " + employeeNumber + " was not found");
    }
}
