package de.hsrm.mi.ssche003.monsterbuilder.akteur;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.abilityScore.AbilityScore;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.savingThrow.SavingThrow;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.zauber.Zauber;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.OneToMany;
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
    @PositiveOrZero //TODO: modulo 5 - custom validator @ValidMovement
    private byte geschwindigkeit_ft; //Geschwindigkeit in 5-Fuß Abstufungen
    @ManyToMany
    private Set<Sprache> sprachen = new HashSet<>();
    
   /* @ManyToMany
    protected HashSet<Zauber> zauber = new HashSet<>();
    */
    @ManyToMany
    private Set<AbilityScore> abilityScores = new HashSet<>(); 
    /*
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

    public Set<Sprache> getSprachen() {
        return sprachen;
    }

    public Set<AbilityScore> getAbilityScores() {
        return abilityScores;
    }

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
*/
    public Akteur setAbilityScore(Set<AbilityScore> score) {
        this.abilityScores = score;
        return this;
    }

    public Akteur addAbilityScore(AbilityScore score) {
        if(this.abilityScores.contains(score))
            this.abilityScores.remove(score);
        this.abilityScores.add(score);
        return this;
    }

}
