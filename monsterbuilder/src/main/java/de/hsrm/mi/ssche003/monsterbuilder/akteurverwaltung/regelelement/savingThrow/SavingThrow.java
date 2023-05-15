package de.hsrm.mi.ssche003.monsterbuilder.akteurverwaltung.regelelement.savingThrow;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import de.hsrm.mi.ssche003.monsterbuilder.akteurverwaltung.Akteur;
import de.hsrm.mi.ssche003.monsterbuilder.akteurverwaltung.regelelement.Regelelement;
import de.hsrm.mi.ssche003.monsterbuilder.akteurverwaltung.regelelement.zauber.Effektzauber;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;

@Entity
public class SavingThrow extends Regelelement {

    @Enumerated(EnumType.STRING)
    private SaveType typ;
    int schwierigkeit;

    @OneToOne(mappedBy = "save") @JsonIgnore
    private Effektzauber effektzauber;

    @ManyToMany(mappedBy = "savingThrows") @JsonIgnore
    private Set<Akteur> alleAkteure = new HashSet<>();

    
    public Set<Akteur> getAlleAkteure() {
        return alleAkteure;
    }

    public void setAlleAkteure(Set<Akteur> alleCharaktere) {
        this.alleAkteure = alleCharaktere;
    }

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

    @Override @JsonIgnore
    public Regelelement getInstance() {
        return new SavingThrow();
    }

    @Override
    public Regelelement übernehmeBasisWerteVon(Regelelement element) {
        if(element instanceof SavingThrow) {
            SavingThrow save = (SavingThrow) element;
            this.setName(element.getName());
            this.schwierigkeit = save.getSchwierigkeit();
            this.typ = save.getTyp();
        }
        return this;
    }



    
}
