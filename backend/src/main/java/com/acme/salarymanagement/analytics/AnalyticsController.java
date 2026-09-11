package com.acme.salarymanagement.analytics;

import com.acme.salarymanagement.employee.EmploymentStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/v1/analytics")
public class AnalyticsController {
    private final AnalyticsService analyticsService;

    public AnalyticsController(AnalyticsService analyticsService) {
        this.analyticsService = analyticsService;
    }

    @GetMapping
    public CompensationAnalytics getAnalytics(@RequestParam(required = false) String country,
                                              @RequestParam(required = false) String department,
                                              @RequestParam(required = false) EmploymentStatus status) {
        return analyticsService.getAnalytics(new AnalyticsQuery(country, department, status));
    }
}
