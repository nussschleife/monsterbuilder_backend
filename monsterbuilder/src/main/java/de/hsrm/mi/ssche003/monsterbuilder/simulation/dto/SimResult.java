package de.hsrm.mi.ssche003.monsterbuilder.simulation.dto;

import de.hsrm.mi.ssche003.monsterbuilder.akteur.SimValue;

public class SimResult {
    String simID;
    int runden;
    SimValue value;
    String userSessionID;
    String message;

    public SimResult(String simID, String message) {
        this.simID = simID;
        this.message = message;
    }

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

    public String getUserSessionID() {
        return userSessionID;
    }

    public void setUserSessionID(String userSessionID) {
        this.userSessionID = userSessionID;
    }

    
}
