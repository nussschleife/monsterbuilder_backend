package de.hsrm.mi.ssche003.monsterbuilder.model;

import jakarta.validation.constraints.PositiveOrZero;

import jakarta.validation.constraints.Positive;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.Alignment;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.Sprache;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public class MonsterDTO {
    //TODO: Traits usw per id uebergeben vom frontend.
    //TODO: schrittweise konstruktor wie ichs damals bei vanessa gesehen habe?

    public MonsterDTO(){

    }

    public Long id;

    @NotNull @NotEmpty
    private String name;

    @PositiveOrZero
    private int lebenspunkte;

    @Positive
    private byte ruestungsklasse;

    @PositiveOrZero
    private byte geschwindigkeit_ft; //Geschwindigkeit in 5-Fuß Abstufungen
    
    @Enumerated(EnumType.STRING)
    Alignment alignment;
    
    private Sprache[] sprachen;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLebenspunkte() {
        return lebenspunkte;
    }

    public void setLebenspunkte(int lebenspunkte) {
        this.lebenspunkte = lebenspunkte;
    }

    public byte getRuestungsklasse() {
        return ruestungsklasse;
    }

    public void setRuestungsklasse(byte ruestungsklasse) {
        this.ruestungsklasse = ruestungsklasse;
    }

    public byte getGeschwindigkeit_ft() {
        return geschwindigkeit_ft;
    }

    public void setGeschwindigkeit_ft(byte geschwindigkeit_ft) {
        this.geschwindigkeit_ft = geschwindigkeit_ft;
    }

    public Alignment getAlignment() {
        return alignment;
    }

    public void setAlignment(Alignment alignment) {
        this.alignment = alignment;
    }

    public Sprache[] getSprachen() {
        return sprachen;
    }

    public void setSprachen(Sprache[] sprachen) {
        this.sprachen = sprachen;
    }
    
    
}
