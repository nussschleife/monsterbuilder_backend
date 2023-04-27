package de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.zauber;

import java.util.HashSet;

import com.fasterxml.jackson.annotation.JsonIgnore;

import de.hsrm.mi.ssche003.monsterbuilder.akteur.charakter.Charakter;
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

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id;
    @Version @JsonIgnore
    private Long version = 1l;
    @Positive
    private byte level;
    private String beschreibung;
    @PositiveOrZero
    private byte reichweite;
    
    @ManyToMany(mappedBy = "alleZauber") @JsonIgnore
    private HashSet<@Valid Monster> alleMonster = new HashSet<>();

    public Long getId() {
        return id;
    }

    public Long getVersion() {
        return version;
    }

    public byte getLevel() {
        return level;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public byte getReichweite() {
        return reichweite;
    }

    public HashSet<Monster> getAlleMonster() {
        return alleMonster;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public void setLevel(byte level) {
        this.level = level;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    public void setReichweite(byte reichweite) {
        this.reichweite = reichweite;
    }

    public void setAlleMonster(HashSet<Monster> alleMonster) {
        this.alleMonster = alleMonster;
    }

    //TODO: haschode & equals
}
