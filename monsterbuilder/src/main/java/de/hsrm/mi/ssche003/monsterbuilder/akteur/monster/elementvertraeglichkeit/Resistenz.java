package de.hsrm.mi.ssche003.monsterbuilder.akteur.monster.elementvertraeglichkeit;

import jakarta.persistence.Entity;

@Entity
public class Resistenz extends Elementvertraeglichkeit {

    @Override
    public int berechneSchadenNeu(int wert) {
        int value = wert -= this.wert;
        return value >= 0 ? value : 0;
    }
    
}
