package de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.effekt;

import de.hsrm.mi.ssche003.monsterbuilder.akteur.Akteur;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.Regelelement;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.abilityScore.AbilityScoreName;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity @DiscriminatorValue("ENFEEBLED")
public class Enfeebled extends Condition {
    private String name = "ENFEEBLED";
    @Override
    public Akteur wirkeCondition(Akteur gegner) {
        Condition copy = new Enfeebled();
        copy.setDauer(dauer);
        copy.getBetroffeneAkteure().add(gegner);
        gegner.addCondition(copy); 
        gegner.getAbilityScores().forEach((abilityscore) -> {
            if(abilityscore.getScoreName() == AbilityScoreName.STRENGTH) {
                abilityscore.setScore(abilityscore.getScore() - 2);
            }
        });
        return gegner;
    }

    @Override
    public void beendeCondition(Akteur gegner) {
        super.beendeCondition(gegner);
        gegner.getAbilityScores().forEach((abilityscore) -> {
            if(abilityscore.getScoreName() == AbilityScoreName.STRENGTH) {
                abilityscore.setScore(abilityscore.getScore() + 2);
            }
        });
    }

    @Override
    public Regelelement getInstance() {
        return this;
    }

    @Override
    public Regelelement übernehmeBasisWerteVon(Regelelement element) {
        if(element instanceof Enfeebled) {
            return this;
        }
        return null;
    }

    public String getName() {
        return name;
    }

    
}
