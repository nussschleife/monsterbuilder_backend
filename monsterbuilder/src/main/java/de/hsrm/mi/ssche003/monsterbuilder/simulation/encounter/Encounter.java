package de.hsrm.mi.ssche003.monsterbuilder.simulation.encounter;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Encounter {

    @Id
    private Long id; 

    @ElementCollection
    private Set<String> rundenBeschreibungen = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<String> getRundenBeschreibungen() {
        return rundenBeschreibungen;
    }

    public void setRundenBeschreibungen(Set<String> rundenBeschreibungen) {
        this.rundenBeschreibungen = rundenBeschreibungen;
    }



    

    
}
