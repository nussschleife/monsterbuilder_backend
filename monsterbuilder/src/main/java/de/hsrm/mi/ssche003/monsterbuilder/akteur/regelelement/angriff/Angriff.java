package de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.angriff;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import de.hsrm.mi.ssche003.monsterbuilder.akteur.monster.Monster;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.Regelelement;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.schaden.Schadensart;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.schaden.Wuerfel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Version;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;

@Entity //TODO: ValidAngriff -> checkt werte mit level ab
public class Angriff extends Regelelement{
    
    @ManyToOne
    Schadensart schadensart;

    @Enumerated(EnumType.STRING)
    Wuerfel wuerfel;

    @Min(1)
    int wuerfelanzahl;

    int schadenModifikator;

    int angriffModifikator;

    @Positive @Min(5) @JsonIgnore
    int reichweite_ft = 5;

    @ManyToMany(mappedBy = "alleAngriffe") @JsonIgnore
    private Set<Monster> alleMonster = new HashSet<>();

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

    public int getWuerfelanzahl() {
        return wuerfelanzahl;
    }

    public void setWuerfelanzahl(int wuerfelanzahl) {
        this.wuerfelanzahl = wuerfelanzahl;
    }

    public int getSchadenModifikator() {
        return schadenModifikator;
    }

    public void setSchadenModifikator(int modifikator) {
        this.schadenModifikator = modifikator;
    }

    public int getReichweite_ft() {
        return reichweite_ft;
    }

    public void setReichweite_ft(int reichweite_ft) {
        this.reichweite_ft = reichweite_ft;
    }

    public int getAngriffModifikator() {
        return angriffModifikator;
    }

    public void setAngriffModifikator(int angriffModifikator) {
        this.angriffModifikator = angriffModifikator;
    }

    @JsonIgnore
    public Set<Monster> getAlleMonster() {
        return alleMonster;
    }

    public void setAlleMonster(Set<Monster> alleMonster) {
        this.alleMonster = alleMonster;
    }

    public void addMonster(Monster monster) {
        this.alleMonster.add(monster);
    }

    @Override @JsonIgnore
    public Regelelement getInstance() {
        return new Angriff();
    }

    @Override
    public Angriff übernehmeBasisWerteVon(Regelelement element) {
        if(element instanceof Angriff) {
            Angriff angriff = (Angriff) element;
            this.schadenModifikator = angriff.getSchadenModifikator();
            this.angriffModifikator = angriff.getAngriffModifikator();
            this.wuerfel = angriff.getWuerfel();
            this.reichweite_ft = angriff.getReichweite_ft();
            this.schadensart = angriff.getSchadensart();
            this.wuerfelanzahl = angriff.getWuerfelanzahl();
        }
        return this;
    }

}
