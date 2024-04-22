package com.sergio.bakalarka.backend.repository;

/**
 * Výjimka pro chyby interakcí s databázi
 */

public class DaoException extends RuntimeException {

    private String message;

    public DaoException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
