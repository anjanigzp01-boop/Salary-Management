package com.acme.salarymanagement.employee;

import com.acme.salarymanagement.api.PageResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class EmployeeDirectoryService {
    private final EmployeeRepository employeeRepository;

    public EmployeeDirectoryService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public PageResponse<EmployeeDirectoryItem> findEmployees(EmployeeDirectoryQuery query) {
        return PageResponse.from(employeeRepository.findAll(EmployeeSpecifications.matching(query), query.pageable()),
                EmployeeDirectoryItem::from);
    }
}
