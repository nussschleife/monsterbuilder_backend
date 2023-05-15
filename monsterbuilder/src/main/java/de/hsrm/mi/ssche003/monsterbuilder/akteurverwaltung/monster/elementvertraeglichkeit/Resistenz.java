package de.hsrm.mi.ssche003.monsterbuilder.akteurverwaltung.monster.elementvertraeglichkeit;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity @DiscriminatorValue("r")
public class Resistenz extends Elementvertraeglichkeit {

    @Override
    public int berechneSchadenNeu(int wert) {
        int value = wert -= this.wert;
        return value >= 0 ? value : 0;
    }
    
}
