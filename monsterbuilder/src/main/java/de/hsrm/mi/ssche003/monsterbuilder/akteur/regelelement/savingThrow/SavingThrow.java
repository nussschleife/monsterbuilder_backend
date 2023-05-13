package de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.savingThrow;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import de.hsrm.mi.ssche003.monsterbuilder.akteur.charakter.Charakter;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.monster.Monster;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.Regelelement;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.zauber.Effektzauber;
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
    private Set<Charakter> alleCharaktere = new HashSet<>();

    @ManyToMany(mappedBy = "savingThrows") @JsonIgnore
    private Set<Monster> alleMonster = new HashSet<>();
    
    public Set<Charakter> getAlleCharaktere() {
        return alleCharaktere;
    }

    public void setAlleCharaktere(Set<Charakter> alleCharaktere) {
        this.alleCharaktere = alleCharaktere;
    }

    public Set<Monster> getAlleMonster() {
        return alleMonster;
    }

    public void setAlleMonster(Set<Monster> alleMonster) {
        this.alleMonster = alleMonster;
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
