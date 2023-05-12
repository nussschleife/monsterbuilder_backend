package de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.savingThrow;

import de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.Regelelement;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.zauber.Effektzauber;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Version;

@Entity
public class SavingThrow extends Regelelement {

    @Enumerated(EnumType.STRING)
    private SaveType typ;
    int schwierigkeit;

    @OneToOne(mappedBy = "save")
    private Effektzauber effektzauber;

    public int getSchwierigkeit() {
        return schwierigkeit;
    }

    public void setSchwierigkeit(int schwierigkeit) {
        this.schwierigkeit = schwierigkeit;
    }

    public SaveType getTyp() {
        return typ;
    }

    public void setTyp(SaveType typ) {
        this.typ = typ;
    }

    public Effektzauber getEffektzauber() {
        return effektzauber;
    }

    public void setEffektzauber(Effektzauber effektzauber) {
        this.effektzauber = effektzauber;
    }

    @Override
    public Regelelement getInstance() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getInstance'");
    }

    @Override
    public Regelelement übernehmeBasisWerteVon(Regelelement element) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'übernehmeBasisWerteVon'");
    }



    
}
