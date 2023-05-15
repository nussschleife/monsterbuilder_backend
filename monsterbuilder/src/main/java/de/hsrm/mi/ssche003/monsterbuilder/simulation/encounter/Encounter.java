package de.hsrm.mi.ssche003.monsterbuilder.simulation.encounter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.hsrm.mi.ssche003.monsterbuilder.akteur.Akteur;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Encounter {

    @Id
    private Long id; 

   // private List<Akteur> akteure = new ArrayList<>();
    
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
