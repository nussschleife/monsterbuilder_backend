package de.hsrm.mi.ssche003.monsterbuilder.akteurverwaltung.regelelement.condition;

import java.util.Collections;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import de.hsrm.mi.ssche003.monsterbuilder.akteurverwaltung.Akteur;
import de.hsrm.mi.ssche003.monsterbuilder.akteurverwaltung.regelelement.Regelelement;
import de.hsrm.mi.ssche003.monsterbuilder.akteurverwaltung.regelelement.angriff.Angriff;
import de.hsrm.mi.ssche003.monsterbuilder.akteurverwaltung.regelelement.zauber.Effektzauber;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Transient;

@Entity @DiscriminatorValue("PETRIFIED")
public class Petrified extends Condition{

    private String name = "PETRIFIED";
    @Transient @JsonIgnore
    private Set<Effektzauber> zauber;
    @Transient @JsonIgnore
    private Set<Angriff> angriffe;

    @Override
    public Akteur wirkeCondition(Akteur gegner) {
        Condition kopie = new Petrified();
        kopie.setDauer(dauer);
        kopie.getBetroffeneAkteure().add(gegner);
        gegner.addCondition(kopie); 
        zauber = gegner.getAlleZauber();
        angriffe = gegner.getAlleAngriffe();
        gegner.setAlleZauber(Collections.<Effektzauber>emptySet());
        gegner.setAlleAngriffe(Collections.<Angriff>emptySet());
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
