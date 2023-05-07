package de.hsrm.mi.ssche003.monsterbuilder.simulation.encounter;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.ElementCollection;
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
    @ElementCollection
    private Set<String> rundenBeschreibungen = new HashSet<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Set<String> getRundenBeschreibungen() {
        return rundenBeschreibungen;
    }

    public void setRundenBeschreibungen(Set<String> rundenBeschreibungen) {
        this.rundenBeschreibungen = rundenBeschreibungen;
    }



    

    
}
