package com.acme.salarymanagement.employee;

import com.acme.salarymanagement.api.PageResponse;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/v1/employees")
@Validated
public class EmployeeDirectoryController {
    private final EmployeeDirectoryService employeeDirectoryService;
    private final EmployeeDetailService employeeDetailService;

    public EmployeeDirectoryController(EmployeeDirectoryService employeeDirectoryService, EmployeeDetailService employeeDetailService) {
        this.employeeDirectoryService = employeeDirectoryService;
        this.employeeDetailService = employeeDetailService;
    }

    @GetMapping
    public PageResponse<EmployeeDirectoryItem> findEmployees(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String country,
            @RequestParam(required = false) String department,
            @RequestParam(required = false) EmploymentStatus status,
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "25") @Min(1) @Max(100) int size) {
        return employeeDirectoryService.findEmployees(
                new EmployeeDirectoryQuery(search, country, department, status, page, size));
    }

    @GetMapping("/{employeeNumber}")
    public EmployeeDetail findEmployee(@PathVariable String employeeNumber) {
        return employeeDetailService.findByEmployeeNumber(employeeNumber);
    }

    @PutMapping("/{employeeNumber}/compensation")
    public EmployeeDetail updateCompensation(@PathVariable String employeeNumber,
                                             @RequestBody @jakarta.validation.Valid UpdateCompensationRequest request,
                                             @RequestHeader(name = "X-Actor", defaultValue = "development.hr.manager") String actor) {
        return employeeDetailService.updateCompensation(employeeNumber, request, actor);
    }
}
