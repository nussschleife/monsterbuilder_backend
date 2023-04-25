package de.hsrm.mi.ssche003.monsterbuilder.akteur;

import java.util.HashSet;

import com.fasterxml.jackson.annotation.JsonIgnore;

import de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.abilityScore.AbilityScore;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.savingThrow.SavingThrow;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.zauber.Zauber;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

@MappedSuperclass	
public class Akteur {

    @Version @JsonIgnore
    private Long version;
    @NotNull @NotEmpty
    private String name;
    @PositiveOrZero
    private int lebenspunkte;
    @Positive
    private byte ruestungsklasse;
    @PositiveOrZero //TODO: modulo 5
    private byte geschwindigkeit_ft; //Geschwindigkeit in 5-Fuß Abstufungen
    @Enumerated(EnumType.STRING)
    private HashSet<Sprache> sprachen = new HashSet<>();
    
   /* @ManyToMany
    protected HashSet<Zauber> zauber = new HashSet<>();

    @OneToOne @Valid
    private AbilityScore abilityScore; 

    public HashSet<Zauber> getZauber() {
        return zauber;
    }*/

    public Long getVersion() {
        return version;
    }

    public String getName() {
        return name;
    }

    public int getLebenspunkte() {
        return lebenspunkte;
    }

    public byte getRuestungsklasse() {
        return ruestungsklasse;
    }

    public byte getGeschwindigkeit_ft() {
        return geschwindigkeit_ft;
    }

    public HashSet<Sprache> getSprachen() {
        return sprachen;
    }

    /*public AbilityScore getAbilityScore() {
        return abilityScore;
    }*/

    public Akteur setName(String name) {
        this.name = name;
        return this;
    }

    public Akteur setLebenspunkte(int lebenspunkte) {
        this.lebenspunkte = lebenspunkte;
        return this;
    }

    public Akteur setRuestungsklasse(byte ruestungsklasse) {
        this.ruestungsklasse = ruestungsklasse;
        return this;
    }

    public Akteur setGeschwindigkeit_ft(byte geschwindigkeit_ft) {
        this.geschwindigkeit_ft = geschwindigkeit_ft;
        return this;
    }

    public int wuerfleInitiative() {
        return (int) Math.random()*20; //+ini modifier wenn nötig
    }

    public boolean ausweichen(SavingThrow event, int schwierigkeit) {
        return false;
    }

   /* public Akteur setZauber(HashSet<Zauber> zauber) {
        this.zauber = zauber;
        return this;
    }

    public Akteur setAbilityScore(AbilityScore abilityScore) {
        this.abilityScore = abilityScore;
        return this;
    }*/

}
