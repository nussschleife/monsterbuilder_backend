package de.hsrm.mi.ssche003.monsterbuilder.simulation.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import de.hsrm.mi.ssche003.monsterbuilder.simulation.dto.simValue.SimValue;

public class SimResult {
    String simID;
    int runden;
    SimValue value;
    String nachricht = "";

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

    public void setValue(SimValue value) {
        this.value = value;
    }

    public String getNachricht() {
        if(this.nachricht.contains("Fehler"))
            return this.nachricht;
        return this.nachricht + "runden: "+ runden + " "+ value.toString();
    }

    @JsonIgnore 
    public SimValue getValue() {
        return value;
    }

    public void setNachricht(String nachricht) {
        this.nachricht = nachricht;
    }

}
