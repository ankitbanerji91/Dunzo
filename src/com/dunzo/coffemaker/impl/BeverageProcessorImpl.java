package com.dunzo.coffemaker.impl;

import com.dunzo.coffemaker.entities.Beverage;
import com.dunzo.coffemaker.entities.IngrediantRequirement;
import com.dunzo.coffemaker.entities.Nozzle;
import com.dunzo.coffemaker.entities.exceptions.IngrediantNotEnough;
import com.dunzo.coffemaker.entities.exceptions.IngrediantNotFound;
import com.dunzo.coffemaker.entities.exceptions.NozelInUse;
import com.dunzo.coffemaker.factory.BeverageMakerFactory;
import com.dunzo.coffemaker.ifaces.BeverageCarrier;
import com.dunzo.coffemaker.ifaces.BeverageProcessor;
import com.dunzo.coffemaker.ifaces.IngrediantFetcher;

import java.util.Collection;
import java.util.Map;

public class BeverageProcessorImpl implements BeverageProcessor {
    final BeverageMakerFactory beverageMakerFactory;
    final BeverageCarrier beverageCarrier;
    final IngrediantFetcher ingrediantFetcher;
    final Map<Beverage, Collection<IngrediantRequirement>> beverageToIngrediantRequirements;
    public BeverageProcessorImpl(final BeverageMakerFactory beverageMakerFactory,
                                 final BeverageCarrier beverageCarrier,
                                 final IngrediantFetcher ingrediantFetcher,
                                 final Map<Beverage, Collection<IngrediantRequirement>> beverageToIngrediantRequirements) {
        this.beverageCarrier = beverageCarrier;
        this.beverageMakerFactory = beverageMakerFactory;
        this.ingrediantFetcher = ingrediantFetcher;
        this.beverageToIngrediantRequirements = beverageToIngrediantRequirements;
    }

    @Override
    public void process(Beverage beverage, Nozzle n) {
        try {
            // Only one beverage can be served from a nozzle at a time
            n.useNozel();

            try {
                ingrediantFetcher.fetchIngrediants(getIngrediantsRequired(beverage));
            } catch (IngrediantNotFound e) {
                System.out.println(beverage.getName() + " cannot be prepared, " + e.getMessage() + " is not available.");
                return;
            } catch (IngrediantNotEnough e) {
                System.out.println(beverage.getName() + " cannot be prepared, " + e.getMessage() + " is not sufficient.");
                return;
            }

            beverageMakerFactory.getBeverageMaker(beverage).makeBeverage();
            beverageCarrier.carryBeverage(n);
        } catch (NozelInUse e) {
            System.out.println(beverage.getName() + " cannot be prepared, Nozzle : " + n.getNozzleID() + " in use.");
        } finally {
            n.freeNozel();
        }
    }

    private Collection<IngrediantRequirement> getIngrediantsRequired(Beverage beverage) {
        return beverageToIngrediantRequirements.get(beverage);
    }
}
