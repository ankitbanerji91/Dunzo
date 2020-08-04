package com.dunzo.coffemaker.entities;

public enum Beverage {
    HOT_TEA("hot_tea"),
    HOT_COFFEE("hot_coffee"),
    BLACK_TEA("black_tea"),
    GREEN_TEA("green_tea");

    private final String name;

    Beverage(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
