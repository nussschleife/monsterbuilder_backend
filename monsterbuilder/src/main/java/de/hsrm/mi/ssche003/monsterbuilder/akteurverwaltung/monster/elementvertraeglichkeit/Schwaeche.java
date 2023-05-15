package de.hsrm.mi.ssche003.monsterbuilder.akteurverwaltung.monster.elementvertraeglichkeit;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity @DiscriminatorValue("s")
public class Schwaeche extends Elementvertraeglichkeit{

    @Override
    public int berechneSchadenNeu(int wert) {
        return wert += this.wert;
    }
    
}
