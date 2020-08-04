package com.dunzo.coffemaker.ifaces;

import com.dunzo.coffemaker.entities.Beverage;
import com.dunzo.coffemaker.entities.Nozzle;

public interface BeverageProcessor {
    public void process(Beverage beverage, Nozzle nozzle);
}
