package com.example.androidproject.core.errors;

public class Failure {
    private final String message;
    private static final String UNEXPECTED_ERROR = "An unexpected error occurred.";

    public Failure(String message) {
        this.message = message != null ? message : UNEXPECTED_ERROR;
    }

    public String getErrorMessage() {
        return message;
    }
}
