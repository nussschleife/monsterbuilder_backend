package de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.sprache;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import de.hsrm.mi.ssche003.monsterbuilder.akteur.Akteur;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.Regelelement;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;

@Entity
public class Sprache extends Regelelement{ 

    @ManyToMany(mappedBy = "sprachen") @JsonIgnore
    private Set<Akteur> alleAkteure = new HashSet<>();
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Sprache other = (Sprache) obj;
        return true;
    }

    @Override @JsonIgnore
    public Sprache getInstance() {
        return new Sprache();
    }

    public Sprache übernehmeBasisWerteVon(Regelelement element) {
        this.setName(element.getName());
        return this;
    }

    public Set<Akteur> getAlleAkteure() {
        return alleAkteure;
    }

    public void setAlleAkteure(Set<Akteur> alleAkteure) {
        this.alleAkteure = alleAkteure;
    }


    public void addAkteur(Akteur akteur) {
        this.alleAkteure.add(akteur);
    }
}
