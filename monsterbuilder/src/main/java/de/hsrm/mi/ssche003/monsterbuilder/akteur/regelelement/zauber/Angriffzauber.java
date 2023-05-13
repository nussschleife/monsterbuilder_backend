package de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.zauber;

import de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.angriff.WaffenAngriff;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.AkteurAktion;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.Akteur;
import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity @DiscriminatorValue("ANGRIFF")
public class Angriffzauber extends Zauber implements AkteurAktion { 

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "angriffzauber_angriff", referencedColumnName = "id")
    private WaffenAngriff angriff;


    public int berechneSchaden() {
        return angriff.berechneSchaden();
    }

    public Akteur ausfuehren(Akteur gegner, int modifikator) {
        return angriff.ausfuehren(gegner, modifikator);
    }

}
