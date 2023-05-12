package de.hsrm.mi.ssche003.monsterbuilder.simulation.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import de.hsrm.mi.ssche003.monsterbuilder.akteur.simValue.SimValue;

public class SimResult {
    String simID;
    int runden;
    SimValue value;
    String nachricht;

    public SimResult(int runden, SimValue value, String simID) {
       this.simID = simID;
       this.runden = runden;
        this.value = value;
        
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

/*     public SimValue getValue() {
        return value;
    } */

    public void setValue(SimValue value) {
        this.value = value;
    }

    public String getNachricht() {
        return "runden: "+ runden + "value: "+ "noch kein tostr impl.";
    }
    @JsonIgnore //TODO: json kann level noch nicht verarbeiten
    public SimValue getValue() {
        return value;
    }

}
