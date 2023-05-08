package de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.effekt;

import de.hsrm.mi.ssche003.monsterbuilder.akteur.Akteur;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity @DiscriminatorValue("PRONE")
public class Prone extends Condition {
    private String name = "PRONE";
    @Override
    public void wirkeCondition(Akteur gegner) {
        Condition copy = new Prone();
        copy.setDauer(dauer);
        copy.getBetroffeneAkteure().add(gegner);
        gegner.addCondition(copy); 
        gegner.setRuestungsklasse(gegner.getRuestungsklasse() - 2);
    }

    @Override
    public void beendeCondition(Akteur gegner) {
        super.beendeCondition(gegner);
        gegner.setRuestungsklasse(gegner.getRuestungsklasse() + 2);
    }

}
