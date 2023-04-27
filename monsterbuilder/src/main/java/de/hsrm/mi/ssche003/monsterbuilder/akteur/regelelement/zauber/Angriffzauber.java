package de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.zauber;

import de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.schaden.Schadensart;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.schaden.Wuerfel;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Version;
import jakarta.validation.constraints.Min;

@Entity
public class Angriffzauber extends Zauber {
  //TODO: wie verbinden wir den ability score?

    @ManyToOne
    Schadensart schadensart;

    @Enumerated(EnumType.STRING)
    Wuerfel wuerfel;

    @Min(1)
    byte wuerfelanzahl;

    byte modifikator;

    public Schadensart getSchadensart() {
        return schadensart;
    }

    public void setSchadensart(Schadensart schadensart) {
        this.schadensart = schadensart;
    }

    public Wuerfel getWuerfel() {
        return wuerfel;
    }

    public void setWuerfel(Wuerfel wuerfel) {
        this.wuerfel = wuerfel;
    }

    public byte getWuerfelanzahl() {
        return wuerfelanzahl;
    }

    public void setWuerfelanzahl(byte wuerfelanzahl) {
        this.wuerfelanzahl = wuerfelanzahl;
    }

    public byte getModifikator() {
        return modifikator;
    }

    public void setModifikator(byte modifikator) {
        this.modifikator = modifikator;
    }

}
