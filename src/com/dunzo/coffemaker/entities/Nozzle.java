package com.dunzo.coffemaker.entities;

import com.dunzo.coffemaker.entities.exceptions.NozelInUse;

import java.util.Collection;

public class Nozzle {
    int nozzleNumber;

    public Nozzle(int nozzleNumber) {
        this.nozzleNumber = nozzleNumber;
    }

    boolean isNozelInUse = false;

    public synchronized void useNozel() {
        if (isNozelInUse) {
            throw new NozelInUse();
        }

        isNozelInUse = true;
    }

    public synchronized void freeNozel() {
        isNozelInUse = false;
    }

    public String getNozzleID() {
        return Integer.toString(nozzleNumber);
    }
}
