package de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.sprache;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import de.hsrm.mi.ssche003.monsterbuilder.akteur.charakter.Charakter;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.monster.Monster;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.Regelelement;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;

@Entity
public class Sprache extends Regelelement{ 

    @ManyToMany(mappedBy = "sprachen") @JsonIgnore
    private Set<Monster> alleMonster = new HashSet<>();

    @ManyToMany(mappedBy = "sprachen") @JsonIgnore
    private Set<Charakter> alleCharaktere = new HashSet<>();

    
    
    @JsonIgnore
    public Set<Monster> getAlleMonster() {
        return alleMonster;
    }

    public void setAlleMonster(Set<Monster> alleMonster) {
        this.alleMonster = alleMonster;
    }
    
    public void addMonster(Monster monster) {
        this.alleMonster.add(monster);
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Sprache other = (Sprache) obj;
        if (alleMonster == null) {
            if (other.alleMonster != null)
                return false;
        } else if (!alleMonster.equals(other.alleMonster))
            return false;
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

    public Set<Charakter> getAlleCharaktere() {
        return alleCharaktere;
    }

    public void setAlleCharaktere(Set<Charakter> alleCharaktere) {
        this.alleCharaktere = alleCharaktere;
    }

}
