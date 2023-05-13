package de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.condition;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
        
        Condition kopie = new Enfeebled();
        kopie.setDauer(dauer);
        kopie.getBetroffeneAkteure().add(gegner);
        gegner.addCondition(kopie); 
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

    @Override @JsonIgnore
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
