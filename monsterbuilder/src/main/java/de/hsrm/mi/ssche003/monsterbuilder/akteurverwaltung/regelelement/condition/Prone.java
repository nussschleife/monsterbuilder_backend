package de.hsrm.mi.ssche003.monsterbuilder.akteurverwaltung.regelelement.condition;

import com.fasterxml.jackson.annotation.JsonIgnore;

import de.hsrm.mi.ssche003.monsterbuilder.akteurverwaltung.Akteur;
import de.hsrm.mi.ssche003.monsterbuilder.akteurverwaltung.regelelement.Regelelement;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Transient;

@Entity @DiscriminatorValue("PRONE")
public class Prone extends Condition {
    private String name = "PRONE";
    
    @Transient
    int ruestungsklasse;

    @Override
    public Akteur wirkeCondition(Akteur gegner) {
        Condition kopie = new Prone();
        kopie.setDauer(dauer);
        kopie.getBetroffeneAkteure().add(gegner);
        gegner.addCondition(kopie); 
        gegner.setRuestungsklasse(0);
        return gegner;
    }

    @Override
    public void beendeCondition(Akteur gegner) {
        super.beendeCondition(gegner);
        gegner.setRuestungsklasse(ruestungsklasse);
    }

    @Override @JsonIgnore
    public Regelelement getInstance() {
        return new Prone();
    }

    @Override
    public Regelelement übernehmeBasisWerteVon(Regelelement element) {
        if(element instanceof Prone) {
            return this;
        }
        return null;
    }

    public String getName() {
        return name;
    }

}
