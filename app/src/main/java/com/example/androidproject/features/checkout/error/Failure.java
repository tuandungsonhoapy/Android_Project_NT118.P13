package com.example.androidproject.features.checkout.error;

public abstract class Failure {
    private final String message;

    protected Failure(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public static class DatabaseFailure extends Failure {
        public DatabaseFailure(String message) {
            super(message);
        }
    }
}
