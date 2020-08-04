package com.dunzo.coffemaker.impl;

import com.dunzo.coffemaker.entities.Ingrediant;
import com.dunzo.coffemaker.entities.IngrediantRequirement;
import com.dunzo.coffemaker.entities.exceptions.IngrediantNotEnough;
import com.dunzo.coffemaker.entities.exceptions.IngrediantNotFound;
import com.dunzo.coffemaker.ifaces.IngrediantFetcher;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class IngredientFetcherImpl implements IngrediantFetcher {
    final Map<Ingrediant, Integer> pendingItemQuantity = new HashMap<>();
    public IngredientFetcherImpl(Map<Ingrediant, Integer> itemQuantity) {
        this.pendingItemQuantity.putAll(itemQuantity);
    }

    @Override
    public synchronized void fetchIngrediants(Collection<IngrediantRequirement> ingrediantRequirements) {
        // Check if Ingrediants are sufficient
        for (IngrediantRequirement ir : ingrediantRequirements) {
            if (pendingItemQuantity.get(ir.ingrediant) == null) {
                throw new IngrediantNotFound(ir.ingrediant.getName());
            }
            if (ir.quantityInMl > pendingItemQuantity.get(ir.ingrediant)) {
                throw new IngrediantNotEnough(ir.ingrediant.getName());
            }
        }
        for (IngrediantRequirement ir : ingrediantRequirements) {
            pendingItemQuantity.put(ir.ingrediant, pendingItemQuantity.get(ir.ingrediant) - ir.quantityInMl);
        }
    }
}
