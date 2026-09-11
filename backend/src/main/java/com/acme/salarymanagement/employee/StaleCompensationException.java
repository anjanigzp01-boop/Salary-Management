package com.acme.salarymanagement.employee;

public class StaleCompensationException extends RuntimeException {
    public StaleCompensationException() {
        super("This compensation record was changed by someone else. Refresh and try again.");
    }
}
