package com.dunzo.coffemaker.ifaces;

import com.dunzo.coffemaker.entities.IngrediantRequirement;

import java.util.Collection;

public interface IngrediantFetcher {

    public void fetchIngrediants(final Collection<IngrediantRequirement> ingrediantRequirements);
}
