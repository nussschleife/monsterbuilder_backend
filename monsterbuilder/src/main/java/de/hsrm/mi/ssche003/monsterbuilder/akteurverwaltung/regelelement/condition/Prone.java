package de.hsrm.mi.ssche003.monsterbuilder.akteurverwaltung.regelelement.condition;

import com.fasterxml.jackson.annotation.JsonIgnore;

import de.hsrm.mi.ssche003.monsterbuilder.akteurverwaltung.Akteur;
import de.hsrm.mi.ssche003.monsterbuilder.akteurverwaltung.regelelement.Regelelement;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity @DiscriminatorValue("PRONE")
public class Prone extends Condition {
    private String name = "PRONE";
    
    @Override
    public Akteur wirkeCondition(Akteur gegner) {
        Condition kopie = new Prone();
        kopie.setDauer(dauer);
        kopie.getBetroffeneAkteure().add(gegner);
        gegner.addCondition(kopie); 
        gegner.setRuestungsklasse(gegner.getRuestungsklasse()-2);
        return gegner;
    }

    @Override
    public void beendeCondition(Akteur gegner) {
        super.beendeCondition(gegner);
        gegner.setRuestungsklasse(gegner.getRuestungsklasse()+2);
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
