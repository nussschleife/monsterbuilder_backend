package de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.zauber;

import java.util.HashSet;

import com.fasterxml.jackson.annotation.JsonIgnore;

import de.hsrm.mi.ssche003.monsterbuilder.akteur.monster.Monster;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.Regelelement;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.persistence.Version;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

@Entity @Table(name="Zauber", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
public class Zauber extends Regelelement{

    @Positive
    private byte level;

    private String beschreibung;

    @PositiveOrZero
    private byte reichweite_ft;
    
    @ManyToMany(mappedBy = "alleZauber") @JsonIgnore
    private HashSet<@Valid Monster> alleMonster = new HashSet<>();

    public byte getLevel() {
        return level;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public byte getReichweite_ft() {
        return reichweite_ft;
    }

    @JsonIgnore
    public HashSet<Monster> getAlleMonster() {
        return alleMonster;
    }

    public void setLevel(byte level) {
        this.level = level;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    public void setReichweite_ft(byte reichweite) {
        this.reichweite_ft = reichweite;
    }

    public void setAlleMonster(HashSet<Monster> alleMonster) {
        this.alleMonster = alleMonster;
    }

    @Override @JsonIgnore
    public Regelelement getInstance() {
        return new Zauber();
    }

    @Override
    public Zauber übernehmeBasisWerteVon(Regelelement element) {
        if(element instanceof Zauber) {
            Zauber zauber = (Zauber) element;
            this.level = zauber.getLevel();
            this.beschreibung = zauber.getBeschreibung();
            this.reichweite_ft = zauber.getReichweite_ft();
        }
        return this;
    }

    //TODO: haschode & equals
}
