package com.dunzo.coffemaker.entities;

public enum Ingrediant {
    HOT_WATER("hot_water"),
    HOT_MILK("hot_milk"),
    GINGER_SYRUP("ginger_syrup"),
    SUGAR_SYRUP("sugar_syrup"),
    TEA_LEAVES_SYRUP("tea_leaves_syrup"),
    GREEN_MIXTURE("green_mixture");

    private final String name;

    Ingrediant(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
