package de.hsrm.mi.ssche003.monsterbuilder.akteur;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.abilityScore.AbilityScore;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.angriff.Angriff;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.savingThrow.SavingThrow;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.sprache.Sprache;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.zauber.Zauber;
import jakarta.persistence.ManyToMany;
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
    @PositiveOrZero //TODO: modulo 5 - custom validator @ValidMovement
    private byte geschwindigkeit_ft; //Geschwindigkeit in 5-Fuß Abstufungen
    @ManyToMany
    private Set<Sprache> sprachen = new HashSet<>();
    
    @ManyToMany
    protected Set<Zauber> alleZauber = new HashSet<>();

    @ManyToMany
    protected Set<Angriff> alleAngriffe = new HashSet<>();
    
    @ManyToMany
    protected Set<AbilityScore> abilityScores = new HashSet<>(); 
    

    public Set<Zauber> getAlleZauber() {
        return alleZauber;
    }

    public void setAlleZauber(Set<Zauber> alleZauber) {
        this.alleZauber = alleZauber;
    }

    public Set<Angriff> getAlleAngriffe() {
        return alleAngriffe;
    }

    public void setAlleAngriffe(Set<Angriff> alleAngriffe) {
        this.alleAngriffe = alleAngriffe;
    }

    public void addAngriff(Angriff angriff) {
        this.alleAngriffe.add(angriff);
    }

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


   public void setSprachen(Set<Sprache> sprachen) {
        this.sprachen = sprachen;
    }

    public void addSprache(Sprache sprache) {
        this.sprachen.add(sprache);
    }

    public void setAbilityScores(Set<AbilityScore> abilityScores) {
        this.abilityScores = abilityScores;
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
