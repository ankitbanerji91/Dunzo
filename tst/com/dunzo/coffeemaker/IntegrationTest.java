package com.dunzo.coffeemaker;

import com.dunzo.coffemaker.entities.Beverage;
import com.dunzo.coffemaker.entities.Ingrediant;
import com.dunzo.coffemaker.entities.IngrediantRequirement;
import com.dunzo.coffemaker.impl.CoffeeMaker;
import org.junit.Test;

import java.util.*;

public class IntegrationTest {

    private CoffeeMaker.Configuration getConfiguration() {
        Map<Ingrediant, Integer> ingrediantToQuantityMap = new HashMap<>();
        ingrediantToQuantityMap.put(Ingrediant.HOT_MILK, 500);
        ingrediantToQuantityMap.put(Ingrediant.HOT_WATER, 500);
        ingrediantToQuantityMap.put(Ingrediant.GINGER_SYRUP, 100);
        ingrediantToQuantityMap.put(Ingrediant.SUGAR_SYRUP, 100);
        ingrediantToQuantityMap.put(Ingrediant.TEA_LEAVES_SYRUP, 100);

        Map<Beverage, Collection<IngrediantRequirement>> beverageToIngrediantsMap = new HashMap<>();

        List<IngrediantRequirement> ingrediantRequirementList1 = new ArrayList<>();
        ingrediantRequirementList1.add(new IngrediantRequirement(Ingrediant.HOT_WATER, 200));
        ingrediantRequirementList1.add(new IngrediantRequirement(Ingrediant.HOT_MILK, 100));
        ingrediantRequirementList1.add(new IngrediantRequirement(Ingrediant.GINGER_SYRUP, 10));
        ingrediantRequirementList1.add(new IngrediantRequirement(Ingrediant.SUGAR_SYRUP, 10));
        ingrediantRequirementList1.add(new IngrediantRequirement(Ingrediant.TEA_LEAVES_SYRUP, 30));
        beverageToIngrediantsMap.put(Beverage.HOT_TEA, ingrediantRequirementList1);

        List<IngrediantRequirement> ingrediantRequirementList2 = new ArrayList<>();
        ingrediantRequirementList2.add(new IngrediantRequirement(Ingrediant.HOT_WATER, 100));
        ingrediantRequirementList2.add(new IngrediantRequirement(Ingrediant.GINGER_SYRUP, 30));
        ingrediantRequirementList2.add(new IngrediantRequirement(Ingrediant.HOT_MILK, 400));
        ingrediantRequirementList2.add(new IngrediantRequirement(Ingrediant.SUGAR_SYRUP, 50));
        ingrediantRequirementList2.add(new IngrediantRequirement(Ingrediant.TEA_LEAVES_SYRUP, 30));
        beverageToIngrediantsMap.put(Beverage.HOT_COFFEE, ingrediantRequirementList2);

        List<IngrediantRequirement> ingrediantRequirementList3 = new ArrayList<>();
        ingrediantRequirementList3.add(new IngrediantRequirement(Ingrediant.HOT_WATER, 300));
        ingrediantRequirementList3.add(new IngrediantRequirement(Ingrediant.GINGER_SYRUP, 30));
        ingrediantRequirementList3.add(new IngrediantRequirement(Ingrediant.SUGAR_SYRUP, 50));
        ingrediantRequirementList3.add(new IngrediantRequirement(Ingrediant.TEA_LEAVES_SYRUP, 30));
        beverageToIngrediantsMap.put(Beverage.BLACK_TEA, ingrediantRequirementList3);

        List<IngrediantRequirement> ingrediantRequirementList4 = new ArrayList<>();
        ingrediantRequirementList4.add(new IngrediantRequirement(Ingrediant.HOT_WATER, 100));
        ingrediantRequirementList4.add(new IngrediantRequirement(Ingrediant.GINGER_SYRUP, 30));
        ingrediantRequirementList4.add(new IngrediantRequirement(Ingrediant.SUGAR_SYRUP, 50));
        ingrediantRequirementList4.add(new IngrediantRequirement(Ingrediant.GREEN_MIXTURE, 30));
        beverageToIngrediantsMap.put(Beverage.GREEN_TEA, ingrediantRequirementList4);

        CoffeeMaker.Configuration configuration = new CoffeeMaker.Configuration();
        configuration.nozzleCount = 5;
        configuration.beverageToIngredientRequirements = beverageToIngrediantsMap;
        configuration.ingrediantToQuantityMap = ingrediantToQuantityMap;

        return configuration;
    }

    @Test
    public void testHappyCase_1() throws InterruptedException {
        CoffeeMaker cf = new CoffeeMaker(getConfiguration());
        // Adding a jitter to get deterministic output.
        Thread.sleep(100);
        cf.makeCoffee(Beverage.HOT_TEA, 0);
        // Adding a jitter to get deterministic output.
        Thread.sleep(100);
        cf.makeCoffee(Beverage.HOT_COFFEE, 1);
        // Adding a jitter to get deterministic output.
        Thread.sleep(100);
        cf.makeCoffee(Beverage.GREEN_TEA, 2);
        // Adding a jitter to get deterministic output.
        Thread.sleep(100);
        cf.makeCoffee(Beverage.BLACK_TEA, 3);

        // Coffee making takes time
        Thread.sleep(5000);
    }

    @Test
    public void testHappyCase_2() throws InterruptedException {
        CoffeeMaker cf = new CoffeeMaker(getConfiguration());
        // Adding a jitter to get deterministic output.
        Thread.sleep(100);
        cf.makeCoffee(Beverage.HOT_TEA, 0);
        // Adding a jitter to get deterministic output.
        Thread.sleep(100);
        cf.makeCoffee(Beverage.BLACK_TEA, 3);
        // Adding a jitter to get deterministic output.
        Thread.sleep(100);
        cf.makeCoffee(Beverage.GREEN_TEA, 1);
        // Adding a jitter to get deterministic output.
        Thread.sleep(100);
        cf.makeCoffee(Beverage.HOT_COFFEE, 2);


        // Coffee making takes time
        Thread.sleep(5000);
    }

    @Test
    public void testHappyCase_3() throws InterruptedException {
        CoffeeMaker cf = new CoffeeMaker(getConfiguration());
        cf.makeCoffee(Beverage.HOT_COFFEE, 0);
        // Adding a jitter to get deterministic output.
        Thread.sleep(100);
        cf.makeCoffee(Beverage.BLACK_TEA, 3);
        Thread.sleep(100);
        cf.makeCoffee(Beverage.GREEN_TEA, 1);
        Thread.sleep(100);
        cf.makeCoffee(Beverage.HOT_TEA, 2);


        // Coffee making takes time
        Thread.sleep(5000);
    }

    /*
     * Output :
     * black_tea cannot be prepared, Nozzle : 0 in use.
     * [hot_coffee is prepared]
     */
    @Test
    public void testSadCase_CustomerTryingToFetchBeverageFromSameNozzle() throws InterruptedException {
        CoffeeMaker cf = new CoffeeMaker(getConfiguration());
        cf.makeCoffee(Beverage.HOT_COFFEE, 0);
        Thread.sleep(100);
        cf.makeCoffee(Beverage.BLACK_TEA, 0);

        // Coffee making takes time
        Thread.sleep(5000);
    }

    /*
     * Output :
     * [hot_coffee is prepared]
     * [black_tea is prepared]
     */
    @Test
    public void testHappyCase_CustomerTryingToFetchBeverageFromSameNozzleButLater() throws InterruptedException {
        CoffeeMaker cf = new CoffeeMaker(getConfiguration());
        cf.makeCoffee(Beverage.HOT_COFFEE, 0);
        // 5 sec is long enough for beverage preparation.
        Thread.sleep(5000);
        cf.makeCoffee(Beverage.BLACK_TEA, 0);

        // Coffee making takes time
        Thread.sleep(5000);
    }
}
