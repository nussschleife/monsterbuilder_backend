package de.hsrm.mi.ssche003.monsterbuilder.akteurverwaltung.regelelement.zauber;

import de.hsrm.mi.ssche003.monsterbuilder.akteurverwaltung.Akteur;
import de.hsrm.mi.ssche003.monsterbuilder.akteurverwaltung.regelelement.angriff.Angriff;
import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity @DiscriminatorValue("ANGRIFF")
public class Angriffzauber extends Zauber { 

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "angriffzauber_angriff", referencedColumnName = "id")
    private Angriff angriff;


    public int berechneSchaden() {
        return angriff.berechneSchaden();
    }

    public Akteur ausfuehren(Akteur gegner, int modifikator) {
        return angriff.ausfuehren(gegner, modifikator);
    }

}
