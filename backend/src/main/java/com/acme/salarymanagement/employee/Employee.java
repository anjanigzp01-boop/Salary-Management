package com.acme.salarymanagement.employee;

import jakarta.persistence.Column;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Version;

import java.time.LocalDate;

@Entity
@Table(name = "employees", indexes = {
        @Index(name = "idx_employee_number", columnList = "employee_number", unique = true),
        @Index(name = "idx_employee_directory_filter", columnList = "country,department,employment_status"),
        @Index(name = "idx_employee_name", columnList = "last_name,first_name")
})
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "employee_number", nullable = false, updatable = false, length = 32)
    private String employeeNumber;

    @Column(name = "first_name", nullable = false, length = 100)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 100)
    private String lastName;

    @Column(nullable = false, unique = true, length = 255)
    private String email;

    @Column(nullable = false, length = 100)
    private String department;

    @Column(nullable = false, length = 2)
    private String country;

    @Column(nullable = false, length = 100)
    private String jobTitle;

    @Column(nullable = false)
    private LocalDate hireDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "employment_status", nullable = false, length = 20)
    private EmploymentStatus employmentStatus;

    @Version
    private long version;

    @OneToOne(mappedBy = "employee", optional = false, cascade = CascadeType.ALL)
    private Compensation compensation;

    protected Employee() {
    }

    public Employee(String employeeNumber, String firstName, String lastName, String email,
                    String department, String country, String jobTitle, LocalDate hireDate,
                    EmploymentStatus employmentStatus) {
        this.employeeNumber = employeeNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.department = department;
        this.country = country;
        this.jobTitle = jobTitle;
        this.hireDate = hireDate;
        this.employmentStatus = employmentStatus;
    }

    public Long getId() { return id; }
    public String getEmployeeNumber() { return employeeNumber; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getEmail() { return email; }
    public String getCountry() { return country; }
    public String getDepartment() { return department; }
    public String getJobTitle() { return jobTitle; }
    public LocalDate getHireDate() { return hireDate; }
    public EmploymentStatus getEmploymentStatus() { return employmentStatus; }
    public Compensation getCompensation() { return compensation; }

    public void assignCompensation(Compensation compensation) {
        this.compensation = compensation;
    }
}
