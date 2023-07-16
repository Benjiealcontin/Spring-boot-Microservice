package com.service.StudentService.Exception;

public class StudentDataRetrievalException extends RuntimeException {
    public StudentDataRetrievalException(String message) {
        super(message);
    }

    public StudentDataRetrievalException(String message, Throwable cause) {
        super(message, cause);
    }
}

