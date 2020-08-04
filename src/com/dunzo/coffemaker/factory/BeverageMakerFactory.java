package com.dunzo.coffemaker.factory;

import com.dunzo.coffemaker.entities.Beverage;
import com.dunzo.coffemaker.ifaces.BeverageAlgorithm;
import com.dunzo.coffemaker.ifaces.BeverageMaker;
import com.dunzo.coffemaker.impl.BeverageMakerImpl;

import java.util.ArrayList;
import java.util.Collection;

public class BeverageMakerFactory {

    public BeverageMaker getBeverageMaker(Beverage beverage) {
        // Just a simple algorithm this can be extended in future.
        BeverageMaker bm = new BeverageMakerImpl(new BeverageAlgorithm() {
            @Override
            public Collection<String> getInstructions() {
                ArrayList<String> instructions = new ArrayList<>();
                instructions.add(beverage.getName() + " is prepared");
                return instructions;
            }
        });
        return bm;
    }
}
