package de.hsrm.mi.ssche003.monsterbuilder.akteur.monster;

import java.util.ArrayList;
import java.util.HashSet;

import de.hsrm.mi.ssche003.monsterbuilder.IValidator;
import de.hsrm.mi.ssche003.monsterbuilder.Schadensart;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.Akteur;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.Alignment;
import de.hsrm.mi.ssche003.monsterbuilder.skill.Schadenszauber;
import de.hsrm.mi.ssche003.monsterbuilder.skill.Skill;
import de.hsrm.mi.ssche003.monsterbuilder.spielleiter.Spielleiter;
import de.hsrm.mi.ssche003.monsterbuilder.trait.StatusTrait;
import de.hsrm.mi.ssche003.monsterbuilder.trait.Trait;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;

@Entity
public class Monster extends Akteur{

    @GeneratedValue(strategy = GenerationType.IDENTITY) @Id
    private Long id;

    @Enumerated(EnumType.STRING)
    private Alignment alignment;
    //TODO: Regex
    private String bildpfad;

    @ManyToOne
    private Spielleiter spielleiter;

    @ManyToMany
    private HashSet<Trait> alleTraits = new HashSet<>();

    @ManyToMany
    private HashSet<Skill> skills = new HashSet<>();

    @Transient
    private ArrayList<Schadensart> weaknesses = new ArrayList<>(); 
    
    public boolean validiere(IValidator validator) {
        return validator.validiere(this);
    }

    public Alignment getAlignment() {
        return alignment;
    }

    public String getBildpfad() {
        return bildpfad;
    }

    public Spielleiter getSpielleiter() {
        return spielleiter;
    }

    public HashSet<Trait> getAlleTraits() {
        return alleTraits;
    }

    public HashSet<Skill> getSkills() {
        return skills;
    }

    public void addSchadenszauber(Schadenszauber zauber) {
        if(!weaknesses.contains(zauber.getTyp())) {
            this.zauber.add(zauber);
        }
    }

    public void addStatusTrait(StatusTrait trait) {
        if(trait.bestimmtSchwäche())
            this.weaknesses.add(trait.getTyp());
    }

    
}
