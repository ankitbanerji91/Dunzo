package com.dunzo.coffemaker.impl;

import com.dunzo.coffemaker.entities.Beverage;
import com.dunzo.coffemaker.entities.Ingrediant;
import com.dunzo.coffemaker.entities.IngrediantRequirement;
import com.dunzo.coffemaker.entities.Nozzle;
import com.dunzo.coffemaker.factory.BeverageMakerFactory;
import com.dunzo.coffemaker.ifaces.BeverageProcessor;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * Design :
 *
 * We are assuming that our coffee maker is has multiple CPUs which are attached to multiple coffee making units.
 * We are also assuming that when any java thread scheduled on a CPU/Coffee Making Unit, it will run it course and
 * would leave the CPU until it dies or is finished.
 *
 * Customer Flow :
 * -> Customer can only take or request a single beverage at a time from a nozzle.
 *    Any parallel requests to request multiple beverages, will only allow the very first request to go through.
 * -> Beverage Making takes ~ 1 second, @{@link BeverageMakerImpl#makeBeverage()}
 *
 * Details :
 * -> All the coffee making happens in parallel for N nozzles in different CPU units.
 * -> @{@link BeverageMakerImpl} is responsible for making beverages, different algorithms are inserted to make different beverages.
 * -> @{@link BeverageProcessorImpl} is responsible for carrying beverages from processing units to the nozzles.
 * -> @{@link BeverageProcessorImpl} is the main orchestrator of the workflow.
 *
 *
 * TODO :
 * -> Didnt implement state storage of ingredients left when power shuts off, we are assuming process is never killed.
 * -> Didnt implement state cleanup for the case when the beverage making thread dies in between.
 */
public class CoffeeMaker {
    public static class Configuration {
        // @Visible for testing
        public Map<Ingrediant, Integer> ingrediantToQuantityMap;
        public Map<Beverage, Collection<IngrediantRequirement>> beverageToIngredientRequirements;
        public int nozzleCount;
    }

    // These represent actual Nozzle which drips coffee/tea
    private final Map<Integer, Nozzle> integerToNozzleMap = new HashMap<>();
    private final ExecutorService executorService;
    private final Configuration configuration;
    private final BeverageProcessor bp;

    public CoffeeMaker(final Configuration configuration) {
        this.configuration = configuration;
        // 10 times the nozzle should be good enough to handle cases when customer is trying to queue a
        // a beverage on same nozzle at the same time which would make then number of threads require to more
        // than nozzleCount.
        this.executorService = Executors.newFixedThreadPool(10 * configuration.nozzleCount);
        this.bp = new BeverageProcessorImpl(new BeverageMakerFactory(),
                new BeverageCarrierImpl(),
                new IngredientFetcherImpl(this.configuration.ingrediantToQuantityMap),
                this.configuration.beverageToIngredientRequirements);

        for (int i = 0; i < this.configuration.nozzleCount; i++) {
            integerToNozzleMap.put(i, new Nozzle(i));
        }

    }

    public void makeCoffee(final Beverage beverage, final int nozzleNumber) {
        executorService.submit(getBeverageProcessorRunnable(beverage, integerToNozzleMap.get(nozzleNumber)));
    }

    private Runnable getBeverageProcessorRunnable(Beverage beverage, Nozzle nozzle) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                bp.process(beverage, nozzle);
            }
        };
        return runnable;
    }
}
