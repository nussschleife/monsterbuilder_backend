package de.hsrm.mi.ssche003.monsterbuilder.simulation.dto;

public class SimResponse {
    String simID;
    String simName;

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
    public String getSimName() {
        return simName;
    }
    public void setSimName(String simName) {
        this.simName = simName;
    }

    
    
}
