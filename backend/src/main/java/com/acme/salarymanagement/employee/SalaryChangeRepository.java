package com.acme.salarymanagement.employee;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SalaryChangeRepository extends JpaRepository<SalaryChange, Long> {
    List<SalaryChange> findByEmployeeEmployeeNumberOrderByChangedAtDesc(String employeeNumber);
}
