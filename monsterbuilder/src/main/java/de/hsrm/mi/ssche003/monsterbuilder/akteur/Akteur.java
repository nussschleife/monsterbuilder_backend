package de.hsrm.mi.ssche003.monsterbuilder.akteur;

import java.util.HashSet;

import com.fasterxml.jackson.annotation.JsonIgnore;

import de.hsrm.mi.ssche003.monsterbuilder.akteur.abilityScore.AbilityScore;
import de.hsrm.mi.ssche003.monsterbuilder.validation.IValidator;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.zauber.Zauber;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Version;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

@MappedSuperclass	
public class Akteur {
    @Id @GeneratedValue @JsonIgnore
    private Long id;
    @Version
    private Long version;
    @NotNull @NotEmpty
    private String name;
    @PositiveOrZero
    private byte lebenspunkte;
    @Positive
    private byte ruestungsklasse;
    @PositiveOrZero //TODO: modulo 5
    private byte geschwindigkeit_ft; //Geschwindigkeit in 5-Fuß Abstufungen
    @Enumerated(EnumType.STRING)
    private HashSet<Sprache> sprachen = new HashSet<>();
    
    @ManyToMany
    protected HashSet<Zauber> zauber = new HashSet<>();

    @OneToOne @Valid
    private AbilityScore abilityScore;

    public HashSet<Zauber> getZauber() {
        return zauber;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLebenspunkte(byte lebenspunkte) {
        this.lebenspunkte = lebenspunkte;
    }

    public void setRuestungsklasse(byte ruestungsklasse) {
        this.ruestungsklasse = ruestungsklasse;
    }

    public void setGeschwindigkeit_ft(byte geschwindigkeit_ft) {
        this.geschwindigkeit_ft = geschwindigkeit_ft;
    }

    public void setZauber(HashSet<Zauber> zauber) {
        this.zauber = zauber;
    }

    public void setAbilityScore(AbilityScore abilityScore) {
        this.abilityScore = abilityScore;
    }

}
