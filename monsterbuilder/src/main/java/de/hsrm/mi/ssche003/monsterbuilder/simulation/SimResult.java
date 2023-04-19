package de.hsrm.mi.ssche003.monsterbuilder.simulation;

import de.hsrm.mi.ssche003.monsterbuilder.akteur.SimValue;

public class SimResult {
    String simID;
    int runden;
    SimValue value;

    public SimResult(int runden) {
        this.runden = runden;
    }

    public String getSimID() {
        return simID;
    }

    public void setSimID(String simID) {
        this.simID = simID;
    }

    public int getRunden() {
        return runden;
    }

    public void setRunden(int runden) {
        this.runden = runden;
    }

    public SimValue getValue() {
        return value;
    }

    public void setValue(SimValue value) {
        this.value = value;
    }

    
}
