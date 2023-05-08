package de.hsrm.mi.ssche003.monsterbuilder.akteur.monster.elementvertraeglichkeit;

import jakarta.persistence.Entity;

@Entity
public class Schwaeche extends Elementvertraeglichkeit{

    @Override
    public int berechneSchadenNeu(int wert) {
        return wert += this.wert;
    }
    
}
