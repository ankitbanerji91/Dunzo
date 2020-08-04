package com.dunzo.coffemaker.entities;

public class IngrediantRequirement {
    public Ingrediant ingrediant;
    public int quantityInMl;
    public IngrediantRequirement(Ingrediant ingrediant, int quantityInMl) {
        this.ingrediant = ingrediant;
        this.quantityInMl = quantityInMl;
    }

    @Override
    public String toString() {
        return "IngrediantRequirement{" +
                "ingrediant=" + ingrediant +
                ", quantityInMl=" + quantityInMl +
                '}';
    }
}
