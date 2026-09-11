Salary Management System

Requirements:

Build a web application for the HR team to manage salary information for around 10,000 employees instead of maintaining it in Excel files.
The HR Manager should be able to easily search employees, update salaries, and view basic salary-related reports.

User:

HR Manager

Scope:

Employee Management-

1. View employee list with pagination.
2. Search by employee ID, name, or email.
3. Filter employees by country, department, designation, and status.
4. View employee details.
5. Add or update employee salary details.

Salary Management-

1. Store current salary and currency.
2. Update salary with an effective date.
3. Keep previous salary information as salary history.
4. Validate salary values before saving.

Dashboard:

The HR Manager should be able to see:

1. Total number of employees.
2. Average salary.
3. Minimum and maximum salary.
4. Salary by country.
5. Salary by department.
6. Basic salary range/distribution.

Data & Testing:

1. Provide a seed script to create 10,000 employees.
2. Add proper database indexes for commonly used search/filter fields.
3. Add unit tests for important business logic.
4. Add API/integration tests for the main endpoints.

Tech Used:

Backend: Java with Spring Boot and REST APIs.
Database: Relational database.
Frontend: ReactJS.

- Use pagination instead of loading all employees at once.
- Follow a simple layered structure: Controller → Service → Repository.
- Proper validation and error handling.
- The application should be easy to run locally and deploy.

Assumptions:

1. Each employee has one current salary.
2. Salary changes are stored in salary history.
3. Salary is stored along with its currency.
4. Employee ID is unique.
5. Initial employee data will be generated using the seed script.

Expectations from Task:

The HR Manager should be able to quickly find an employee, view or update salary information, see salary history, and get basic salary insights without maintaining Excel files.
