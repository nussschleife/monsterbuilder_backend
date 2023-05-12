package de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.effekt;

import de.hsrm.mi.ssche003.monsterbuilder.akteur.Akteur;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.Regelelement;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity @DiscriminatorValue("PRONE")
public class Prone extends Condition {
    private String name = "PRONE";
    @Override
    public Akteur wirkeCondition(Akteur gegner) {
        Condition copy = new Prone();
        copy.setDauer(dauer);
        copy.getBetroffeneAkteure().add(gegner);
        gegner.addCondition(copy); 
        gegner.setRuestungsklasse(gegner.getRuestungsklasse() - 2);
        return gegner;
    }

    @Override
    public void beendeCondition(Akteur gegner) {
        super.beendeCondition(gegner);
        gegner.setRuestungsklasse(gegner.getRuestungsklasse() + 2);
    }

    @Override
    public Regelelement getInstance() {
        return this;
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
