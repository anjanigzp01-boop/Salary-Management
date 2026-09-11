package com.acme.salarymanagement.seed;

import com.acme.salarymanagement.employee.EmploymentStatus;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/** Produces stable synthetic records: safe for demos, repeatable in tests. */
@Component
public class EmployeeSeedFactory {
    private static final List<Location> LOCATIONS = List.of(
            new Location("US", "USD", 72_000), new Location("IN", "INR", 1_200_000),
            new Location("GB", "GBP", 48_000), new Location("DE", "EUR", 55_000),
            new Location("SG", "SGD", 65_000), new Location("AU", "AUD", 70_000));
    private static final List<String> DEPARTMENTS = List.of("Engineering", "Sales", "People", "Finance", "Operations", "Product");
    private static final List<String> FIRST_NAMES = List.of("Avery", "Jordan", "Riley", "Morgan", "Casey", "Taylor", "Arun", "Mei");
    private static final List<String> LAST_NAMES = List.of("Shah", "Patel", "Kim", "Garcia", "Smith", "Nguyen", "Brown", "Miller");

    public SeedEmployee create(int sequence) {
        if (sequence < 1) {
            throw new IllegalArgumentException("sequence must be positive");
        }
        Location location = LOCATIONS.get((sequence - 1) % LOCATIONS.size());
        String department = DEPARTMENTS.get((sequence / 3) % DEPARTMENTS.size());
        String firstName = FIRST_NAMES.get(sequence % FIRST_NAMES.size());
        String lastName = LAST_NAMES.get((sequence / FIRST_NAMES.size()) % LAST_NAMES.size());
        int salaryMultiplier = 1 + (sequence % 7);
        BigDecimal salary = BigDecimal.valueOf(location.baseSalary() + (salaryMultiplier * location.baseSalary() / 10L));
        EmploymentStatus status = sequence % 97 == 0 ? EmploymentStatus.ON_LEAVE : EmploymentStatus.ACTIVE;

        return new SeedEmployee(
                "ACME-%05d".formatted(sequence), firstName, lastName,
                "employee%05d@acme.example".formatted(sequence), department, location.country(),
                department + " Specialist", LocalDate.of(2015 + (sequence % 10), 1 + (sequence % 12), 1 + (sequence % 28)),
                status, salary, department.equals("Sales") ? 20 : 10, location.currency(),
                LocalDate.of(2026, 1, 1));
    }

    private record Location(String country, String currency, long baseSalary) {
    }
}
