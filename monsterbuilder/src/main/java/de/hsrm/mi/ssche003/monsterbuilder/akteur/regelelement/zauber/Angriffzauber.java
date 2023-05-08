package de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.zauber;

import de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.angriff.WaffenAngriff;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.Akteur;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.Aktion;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.abilityScore.AbilityScoreName;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.schaden.Schadensart;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.schaden.Wuerfel;
import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Min;

@Entity @DiscriminatorValue("ANGRIFF")
public class Angriffzauber extends Zauber implements Aktion { 

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "angriffzauber_angriff", referencedColumnName = "id")
    private WaffenAngriff angriff;


    public int berechneSchaden() {
        return angriff.berechneSchaden();
    }

    public Akteur ausfuehren(Akteur gegner, int modifikator) {
        return angriff.ausfuehren(gegner, modifikator);
    }

    @Override
    public void ausführen() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'ausführen'");
    }

}
