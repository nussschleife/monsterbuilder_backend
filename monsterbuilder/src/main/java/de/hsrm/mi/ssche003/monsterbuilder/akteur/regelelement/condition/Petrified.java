package de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.condition;

import java.util.Collections;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import de.hsrm.mi.ssche003.monsterbuilder.akteur.Akteur;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.Regelelement;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.angriff.WaffenAngriff;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.zauber.Zauber;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Transient;

@Entity
public class Petrified extends Condition{

    private String name = "PETRIFIED";
    @Transient @JsonIgnore
    private Set<Zauber> zauber;
    @Transient @JsonIgnore
    private Set<WaffenAngriff> angriffe;

    @Override
    public Akteur wirkeCondition(Akteur gegner) {
        Condition kopie = new Petrified();
        kopie.setDauer(dauer);
        kopie.getBetroffeneAkteure().add(gegner);
        gegner.addCondition(kopie); 
        zauber = gegner.getAlleZauber();
        angriffe = gegner.getAlleAngriffe();
        gegner.setAlleZauber(Collections.<Zauber>emptySet());
        gegner.setAlleAngriffe(Collections.<WaffenAngriff>emptySet());
        return gegner;
    }

    @Override
    public void beendeCondition(Akteur gegner){
        gegner.getConditions().remove(this); this.betroffeneAkteure.remove(gegner);
        gegner.setAlleAngriffe(angriffe);
        gegner.setAlleZauber(zauber);
    }


    @Override @JsonIgnore
    public Regelelement getInstance() {
        return this;
    }

    @Override
    public Regelelement übernehmeBasisWerteVon(Regelelement element) {
        setName(element.getName());
        return this;
    }
    
}
