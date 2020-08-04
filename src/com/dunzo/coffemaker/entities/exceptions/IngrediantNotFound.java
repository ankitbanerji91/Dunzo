package com.dunzo.coffemaker.entities.exceptions;

public class IngrediantNotFound extends RuntimeException {
    public IngrediantNotFound(String message) {
        super(message);
    }
}
