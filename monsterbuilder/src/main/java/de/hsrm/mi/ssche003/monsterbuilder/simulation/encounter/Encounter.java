package de.hsrm.mi.ssche003.monsterbuilder.simulation.encounter;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Encounter {

    @Id
    private String id; //id war simID
    //Monster
    //Charakter
    //paramter -> simvalue[]
    //durchschn. kampfrunden
    //bester effekt
    //TODO: Liste an Runden? -> wäre nice um entscheidungen nachzuvollziehen

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    
}
