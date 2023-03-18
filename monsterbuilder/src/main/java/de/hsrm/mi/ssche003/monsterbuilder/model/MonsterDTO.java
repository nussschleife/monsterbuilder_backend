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

    public MonsterDTO(){

    }

    public MonsterDTO(String name) {
        this.name = name;
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

    public MonsterDTO setName(String name) {
        this.name = name;
        return this;
    }

    public int getLebenspunkte() {
        return lebenspunkte;
    }

    public MonsterDTO setLebenspunkte(int lebenspunkte) {
        this.lebenspunkte = lebenspunkte;
        return this;
    }

    public byte getRuestungsklasse() {
        return ruestungsklasse;
    }

    public MonsterDTO setRuestungsklasse(byte ruestungsklasse) {
        this.ruestungsklasse = ruestungsklasse;
        return this;
    }

    public byte getGeschwindigkeit_ft() {
        return geschwindigkeit_ft;
    }

    public MonsterDTO setGeschwindigkeit_ft(byte geschwindigkeit_ft) {
        this.geschwindigkeit_ft = geschwindigkeit_ft;
        return this;
    }

    public Alignment getAlignment() {
        return alignment;
    }

    public MonsterDTO setAlignment(Alignment alignment) {
        this.alignment = alignment;
        return this;
    }

    public Sprache[] getSprachen() {
        return sprachen;
    }

    public MonsterDTO setSprachen(Sprache[] sprachen) {
        this.sprachen = sprachen;
        return this;
    }
    
    
}
