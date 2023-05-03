package de.hsrm.mi.ssche003.monsterbuilder.simulation.encounter;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

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
    @OneToMany(mappedBy = "encounter")
    private Set<Runde> alleRunden = new HashSet<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Set<Runde> getAlleRunden() {
        return alleRunden;
    }

    public void setAlleRunden(Set<Runde> alleRunden) {
        this.alleRunden = alleRunden;
    }

    

    
}
