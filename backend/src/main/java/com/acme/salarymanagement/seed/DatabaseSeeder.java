package com.acme.salarymanagement.seed;

import com.acme.salarymanagement.employee.Compensation;
import com.acme.salarymanagement.employee.Employee;
import com.acme.salarymanagement.employee.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

@Configuration
class DatabaseSeeder {
    private static final Logger log = LoggerFactory.getLogger(DatabaseSeeder.class);
    private static final int EMPLOYEE_COUNT = 10_000;

    @Bean
    CommandLineRunner seedDatabase(EmployeeRepository employeeRepository, EmployeeSeedFactory factory,
                                   @Value("${app.seed.enabled:true}") boolean enabled) {
        return arguments -> {
            if (enabled && employeeRepository.count() == 0) {
                seed(employeeRepository, factory);
            }
        };
    }

    @Transactional
    void seed(EmployeeRepository employeeRepository, EmployeeSeedFactory factory) {
        for (int sequence = 1; sequence <= EMPLOYEE_COUNT; sequence++) {
            SeedEmployee source = factory.create(sequence);
            Employee employee = new Employee(source.employeeNumber(), source.firstName(), source.lastName(), source.email(),
                    source.department(), source.country(), source.jobTitle(), source.hireDate(), source.employmentStatus());
            Compensation compensation = new Compensation(employee, source.annualBaseSalary(), source.bonusTargetPercent(),
                    source.currency(), source.salaryEffectiveDate());
            employee.assignCompensation(compensation);
            employeeRepository.save(employee);
        }
        log.info("Seeded {} synthetic employees and compensation records", EMPLOYEE_COUNT);
    }
}
