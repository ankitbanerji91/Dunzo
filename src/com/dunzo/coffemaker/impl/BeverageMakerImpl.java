package com.dunzo.coffemaker.impl;

import com.dunzo.coffemaker.ifaces.BeverageAlgorithm;
import com.dunzo.coffemaker.ifaces.BeverageMaker;

public class BeverageMakerImpl implements BeverageMaker {
    final BeverageAlgorithm beverageAlgorithm;
    public BeverageMakerImpl(BeverageAlgorithm b) {
        this.beverageAlgorithm = b;
    }

    @Override
    public void makeBeverage() {
        // Sleep for a second to prepare beverage
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }

        // As of now we are not doing anything special with it.
        System.out.println(beverageAlgorithm.getInstructions());
        // TODO : Prepare the beverage with the instructions fetched
    }
}
