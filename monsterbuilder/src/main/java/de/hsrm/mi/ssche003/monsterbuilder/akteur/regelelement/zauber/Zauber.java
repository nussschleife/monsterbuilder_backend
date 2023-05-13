package de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.zauber;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import de.hsrm.mi.ssche003.monsterbuilder.akteur.Akteur;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.AkteurAktion;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.charakter.Charakter;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.monster.Monster;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.Regelelement;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.abilityScore.AbilityScoreName;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

@DiscriminatorColumn(name="dtype",  discriminatorType = DiscriminatorType.STRING)
@Entity @Table(name="Zauber", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Zauber extends Regelelement implements AkteurAktion {

    @Positive
    private int level;

    private String beschreibung;

    @PositiveOrZero
    private int reichweite_ft;

    @Enumerated(EnumType.STRING)
    private AbilityScoreName abilityScoreName;
    
    @ManyToMany(mappedBy = "alleZauber") @JsonIgnore
    private Set<@Valid Monster> alleMonster = new HashSet<>();

    @ManyToMany(mappedBy = "alleZauber") @JsonIgnore
    private Set<@Valid Charakter> alleCharaktere = new HashSet<>();
    
    public void setAbilityScoreName(AbilityScoreName abilityScoreName) {
        this.abilityScoreName = abilityScoreName;
    }

    public Set<Charakter> getAlleCharaktere() {
        return alleCharaktere;
    }

    public void setAlleCharaktere(Set<Charakter> alleCharaktere) {
        this.alleCharaktere = alleCharaktere;
    }

    public int getLevel() {
        return level;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public int getReichweite_ft() {
        return reichweite_ft;
    }

    @JsonIgnore
    public Set<Monster> getAlleMonster() {
        return alleMonster;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    public void setReichweite_ft(int reichweite) {
        this.reichweite_ft = reichweite;
    }

    public void setAlleMonster(Set<Monster> alleMonster) {
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

    public AbilityScoreName getAbilityScoreName() {
        return abilityScoreName;
    }

    @Override
    public Akteur ausfuehren(Akteur gegner, int modifikator) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'ausfuehren'");
    }

    //TODO: hashode & equals
}
