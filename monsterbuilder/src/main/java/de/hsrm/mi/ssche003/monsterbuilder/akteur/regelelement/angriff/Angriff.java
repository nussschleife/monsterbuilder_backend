package de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.angriff;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import de.hsrm.mi.ssche003.monsterbuilder.akteur.monster.Monster;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.Regelelement;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.schaden.Schadensart;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.schaden.Wuerfel;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Version;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;

@Entity //TODO: ValidAngriff -> checkt werte mit level ab
public class Angriff extends Regelelement{
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Version
    private Long version = 1l;

    @ManyToOne
    Schadensart schadensart;

    @Enumerated(EnumType.STRING)
    Wuerfel wuerfel;

    @Min(1)
    byte wuerfelanzahl;

    byte schadenModifikator;

    byte angriffModifikator;

    @Positive @Min(5) @JsonIgnore
    byte reichweite_ft = 5;

    @ManyToMany(mappedBy = "alleAngriffe")
    private Set<Monster> alleMonster = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

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

    public byte getWuerfelanzahl() {
        return wuerfelanzahl;
    }

    public void setWuerfelanzahl(byte wuerfelanzahl) {
        this.wuerfelanzahl = wuerfelanzahl;
    }

    public byte getSchadenModifikator() {
        return schadenModifikator;
    }

    public void setSchadenModifikator(byte modifikator) {
        this.schadenModifikator = modifikator;
    }

    public byte getReichweite_ft() {
        return reichweite_ft;
    }

    public void setReichweite_ft(byte reichweite_ft) {
        this.reichweite_ft = reichweite_ft;
    }

    public byte getAngriffModifikator() {
        return angriffModifikator;
    }

    public void setAngriffModifikator(byte angriffModifikator) {
        this.angriffModifikator = angriffModifikator;
    }

    public Set<Monster> getAlleMonster() {
        return alleMonster;
    }

    public void setAlleMonster(Set<Monster> alleMonster) {
        this.alleMonster = alleMonster;
    }

    public void addMonster(Monster monster) {
        this.alleMonster.add(monster);
    }

}
