package de.hsrm.mi.ssche003.monsterbuilder.simulation.dto;

public class SimResponse {
    String simID;

    public SimResponse() {}
    public SimResponse(String id) {
        super();
        this.simID = id;
    }

    public String getSimID() {
        return simID;
    }

    public void setSimID(String simID) {
        this.simID = simID;
    }

    
}
